package cn.lichuachua.mp.mpserver.web.controller;

import cn.lichuachua.mp.core.support.web.controller.BaseController;
import cn.lichuachua.mp.mpserver.dto.UserInfoDTO;
import cn.lichuachua.mp.mpserver.service.IFollowService;
import cn.lichuachua.mp.mpserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李歘歘
 */
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@Api(value = "FollowController",tags = {"关注API"})
@RestController
@RequestMapping(value = "/follow")
public class FollowController extends BaseController<UserInfoDTO> {

    @Autowired
    private IFollowService followService;

    @ApiOperation("关注")
    @PostMapping("/{userId}")
    public ResultWrapper<Integer> follow(
            @PathVariable("userId") String userId ){
        /**
         * 获取当前用户
         */
        String userId1 = getCurrentUserInfo().getUserId();
        Integer status  = followService.follow(userId, userId1);
        return ResultWrapper.successWithData(status);
    }



}
