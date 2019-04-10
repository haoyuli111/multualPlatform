package cn.lichuachua.mp.mpserver.enums;

import lombok.Getter;

/**
 * @author 李宇豪
 */
@Getter
public enum FollowStatusEnum {

    /**
     * 用户不喜欢
     */
    FOLLOW_NO_EXIT(1, "不关注"),

    /**
     * 用户喜欢
     */
    FOLLOW_EXIT(0, "关注"),

    ;

    FollowStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    /**
     * 点赞状态
     */
    private Integer status;

    /**
     * 点赞 状态描述
     */
    private String desc;
}
