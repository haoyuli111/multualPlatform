package cn.lichuachua.mp.mpserver.service.impl;

import cn.lichuachua.mp.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp.mpserver.entity.Follow;
import cn.lichuachua.mp.mpserver.entity.FollowPK;
import cn.lichuachua.mp.mpserver.entity.User;
import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp.mpserver.enums.FollowStatusEnum;
import cn.lichuachua.mp.mpserver.enums.UserStatusEnum;
import cn.lichuachua.mp.mpserver.exception.FollowException;
import cn.lichuachua.mp.mpserver.exception.UserException;
import cn.lichuachua.mp.mpserver.service.IFollowService;
import cn.lichuachua.mp.mpserver.service.IUserService;
import cn.lichuachua.mp.mpserver.vo.FollowVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author 李歘歘
 */
@Service

public class FollowServiceImpl extends BaseServiceImpl<Follow, FollowPK> implements IFollowService {

    @Autowired
    private IUserService userService;
    @Override
    public Integer follow(String userId, String userId1) {
        /**
         * 判断当前登录用户是否存在或者用户状态是否正常
         */
        Optional<User> userOptional1 = userService.selectByKey(userId1);
        /**
         * 判断被关注者是否存在或者用户状态是否正常
         */
        User user = new User();
        user.setStatus(UserStatusEnum.NORMAL.getStatus());
        user.setUserId(userId);
        Optional<User> userOptional = userService.selectOne(Example.of(user));
        if (!userOptional.isPresent()){
            throw new UserException(ErrorCodeEnum.ERROR_USER_OR_MOBILE_DELETE);
        }
        /**
         * 1.查找数据表中是否有 该记录，
         *  若没有则添加，若存在
         *      判断其status的值：0-->1;1-->0
         */
        Follow follow = new Follow();
        follow.setUserId(userId1);
        follow.setAttentionId(userId);
        Optional<Follow> followOptional = selectOne(Example.of(follow));
        /**
         * 不存在该记录，加入
         */
        if (!followOptional.isPresent()){
            follow.setAttentionAvatar(userOptional.get().getUserAvatar());
            follow.setAttentionNick(userOptional.get().getUserNick());
            follow.setStatus(FollowStatusEnum.FOLLOW_EXIT.getStatus());
            follow.setUserAvatar(userOptional1.get().getUserAvatar());
            follow.setUserNick(userOptional1.get().getUserNick());
            follow.setCreatedAt(new Date());
            follow.setUpdatedAt(new Date());
            save(follow);
            return 0;
        }else {
            /**
             * 存在则修改：
             *         判断其status的值：0-->1;1-->0
             */
            follow.setCreatedAt(followOptional.get().getCreatedAt());
            follow.setUpdatedAt(new Date());
            follow.setAttentionAvatar(followOptional.get().getUserAvatar());
            follow.setAttentionNick(followOptional.get().getUserNick());
            follow.setUserAvatar(followOptional.get().getUserAvatar());
            follow.setUserNick(followOptional.get().getUserNick());
            if (followOptional.get().getStatus().equals(FollowStatusEnum.FOLLOW_EXIT.getStatus())){
                follow.setStatus(FollowStatusEnum.FOLLOW_NO_EXIT.getStatus());
                update(follow);
                return 1;
            }else {
                follow.setStatus(FollowStatusEnum.FOLLOW_EXIT.getStatus());
                update(follow);
                return 0;
            }
        }
    }


    /**
     * 获取我关注的人的列表
     * @param userId
     * @return
     */
    @Override
    public List<FollowVO> queryMyFollowList(String userId) {

        List<Follow> followList = selectAll();
        List<FollowVO> followVOList = new ArrayList<>();
        for (Follow follow : followList){
                /**
                 * 查看我关注的用户和在关注表里面是否正常
                 */
                if (follow.getUserId().equals(userId)&&follow.getStatus().equals(FollowStatusEnum.FOLLOW_EXIT.getStatus())){
                    /**
                     * 查看该被关注用户账号是否正常
                     */
                    User user = new User();
                    user.setUserId(follow.getAttentionId());
                    user.setStatus(UserStatusEnum.NORMAL.getStatus());
                    Optional<User> userOptional = userService.selectOne(Example.of(user));
                    if (userOptional.isPresent()){
                    FollowVO followVO = new FollowVO();
                    followVO.setFollowerAvatar(follow.getAttentionAvatar());
                    followVO.setFollowerId(follow.getAttentionId());
                    followVO.setFollowerNick(follow.getAttentionNick());
                    followVO.setFollowerCreatedAt(follow.getUpdatedAt());
                    BeanUtils.copyProperties(follow,followVO);
                    followVOList.add(followVO);
                }
            }
        }
        return followVOList;
    }

    /**
     * 获取关注我的人的列表
     * @param userId
     * @return
     */
    @Override
    public List<FollowVO> queryFollowedMeList(String userId) {

        List<Follow> followList = selectAll();
        List<FollowVO> followVOList = new ArrayList<>();
        for (Follow follow : followList){

                /**
                 * 查看关注我的用户和在关注表里面是否正常
                 */
                if (follow.getAttentionId().equals(userId)&&follow.getStatus().equals(FollowStatusEnum.FOLLOW_EXIT.getStatus())){
                    /**
                     * 查看关注我的用户账号是否正常
                     */
                    User user = new User();
                    user.setUserId(follow.getUserId());
                    user.setStatus(UserStatusEnum.NORMAL.getStatus());
                    Optional<User> userOptional = userService.selectOne(Example.of(user));
                    if (userOptional.isPresent()){
                    FollowVO followVO = new FollowVO();
                    followVO.setFollowerAvatar(follow.getUserAvatar());
                    followVO.setFollowerId(follow.getUserId());
                    followVO.setFollowerNick(follow.getUserNick());
                    followVO.setFollowerCreatedAt(follow.getUpdatedAt());
                    BeanUtils.copyProperties(follow,followVO);
                    followVOList.add(followVO);
                }
            }
        }
        return followVOList;
    }
}
