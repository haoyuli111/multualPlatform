package cn.lichuachua.mp.mpserver.web.controller;


import cn.lichuachua.mp.core.support.web.controller.BaseController;
import cn.lichuachua.mp.mpserver.dto.UserInfoDTO;
import cn.lichuachua.mp.mpserver.entity.ArticleLike;
import cn.lichuachua.mp.mpserver.service.IArticleLikeService;
import cn.lichuachua.mp.mpserver.service.IArticleService;
import cn.lichuachua.mp.mpserver.vo.ArticleLikeVO;
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
@Api(value ="ArticleLikeController",tags = {"点赞API"})
@RestController
@RequestMapping(value = "/article/likes")
public class ArticleLikeController extends BaseController<UserInfoDTO> {

    @Autowired
    private IArticleLikeService articleLikeService;

    /**
     * 点赞
     * @param articleId
     * @return
     */
    @ApiOperation("点赞")
    @PutMapping("/{articleId}")
    public ResultWrapper<Integer> likes(
            @PathVariable("articleId") String articleId ) {
        /**
         * 获取当前登录的用户
         */
        String userId = getCurrentUserInfo().getUserId();
        Integer status = articleLikeService.likes(articleId, userId);
        return ResultWrapper.successWithData(status);
    }


    /**
     * 获取点赞列表
     * @param articleId
     * @return
     */
    @ApiOperation("获取点赞列表")
    @GetMapping("/likesList/{articleId}")
    public ResultWrapper<List<ArticleLikeVO>> likesList(
            @PathVariable("articleId") String articleId){
        List<ArticleLikeVO> circleLikeVOList = articleLikeService.likesList(articleId);
        return ResultWrapper.successWithData(circleLikeVOList);
    }


}
