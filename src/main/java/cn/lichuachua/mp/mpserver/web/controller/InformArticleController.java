package cn.lichuachua.mp.mpserver.web.controller;

import cn.lichuachua.mp.core.support.web.controller.BaseController;
import cn.lichuachua.mp.mpserver.dto.UserInfoDTO;
import cn.lichuachua.mp.mpserver.service.IIformArticleService;
import cn.lichuachua.mp.mpserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 李歘歘
 */
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@Api(value = "InformArticleController", tags = {"文章举报API"})
@RestController
@RequestMapping(value = "/inform/article")
public class InformArticleController extends BaseController<UserInfoDTO> {
    @Autowired
    private IIformArticleService iIformArticleService;

    /**
     * 举报文章
     * @param articleId
     * @return
     */
    @ApiOperation("举报文章")
    @PostMapping("/publish/{articleId}")
    public ResultWrapper publish(
            @PathVariable(value = "articleId") String articleId){
        /**
         * 获取当前登录的用户
         */
        String userId = getCurrentUserInfo().getUserId();
        /**
         * 举报
         */
        iIformArticleService.publish(articleId, userId);
        return ResultWrapper.success();

    }
}
