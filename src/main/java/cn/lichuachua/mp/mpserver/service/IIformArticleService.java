package cn.lichuachua.mp.mpserver.service;

import cn.lichuachua.mp.core.support.service.IBaseService;
import cn.lichuachua.mp.mpserver.entity.InformArticle;

public interface IIformArticleService extends IBaseService<InformArticle, String> {
    /**
     * 文章举报
     * @param articleId
     * @param userId
     */
    void publish(String articleId, String userId);
}
