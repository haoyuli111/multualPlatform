package cn.lichuachua.mp.mpserver.service;

import cn.lichuachua.mp.core.support.service.IBaseService;
import cn.lichuachua.mp.mpserver.entity.Article;
import cn.lichuachua.mp.mpserver.form.ArticleChangeForm;
import cn.lichuachua.mp.mpserver.form.ArticlePublishForm;
import cn.lichuachua.mp.mpserver.vo.ArticleCommentVO;
import cn.lichuachua.mp.mpserver.vo.ArticleListVO;
import cn.lichuachua.mp.mpserver.vo.ArticleVO;
import cn.lichuachua.mp.mpserver.vo.MyArticleListVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    void publish(@Valid ArticlePublishForm articlePublishForm, String userId, String fileName);

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
    List<ArticleListVO> queryUserArticleList(String userId, Pageable pageable);


    /**
     * 查询我的文章列表
     * @param userId
     * @return
     */
    List<MyArticleListVO> queryMyArticleList(String userId, Pageable pageable);


    /**
     * 更新头像
     * @param userId
     * @param fileName
     */
    void updateAvatar(String userId, String fileName);

    /**
     * 根据文章Id查询当前作者的其他文章
     * @param articleId
     * @return
     */
    List<ArticleListVO> queryArticleListByArticleId(String articleId, Pageable pageable);

    /**
     * 分页获取文章列表
     * @param pageable
     * @return
     */
    List<ArticleListVO> queryListByPage(Pageable pageable);

    /**
     * 下载附件
     * @param articleId
     * @param userId
     * @return
     */
    String download(String articleId, String userId);
}




