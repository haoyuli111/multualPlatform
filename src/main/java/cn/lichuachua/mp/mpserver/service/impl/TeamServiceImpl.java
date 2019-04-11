package cn.lichuachua.mp.mpserver.service.impl;

import cn.lichuachua.mp.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp.mpserver.entity.Team;
import cn.lichuachua.mp.mpserver.entity.User;
import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp.mpserver.enums.TeamStatusEnum;
import cn.lichuachua.mp.mpserver.exception.TeamException;
import cn.lichuachua.mp.mpserver.form.TeamPasswordForm;
import cn.lichuachua.mp.mpserver.form.TeamPublishForm;
import cn.lichuachua.mp.mpserver.service.ITeamService;
import cn.lichuachua.mp.mpserver.service.IUserService;
import cn.lichuachua.mp.mpserver.vo.TeamListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author 李歘歘
 */
@Service
public class TeamServiceImpl extends BaseServiceImpl<Team, String> implements ITeamService {

    @Autowired
    private IUserService userService;

    /**
     * 创建队伍
     * @param teamPublishForm
     * @param userId
     */
    @Override
    public void publish(TeamPublishForm teamPublishForm, String userId){
        Team team = new Team();
        team.setTeamName(teamPublishForm.getTeamName());
        team.setDescription(teamPublishForm.getDescription());
        team.setPassword(teamPublishForm.getPassword());
        team.setType(teamPublishForm.getType());
        team.setVisual(teamPublishForm.getVisual());
        team.setNumber(teamPublishForm.getNumber());
        team.setHeaderId(userId);
        /**
         * 根据userId取出用户的信息
         */
        Optional<User> userOptional = userService.selectByKey(userId);
        team.setHeaderAvatar(userOptional.get().getUserAvatar());
        team.setHeaderMobile(userOptional.get().getMobile());
        team.setHeaderNick(userOptional.get().getUserNick());
        team.setStatus(TeamStatusEnum.NORMAL.getStatus());
        team.setCreatedAt(new Date());
        team.setUpdatedAt(new Date());
        save(team);
    }

    /**
     * 删除队伍
     * @param teamId
     * @param userId
     */
    @Override
    public void deleted(String teamId, String userId){
        /**
         * 查看登录的用户是否是队长，team是否存在
         */
        Team team = new Team();
        team.setTeamId(teamId);
        team.setHeaderId(userId);
        team.setStatus(TeamStatusEnum.NORMAL.getStatus());
        Optional<Team> teamOptional = selectOne(Example.of(team));
        if (!teamOptional.isPresent()){
            throw new TeamException(ErrorCodeEnum.TEAM_NO_EXIT);
        }
        team.setDescription(teamOptional.get().getDescription());
        team.setNumber(teamOptional.get().getNumber());
        team.setUpdatedAt(new Date());
        team.setStatus(TeamStatusEnum.DELETED.getStatus());
        team.setCreatedAt(teamOptional.get().getCreatedAt());
        team.setHeaderNick(teamOptional.get().getHeaderNick());
        team.setHeaderMobile(teamOptional.get().getHeaderMobile());
        team.setHeaderAvatar(teamOptional.get().getHeaderAvatar());
        team.setVisual(teamOptional.get().getVisual());
        team.setType(teamOptional.get().getType());
        team.setPassword(teamOptional.get().getPassword());
        team.setTeamName(teamOptional.get().getTeamName());
        update(team);
    }

    /**
     * 显示队伍列表
     * @return
     */
    @Override
    public List<TeamListVO> queryList() {
        List<Team> teamList = selectAll();
        List<TeamListVO> teamListVOList = new ArrayList<>();
        for (Team team : teamList){
            if (team.getStatus().equals(TeamStatusEnum.NORMAL.getStatus())){
                TeamListVO teamListVO = new TeamListVO();
                teamListVO.setCreatedAt(team.getCreatedAt());
                teamListVO.setDescription(team.getDescription());
                teamListVO.setHeaderAvatar(team.getHeaderAvatar());
                teamListVO.setHeaderNick(team.getHeaderNick());
                teamListVO.setTeamId(team.getTeamId());
                teamListVO.setTeamName(team.getTeamName());
                teamListVO.setType(team.getType());
                BeanUtils.copyProperties(team, teamListVO);
                teamListVOList.add(teamListVO);
            }
        }
        return teamListVOList;
    }

    /**
     * 更改队伍密码
     * @param teamPasswordForm
     * @param userId
     */
    @Override
    public void updatedPassword(TeamPasswordForm teamPasswordForm, String userId){
        Team team = new Team();
        team.setTeamId(teamPasswordForm.getTeamId());
        team.setStatus(TeamStatusEnum.NORMAL.getStatus());
        /**
         * 判断队伍是否存在
         */
        Optional<Team> teamOptional = selectOne(Example.of(team));
        if (!teamOptional.isPresent()){
            throw new TeamException(ErrorCodeEnum.TEAM_NO_EXIT);
        }
        /**
         * 判断当前用户否是队长
         */
        team.setHeaderId(userId);
        Optional<Team> teamOptional1 = selectOne(Example.of(team));
        if (!teamOptional1.isPresent()){
            throw new TeamException(ErrorCodeEnum.NO_JURISDICTION);
        }
        /**
         * 判断原密码是否正确
         */
        if (!teamOptional1.get().getPassword().equals(teamPasswordForm.getFormerPassword())){
            throw new TeamException(ErrorCodeEnum.PASSWORD_ERROR);
        }
        team.setDescription(teamOptional1.get().getDescription());
        team.setNumber(teamOptional1.get().getNumber());
        team.setUpdatedAt(new Date());
        team.setCreatedAt(teamOptional1.get().getCreatedAt());
        team.setHeaderNick(teamOptional1.get().getHeaderNick());
        team.setHeaderMobile(teamOptional1.get().getHeaderMobile());
        team.setHeaderAvatar(teamOptional1.get().getHeaderAvatar());
        team.setVisual(teamOptional1.get().getVisual());
        team.setType(teamOptional1.get().getType());
        team.setPassword(teamPasswordForm.getPassword());
        team.setTeamName(teamOptional1.get().getTeamName());
        update(team);
    }



}
