package cn.lichuachua.mp.mpserver.form;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author 李歘歘
 *  创建队伍表单
 */
@Data
public class TeamDeletedForm {


    @NotEmpty(message = "队伍Id不能为空")
    private String teamId;

    @NotEmpty(message = "队伍密码不能为空")
    private String password;


}
