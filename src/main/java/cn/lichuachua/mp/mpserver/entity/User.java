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
@Entity(name = "mp_user")
public class User {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_nick")
    private String userNick;

    @Column(name = "user_number")
    private String userNumber;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "status")
    private Integer status;

    @Column(name = "password")
    private String password;

    @Column(name = "user_avatar")
    private String userAvatar;

    @Column(name = "visual")
    private Integer visual;

    @Column(name = "school_id")
    private Integer schoolId;

    @Column(name = "academy_id")
    private Integer academyId;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
