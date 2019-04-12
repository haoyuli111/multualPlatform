package cn.lichuachua.mp.mpserver.form;

import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author 李歘歘
 */
@Data
public class TeamChangeForm {

    @NotEmpty(message = "队伍Id不能为空")
    private String teamId;

    @NotEmpty(message = "队伍名不能为空")
    private String teamName;

    @NotEmpty(message = "队伍描述不能为空")
    private String description;

    @NotEmpty(message = "队伍密码不能为空")
    private String password;

    @NotNull(message = "是否可视不能为空")
    private Integer visual;

    @NotNull(message = "成员数不能为空")
    private Integer number;

    @NotNull(message = "队伍类型不能为空")
    private Integer type;


}
