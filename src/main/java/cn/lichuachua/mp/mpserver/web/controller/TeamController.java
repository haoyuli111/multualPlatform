package cn.lichuachua.mp.mpserver.web.controller;

import cn.lichuachua.mp.core.support.web.controller.BaseController;
import cn.lichuachua.mp.mpserver.dto.UserInfoDTO;
import cn.lichuachua.mp.mpserver.form.TeamPasswordForm;
import cn.lichuachua.mp.mpserver.form.TeamPublishForm;
import cn.lichuachua.mp.mpserver.service.ITeamService;
import cn.lichuachua.mp.mpserver.vo.TeamListVO;
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
@Api(value = "TeamController", tags = {"队伍API"})
@RestController
@RequestMapping(value = "/team")
public class TeamController extends BaseController<UserInfoDTO> {
    @Autowired
    private ITeamService teamService;

    /**
     * 创建队伍
     * @param teamPublishForm
     * @param bindingResult
     * @return
     */
    @ApiOperation("创建队伍")
    @PostMapping("/publish")
    public ResultWrapper publish(
            @Valid TeamPublishForm teamPublishForm,
            BindingResult bindingResult){
        /**
         * 参数检验
         */
        validateParams( bindingResult);
        /**
         * 获取当前登录的用户Id
         */
        String userId = getCurrentUserInfo().getUserId();
        teamService.publish(teamPublishForm, userId);
        return ResultWrapper.success();
    }

    /**
     * 删除队伍
     * @param teamId
     * @return
     */
    @ApiOperation("删除队伍")
    @PutMapping("/deleted/{teamId}")
    public ResultWrapper deleted(
            @PathVariable(value = "teamId") String teamId ){
        /**
         * 获取当前登录的用户ID
         */
        String userId = getCurrentUserInfo().getUserId();
        teamService.deleted(teamId, userId);
        return ResultWrapper.success();
    }

    /**
     * 更改队伍密码
     * @param teamPasswordForm
     * @param bindingResult
     * @return
     */
    @ApiOperation("修改队伍密码")
    @PutMapping("/updatedPassword")
    public ResultWrapper updatedPassword(
            @Valid TeamPasswordForm teamPasswordForm,
            BindingResult bindingResult) {
        /**
         * 参数检验
         */
        validateParams(bindingResult);
        /**
         * 获取当前登录的用户Id
         */
        String userId = getCurrentUserInfo().getUserId();
        teamService.updatedPassword(teamPasswordForm, userId);
        return ResultWrapper.success();
    }

    /**
     * 显示队伍列表
     * @return
     */
    @ApiOperation("显示队伍列表")
    @GetMapping("/queryList")
    public ResultWrapper<List<TeamListVO>> queryList(){
        List<TeamListVO> teamListVOList = teamService.queryList();
        return ResultWrapper.successWithData(teamListVOList);
    }




}
