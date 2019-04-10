package cn.lichuachua.mp.mpserver.enums;

import lombok.Getter;

/**
 * @author 李宇豪
 */
@Getter
public enum CommentEnum {

    /**
     * 评论不存在
     */
    COMMENT_EXIT(0, "评论存在"),

    /**
     * 评论存在
     */
    COMMENT_NO_EXIT(1, "评论不存在"),
    /**
     * 评论深度为0
     */
    DEPTH_ZERO(0,"深度为零"),
    /**
     * 深度加一
     */
    DEPTH_ADD(1,"深度+1"),
    /**
     * 分隔符
     */
    SEPARATOR("/","分隔符")

    ;

    CommentEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
    CommentEnum(String symbol, String desc){
        this.desc=desc;
        this.symbol=symbol;
    }

    /**
     * 字段状态
     */
    private Integer status;
    /**
     * 字段属性
     */
    private String symbol;

    /**
     * 字段 状态描述
     */
    private String desc;

}
