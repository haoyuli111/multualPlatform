package cn.lichuachua.mp.mpserver.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author 李歘歘
 */

@Data
public class UserLoginForm {
    @NotEmpty(message = "请填写手机号")
    private String mobile;

    @NotEmpty(message = "请填写密码")
    private String password;

}
