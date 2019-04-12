package cn.lichuachua.mp.mpserver.service;

import cn.lichuachua.mp.core.support.service.IBaseService;
import cn.lichuachua.mp.mpserver.entity.Team;
import cn.lichuachua.mp.mpserver.form.*;
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

    /**
     * 修改队伍信息
     * @param teamChangeForm
     * @param userId
     */
    void updatedTeamInfor(@Valid TeamChangeForm teamChangeForm, String userId);

    /**
     * 队伍转让
     * @param teamTransfer
     * @param userId
     */
    void transfer(@Valid TeamTransfer teamTransfer, String userId);

    void forgetPassword(@Valid TeamForgetPasswordForm teamForgetPasswordForm, String userId);
}
