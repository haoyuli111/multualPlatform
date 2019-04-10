package cn.lichuachua.mp.mpserver.service;

import cn.lichuachua.mp.core.support.service.IBaseService;
import cn.lichuachua.mp.mpserver.entity.ArticleLike;
import cn.lichuachua.mp.mpserver.vo.ArticleLikeVO;

import java.util.List;

/**
 * @author 李歘歘
 */
public interface IArticleLikeService extends IBaseService<ArticleLike, String> {

    /**
     * 点赞
     * @param articleId
     * @param userId
     */
    Integer likes(String articleId, String userId);

    /**
     * 获取点赞列表
     * @param articleId
     * @return
     */
    List<ArticleLikeVO> likesList(String articleId);
}
