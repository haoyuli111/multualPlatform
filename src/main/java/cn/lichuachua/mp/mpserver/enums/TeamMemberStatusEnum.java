package cn.lichuachua.mp.mpserver.enums;

import lombok.Getter;

/**
 * @author 李歘歘
 */

@Getter
public enum TeamMemberStatusEnum {

    /**
     * 队伍处于正常状态
     */
    NORMAL(0,"正常"),

    /**
     * 队伍处于禁用状态
     */
    DISABLED(-1,"禁用"),

    /**
     * 队伍处于删除状态
     */
    DELETED(1,"删除")

    ;


    TeamMemberStatusEnum(Integer status, String desc){
        this.status = status;
        this.desc = desc;
    }

    /**
     * 文章状态
     */
    private Integer status;

    /**
     * 文章状态描述
     */
    private String desc;

}
