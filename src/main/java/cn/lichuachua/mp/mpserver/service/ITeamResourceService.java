package cn.lichuachua.mp.mpserver.service;

import cn.lichuachua.mp.core.support.service.IBaseService;
import cn.lichuachua.mp.mpserver.entity.TeamResource;
import cn.lichuachua.mp.mpserver.form.TeamResourcePublishForm;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author  李歘歘
 */

public interface ITeamResourceService extends IBaseService<TeamResource, String> {


    void publish(@Valid TeamResourcePublishForm teamResourcePublishForm, String fileName, String userId);
}
