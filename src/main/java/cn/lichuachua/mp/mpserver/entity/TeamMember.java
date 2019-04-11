package cn.lichuachua.mp.mpserver.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Date;

/**
 * @author 李歘歘
 */
@Data
@IdClass(TeamMemberPK.class)
@Entity(name = "mp_team_member")
public class TeamMember {
    @Id
    @Column(name = "team_id")
    private String teamId;

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
