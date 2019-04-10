package cn.lichuachua.mp.mpserver.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 李歘歘
 */
@Data
public class UserInfoDTO implements Serializable{

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 昵称
     */
    private String userNick;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 注册时间
     */
    private String createdAt;


}
