package cn.lichuachua.mp.mpserver.service;

import cn.lichuachua.mp.core.support.service.IBaseService;
import cn.lichuachua.mp.mpserver.entity.InformComment;

public interface IIformCommentService extends IBaseService<InformComment, String> {
    /**
     * 评论举报
     * @param commentId
     * @param userId
     */
    void publish(String commentId, String userId);
}
