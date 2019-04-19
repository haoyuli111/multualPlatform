package cn.lichuachua.mp.mpserver.service;

import cn.lichuachua.mp.core.support.service.IBaseService;
import cn.lichuachua.mp.mpserver.entity.Follow;
import cn.lichuachua.mp.mpserver.entity.FollowPK;
import cn.lichuachua.mp.mpserver.vo.FollowVO;

import java.util.List;

public interface IFollowService extends IBaseService<Follow, FollowPK> {
    /**
     * 关注
     * @param userId
     * @param userId1
     * @return
     */
    Integer follow(String userId, String userId1);

    /**
     * 获取我关注的人的列表
     * @param userId
     * @return
     */
    List<FollowVO> queryMyFollowList(String userId);

    /**
     * 获取关注我的人的列表
     * @param userId
     * @return
     */
    List<FollowVO> queryFollowedMeList(String userId);

    /**
     * 更新头像
     * @param userId
     * @param fileName
     */
    void updateAvatar(String userId, String fileName);
}
