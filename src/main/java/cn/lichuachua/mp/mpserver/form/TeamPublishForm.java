package cn.lichuachua.mp.mpserver.form;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author 李歘歘
 *  创建队伍表单
 */
@Data
public class TeamPublishForm {
    @NotEmpty(message = "队伍名不能为空")
    private String TeamName;

    @NotEmpty(message = "队伍描述不能为空")
    private String description;

    @NotEmpty(message = "队伍密码不能为空")
    private String password;

    @NotNull(message = "队伍类型不能为空")
    private Integer type;

    @NotNull(message = "队伍人数不能为空")
    private Integer number;

    @NotNull(message = "队伍是否为私有")
    private Integer visual;


}
