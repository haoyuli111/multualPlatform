package cn.lichuachua.mp.mpserver.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author 李歘歘
 */
@Data
@Entity(name = "mp_article")
public class Article {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "article_id")
    private String articleId;

    @Column(name = "publisher_id")
    private String publisherId;

    @Column(name = "publisher_nick")
    private String publisherNick;

    @Column(name = "publisher_avatar")
    private String publisherAvatar;

    @Column(name = "status")
    private Integer status;

    @Column(name = "article_type")
    private Integer articleType;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "accessory")
    private String accessory;

    @Column(name = "visual")
    private Integer visual;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

}
