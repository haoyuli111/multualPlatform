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
@Entity(name = "mp_team")
public class Team {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @Column(name = "team_id")
    private String teamId;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "header_id")
    private String headerId;

    @Column(name = "header_nick")
    private String headerNick;

    @Column(name = "describe")
    private String describe;

    @Column(name = "header_avatar")
    private String headerAvatar;

    @Column(name = "header_mobile")
    private String headerMobile;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private Integer status;

    @Column(name = "visual")
    private Integer visual;

    @Column(name = "type")
    private Integer type;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
