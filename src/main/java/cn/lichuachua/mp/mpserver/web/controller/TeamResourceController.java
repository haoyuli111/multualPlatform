package cn.lichuachua.mp.mpserver.web.controller;

import cn.lichuachua.mp.core.support.web.controller.BaseController;
import cn.lichuachua.mp.mpserver.dto.UserInfoDTO;
import cn.lichuachua.mp.mpserver.entity.TeamResource;
import cn.lichuachua.mp.mpserver.form.TeamResourcePublishForm;
import cn.lichuachua.mp.mpserver.service.ITeamResourceService;
import cn.lichuachua.mp.mpserver.util.FileUtil;
import cn.lichuachua.mp.mpserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

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

    @ApiOperation("/发布资源")
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


}
