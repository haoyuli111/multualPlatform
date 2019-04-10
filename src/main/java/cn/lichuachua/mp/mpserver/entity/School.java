package cn.lichuachua.mp.mpserver.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

/**
 * @author 李歘歘
 */
@Data
@Entity(name = "mp_school")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Integer schoolId;

    @Column(name = "school_name")
    private String schoolName;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

}
