package cn.lichuachua.mp.mpserver.web.controller;

import cn.lichuachua.mp.core.support.web.controller.BaseController;
import cn.lichuachua.mp.mpserver.dto.UserInfoDTO;
import cn.lichuachua.mp.mpserver.entity.TeamResource;
import cn.lichuachua.mp.mpserver.form.TeamResourcePublishForm;
import cn.lichuachua.mp.mpserver.service.ITeamResourceService;
import cn.lichuachua.mp.mpserver.util.FileUtil;
import cn.lichuachua.mp.mpserver.vo.TeamResourceListVO;
import cn.lichuachua.mp.mpserver.vo.TeamResourceVO;
import cn.lichuachua.mp.mpserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * @author  李歘歘
 */
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@Api(value = "TeamResourceController", tags = {"队伍资源API"})
@RestController
@RequestMapping(value = "/teamResource")
public class TeamResourceController extends BaseController<UserInfoDTO> {
    @Autowired
    private ITeamResourceService teamResourceService;


    /**
     * 发布资源
     * @param teamResourcePublishForm
     * @param file
     * @param bindingResult
     * @return
     */
    @ApiOperation("发布资源")
    @PostMapping("/publish")
    public ResultWrapper publish(
            @Valid TeamResourcePublishForm teamResourcePublishForm,
            MultipartFile file,
            BindingResult bindingResult){
        /**
         * 参数检验
         */
        validateParams(bindingResult);
        String filePath = "C:/Users/Administrator/Desktop/Mp/multualPlatform/src/main/resources/static/resource/";
        String fileName = file.getOriginalFilename();
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 获取当前登录的用户Id
         */
        String userId = getCurrentUserInfo().getUserId();
        teamResourceService.publish(teamResourcePublishForm,fileName, userId);
        return ResultWrapper.success();
    }


    /**
     *删除资源
     * @param resourceId
     * @return
     */
    @ApiOperation("删除资源")
    @PutMapping("/deleted/{resourceId}")
    public ResultWrapper deleted(
            @PathVariable(value = "resourceId") String resourceId ) {
        /**
         * 获取当前登录的用户Id
         */
        String userId = getCurrentUserInfo().getUserId();
        teamResourceService.deleted(resourceId, userId);
        return ResultWrapper.success();
    }


    /**
     * 资源列表
     * @param teamId
     * @return
     */
    @ApiOperation("资源列表")
    @GetMapping("/queryList/{teamId}")
    public ResultWrapper<List<TeamResourceListVO>> queryList(
            @PathVariable(value = "teamId") String teamId ) {
        /**
         * 获取当前用户
         */
        String userId = getCurrentUserInfo().getUserId();
        /**
         * 查询列表
         */
        List<TeamResourceListVO> resourceListVOList = teamResourceService.queryList(teamId,userId);
        return ResultWrapper.successWithData(resourceListVOList);
    }

    /**
     * 获取资料详情
     * @param resourceId
     * @return
     */
    @ApiOperation("获取资料详情")
    @GetMapping("/query/{resourceId}")
    public ResultWrapper<TeamResourceVO> query(
            @PathVariable(value = "resourceId") String resourceId ) {
        /**
         * 获取当前用户ID
         */
        String userId = getCurrentUserInfo().getUserId();
        TeamResourceVO teamResourceVO = teamResourceService.query(resourceId, userId);
        return ResultWrapper.successWithData(teamResourceVO);
    }


}