package cn.lichuachua.mp.mpserver.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 李歘歘
 */
@Data
public class TeamResourceVO {

    private String publisherId;

    private String publisherNick;

    private String resourceName;

    private String resource;

    private Date createdAt;

}
