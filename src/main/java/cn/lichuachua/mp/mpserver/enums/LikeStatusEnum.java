package cn.lichuachua.mp.mpserver.enums;

import lombok.Getter;

/**
 * @author 李宇豪
 */
@Getter
public enum LikeStatusEnum {

    /**
     * 用户不喜欢
     */
    NOLIKE(1, "不喜欢"),

    /**
     * 用户喜欢
     */
    LIKE(0, "喜欢"),

    ;

    LikeStatusEnum(Integer status, String desc) {
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
