package cn.lichuachua.mp.mpserver.service.impl;

import cn.lichuachua.mp.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp.mpserver.entity.Team;
import cn.lichuachua.mp.mpserver.entity.TeamMember;
import cn.lichuachua.mp.mpserver.entity.TeamResource;
import cn.lichuachua.mp.mpserver.entity.User;
import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp.mpserver.enums.TeamMemberStatusEnum;
import cn.lichuachua.mp.mpserver.enums.TeamResourceStatusEnum;
import cn.lichuachua.mp.mpserver.enums.TeamStatusEnum;
import cn.lichuachua.mp.mpserver.exception.TeamException;
import cn.lichuachua.mp.mpserver.exception.TeamMemberException;
import cn.lichuachua.mp.mpserver.form.TeamResourcePublishForm;
import cn.lichuachua.mp.mpserver.service.ITeamMemberService;
import cn.lichuachua.mp.mpserver.service.ITeamResourceService;
import cn.lichuachua.mp.mpserver.service.ITeamService;
import cn.lichuachua.mp.mpserver.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @author  李歘歘
 */
@Service
public class TeamResourceServiceImpl extends BaseServiceImpl<TeamResource, String> implements ITeamResourceService {

    @Autowired
    private IUserService userService;

    @Autowired
    private ITeamService teamService;

    @Autowired
    private ITeamMemberService teamMemberService;


    @Override
    public void publish(TeamResourcePublishForm teamResourcePublishForm, String fileName, String userId){
        /**
         * 查看队伍是否存在
         */
        Team team = new Team();
        team.setTeamId(teamResourcePublishForm.getTeamId());
        team.setStatus(TeamStatusEnum.NORMAL.getStatus());
        Optional<Team> teamOptional = teamService.selectOne(Example.of(team));
        if (!teamOptional.isPresent()){
            throw new TeamException(ErrorCodeEnum.TEAM_NO_EXIT);
        }
        /**
         * 查看当前登录的用户是否在队伍
         */
        TeamMember teamMember = new TeamMember();
        teamMember.setTeamId(teamResourcePublishForm.getTeamId());
        teamMember.setUserId(userId);
        teamMember.setStatus(TeamMemberStatusEnum.NORMAL.getStatus());
        Optional<TeamMember> teamMemberOptional = teamMemberService.selectOne(Example.of(teamMember));
        if (!teamMemberOptional.isPresent()){
            throw new TeamMemberException(ErrorCodeEnum.TEAMMEMBER_NO_EXIT);
        }
        /**
         * 发布资源
         */
        TeamResource teamResource = new TeamResource();
        teamResource.setTeamId(teamResourcePublishForm.getTeamId());
        teamResource.setResource(fileName);
        teamResource.setResourceName(teamResourcePublishForm.getRedourceName());
        teamResource.setCreatedAt(new Date());
        teamResource.setUpdatedAt(new Date());
        teamResource.setStatus(TeamResourceStatusEnum.NORMAL.getStatus());
        teamResource.setPublisherId(userId);
        /**
         * 根据uerId查询用户nick
         */
        Optional<User> userOptional = userService.selectByKey(userId);
        teamResource.setPublisherNick(userOptional.get().getUserNick());
        save(teamResource);
    }
}
