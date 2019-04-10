package cn.lichuachua.mp.mpserver.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author 李宇豪
 */
@Data
@Entity(name = "mp_article_comment")
public class ArticleComment {
    /**
     * 评论Id
     */
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @Column(name = "comment_id")
    private String commentId;

    /**
     * 评论者Id
     */
    @Column(name = "comment_user_id")
    private String commentUserId;

    /**
     * 父评论Id
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 评论内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 评论深度
     */
    @Column(name = "depth")
    private Integer depth;

    /**
     * 评论的层级
     */
    @Column(name = "thread")
    private String thread;

    /**
     * 评论的状态
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

}
