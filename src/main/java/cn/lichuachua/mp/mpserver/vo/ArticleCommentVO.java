package cn.lichuachua.mp.mpserver.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 李宇豪
 */
@Data
public class ArticleCommentVO {
    /**
     * 评论Id
     */
    @JsonProperty("id")
    private String commentId;

    /**
     * 父Id
     */
    @JsonProperty("parentId")
    private String parentId;

    /**
     * 回复用户的名称
     */
    @JsonProperty("senderName")
    private String senderName;

    /**
     * 被回复用户的名称
     */
    @JsonProperty("receiveName")
    private String receiveName;
    /**
     * 评论内容
     */
    @JsonProperty("content")
    private String content;

    /**
     * 创建时间
     */
    private Date createdAt;

}
