package cn.lichuachua.mp.mpserver.service.impl;

import cn.lichuachua.mp.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp.mpserver.entity.Article;
import cn.lichuachua.mp.mpserver.entity.ArticleComment;
import cn.lichuachua.mp.mpserver.entity.User;
import cn.lichuachua.mp.mpserver.enums.ArticleStatusEnum;
import cn.lichuachua.mp.mpserver.enums.CommentEnum;
import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp.mpserver.exception.ArticleCommentException;
import cn.lichuachua.mp.mpserver.exception.ArticleException;
import cn.lichuachua.mp.mpserver.form.ArticleCommentForm;
import cn.lichuachua.mp.mpserver.service.IArticleCommentService;
import cn.lichuachua.mp.mpserver.vo.ArticleCommentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleCommentServiceImpl extends BaseServiceImpl<ArticleComment, String> implements IArticleCommentService{

    @Autowired
    private ArticleServiceImpl articleService;

    @Autowired
    private UserServiceImpl userService;

    /**
     * 发布评论
     * @param articleCommentForm
     * @param userId
     */
    @Override
    public void publish(ArticleCommentForm articleCommentForm, String userId){
        ArticleComment articleComment = new ArticleComment();
        articleComment.setCommentUserId(userId);
        articleComment.setContent(articleCommentForm.getContent());
        articleComment.setParentId(articleCommentForm.getParentId());
        articleComment.setCreatedAt(new Date());
        articleComment.setUpdatedAt(new Date());
        articleComment.setStatus(CommentEnum.COMMENT_EXIT.getStatus());
        /**
         * 如果是根评论的话检测该文章是否存在
         */
        Article article = new Article();
        article.setStatus(ArticleStatusEnum.NORMAL.getStatus());
        article.setArticleId(articleCommentForm.getParentId());
        Optional<Article> optionalCircle = articleService.selectOne(Example.of(article));
        if (optionalCircle.isPresent()){
            articleComment.setDepth(CommentEnum.DEPTH_ZERO.getStatus());
            articleComment.setThread(CommentEnum.SEPARATOR.getSymbol()+articleCommentForm.getParentId());
        }else {
            /**
             * 非根评论，检测该父评论是否存在
             */
            Optional<ArticleComment> optionalCircleComment =selectByKey(articleComment.getParentId());
            if (optionalCircleComment.isPresent()){
                articleComment.setDepth(optionalCircleComment.get().getDepth()+CommentEnum.DEPTH_ADD.getStatus());
                articleComment.setThread(optionalCircleComment.get().getThread()+CommentEnum.SEPARATOR.getSymbol()+articleCommentForm.getParentId());
            }else {
                throw new ArticleException(ErrorCodeEnum.COMMENT_NO_EXIT);
            }
        }
        save(articleComment);
    }

    /**
     * 删除评论
     * @param commentId
     * @param userId
     */
    @Override
    public void deleteComment(String commentId, String userId){

        ArticleComment articleComment = new ArticleComment();
        articleComment.setCommentUserId(userId);
        articleComment.setCommentId(commentId);
        articleComment.setStatus(CommentEnum.COMMENT_EXIT.getStatus());
        Optional<ArticleComment> optionalCircleComment = selectOne(Example.of(articleComment));
        /**
         * 查看评论是否存在
         */
        if (optionalCircleComment.isPresent()){
            ArticleComment articleComment1 = new ArticleComment();
            articleComment1.setCommentId(optionalCircleComment.get().getCommentId());
            articleComment1.setCommentUserId(optionalCircleComment.get().getCommentUserId());
            articleComment1.setDepth(optionalCircleComment.get().getDepth());
            articleComment1.setThread(optionalCircleComment.get().getThread());
            articleComment1.setParentId(optionalCircleComment.get().getParentId());
            articleComment1.setStatus(CommentEnum.COMMENT_NO_EXIT.getStatus());
            articleComment1.setCreatedAt(optionalCircleComment.get().getCreatedAt());
            articleComment1.setUpdatedAt(new Date());
            articleComment1.setContent(optionalCircleComment.get().getContent());
            update(articleComment1);
            /**
             * 取出所有的评论
             * 查看其路径中是否有将要删除的Id，只要包含立即删除
             */
            List<ArticleComment> articleCommentList =selectAll();
            for (ArticleComment articleComment2 : articleCommentList ) {
                String thread= articleComment2.getThread();
                if (thread.indexOf(commentId)>0){
                    ArticleComment articleComment3 = new ArticleComment();
                    articleComment3.setCommentId(articleComment2.getCommentId());
                    articleComment3.setCommentUserId(articleComment2.getCommentUserId());
                    articleComment3.setDepth(articleComment2.getDepth());
                    articleComment3.setThread(articleComment2.getThread());
                    articleComment3.setParentId(articleComment2.getParentId());
                    articleComment3.setStatus(CommentEnum.COMMENT_NO_EXIT.getStatus());
                    articleComment3.setCreatedAt(articleComment2.getCreatedAt());
                    articleComment3.setUpdatedAt(new Date());
                    articleComment3.setContent(articleComment2.getContent());
                    update(articleComment3);
                }
            }
        }else {
            throw new ArticleCommentException(ErrorCodeEnum.COMMENT_NO_EXIT);
        }
    }

    /**
     * 查看评论列表
     * @param articleId
     * @return
     */
    @Override
    public List<ArticleCommentVO> queryComment (String articleId) {
        /**
         * 判断文章是否存在
         */
        Article article = new Article();
        article.setArticleId(articleId);
        article.setStatus(ArticleStatusEnum.NORMAL.getStatus());
        Optional<Article> articleOptional = articleService.selectOne(Example.of(article));
        if (!articleOptional.isPresent()){
            throw new ArticleException(ErrorCodeEnum.ARTICLE_NO_EXIT);
        }else {
            List<ArticleComment> articleCommentList = selectAll();
            List<ArticleCommentVO> articleCommentVOList = new ArrayList<>();
            for (ArticleComment articleComment : articleCommentList ) {

                String thread = articleComment.getThread();
                if ((thread.indexOf(articleId) > 0) && articleComment.getStatus().equals(CommentEnum.COMMENT_EXIT.getStatus())) {
                    ArticleCommentVO articleCommentVO = new ArticleCommentVO();
                    articleCommentVO.setCommentId(articleComment.getCommentId());
                    articleCommentVO.setContent(articleComment.getContent());
                    articleCommentVO.setParentId(articleComment.getParentId());
                    articleCommentVO.setCreatedAt(articleComment.getCreatedAt());
                    /**
                     * 查出发送者的nick
                     *  根据当前评论人的Id查nick
                     */
                    User user = new User();
                    user.setUserId(articleComment.getCommentUserId());
                    Optional<User> userOptional = userService.selectOne(Example.of(user));
                    articleCommentVO.setSenderName(userOptional.get().getUserNick());
                    /**
                     * 查出接收者nick
                     *  根据父评论的Id去查出父评论人Id
                     *      根据父评论人Id查nick
                     */
                    Optional<ArticleComment> articleCommentOptional = selectByKey(articleComment.getParentId());
                    if (articleCommentOptional.isPresent()){
                        User user1 = new User();
                        user1.setUserId(articleCommentOptional.get().getCommentUserId());
                        Optional<User> userOptional1 = userService.selectOne(Example.of(user1));
                        articleCommentVO.setReceiveName(userOptional1.get().getUserNick());
                        BeanUtils.copyProperties(articleComment, articleCommentVO);
                    }
                    articleCommentVOList.add(articleCommentVO);
                }
            }
            return articleCommentVOList;
        }

    }


}
