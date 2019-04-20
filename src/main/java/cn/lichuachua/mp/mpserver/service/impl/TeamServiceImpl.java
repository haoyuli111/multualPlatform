package cn.lichuachua.mp.mpserver.service.impl;

import cn.lichuachua.mp.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp.mpserver.entity.Team;
import cn.lichuachua.mp.mpserver.entity.TeamMember;
import cn.lichuachua.mp.mpserver.entity.TeamType;
import cn.lichuachua.mp.mpserver.entity.User;
import cn.lichuachua.mp.mpserver.enums.*;
import cn.lichuachua.mp.mpserver.exception.TeamException;
import cn.lichuachua.mp.mpserver.exception.TeamMemberException;
import cn.lichuachua.mp.mpserver.exception.TeamTypeException;
import cn.lichuachua.mp.mpserver.exception.UserException;
import cn.lichuachua.mp.mpserver.form.*;
import cn.lichuachua.mp.mpserver.service.ITeamMemberService;
import cn.lichuachua.mp.mpserver.service.ITeamService;
import cn.lichuachua.mp.mpserver.service.ITeamTypeService;
import cn.lichuachua.mp.mpserver.service.IUserService;
import cn.lichuachua.mp.mpserver.vo.TeamListVO;
import cn.lichuachua.mp.mpserver.vo.TeamVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
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
    @Autowired
    private ITeamMemberService teamMemberService;
    @Autowired
    private ITeamTypeService teamTypeService;

    /**
     * 创建队伍
     * @param teamPublishForm
     * @param userId
     */
    @Override
    public void publish(TeamPublishForm teamPublishForm, String userId){
        /**
         * 查看该队伍名是否存在
         */
        Team team = new Team();
        team.setTeamName(teamPublishForm.getTeamName());
        Optional<Team> teamOptional = selectOne(Example.of(team));
        if (teamOptional.isPresent()){
            throw new TeamException(ErrorCodeEnum.TEAM_EXIT);
        }
        team.setDescription(teamPublishForm.getDescription());
        team.setPassword(teamPublishForm.getPassword());
        /**
         * 查看队伍类型是否存在
         */
        TeamType teamType = new TeamType();
        teamType.setTypeId(teamPublishForm.getType());
        teamType.setStatus(TeamTypeEnum.NORMAL.getStatus());
        Optional<TeamType> teamTypeOptional = teamTypeService.selectOne(Example.of(teamType));
        if (!teamTypeOptional.isPresent()){
            throw new TeamTypeException(ErrorCodeEnum.TEAM_TYPE_NO_EXIT);
        }
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
        /**
         * 创建队伍完成后，将创建人加入进去
         *  1，查询出队伍Id
         *  2，加入
         */
        String teamId = queryTeamIdByTeamName(teamPublishForm.getTeamName());
        teamMemberService.joinPublicTeam(teamId, userId);
    }


    /**
     * 根据队伍名查找队伍Id
     * @param teamName
     * @return
     */
    @Override
    public String queryTeamIdByTeamName(String teamName){
        Team team = new Team();
        team.setTeamName(teamName);
        team.setStatus(TeamStatusEnum.NORMAL.getStatus());
        Optional<Team> teamOptional = selectOne(Example.of(team));
        if (!teamOptional.isPresent()){
            throw new TeamException(ErrorCodeEnum.TEAM_NO_EXIT);
        }
        return teamOptional.get().getTeamId();
    }

    /**
     * 删除队伍
     * @param teamDeletedForm
     * @param userId
     */
    @Override
    public void deleted(TeamDeletedForm teamDeletedForm, String userId) {
        /**
         * team是否存在
         */
        Team team = new Team();
        team.setTeamId(teamDeletedForm.getTeamId());
        team.setStatus(TeamStatusEnum.NORMAL.getStatus());
        Optional<Team> teamOptional1 = selectOne(Example.of(team));
        if (!teamOptional1.isPresent()){
            throw new TeamException(ErrorCodeEnum.TEAM_NO_EXIT);
        }
        /**
         * 查看登录的用户是否是队长
         */
        team.setHeaderId(userId);
        Optional<Team> teamOptional = selectOne(Example.of(team));
        if (!teamOptional.isPresent()){
            throw new TeamException(ErrorCodeEnum.NO_JURISDICTION);
        }
        /**
         * 判断密码是否正确
         */
        if (!teamOptional.get().getPassword().equals(teamDeletedForm.getPassword())){
            throw new TeamException(ErrorCodeEnum.PASSWORD_ERROR);
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
                if (team.getVisual().equals(TeamVisualEnum.VISUAL.getStatus())){
                    teamListVO.setVisual(TeamVisualEnum.VISUAL.getDesc());
                }else if (team.getVisual().equals(TeamVisualEnum.NO_VISUAL.getStatus())){
                    teamListVO.setVisual(TeamVisualEnum.NO_VISUAL.getDesc());
                }
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
        /**
         * 判断新密码和确认密码是否一致
         */
        if (!teamPasswordForm.getPassword().equals(teamPasswordForm.getConfirmPassword())){
            throw new TeamException(ErrorCodeEnum.TWO_PASSWORD_NO_EQUALS);
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


    /**
     * 修改队伍信息
     * @param teamChangeForm
     * @param userId
     */
    @Override
    public void updatedTeamInfor(TeamChangeForm teamChangeForm, String userId){
        /**
         * Team是否存在
         */
        Team team = new Team();
        team.setTeamId(teamChangeForm.getTeamId());
        team.setStatus(TeamStatusEnum.NORMAL.getStatus());
        Optional<Team> teamOptional = selectOne(Example.of(team));
        if (!teamOptional.isPresent()){
            throw new TeamException(ErrorCodeEnum.TEAM_NO_EXIT);
        }

        /**
         * 查看当前登录的用户是否是队长
         */
        team.setHeaderId(userId);
        Optional<Team> teamOptional1 = selectOne(Example.of(team));
        if (!teamOptional1.isPresent()){
            throw new TeamException(ErrorCodeEnum.NO_JURISDICTION);
        }
        /**
         * 查看密码是否正确
         */
        if (!teamOptional1.get().getPassword().equals(teamChangeForm.getPassword())){
            throw new TeamException(ErrorCodeEnum.PASSWORD_ERROR);
        }
        team.setDescription(teamChangeForm.getDescription());
        team.setNumber(teamChangeForm.getNumber());
        team.setUpdatedAt(new Date());
        team.setCreatedAt(teamOptional1.get().getCreatedAt());
        team.setHeaderNick(teamOptional1.get().getHeaderNick());
        team.setHeaderMobile(teamOptional1.get().getHeaderMobile());
        team.setHeaderAvatar(teamOptional1.get().getHeaderAvatar());
        team.setVisual(teamChangeForm.getVisual());
        team.setType(teamChangeForm.getType());
        team.setPassword(teamOptional1.get().getPassword());
        team.setTeamName(teamChangeForm.getTeamName());
        update(team);
    }


    /**
     * 队伍转让
     * @param teamTransfer
     * @param userId
     */
    @Override
    public void transfer(TeamTransfer teamTransfer, String userId){
        /**
         * Team是否存在
         */
        Team team = new Team();
        team.setTeamId(teamTransfer.getTeamId());
        team.setStatus(TeamStatusEnum.NORMAL.getStatus());
        Optional<Team> teamOptional = selectOne(Example.of(team));
        if (!teamOptional.isPresent()){
            throw new TeamException(ErrorCodeEnum.TEAM_NO_EXIT);
        }

        /**
         * 查看当前登录的用户是否是队长
         */
        team.setHeaderId(userId);
        Optional<Team> teamOptional1 = selectOne(Example.of(team));
        if (!teamOptional1.isPresent()){
            throw new TeamException(ErrorCodeEnum.NO_JURISDICTION);
        }
        /**
         * 查看手机号是否注册，用户状态是否正常
         */
        User user = new User();
        user.setMobile(teamTransfer.getMobile());
        user.setStatus(UserStatusEnum.NORMAL.getStatus());
        Optional<User> userOptional = userService.selectOne(Example.of(user));
        if (!userOptional.isPresent()){
            throw new UserException(ErrorCodeEnum.ERROR_USER_OR_MOBILE_DELETE);
        }
        /**
         * 查看密码是否正确
         */
        if (!teamOptional1.get().getPassword().equals(teamTransfer.getPassword())){
            throw new TeamException(ErrorCodeEnum.PASSWORD_ERROR);
        }
        team.setDescription(teamOptional1.get().getDescription());
        team.setNumber(teamOptional1.get().getNumber());
        team.setUpdatedAt(new Date());
        team.setCreatedAt(teamOptional1.get().getCreatedAt());
        team.setHeaderNick(userOptional.get().getUserNick());
        team.setHeaderMobile(userOptional.get().getMobile());
        team.setHeaderAvatar(userOptional.get().getUserAvatar());
        team.setHeaderId(userOptional.get().getUserId());
        team.setVisual(teamOptional1.get().getVisual());
        team.setType(teamOptional1.get().getType());
        team.setPassword(teamOptional1.get().getPassword());
        team.setTeamName(teamOptional1.get().getTeamName());
        update(team);
    }


    /**
     * 忘记队伍密码
     * @param teamForgetPasswordForm
     * @param userId
     */
    @Override
    public void forgetPassword(TeamForgetPasswordForm teamForgetPasswordForm, String userId){
        /**
         * Team是否存在
         */
        Team team = new Team();
        team.setTeamId(teamForgetPasswordForm.getTeamId());
        team.setStatus(TeamStatusEnum.NORMAL.getStatus());
        Optional<Team> teamOptional = selectOne(Example.of(team));
        if (!teamOptional.isPresent()){
            throw new TeamException(ErrorCodeEnum.TEAM_NO_EXIT);
        }

        /**
         * 查看当前登录的用户是否是队长
         */
        team.setHeaderId(userId);
        Optional<Team> teamOptional1 = selectOne(Example.of(team));
        if (!teamOptional1.isPresent()){
            throw new TeamException(ErrorCodeEnum.NO_JURISDICTION);
        }
        /**
         * 判断新密码和确认密码是否一致
         */
        if (!teamForgetPasswordForm.getPassword().equals(teamForgetPasswordForm.getConfirmPassword())){
            throw new TeamException(ErrorCodeEnum.TWO_PASSWORD_NO_EQUALS);
        }
        /**
         * 更新team信息
         */
        team.setDescription(teamOptional1.get().getDescription());
        team.setNumber(teamOptional1.get().getNumber());
        team.setUpdatedAt(new Date());
        team.setCreatedAt(teamOptional1.get().getCreatedAt());
        team.setHeaderNick(teamOptional1.get().getHeaderNick());
        team.setHeaderMobile(teamOptional1.get().getHeaderMobile());
        team.setHeaderAvatar(teamOptional1.get().getHeaderAvatar());
        team.setVisual(teamOptional1.get().getVisual());
        team.setType(teamOptional1.get().getType());
        team.setPassword(teamForgetPasswordForm.getPassword());
        team.setTeamName(teamOptional1.get().getTeamName());
        update(team);
    }


    /**
     * 私有队伍显示详情
     * @param teamId
     * @param userId
     * @return
     */
    @Override
    public TeamVO queryPrivate(String teamId, String userId){
        /**
         * 查看队伍是否存在
         */
        Team team = new Team();
        team.setTeamId(teamId);
        team.setStatus(TeamStatusEnum.NORMAL.getStatus());
        Optional<Team> teamOptional = selectOne(Example.of(team));
        if (!teamOptional.isPresent()){
            throw new TeamException(ErrorCodeEnum.TEAM_NO_EXIT);
        }
        TeamVO teamVO = new TeamVO();
        teamVO.setTeamName(teamOptional.get().getTeamName());
        teamVO.setDescription(teamOptional.get().getDescription());
        teamVO.setHeaderId(teamOptional.get().getHeaderId());
        teamVO.setHeaderAvatar(teamOptional.get().getHeaderAvatar());
        teamVO.setHeaderNick(teamOptional.get().getHeaderNick());
        teamVO.setType(team.getType());
        teamVO.setCreatedAt(teamOptional.get().getCreatedAt());
        /**
         * 队伍是私有的
         *  查看是否是该队伍成员
         */
            TeamMember teamMember = new TeamMember();
            teamMember.setStatus(TeamMemberStatusEnum.NORMAL.getStatus());
            teamMember.setUserId(userId);
            teamMember.setTeamId(teamId);
            Optional<TeamMember> teamMemberOptional = teamMemberService.selectOne(Example.of(teamMember));
            if (!teamMemberOptional.isPresent()){
                /**
                 * 用户不在该队内
                 *  没有权限查看
                 */
                throw new TeamMemberException(ErrorCodeEnum.NO_JURISDICTION);
            }

        teamVO.setTeamMemberVOList(teamMemberService.queryList(teamId));
        return teamVO;
    }

    /**
     * 公有队伍显示详情
     * @param teamId
     * @return
     */
    @Override
    public TeamVO queryPublic(String teamId){
        /**
         * 查看公有队伍是否存在
         */
        Team team = new Team();
        team.setTeamId(teamId);
        team.setStatus(TeamStatusEnum.NORMAL.getStatus());
        Optional<Team> teamOptional = selectOne(Example.of(team));
        if (!teamOptional.isPresent()){
            throw new TeamException(ErrorCodeEnum.TEAM_NO_EXIT);
        }
        /**
         * 如果队伍不是公有的
         */
        if (!teamOptional.get().getVisual().equals(TeamVisualEnum.VISUAL.getStatus())){
            throw new TeamException(ErrorCodeEnum.NO_JURISDICTION);
        }
        TeamVO teamVO = new TeamVO();
        teamVO.setTeamName(teamOptional.get().getTeamName());
        teamVO.setDescription(teamOptional.get().getDescription());
        teamVO.setHeaderId(teamOptional.get().getHeaderId());
        teamVO.setHeaderAvatar(teamOptional.get().getHeaderAvatar());
        teamVO.setHeaderNick(teamOptional.get().getHeaderNick());
        teamVO.setType(team.getType());
        teamVO.setCreatedAt(teamOptional.get().getCreatedAt());
        /**
         * 队伍是公有的
         *  直接可以查看队伍详情
         */
        teamVO.setTeamMemberVOList(teamMemberService.queryList(teamId));
        return teamVO;
    }


    /**
     * 按照队伍类型查找队伍列表
     * @param typeId
     * @return
     */
    @Override
    public List<TeamListVO> queryListByType(Integer typeId) {
        /**
         * 判断该类型是否存在
         */
        TeamType teamType = new TeamType();
        teamType.setTypeId(typeId);
        teamType.setStatus(TeamTypeEnum.NORMAL.getStatus());
        Optional<TeamType> teamTypeOptional = teamTypeService.selectOne(Example.of(teamType));
        if (!teamTypeOptional.isPresent()){
            throw new TeamTypeException(ErrorCodeEnum.TEAM_TYPE_NO_EXIT);
        }
        /**
         * 队伍类型存在，查询该类型的队伍
         */
        List<Team> teamList = selectAll();
        List<TeamListVO> teamListVOList = new ArrayList<>();
        for (Team team : teamList){
            if (team.getStatus().equals(TeamStatusEnum.NORMAL.getStatus())&&team.getType().equals(typeId)){
                TeamListVO teamListVO = new TeamListVO();
                if (team.getVisual().equals(TeamVisualEnum.VISUAL.getStatus())){
                    teamListVO.setVisual(TeamVisualEnum.VISUAL.getDesc());
                }else if (team.getVisual().equals(TeamVisualEnum.NO_VISUAL.getStatus())){
                    teamListVO.setVisual(TeamVisualEnum.NO_VISUAL.getDesc());
                }
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
     * 按照队伍公私有查找队伍列表
     * @param visualId
     * @return
     */
    @Override
    public List<TeamListVO> queryListByVisual(Integer visualId) {
        List<Team> teamList = selectAll();
        List<TeamListVO> teamListVOList = new ArrayList<>();
        for (Team team : teamList){
            if (team.getStatus().equals(TeamStatusEnum.NORMAL.getStatus())&&team.getVisual().equals(visualId)){
                TeamListVO teamListVO = new TeamListVO();
                if (team.getVisual().equals(TeamVisualEnum.VISUAL.getStatus())){
                    teamListVO.setVisual(TeamVisualEnum.VISUAL.getDesc());
                }else if (team.getVisual().equals(TeamVisualEnum.NO_VISUAL.getStatus())){
                    teamListVO.setVisual(TeamVisualEnum.NO_VISUAL.getDesc());
                }
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
     * 按照队伍类型和公私有类型共同查找队伍列表
     * @param teamListForm
     * @return
     */
    @Override
    public List<TeamListVO> queryListByVisualAndType(TeamListForm teamListForm){
            List<Team> teamList = selectAll();
            List<TeamListVO> teamListVOList = new ArrayList<>();
            for (Team team : teamList){
                /**
                 * 队伍正常并且visual和type符合
                 */
                if (team.getStatus().equals(TeamStatusEnum.NORMAL.getStatus())&&team.getType().equals(teamListForm.getType())&&team.getVisual().equals(teamListForm.getVisual())){
                    TeamListVO teamListVO = new TeamListVO();
                    if (team.getVisual().equals(TeamVisualEnum.VISUAL.getStatus())){
                        teamListVO.setVisual(TeamVisualEnum.VISUAL.getDesc());
                    }else if (team.getVisual().equals(TeamVisualEnum.NO_VISUAL.getStatus())){
                        teamListVO.setVisual(TeamVisualEnum.NO_VISUAL.getDesc());
                    }
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
     * 查找队伍列表
     * @param teamListForm
     * @return
     */
    @Override
    public List<TeamListVO> queryAllList(TeamListForm teamListForm){
        List<TeamListVO> teamListVOList = new ArrayList<>();
        if (teamListForm.getType()!=null&&teamListForm.getVisual()!=null){
            /**
             * 如果typeId和visualId都不为空
             */
            teamListVOList = queryListByVisualAndType(teamListForm);
        } else if (teamListForm.getVisual() == null && teamListForm.getType() == null) {
            /**
             * 如果typeId和visualId都为空
             */
            teamListVOList = queryList();
        } else if (teamListForm.getVisual() == null && teamListForm.getType() != null){
            /**
             * 如果typeId不为空，visualId为空
             */
            teamListVOList = queryListByType(teamListForm.getType());
        } else if (teamListForm.getVisual() != null && teamListForm.getType() == null){
            /**
             * 如果typeId为空，visualId不为空
             */
            teamListVOList = queryListByVisual(teamListForm.getVisual());
        }
        return teamListVOList;
    }


    /**
     * 根据队伍名查找队伍
     * @param teamName
     * @return
     */
    @Override
    public TeamListVO queryTeamByTeamName(String teamName){
        /**
         * 查看队伍是否存在
         */
        Team team = new Team();
        team.setTeamName(teamName);
        team.setStatus(TeamStatusEnum.NORMAL.getStatus());
        Optional<Team> teamOptional = selectOne(Example.of(team));
        if (!teamOptional.isPresent()){
            throw new TeamException(ErrorCodeEnum.TEAM_NO_EXIT);
        }
        TeamListVO teamListVO = new TeamListVO();
        if (teamOptional.get().getVisual().equals(TeamVisualEnum.VISUAL.getStatus())){
            teamListVO.setVisual(TeamVisualEnum.VISUAL.getDesc());
        }else if (teamOptional.get().getVisual().equals(TeamVisualEnum.NO_VISUAL.getStatus())){
            teamListVO.setVisual(TeamVisualEnum.NO_VISUAL.getDesc());
        }
        teamListVO.setCreatedAt(teamOptional.get().getCreatedAt());
        teamListVO.setDescription(teamOptional.get().getDescription());
        teamListVO.setHeaderAvatar(teamOptional.get().getHeaderAvatar());
        teamListVO.setHeaderNick(teamOptional.get().getHeaderNick());
        teamListVO.setTeamId(teamOptional.get().getTeamId());
        teamListVO.setTeamName(teamOptional.get().getTeamName());
        teamListVO.setType(teamOptional.get().getType());
        return teamListVO;
    }


    /**
     * 更换头像
     * @param userId
     * @param fileName
     */
    @Override
    public void updateAvatar(String userId, String fileName){
        List<Team> teamList = selectAll();
        for (Team team : teamList){
            if (team.getHeaderId().equals(userId)){
                Team team1 = new Team();
                team1.setDescription(team.getDescription());
                team1.setNumber(team.getNumber());
                team1.setUpdatedAt(team.getUpdatedAt());
                team1.setCreatedAt(team.getCreatedAt());
                team1.setHeaderNick(team.getHeaderNick());
                team1.setHeaderMobile(team.getHeaderMobile());
                team1.setHeaderAvatar(fileName);
                team1.setVisual(team.getVisual());
                team1.setType(team.getType());
                team1.setPassword(team.getPassword());
                team1.setTeamName(team.getTeamName());
                team1.setTeamId(team.getTeamId());
                team1.setStatus(team.getStatus());
                team1.setHeaderId(team.getHeaderId());
                update(team1);
            }
        }
    }

}
