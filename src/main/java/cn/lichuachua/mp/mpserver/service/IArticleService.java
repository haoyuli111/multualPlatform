package cn.lichuachua.mp.mpserver.service;

import cn.lichuachua.mp.core.support.service.IBaseService;
import cn.lichuachua.mp.mpserver.entity.Article;
import cn.lichuachua.mp.mpserver.form.ArticleChangeForm;
import cn.lichuachua.mp.mpserver.form.ArticlePublishForm;
import cn.lichuachua.mp.mpserver.vo.ArticleCommentVO;
import cn.lichuachua.mp.mpserver.vo.ArticleListVO;
import cn.lichuachua.mp.mpserver.vo.ArticleVO;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author 李歘歘
 */
public interface IArticleService extends IBaseService<Article, String> {
    /**
     * 发布文章
     *
     * @param articlePublishForm
     * @param userId
     */
    void publish(@Valid ArticlePublishForm articlePublishForm, String userId);

    /**
     * 更改文章
     *
     * @param articleChangeForm
     * @param userId
     */
    void updateArticle(@Valid ArticleChangeForm articleChangeForm, String userId);

    /**
     * 删除文章
     *
     * @param articlrId
     * @param userId
     */
    void deletaArticle(String articlrId, String userId);

    /**
     * 获取文章详情
     *
     * @param articleId
     * @return
     */
    ArticleVO queryArticle(String articleId);

    /**
     * 获取文章列表
     *
     * @return
     */
    List<ArticleListVO> queryList();



    /**
     * 根据用户Id查询文章用户文章列表
     * @param userId
     * @return
     */
    List<ArticleListVO> queryUserArticleList(String userId);

    /**
     * 查询我的文章列表
     * @param userId
     * @return
     */
    List<ArticleListVO> queryMyArticleList(String userId);
}




