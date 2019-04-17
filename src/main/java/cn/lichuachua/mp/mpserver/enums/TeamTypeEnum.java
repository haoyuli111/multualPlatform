package cn.lichuachua.mp.mpserver.enums;

import lombok.Getter;

/**
 * @author 李歘歘
 */

@Getter
public enum TeamTypeEnum {

    /**
     * 队伍类型处于正常状态
     */
    NORMAL(0,"正常"),


    /**
     * 队伍类型处于删除状态
     */
    DELETED(1,"删除")

    ;


    TeamTypeEnum(Integer status, String desc){
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
