package cn.lichuachua.mp.mpserver.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author 李歘歘
 */
@Data
public class TeamForgetPasswordForm {
    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "确认密码不能为空")
    private String confirmPassword;

    @NotEmpty(message = "队伍Id不能为空")
    private String teamId;

    @Pattern(message = "请填写正确的验证码",regexp ="^[0-9].{5}" )
    @NotEmpty(message = "验证码不为空")
    private String code;
}
