package cn.lichuachua.mp.mpserver.service.impl;

import cn.lichuachua.mp.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp.mpserver.entity.Article;
import cn.lichuachua.mp.mpserver.entity.ArticleLike;
import cn.lichuachua.mp.mpserver.entity.User;
import cn.lichuachua.mp.mpserver.enums.ArticleStatusEnum;
import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp.mpserver.enums.LikeStatusEnum;
import cn.lichuachua.mp.mpserver.exception.ArticleException;
import cn.lichuachua.mp.mpserver.service.IArticleLikeService;
import cn.lichuachua.mp.mpserver.service.IArticleService;
import cn.lichuachua.mp.mpserver.service.IUserService;
import cn.lichuachua.mp.mpserver.vo.ArticleLikeVO;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author  李歘歘
 */
@Service
public class ArticleLikeServiceImpl extends BaseServiceImpl<ArticleLike, String> implements IArticleLikeService {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IUserService userService;

    /**
     * 点赞
     * @param articleId
     * @param userId
     * @return
     */
    @Override
    public Integer likes(String articleId, String userId) {
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
            /**
             * 1.查找数据表中是否有 该记录，
             *  若没有则添加，若存在
             *      判断其status的值：0-->1;1-->0
             */
            ArticleLike articleLike = new ArticleLike();
            articleLike.setArticleId(articleId);
            articleLike.setLikeUserId(userId);
            Optional<ArticleLike> articleLikeOptional = selectOne(Example.of(articleLike));
            /**
             * 不存在该记录，加入
             */
            if (!articleLikeOptional.isPresent()){
                articleLike.setCreatedAt(new Date());
                articleLike.setUpdatedAt(new Date());
                articleLike.setStatus(LikeStatusEnum.LIKE.getStatus());
                save(articleLike);
                return 0;
            }else {
                /**
                 * 存在，修改
                 * 判断其status的值：0-->1;1-->0
                 */
                articleLike.setCreatedAt(articleLikeOptional.get().getCreatedAt());
                articleLike.setLikeId(articleLikeOptional.get().getLikeId());
                articleLike.setUpdatedAt(new Date());
                if (articleLikeOptional.get().getStatus().equals(LikeStatusEnum.LIKE.getStatus())){
                    articleLike.setStatus(LikeStatusEnum.NOLIKE.getStatus());
                    update(articleLike);
                    return 1;
                }else {
                    articleLike.setStatus(LikeStatusEnum.LIKE.getStatus());
                    update(articleLike);
                    return 0;
                }
            }
        }

    }

    /**
     * 获取点赞列表
     * @param articleId
     * @return
     */
    @Override
    public List<ArticleLikeVO> likesList(String articleId){
        /**
         * 判断文章是否存在
         */
        Article article = new Article();
        article.setStatus(ArticleStatusEnum.NORMAL.getStatus());
        article.setArticleId(articleId);
        Optional<Article> articleOptional = articleService.selectOne(Example.of(article));
        if (!articleOptional.isPresent()){
            throw new ArticleException(ErrorCodeEnum.ARTICLE_NO_EXIT);
        }else {
            /**
             * 查询出该文章点赞的记录并放在list中
             */
            ArticleLike articleLike = new ArticleLike();
            articleLike.setArticleId(articleId);
            articleLike.setStatus(LikeStatusEnum.LIKE.getStatus());
            List<ArticleLike> circleLikeList = select(articleLike);

            /**
             * 将List中的数组中的值返回给VO
             */
            List<ArticleLikeVO> articleLikeVOList = new ArrayList<>();
            for (ArticleLike articleLike1 : circleLikeList) {
                ArticleLikeVO articleLikeVO = new ArticleLikeVO();
                articleLikeVO.setLikeUserId(articleLike1.getLikeUserId());
                /**
                 * 查询用户nick
                 */
                User user = new User();
                user.setUserId(articleLike1.getLikeUserId());
                user.setStatus(ArticleStatusEnum.NORMAL.getStatus());
                Optional<User> userOptional = userService.selectOne(Example.of(user));
                if (userOptional.isPresent()){
                    articleLikeVO.setUserNick(userOptional.get().getUserNick());
                }
                BeanUtils.copyProperties(articleLike1, articleLikeVO);
                articleLikeVOList.add(articleLikeVO);
            }
            return articleLikeVOList;
        }

    }

}
