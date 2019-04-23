package cn.lichuachua.mp.mpserver.service.impl;

import cn.lichuachua.mp.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp.mpserver.entity.Team;
import cn.lichuachua.mp.mpserver.entity.TeamMember;
import cn.lichuachua.mp.mpserver.entity.TeamMemberPK;
import cn.lichuachua.mp.mpserver.entity.User;
import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp.mpserver.enums.TeamMemberStatusEnum;
import cn.lichuachua.mp.mpserver.enums.TeamStatusEnum;
import cn.lichuachua.mp.mpserver.exception.TeamException;
import cn.lichuachua.mp.mpserver.exception.TeamMemberException;
import cn.lichuachua.mp.mpserver.form.JoinPrivateTeamForm;
import cn.lichuachua.mp.mpserver.form.RemoveMember;
import cn.lichuachua.mp.mpserver.service.*;
import cn.lichuachua.mp.mpserver.vo.TeamMemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static cn.lichuachua.mp.mpserver.util.MD5Util.string2MD5;

/**
 * @author 李歘歘
 */
@Service
public class TeamMemberServiceImpl extends BaseServiceImpl<TeamMember, TeamMemberPK> implements ITeamMemberService {

    @Autowired
    private ITeamService teamService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAcademyService academyService;

    @Autowired
    private ISchoolService schoolService;


    /**
     * 加入公有队伍
     * @param teamId
     * @param userId
     */
    @Override
    public  void joinPublicTeam(String teamId, String userId){
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
        teamMember.setTeamId(teamId);
        /**
         * 查看队伍人数是否已经满了
         */
        long count = selectCountByExample(Example.of(teamMember));
        if (count>=teamOpenOption.get().getNumber()){
            throw new TeamMemberException(ErrorCodeEnum.TEAM_FULL);
        }
        /**
         * 查询该成员是否已经加入
         */
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


    /**
     * 加入私有队伍
     * @param joinPrivateTeamForm
     * @param userId
     */
    @Override
    public void joinPrivateTeam(JoinPrivateTeamForm joinPrivateTeamForm, String userId){
        /**
         * 查询teamId是否存在
         */
        Team team = new Team();
        team.setTeamId(joinPrivateTeamForm.getTeamId());
        team.setStatus(TeamStatusEnum.NORMAL.getStatus());
        Optional<Team> teamOpenOption = teamService.selectOne(Example.of(team));
        if (!teamOpenOption.isPresent()){
            throw new TeamException(ErrorCodeEnum.TEAM_NO_EXIT);
        }
        /**
         * 密码是否正确
         */
        if (!teamOpenOption.get().getPassword().equals(string2MD5(joinPrivateTeamForm.getPassword()))){
            throw new TeamMemberException(ErrorCodeEnum.PASSWORD_ERROR);
        }
        TeamMember teamMember = new TeamMember();
        teamMember.setTeamId(joinPrivateTeamForm.getTeamId());
        /**
         * 查看队伍人数是否已经满了
         */
        long count = selectCountByExample(Example.of(teamMember));
        if (count>=teamOpenOption.get().getNumber()){
            throw new TeamMemberException(ErrorCodeEnum.TEAM_FULL);
        }
        /**
         * 查询该成员是否已经加入
         */
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




    /**
     * 退出队伍
     * @param teamId
     * @param userId
     */
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


    /**
     * 查询队伍
     * @param teamId
     * @return
     */
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
                    teamMemberVO.setAcademyName(academyService.queryAcademyName(userOptional.get().getUserId(),userOptional.get().getAcademyId()));
                    teamMemberVO.setSchoolName(schoolService.querySchoolName(userOptional.get().getUserId(),userOptional.get().getSchoolId()));
                    teamMemberVOList.add(teamMemberVO);
                }
            }
        }
        return teamMemberVOList;
    }


    /**
     * 将某人移除队伍
     * @param removeMember
     * @param userId
     */
    @Override
    public void remove(RemoveMember removeMember, String userId){
        /**
         * 判断队伍是否存在
         */
        Team team = new Team();
        team.setTeamId(removeMember.getTeamId());
        team.setStatus(TeamStatusEnum.NORMAL.getStatus());
        Optional<Team> teamOptional = teamService.selectOne(Example.of(team));
        if (!teamOptional.isPresent()){
            throw new TeamException(ErrorCodeEnum.TEAM_NO_EXIT);
        }
        /**
         * 查询当前登录的用户是否是队长
         */
        if (!teamOptional.get().getHeaderId().equals(userId)){
            throw new TeamException(ErrorCodeEnum.NO_JURISDICTION);
        }
        /**
         * 判断密码是否正确
         */
        if (!teamOptional.get().getPassword().equals(string2MD5(removeMember.getPassword()))){
            throw new TeamException(ErrorCodeEnum.PASSWORD_ERROR);
        }
        /**
         * 队长不能将自己移除
         */
        if (userId.equals(removeMember.getMemberId())){
            throw new TeamMemberException(ErrorCodeEnum.NO_REMOVE_SELF);
        }
        /**
         * 查看该成员是否在队伍
         */
        TeamMember teamMember = new TeamMember();
        teamMember.setUserId(removeMember.getMemberId());
        teamMember.setTeamId(removeMember.getTeamId());
        teamMember.setStatus(TeamMemberStatusEnum.NORMAL.getStatus());
        Optional<TeamMember> teamMemberOptional = selectOne(Example.of(teamMember));
        if (!teamMemberOptional.isPresent()){
            throw new TeamMemberException(ErrorCodeEnum.MEMBER_NO_EXIT);
        }
        /**
         * 移除该成员
         */
        teamMember.setStatus(TeamMemberStatusEnum.DELETED.getStatus());
        teamMember.setUpdatedAt(new Date());
        teamMember.setCreatedAt(teamMemberOptional.get().getCreatedAt());
        update(teamMember);
    }
}
