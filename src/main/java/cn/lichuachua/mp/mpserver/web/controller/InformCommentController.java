package cn.lichuachua.mp.mpserver.web.controller;

import cn.lichuachua.mp.core.support.web.controller.BaseController;
import cn.lichuachua.mp.mpserver.dto.UserInfoDTO;
import cn.lichuachua.mp.mpserver.service.IIformCommentService;
import cn.lichuachua.mp.mpserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李歘歘
 */
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@Api(value = "InformArticleController", tags = {"评论举报API"})
@RestController
@RequestMapping(value = "/inform/comment")
public class InformCommentController extends BaseController<UserInfoDTO> {
    @Autowired
    private IIformCommentService iIformCommentService;


    /**
     * 评论举报
     * @param commentId
     * @return
     */
    @ApiOperation("举报评论")
    @PostMapping("/publish/{commentId}")
    public ResultWrapper publish(
            @PathVariable(value = "commentId") String commentId){
        /**
         * 获取当前登录的用户
         */
        String userId = getCurrentUserInfo().getUserId();
        /**
         * 举报
         */
        iIformCommentService.publish(commentId, userId);
        return ResultWrapper.success();

    }
}
