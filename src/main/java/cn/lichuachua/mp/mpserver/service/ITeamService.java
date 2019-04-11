package cn.lichuachua.mp.mpserver.service;

import cn.lichuachua.mp.core.support.service.IBaseService;
import cn.lichuachua.mp.mpserver.entity.Team;
import cn.lichuachua.mp.mpserver.form.TeamPasswordForm;
import cn.lichuachua.mp.mpserver.form.TeamPublishForm;
import cn.lichuachua.mp.mpserver.vo.TeamListVO;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 李歘歘
 */

public interface ITeamService extends IBaseService<Team, String> {

    /**
     * 创建队伍
     * @param teamPublishForm
     * @param userId
     */
    void publish(@Valid TeamPublishForm teamPublishForm, String userId);

    /**
     * 删除队伍
     * @param teamId
     * @param userId
     */
    void deleted(String teamId, String userId);

    /**
     * 显示队伍列表
     * @return
     */
    List<TeamListVO> queryList();

    /**
     * 更改队伍密码
     * @param teamPasswordForm
     * @param userId
     */
    void updatedPassword(@Valid TeamPasswordForm teamPasswordForm, String userId);

}
