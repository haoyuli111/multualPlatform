package cn.lichuachua.mp.mpserver.web.controller;

import cn.lichuachua.mp.core.support.web.controller.BaseController;
import cn.lichuachua.mp.mpserver.dto.UserInfoDTO;
import cn.lichuachua.mp.mpserver.enums.TeamVisualEnum;
import cn.lichuachua.mp.mpserver.form.*;
import cn.lichuachua.mp.mpserver.service.ITeamService;
import cn.lichuachua.mp.mpserver.service.IUserService;
import cn.lichuachua.mp.mpserver.vo.TeamListVO;
import cn.lichuachua.mp.mpserver.vo.TeamVO;
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
     * @param teamDeletedForm
     * @param bindingResult
     * @return
     */
    @ApiOperation("删除队伍")
    @PutMapping("/deleted")
    public ResultWrapper deleted(
            @Valid TeamDeletedForm teamDeletedForm,
            BindingResult bindingResult){
        /**
         * 参数检验
         */
        validateParams(bindingResult);
        /**
         * 获取当前登录的用户ID
         */
        String userId = getCurrentUserInfo().getUserId();
        teamService.deleted(teamDeletedForm, userId);
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
    @ApiOperation("无信息查询队伍列表")
    @GetMapping("/queryList")
    public ResultWrapper<List<TeamListVO>> queryList(){
        List<TeamListVO> teamListVOList = teamService.queryList();
        return ResultWrapper.successWithData(teamListVOList);
    }


    /**
     * 修改队伍信息
     * @param teamChangeForm
     * @param bindingResult
     * @return
     */
    @ApiOperation("修改队伍信息")
    @PutMapping("/updatedTeamInfor")
    public ResultWrapper updatedTeamInfor(
            @Valid TeamChangeForm teamChangeForm,
            BindingResult bindingResult){
        /**
         * 参数检验
         */
        validateParams(bindingResult);
        /**
         * 获取当前登录的用户Id
         */
        String userId = getCurrentUserInfo().getUserId();
        teamService.updatedTeamInfor(teamChangeForm, userId);
        return ResultWrapper.success();
    }

    /**
     * 队伍转让
     * @param teamTransfer
     * @param bindingResult
     * @return
     */
    @ApiOperation("队伍转让")
    @PutMapping("/transfer")
    public ResultWrapper transfer(
            @Valid TeamTransfer teamTransfer,
            BindingResult bindingResult) {
        /**
         * 检验参数
         */
        validateParams(bindingResult);
        /**
         * 获取当前用户
         */
        String userId = getCurrentUserInfo().getUserId();
        teamService.transfer(teamTransfer, userId);
        return ResultWrapper.success();
    }

    /**
     * 忘记队伍密码
     * @param teamForgetPasswordForm
     * @param bindingResult
     * @return
     */
    @ApiOperation("忘记队伍密码")
    @PutMapping("/forgetPassword")
    public ResultWrapper forgetPassword(
            @Valid TeamForgetPasswordForm teamForgetPasswordForm,
            BindingResult bindingResult){
        /**
         * 检验参数
         */
        validateParams(bindingResult);
        /**
         * 回去当前登录的用户Id
         */
        String userId = getCurrentUserInfo().getUserId();
        /**
         * 在serviceImpl里面进行验证code和mobile
         */
        teamService.forgetPassword(teamForgetPasswordForm, userId);
        return ResultWrapper.success();
    }

    /**
     * 显示公有队伍详情
     * @param teamId
     * @return
     */
    @ApiOperation("显示公有队伍详情")
    @GetMapping("/queryPublic/{teamId}")
    public ResultWrapper<TeamVO> queryPublic(
            @PathVariable(value = "teamId") String teamId) {
            TeamVO teamVO= teamService.queryPublic(teamId);
            return ResultWrapper.successWithData(teamVO);

    }

    /**
     * 显示私有队伍详情
     * @param teamId
     * @return
     */
    @ApiOperation("显示私有队伍详情")
    @GetMapping("/queryPrivate/{teamId}")
    public ResultWrapper<TeamVO> queryPrivate(
            @PathVariable(value = "teamId") String teamId) {
            /**
             * 获取当前登录的用户
             */
            String userId = getCurrentUserInfo().getUserId();
            /**
             * 私有队伍显示详情
             */
            TeamVO teamVO= teamService.queryPrivate(teamId, userId);
            return ResultWrapper.successWithData(teamVO);
    }


    /**
     * 按照队伍类型查找队伍列表
     * @param typeId
     * @return
     */
    @ApiOperation("按照队伍类型查找队伍列表")
    @GetMapping("/queryListByType/{typeId}")
    public ResultWrapper<List<TeamListVO>> queryListByType(
            @PathVariable(value = "typeId") Integer typeId ){
        List<TeamListVO> teamListVOList = teamService.queryListByType(typeId);
        return ResultWrapper.successWithData(teamListVOList);
    }

    /**
     * 按照队伍类型查找队伍列表
     * @param visualId
     * @return
     */
    @ApiOperation("按照队伍公私有查找队伍列表")
    @GetMapping("/queryListByVisual/{visualId}")
    public ResultWrapper<List<TeamListVO>> queryListByVisual(
            @PathVariable(value = "visualId") Integer visualId ){
        List<TeamListVO> teamListVOList = teamService.queryListByVisual(visualId);
        return ResultWrapper.successWithData(teamListVOList);
    }


    /**
     * 按照队伍类型和公私有类型共同查找队伍列表
     * @param teamListForm
     * @param bindingResult
     * @return
     */
    @ApiOperation("按照队伍类型和公私有类型共同查找队伍列表")
    @GetMapping("/queryListByVisualAndType")
    public ResultWrapper<List<TeamListVO>> queryListByVisualAndType(
            @Valid TeamListForm teamListForm,
            BindingResult bindingResult){
        /**
         * 参数检验
         */
        validateParams(bindingResult);
        List<TeamListVO> teamListVOList = teamService.queryListByVisualAndType(teamListForm);
        return ResultWrapper.successWithData(teamListVOList);
    }

    /**
     * 查找队伍列表
     * @param teamListForm
     * @param bindingResult
     * @return
     */
    @ApiOperation("查找队伍列表")
    @GetMapping("/queryAllList")
    public ResultWrapper<List<TeamListVO>> queryAllList(
            @Valid TeamListForm teamListForm,
            BindingResult bindingResult){
        /**
         * 参数检验
         */
        validateParams(bindingResult);
        List<TeamListVO> teamListVOList = teamService.queryAllList(teamListForm);
        return ResultWrapper.successWithData(teamListVOList);
    }


    /**
     * 根据队伍名查找队伍
     * @param teamName
     * @return
     */
    @ApiOperation("根据队伍名查找队伍")
    @GetMapping("/queryTeamByTeamName/{teamName}")
    public ResultWrapper<TeamListVO> queryTeamByTeamName(
            @PathVariable(value = "teamName") String teamName ){
        TeamListVO teamListVO = teamService.queryTeamByTeamName(teamName);
        return ResultWrapper.successWithData(teamListVO);
    }




}
