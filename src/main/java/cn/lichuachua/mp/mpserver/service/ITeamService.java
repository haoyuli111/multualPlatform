package cn.lichuachua.mp.mpserver.service;

import cn.lichuachua.mp.core.support.service.IBaseService;
import cn.lichuachua.mp.mpserver.entity.Team;
import cn.lichuachua.mp.mpserver.form.*;
import cn.lichuachua.mp.mpserver.vo.TeamListVO;
import cn.lichuachua.mp.mpserver.vo.TeamVO;

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
     * @param teamDeletedForm
     * @param userId
     */
    void deleted(@Valid TeamDeletedForm teamDeletedForm, String userId);

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

    /**
     * 忘记队伍密码
     * @param teamForgetPasswordForm
     * @param userId
     */
    void forgetPassword(@Valid TeamForgetPasswordForm teamForgetPasswordForm, String userId);


    /**
     * 显示公有队伍详情
     * @param teamId
     * @return
     */
    TeamVO queryPublic(String teamId);

    /**
     * 显示私有队伍详情
     * @param teamId
     * @param userId
     * @return
     */
    TeamVO queryPrivate(String teamId, String userId);

    /**
     * 按照队伍类型查找队伍列表
     * @param typeId
     * @return
     */
    List<TeamListVO> queryListByType(Integer typeId);

    /**
     * 按照队伍类型和公私有类型共同查找队伍列表
     * @param teamListForm
     * @return
     */
    List<TeamListVO> queryListByVisualAndType(@Valid TeamListForm teamListForm);

    /**
     * 按照队伍公私有查找队伍列表
     * @param visualId
     * @return
     */
    List<TeamListVO> queryListByVisual(Integer visualId);

    /**
     * 查找队伍列表
     * @param teamListForm
     * @return
     */
    List<TeamListVO> queryAllList(@Valid TeamListForm teamListForm);

    /**
     * 根据队伍名查找队伍Id
     * @param teamName
     * @return
     */
    String queryTeamIdByTeamName(String teamName);

    /**
     * 根据队伍名查找队伍
     * @param teamName
     * @return
     */
    TeamListVO queryTeamByTeamName(String teamName);


    /**
     * 更新头像
     * @param userId
     * @param fileName
     */
    void updateAvatar(String userId, String fileName);

}
