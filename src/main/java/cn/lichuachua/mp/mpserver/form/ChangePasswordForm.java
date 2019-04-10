package cn.lichuachua.mp.mpserver.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author 李歘歘
 */
@Data
public class ChangePasswordForm {
    @NotEmpty(message = "原密码不能为空")
    private String formerPassword;

    @Pattern(message = "请将密码设置的更复杂",regexp ="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}" )
    @NotEmpty(message = "密码不能为空")
    private String password;

    @Pattern(message = "确认密码设置的更复杂",regexp ="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}" )
    @NotEmpty(message = "确认密码不能为空")
    private String confirmPassword;
}
