package cn.lichuachua.mp.mpserver.service.impl;

import cn.lichuachua.mp.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp.mpserver.entity.Article;
import cn.lichuachua.mp.mpserver.entity.InformArticle;
import cn.lichuachua.mp.mpserver.entity.User;
import cn.lichuachua.mp.mpserver.enums.ArticleStatusEnum;
import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp.mpserver.enums.InformArticleEnum;
import cn.lichuachua.mp.mpserver.exception.ArticleException;
import cn.lichuachua.mp.mpserver.exception.InformArticleException;
import cn.lichuachua.mp.mpserver.exception.UserException;
import cn.lichuachua.mp.mpserver.service.IArticleService;
import cn.lichuachua.mp.mpserver.service.IIformArticleService;
import cn.lichuachua.mp.mpserver.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @author 李歘歘
 */
@Service
public class InformArticleServiceImpl extends BaseServiceImpl<InformArticle, String> implements IIformArticleService {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IUserService userService;


    /**
     * 文章举报
     * @param articleId
     * @param userId
     */
    @Override
    public void publish(String articleId, String userId ) {
        /**
         * 根据articleId查出发布人Id，文章Id
         *
         */
        Article article = new Article();
        article.setStatus(ArticleStatusEnum.NORMAL.getStatus());
        article.setArticleId(articleId);
        Optional<Article> articleOptional = articleService.selectOne(Example.of(article));
        if (!articleOptional.isPresent()){
            throw new ArticleException(ErrorCodeEnum.ARTICLE_NO_EXIT);
        }
        /**
         * 根据发布人Id查出发布人name和mobile
         */
        Optional<User> userOptional = userService.selectByKey(articleOptional.get().getPublisherId());
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
        InformArticle informArticle = new InformArticle();
        informArticle.setArticelId(articleId);
        informArticle.setInformerId(userId);
        informArticle.setInformedId(articleOptional.get().getPublisherId());
        /**
         * 每个用户对于一篇文章只能举报一次
         *  就算管理员对举报进行处理，该用户也不能再次进行举报
         */
        long count = selectCountByExample(Example.of(informArticle));
        if (count>0){
            throw new InformArticleException(ErrorCodeEnum.INFORM_EXIT);
        }
        informArticle.setInformerMobile(userOptional.get().getMobile());
        informArticle.setInformerName(userOptional.get().getUserName());
        informArticle.setInformedName(userOptional1.get().getUserName());
        informArticle.setArticleTitle(articleOptional.get().getTitle());
        informArticle.setCreatedAt(new Date());
        informArticle.setUpdatedAt(new Date());
        save(informArticle);

    }
}
