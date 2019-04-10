package cn.lichuachua.mp.mpserver.service;


import cn.lichuachua.mp.core.support.service.IBaseService;
import cn.lichuachua.mp.mpserver.entity.ArticleComment;
import cn.lichuachua.mp.mpserver.form.ArticleCommentForm;
import cn.lichuachua.mp.mpserver.vo.ArticleCommentVO;

import javax.validation.Valid;
import java.util.List;

public interface IArticleCommentService extends IBaseService<ArticleComment, String> {
    /**
     * 发布评论
     * @param articleCommentForm
     * @param userId
     */
    void publish(@Valid ArticleCommentForm articleCommentForm, String userId);

    /**
     * 删除评论
     * @param commentId
     * @param userId
     */
    void deleteComment(String commentId, String userId);

    /**
     * 查看评论列表
     * @param articleId
     * @return
     */
    List<ArticleCommentVO> queryComment(String articleId);
}
