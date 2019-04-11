package cn.lichuachua.mp.mpserver.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author 李歘歘
 */
@Data
public class TeamPasswordForm {

    @NotEmpty(message = "队伍ID不能为空")
    private String teamId;

    @NotEmpty(message = "原密码不能为空")
    private String formerPassword;

    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "确认密码不能为空")
    private String confirmPassword;

}
