package cn.lichuachua.mp.mpserver.service;

import cn.lichuachua.mp.core.support.service.IBaseService;
import cn.lichuachua.mp.mpserver.entity.TeamResource;
import cn.lichuachua.mp.mpserver.form.TeamResourcePublishForm;
import cn.lichuachua.mp.mpserver.vo.TeamResourceListVO;
import cn.lichuachua.mp.mpserver.vo.TeamResourceVO;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * @author  李歘歘
 */

public interface ITeamResourceService extends IBaseService<TeamResource, String> {

    /**
     * 发布资源
     * @param teamResourcePublishForm
     * @param fileName
     * @param userId
     */
    void publish(@Valid TeamResourcePublishForm teamResourcePublishForm, String fileName, String userId);


    /**
     * 删除资源
     * @param resourceId
     * @param userId
     */
    void deleted(String resourceId, String userId);

    /**
     * 资源列表
     * @param teamId
     * @param userId
     * @return
     */
    List<TeamResourceListVO> queryList(String teamId, String userId);

    TeamResourceVO query(String resourceId, String userId);

}
