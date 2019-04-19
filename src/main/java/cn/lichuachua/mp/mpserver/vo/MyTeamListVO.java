package cn.lichuachua.mp.mpserver.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 李歘歘
 */
@Data
public class MyTeamListVO {
    private String teamId;

    private String teamName;

    private String description;

    private String type;

    private String visual;

    private Date createdAt;
}
