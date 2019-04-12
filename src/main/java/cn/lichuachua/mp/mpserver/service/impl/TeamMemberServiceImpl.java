package cn.lichuachua.mp.mpserver.service.impl;

import cn.lichuachua.mp.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp.mpserver.entity.Team;
import cn.lichuachua.mp.mpserver.entity.TeamMember;
import cn.lichuachua.mp.mpserver.entity.TeamMemberPK;
import cn.lichuachua.mp.mpserver.entity.User;
import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp.mpserver.enums.TeamMemberStatusEnum;
import cn.lichuachua.mp.mpserver.enums.TeamStatusEnum;
import cn.lichuachua.mp.mpserver.enums.UserStatusEnum;
import cn.lichuachua.mp.mpserver.exception.TeamException;
import cn.lichuachua.mp.mpserver.exception.TeamMemberException;
import cn.lichuachua.mp.mpserver.exception.UserException;
import cn.lichuachua.mp.mpserver.service.ITeamMemberService;
import cn.lichuachua.mp.mpserver.service.ITeamService;
import cn.lichuachua.mp.mpserver.service.IUserService;
import cn.lichuachua.mp.mpserver.vo.TeamMemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author 李歘歘
 */
@Service
public class TeamMemberServiceImpl extends BaseServiceImpl<TeamMember, TeamMemberPK> implements ITeamMemberService {

    @Autowired
    private ITeamService teamService;

    @Autowired
    private IUserService userService;


    /**
     * 加入队伍
     * @param teamId
     * @param userId
     */
    @Override
    public  void joinTeam(String teamId, String userId){
        /**
         * 查询teamId是否存在
         */
        Team team = new Team();
        team.setTeamId(teamId);
        team.setStatus(TeamStatusEnum.NORMAL.getStatus());
        Optional<Team> teamOpenOption = teamService.selectOne(Example.of(team));
        if (!teamOpenOption.isPresent()){
            throw new TeamException(ErrorCodeEnum.TEAM_NO_EXIT);
        }
        TeamMember teamMember = new TeamMember();
        /**
         * 查询该成员是否已经加入
         */
        teamMember.setTeamId(teamId);
        teamMember.setUserId(userId);
        teamMember.setStatus(TeamMemberStatusEnum.NORMAL.getStatus());
        Optional<TeamMember> teamMemberOptional = selectOne(Example.of(teamMember));
        if (teamMemberOptional.isPresent()){
            throw new TeamMemberException(ErrorCodeEnum.TEAMMEMBER_EXIT);
        }
        teamMember.setCreatedAt(new Date());
        teamMember.setUpdatedAt(new Date());
        save(teamMember);
    }

    @Override
    public void exitTeam(String teamId, String userId){
        /**
         * 查询teamId是否存在
         */
        Team team = new Team();
        team.setTeamId(teamId);
        team.setStatus(TeamStatusEnum.NORMAL.getStatus());
        Optional<Team> teamOpenOption = teamService.selectOne(Example.of(team));
        if (!teamOpenOption.isPresent()){
            throw new TeamException(ErrorCodeEnum.TEAM_NO_EXIT);
        }
        TeamMember teamMember = new TeamMember();
        /**
         * 查询该成员是否已经加入
         */
        teamMember.setTeamId(teamId);
        teamMember.setUserId(userId);
        teamMember.setStatus(TeamMemberStatusEnum.NORMAL.getStatus());
        Optional<TeamMember> teamMemberOptional = selectOne(Example.of(teamMember));
        if (!teamMemberOptional.isPresent()){
            throw new TeamMemberException(ErrorCodeEnum.TEAMMEMBER_NO_EXIT);
        }
        teamMember.setCreatedAt(new Date());
        teamMember.setStatus(TeamMemberStatusEnum.DELETED.getStatus());
        teamMember.setUpdatedAt(teamMemberOptional.get().getUpdatedAt());
        update(teamMember);
    }


    @Override
    public List<TeamMemberVO> queryList(String teamId){
        List<TeamMember> teamMemberList = selectAll();
        List<TeamMemberVO> teamMemberVOList = new ArrayList<>();
        for (TeamMember teamMember : teamMemberList){
            TeamMemberVO teamMemberVO = new TeamMemberVO();
            if (teamMember.getTeamId().equals(teamId)&&teamMember.getStatus().equals(TeamMemberStatusEnum.NORMAL.getStatus())){
                /**
                 * 根据userId 查出用户信息
                 */
                Optional<User> userOptional = userService.selectByKey(teamMember.getUserId());
                if (userOptional.isPresent()){
                    teamMemberVO.setMemberId(teamMember.getUserId());
                    teamMemberVO.setMemberAvatar(userOptional.get().getUserAvatar());
                    teamMemberVO.setMemberNick(userOptional.get().getUserNick());
                    teamMemberVOList.add(teamMemberVO);
                }
            }
        }
        return teamMemberVOList;
    }
}
