package cn.lichuachua.mp.mpserver.enums;

import lombok.Getter;

/**
 * @author 李歘歘
 */
@Getter
public enum CollectEnum {

    /**
     * 收藏
     */
    COLLECT_EXIT(0,"收藏存在"),

    /**
     * 删除收藏
     */
    COLLECT_NO_EXIT(1,"收藏删除")

    ;
    CollectEnum(Integer status, String desc){
        this.desc = desc;
        this.status = status;
    }

    /**
     * 状态描述
     */
    private String desc;
    /**
     * 状态
     */
    private Integer status;
}
