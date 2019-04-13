package cn.lichuachua.mp.mpserver.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author 李歘歘
 */
@Data
public class JoinPrivateTeamForm {
    @NotEmpty(message = "队伍名不能为空")
    private String teamId;

    @NotEmpty(message = "密码不能为空")
    private String password;

}
