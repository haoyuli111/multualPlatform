package cn.lichuachua.mp.mpserver.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 李歘歘
 */
@Data
public class TeamVO {
    private String teamName;

    private String headerNick;

    private String description;

    private String headerId;

    private String headerAvatar;

    private Integer type;

    private Date createdAt;

    List<TeamMemberVO> teamMemberVOList = new ArrayList<>();

}
