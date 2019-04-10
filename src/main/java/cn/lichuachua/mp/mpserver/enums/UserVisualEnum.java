package cn.lichuachua.mp.mpserver.enums;

import lombok.Getter;

/**
 * @author 李歘歘
 * 用户状态类
 */
@Getter
public enum UserVisualEnum {
    /**
     * 用户正常
     */
    VISUAL(0,"所有人可见"),

    /**
     * 用户被系统禁用封号
     */
    NO_VISUAL(-1,"仅自己可见");


    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 用户状态描述
     */
    private String desc;

    UserVisualEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
