package cn.lichuachua.mp.mpserver.web.controller;

import cn.lichuachua.mp.core.support.web.controller.BaseController;
import cn.lichuachua.mp.mpserver.dto.UserInfoDTO;
import cn.lichuachua.mp.mpserver.form.RemoveMember;
import cn.lichuachua.mp.mpserver.service.ITeamMemberService;
import cn.lichuachua.mp.mpserver.vo.TeamMemberVO;
import cn.lichuachua.mp.mpserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 李歘歘
 */
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@Api(value = "TeamMemberController", tags = {"队伍成员API"})
@RestController
@RequestMapping(value = "/teamMember")
public class TeamMemberController extends BaseController<UserInfoDTO> {

    @Autowired
    private ITeamMemberService teamMemberService;

    /**
     * 加入队伍
     * @param teamId
     * @return
     */
    @ApiOperation("加入队伍")
    @PostMapping("/joinTeam/{teamId}")
    public ResultWrapper joinTeam(
            @PathVariable(value = "teamId") String teamId ){
        /**
         * 获取当前登录的用户Id
         */
        String userId = getCurrentUserInfo().getUserId();
        teamMemberService.joinTeam(teamId, userId);
        return ResultWrapper.success();
    }

    /**
     * 退出队伍
     */
    @ApiOperation("退出队伍")
    @PutMapping("/exitTeam/{teamId}")
    public ResultWrapper exitTeam(
            @PathVariable(value = "teamId") String teamId ){
        /**
         * 获取当前用户的Id
         */
        String userId = getCurrentUserInfo().getUserId();
        teamMemberService.exitTeam(teamId,userId);
        return ResultWrapper.success();
    }

    /**
     * 根据队伍Id查询出队伍里面的人
     * @param teamId
     * @return
     */
    @ApiOperation("根据队伍Id查询出队伍里面的人")
    @GetMapping("/queryList/{teamId}")
    public ResultWrapper<List<TeamMemberVO>> queryList(
            @PathVariable(value = "teamId") String teamId ){
        List<TeamMemberVO> teamMemberVOList= teamMemberService.queryList(teamId);
        return ResultWrapper.successWithData(teamMemberVOList);
    }

    /**
     * 将某人移除队伍
     * @param removeMember
     * @param bindingResult
     * @return
     */
    @ApiOperation("将某人移出团队")
    @PutMapping("/remove")
    public ResultWrapper remove(
            @Valid RemoveMember removeMember,
            BindingResult bindingResult){
        /**
         * 参数检验
         */
        validateParams(bindingResult);
        /**
         * 获取当前登录的用户Id
         */
        String userId = getCurrentUserInfo().getUserId();
        teamMemberService.remove(removeMember, userId);
        return ResultWrapper.success();
    }

}
