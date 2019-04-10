package cn.lichuachua.mp.mpserver.service.impl;

import cn.lichuachua.mp.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp.mpserver.entity.*;
import cn.lichuachua.mp.mpserver.enums.CommentEnum;
import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp.mpserver.enums.InformArticleEnum;
import cn.lichuachua.mp.mpserver.exception.ArticleException;
import cn.lichuachua.mp.mpserver.exception.InformArticleException;
import cn.lichuachua.mp.mpserver.exception.UserException;
import cn.lichuachua.mp.mpserver.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @author 李歘歘
 */
@Service
public class InformCommentServiceImpl extends BaseServiceImpl<InformComment, String> implements IIformCommentService {

    @Autowired
    private IArticleCommentService articleCommentService;

    @Autowired
    private IUserService userService;


    /**
     * 评论举报
     * @param commentId
     * @param userId
     */
    @Override
    public void publish(String commentId, String userId ) {
        /**
         * 根据articleId查出发布人Id，文章Id
         *
         */
        ArticleComment articleComment = new ArticleComment();
        articleComment.setCommentId(commentId);
        articleComment.setStatus(CommentEnum.COMMENT_EXIT.getStatus());
        Optional<ArticleComment> articleCommentOptional = articleCommentService.selectOne(Example.of(articleComment));
        if (!articleCommentOptional.isPresent()){
            throw new ArticleException(ErrorCodeEnum.COMMENT_NO);
        }
        /**
         * 根据发布人Id查出发布人name和mobile
         */
        Optional<User> userOptional = userService.selectByKey(articleCommentOptional.get().getCommentUserId());
        if (!userOptional.isPresent()) {
            throw new UserException(ErrorCodeEnum.ERROR_USER);
        }
        /**
         * 查出举报人的name
         */
        Optional<User> userOptional1 = userService.selectByKey(userId);
        if (!userOptional1.isPresent()){
            throw new UserException(ErrorCodeEnum.ERROR_USER);
        }


        /**
         * 检测是否已经举报过
         */
        InformComment informComment = new InformComment();
        informComment.setCommentId(commentId);
        informComment.setInformerId(userId);
        informComment.setInformedId(userOptional.get().getUserId());
        /**
         * 每个用户对于一个评论只能举报一次
         *  就算管理员对举报进行处理，该用户也不能再次进行举报
         */
        long count = selectCountByExample(Example.of(informComment));
        if (count>0){
            throw new InformArticleException(ErrorCodeEnum.INFORM_EXIT);
        }
        informComment.setInformerMobile(userOptional.get().getMobile());
        informComment.setInformerName(userOptional.get().getUserName());
        informComment.setInformedName(userOptional1.get().getUserName());
        informComment.setCommentContent(articleCommentOptional.get().getContent());
        informComment.setCreatedAt(new Date());
        informComment.setUpdatedAt(new Date());
        save(informComment);
    }
}
