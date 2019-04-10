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
@Entity(name = "mp_admin_article")
public class InformArticle {
    @Id
    @GeneratedValue(generator = "uuid" )
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "operation_id")
    private String operationId;

    @Column(name = "article_id")
    private String articelId;

    @Column(name = "article_title")
    private String articleTitle;

    @Column(name = "informed_mobile")
    private String informerMobile;

    @Column(name = "informer_name")
    private String informerName;

    @Column(name = "informed_id")
    private String informedId;

    @Column(name = "informed_name")
    private String informedName;

    @Column(name = "informer_id")
    private String informerId;

    @Column(name = "admin_mobile")
    private String adminMobile;

    @Column(name = "admin_id")
    private String adminId;

    @Column(name = "operation_type")
    private Integer operationType;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;


}
