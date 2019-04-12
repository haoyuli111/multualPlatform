package cn.lichuachua.mp.mpserver.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author 李歘歘
 */
@Data
public class TeamTransfer {

    @NotEmpty(message = "队伍Id不能为空")
    private String teamId;

    @NotEmpty(message = "转让人手机号不能为空")
    private String mobile;

    @NotEmpty(message = "队伍密码不能为空")
    private String password;
}
