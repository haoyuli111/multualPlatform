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
@Entity(name = "mp_team_resource")
public class TeamResource {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @Column(name = "resource_id")
    private String resourceId;

    @Column(name = "team_id")
    private String teamId;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "publisher_id")
    private String publisherId;

    @Column(name = "publisher_nick")
    private String publisherNick;

    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "resource")
    private String resource;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
