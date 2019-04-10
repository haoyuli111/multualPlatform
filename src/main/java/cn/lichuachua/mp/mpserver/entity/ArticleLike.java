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
@Entity(name = "mp_article_like")
public class ArticleLike {
    /**
     * 点赞Id
     */
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @Column(name = "like_id")
    private String likeId;

    /**
     * 点赞文章Id
     */
    @Column(name = "article_id")
    private String articleId;

    /**
     * 点赞人Id
     */
    @Column(name = "like_user_id")
    private String likeUserId;

    /**
     * 状态
     * 0-点赞
     * 1-未点赞
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
