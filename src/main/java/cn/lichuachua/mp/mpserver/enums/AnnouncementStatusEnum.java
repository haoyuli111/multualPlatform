package cn.lichuachua.mp.mpserver.enums;

import lombok.Getter;

/**
 * @author 李歘歘
 * 用户状态类
 */
@Getter
public enum AnnouncementStatusEnum {
    /**
     * 正常
     */
    NORMAL(0,"正常"),


    /**
     * 删除
     */
    DELETED(1,"删除");

    /**
     * 状态
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String desc;

    AnnouncementStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
