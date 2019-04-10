package cn.lichuachua.mp.mpserver.service.impl;

import cn.lichuachua.mp.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp.mpserver.entity.Article;
import cn.lichuachua.mp.mpserver.entity.ArticleCollect;
import cn.lichuachua.mp.mpserver.entity.ArticleCollectPK;
import cn.lichuachua.mp.mpserver.entity.User;
import cn.lichuachua.mp.mpserver.enums.ArticleStatusEnum;
import cn.lichuachua.mp.mpserver.enums.CollectEnum;
import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp.mpserver.exception.ArticleCollectException;
import cn.lichuachua.mp.mpserver.exception.ArticleException;
import cn.lichuachua.mp.mpserver.exception.UserException;
import cn.lichuachua.mp.mpserver.service.IArticleCollectService;
import cn.lichuachua.mp.mpserver.service.IArticleService;
import cn.lichuachua.mp.mpserver.service.IUserService;
import cn.lichuachua.mp.mpserver.vo.ArticleCollectVO;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleCollectServiceImpl extends BaseServiceImpl<ArticleCollect, ArticleCollectPK> implements IArticleCollectService {

    @Autowired
    private IArticleService articleService;
    @Autowired
    private IUserService userService;

    /**
     * 添加收藏
     * @param articleId
     * @param userId
     */
    @Override
    public Integer add(String articleId, String userId) {
        /**
         * 判断文章是否存在
         */
        Article article = new Article();
        article.setArticleId(articleId);
        article.setStatus(ArticleStatusEnum.NORMAL.getStatus());
        Optional<Article> articleOptional = articleService.selectOne(Example.of(article));
        if (!articleOptional.isPresent()) {
            throw new ArticleException(ErrorCodeEnum.ARTICLE_NO_EXIT);
        }else {
            /**
             * 判断是否存在收藏记录
             */
            ArticleCollect articleCollect = new ArticleCollect();
            articleCollect.setArticleId(articleId);
            articleCollect.setUserId(userId);
            Optional<ArticleCollect> articleCollectOptional = selectOne(Example.of(articleCollect));
            /**
             * 存在该记录
             *  更改  0---》1
             *     1===》0
             */
            if (articleCollectOptional.isPresent()){
                articleCollect.setCreatedAt(articleCollectOptional.get().getCreatedAt());
                articleCollect.setUpdatedAt(new Date());
                /**
                 * 如果该文章你是收藏，则改为非收藏，返回1
                 */
                if (articleCollectOptional.get().getStatus().equals(CollectEnum.COLLECT_NO_EXIT.getStatus())){
//                    articleCollect.setArticleId(articleCollectOptional.get().getArticleId());
//                    articleCollect.setUserId(articleCollectOptional.get().getUserId());
                    articleCollect.setStatus(CollectEnum.COLLECT_EXIT.getStatus());
                    update(articleCollect);
                    return 0;
                }else {
//                    articleCollect.setArticleId(articleCollectOptional.get().getArticleId());
//                    articleCollect.setUserId(articleCollectOptional.get().getUserId());
                    articleCollect.setStatus(CollectEnum.COLLECT_NO_EXIT.getStatus());
                    update(articleCollect);
                    return 1;
                }
            }else {
                /**
                 * 不存在： 添加
                 */
                articleCollect.setStatus(CollectEnum.COLLECT_EXIT.getStatus());
                articleCollect.setCreatedAt(new Date());
                articleCollect.setUpdatedAt(new Date());
                save(articleCollect);
                return 0;
            }
        }
    }

    /**
     * 获取收藏列表
     * @param userId
     * @return
     */

    @Override
    public List<ArticleCollectVO> queryMyCollectList(String userId ) {
        /**
         * 查看该用户是否存在
         */
        Optional<User> userOptional = userService.selectByKey(userId);
        if (!userOptional.isPresent()){
            throw new UserException(ErrorCodeEnum.ERROR_USER);
        }else {
            List<ArticleCollect> articleCollectList = selectAll();
            List<ArticleCollectVO> articleCollectVOList = new ArrayList<>();
            for (ArticleCollect articleCollect : articleCollectList){
                if (articleCollect.getUserId().equals(userId) && articleCollect.getStatus().equals(CollectEnum.COLLECT_EXIT.getStatus())){
                    /**
                     * 查看原文章是否存在，原文章不存在，提示原文章已被删除
                     */
                    Article article = new Article();
                    article.setArticleId(articleCollect.getArticleId());
                    article.setStatus(ArticleStatusEnum.NORMAL.getStatus());
                    Optional<Article> articleOptional = articleService.selectOne(Example.of(article));
                    if (articleOptional.isPresent()){
                        /**
                         * 根据收藏的文章的Id取出文章的标题
                         */
                        ArticleCollectVO articleCollectVO = new ArticleCollectVO();
                        articleCollectVO.setTitle(articleOptional.get().getTitle());
                        articleCollectVO.setCreatedAt(articleCollect.getCreatedAt());
                        articleCollectVO.setArticleId(articleCollect.getArticleId());
                        BeanUtils.copyProperties(articleCollect,articleCollectVO);
                        articleCollectVOList.add(articleCollectVO);
                    }
                }
            }
            return articleCollectVOList;
        }
    }


}
