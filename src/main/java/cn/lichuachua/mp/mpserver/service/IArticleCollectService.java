package cn.lichuachua.mp.mpserver.service;

import cn.lichuachua.mp.core.support.service.IBaseService;
import cn.lichuachua.mp.mpserver.entity.ArticleCollect;
import cn.lichuachua.mp.mpserver.entity.ArticleCollectPK;
import cn.lichuachua.mp.mpserver.vo.ArticleCollectVO;

import java.util.List;

public interface IArticleCollectService extends IBaseService<ArticleCollect, ArticleCollectPK> {
    /**
     * 添加收藏
     * @param articleId
     * @param userId
     */
    Integer add(String articleId, String userId);

    /**
     * 获取收藏列表
     * @param userId
     * @return
     */
    List<ArticleCollectVO> queryMyCollectList(String userId);
}
