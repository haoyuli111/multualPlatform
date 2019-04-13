package cn.lichuachua.mp.mpserver.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

/**
 * @author 李歘歘
 */
@Data
public class TeamResourcePublishForm {

    @NotEmpty(message = "队伍Id不能为空")
    private String teamId;

    @NotEmpty(message = "资源名不能为空")
    private String redourceName;



}
