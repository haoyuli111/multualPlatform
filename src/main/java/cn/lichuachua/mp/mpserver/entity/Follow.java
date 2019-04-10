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
@IdClass(FollowPK.class)
@Entity(name = "mp_follow")
public class Follow {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Id
    @Column(name = "attention_id")
    private String attentionId;

    @Column(name = "attention_nick")
    private String attentionNick;

    @Column(name = "attention_avatar")
    private String attentionAvatar;

    @Column(name = "user_nick")
    private String userNick;

    @Column(name = "user_avatar")
    private String userAvatar;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

}
