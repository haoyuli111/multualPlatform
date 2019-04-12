package cn.lichuachua.mp.mpserver.enums;


import lombok.Getter;

@Getter
public enum TeamVisualEnum {
    /**
     * 用户正常
     */
    VISUAL(0,"公有"),

    /**
     * 用户被系统禁用封号
     */
    NO_VISUAL(1,"私有");


    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 用户状态描述
     */
    private String desc;

    TeamVisualEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
