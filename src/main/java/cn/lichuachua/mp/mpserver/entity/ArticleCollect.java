package cn.lichuachua.mp.mpserver.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author 李歘歘
 */
@Data
@IdClass(ArticleCollectPK.class)
@Entity(name = "mp_collect")
public class ArticleCollect {

    @Id
    @Column(name = "article_id")
    private String articleId;

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;


}
