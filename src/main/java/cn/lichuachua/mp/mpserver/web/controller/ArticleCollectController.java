package cn.lichuachua.mp.mpserver.web.controller;

import cn.lichuachua.mp.core.support.web.controller.BaseController;
import cn.lichuachua.mp.mpserver.dto.UserInfoDTO;
import cn.lichuachua.mp.mpserver.service.IArticleCollectService;
import cn.lichuachua.mp.mpserver.vo.ArticleCollectVO;
import cn.lichuachua.mp.mpserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 李歘歘
 */
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@Api(value = "ArticleCollectController", tags = {"收藏API"})
@RestController
@RequestMapping(value = "article/collect")
public class ArticleCollectController extends BaseController<UserInfoDTO> {

    @Autowired
    private IArticleCollectService articleCollectService;

    /**
     * 收藏
     * @param articleId
     * @return
     */
    @ApiOperation("收藏")
    @PostMapping("/{articleId}")
    public ResultWrapper<Integer> add(
            @PathVariable("articleId") String articleId ){
        /**
         * 获取当前登录的用户
         */
        String userId = getCurrentUserInfo().getUserId();
        Integer status = articleCollectService.add(articleId, userId);
        return ResultWrapper.successWithData(status);
    }



}
