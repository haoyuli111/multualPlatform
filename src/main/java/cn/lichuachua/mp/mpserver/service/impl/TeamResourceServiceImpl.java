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
import cn.lichuachua.mp.mpserver.exception.TeamResourceException;
import cn.lichuachua.mp.mpserver.form.TeamResourcePublishForm;
import cn.lichuachua.mp.mpserver.service.ITeamMemberService;
import cn.lichuachua.mp.mpserver.service.ITeamResourceService;
import cn.lichuachua.mp.mpserver.service.ITeamService;
import cn.lichuachua.mp.mpserver.service.IUserService;
import cn.lichuachua.mp.mpserver.vo.TeamResourceListVO;
import cn.lichuachua.mp.mpserver.vo.TeamResourceVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    /**
     * 发布资源
     * @param teamResourcePublishForm
     * @param fileName
     * @param userId
     */
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

    /**
     * 删除资源
     * @param resourceId
     * @param userId
     */
    @Override
    public void deleted(String resourceId, String userId){
        /**
         * 判断resourceId是否存在
         */
        TeamResource teamResource = new TeamResource();
        teamResource.setResourceId(resourceId);
        teamResource.setStatus(TeamResourceStatusEnum.NORMAL.getStatus());
        Optional<TeamResource> teamResourceOptional = selectOne(Example.of(teamResource));
        if (!teamResourceOptional.isPresent()){
            throw new TeamResourceException(ErrorCodeEnum.TEAM_RESOURCE_NO_EXIT);
        }

        /**
         * 根据资源Id取出队长Id
         */
        Optional<Team> teamOptional = teamService.selectByKey(teamResourceOptional.get().getTeamId());
        /**
         * 判断userId
         *  userId是发布人Id或者队长，即可删除
         */
        if ((teamResourceOptional.get().getPublisherId().equals(userId))||(userId.equals(teamOptional.get().getHeaderId()))){
            teamResource.setStatus(TeamResourceStatusEnum.DELETED.getStatus());
            teamResource.setPublisherId(teamResourceOptional.get().getPublisherId());
            teamResource.setResource(teamResourceOptional.get().getResource());
            teamResource.setPublisherNick(teamResourceOptional.get().getPublisherNick());
            teamResource.setUpdatedAt(new Date());
            teamResource.setCreatedAt(teamResourceOptional.get().getCreatedAt());
            teamResource.setResourceName(teamResourceOptional.get().getResourceName());
            teamResource.setTeamId(teamResourceOptional.get().getTeamId());
            update(teamResource);
        }else {
            throw new TeamResourceException(ErrorCodeEnum.NO_JURISDICTION);
        }

    }


    /**
     * 资源列表
     * @param teamId
     * @param userId
     * @return
     */
    @Override
    public List<TeamResourceListVO> queryList(String teamId, String userId){
        /**
         * 判断该退伍是否存在
         */
        Team team = new Team();
        team.setTeamId(teamId);
        team.setStatus(TeamStatusEnum.NORMAL.getStatus());
        Optional<Team> teamOptional = teamService.selectOne(Example.of(team));
        if (!teamOptional.isPresent()){
            throw new TeamException(ErrorCodeEnum.TEAM_NO_EXIT);
        }
        /**
         * 当前登录的用户是否在该团队
         */
        TeamMember teamMember = new TeamMember();
        teamMember.setUserId(userId);
        teamMember.setTeamId(teamId);
        teamMember.setStatus(TeamMemberStatusEnum.NORMAL.getStatus());
        Optional<TeamMember> teamMemberOptional = teamMemberService.selectOne(Example.of(teamMember));
        if (!teamMemberOptional.isPresent()){
            throw new TeamMemberException(ErrorCodeEnum.TEAMMEMBER_NO_EXIT);
        }
        List<TeamResource> teamResourceList = selectAll();
        List<TeamResourceListVO> teamResourceListVOList = new ArrayList<>();
        for (TeamResource teamResource : teamResourceList){
            TeamResourceListVO teamResourceListVO = new TeamResourceListVO();
            /**
             * 资源属于该Team并且正常
             */
            if (teamResource.getTeamId().equals(teamId)&&teamResource.getStatus().equals(TeamResourceStatusEnum.NORMAL.getStatus())){
                teamResourceListVO.setCreatedAt(teamResource.getCreatedAt());
                teamResourceListVO.setPublisherNick(teamResource.getPublisherNick());
                teamResourceListVO.setResourceId(teamResource.getResourceId());
                teamResourceListVO.setResourceName(teamResource.getResourceName());
                BeanUtils.copyProperties(teamResource, teamResourceListVO);
                teamResourceListVOList.add(teamResourceListVO);
            }
        }
        return teamResourceListVOList;
    }


    /**
     * 获取资料详情
     * @param resourceId
     * @param userId
     * @return
     */
    @Override
    public TeamResourceVO query(String resourceId, String userId){
        /**
         * 资源是否存在
         */
        TeamResource teamResource = new TeamResource();
        teamResource.setStatus(TeamResourceStatusEnum.NORMAL.getStatus());
        teamResource.setResourceId(resourceId);
        Optional<TeamResource> teamResourceOptional = selectOne(Example.of(teamResource));
        if (!teamResourceOptional.isPresent()){
            throw new TeamResourceException(ErrorCodeEnum.TEAM_RESOURCE_NO_EXIT);
        }
        /**
         * 当前登录的用户是否在该团队
         */
        TeamMember teamMember = new TeamMember();
        teamMember.setUserId(userId);
        teamMember.setTeamId(teamResourceOptional.get().getTeamId());
        teamMember.setStatus(TeamMemberStatusEnum.NORMAL.getStatus());
        Optional<TeamMember> teamMemberOptional = teamMemberService.selectOne(Example.of(teamMember));
        if (!teamMemberOptional.isPresent()){
            throw new TeamMemberException(ErrorCodeEnum.TEAMMEMBER_NO_EXIT);
        }
        TeamResourceVO teamResourceVO = new TeamResourceVO();
        teamResourceVO.setPublisherId(teamResourceOptional.get().getPublisherId());
        teamResourceVO.setPublisherNick(teamResourceOptional.get().getPublisherNick());
        teamResourceVO.setResource(teamResourceOptional.get().getResource());
        teamResourceVO.setCreatedAt(teamResourceOptional.get().getCreatedAt());
        teamResourceVO.setResourceName(teamResourceOptional.get().getResourceName());
        return teamResourceVO;
    }


    /**
     * 根据资料的Id查询出文件名字
     * @param resourceId
     * @param userId
     * @return
     */
    @Override
    public String download(String resourceId, String userId){

        /**
         * 查看resource是否存在
         */
        TeamResource teamResource = new TeamResource();
        teamResource.setResourceId(resourceId);
        teamResource.setStatus(TeamResourceStatusEnum.NORMAL.getStatus());
        Optional<TeamResource> teamResourceOptional = selectOne(Example.of(teamResource));
        if (!teamResourceOptional.isPresent()){
            throw new TeamResourceException(ErrorCodeEnum.TEAM_RESOURCE_NO_EXIT);
        }
        /**
         * 查询队伍是否存在
         */
        Team team = new Team();
        team.setTeamId(teamResourceOptional.get().getTeamId());
        team.setStatus(TeamStatusEnum.NORMAL.getStatus());
        Optional<Team> teamOptional = teamService.selectOne(Example.of(team));
        if (!teamOptional.isPresent()){
            throw new TeamException(ErrorCodeEnum.TEAM_NO_EXIT);
        }
        /**据userId和teamId查询是用户否在该队伍中
         * 根
         */
        TeamMember teamMember = new TeamMember();
        teamMember.setUserId(userId);
        teamMember.setTeamId(teamResourceOptional.get().getTeamId());
        teamMember.setStatus(TeamMemberStatusEnum.NORMAL.getStatus());
        Optional<TeamMember> teamMemberOptional = teamMemberService.selectOne(Example.of(teamMember));
        if (!teamMemberOptional.isPresent()){
            throw new TeamResourceException(ErrorCodeEnum.TEAMMEMBER_NO_EXIT);
        }
        if (teamResourceOptional.get().getResourceName()==null){
            throw new TeamResourceException(ErrorCodeEnum.TEAM_RESOURCE_NULL);
        }
        return teamResourceOptional.get().getResource();
    }
}
