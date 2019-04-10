package cn.lichuachua.mp.mpserver.web.controller;

import cn.lichuachua.mp.core.support.web.controller.BaseController;
import cn.lichuachua.mp.mpserver.dto.UserInfoDTO;
import cn.lichuachua.mp.mpserver.form.ArticleChangeForm;
import cn.lichuachua.mp.mpserver.form.ArticlePublishForm;
import cn.lichuachua.mp.mpserver.service.IArticleLikeService;
import cn.lichuachua.mp.mpserver.service.IArticleService;
import cn.lichuachua.mp.mpserver.vo.ArticleListVO;
import cn.lichuachua.mp.mpserver.vo.ArticleVO;
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
@Api(value = "ArticleController",tags = {"文章API"})
@RestController
@RequestMapping(value = "/article")
public class ArticleController extends BaseController<UserInfoDTO> {
    @Autowired
    private IArticleService articleService;

    @Autowired
    private IArticleLikeService articleLikeService;

    /**
     * 发布文章
     * @param articlePublishForm
     * @param bindingResult
     * @return
     */
    @ApiOperation("发布文章")
    @PostMapping("/publish")
    public ResultWrapper publish(
            @Valid ArticlePublishForm articlePublishForm,
            BindingResult bindingResult) {
        /**
         * 验证参数
         */
        validateParams(bindingResult);

        /**
         * 获取当前登录的用户
         */
        String userId = getCurrentUserInfo().getUserId();
        /**
         * 发布文章
         */
        articleService.publish(articlePublishForm, userId);

        return ResultWrapper.success();

    }

    /**
     * 删除文章
     * @param articlrId
     * @return
     */
    @ApiOperation("删除文章")
    @PutMapping("/delete/{articlrId}")
    public ResultWrapper deletaArticle(
            @PathVariable("articlrId") String articlrId){

        /**
         * 获取当前登录的用户
         */
        String userId= getCurrentUserInfo().getUserId();

        /**
         * 删除文章
         */
        articleService.deletaArticle(articlrId, userId);
        return ResultWrapper.success();
    }



    /**
     * 更改文章
     * @param articleChangeForm
     * @param bindingResult
     * @return
     */
    @ApiOperation("更改文章")
    @PutMapping("/update")
    public ResultWrapper updateArticle(
            @Valid ArticleChangeForm articleChangeForm,
            BindingResult bindingResult) {
        /**
         * 检验参数
         */
        validateParams(bindingResult);

        /**
         * 获取当前登录的用户
         */
        String userId = getCurrentUserInfo().getUserId();

        /**
         * 更改文章
         */
        articleService.updateArticle(articleChangeForm, userId);

        return ResultWrapper.success();

    }


    /**
     * 获取文章详情
     * @param articleId
     * @return
     */
    @ApiOperation("获取文章详情")
    @GetMapping("/query/{articleId}")
    public ResultWrapper<ArticleVO>queryArticle(
            @PathVariable ("articleId") String articleId) {
        /**
         * 获取文章详情
         */
        ArticleVO articleVO = articleService.queryArticle(articleId);
        return ResultWrapper.successWithData(articleVO);

    }

    /**
     * 获取文章列表
     * @return
     */
    @ApiOperation("获取文章列表")
    @GetMapping("/queryList")
    public ResultWrapper<List<ArticleListVO>> queryList(){

        /**
         * 获取文章列表
         */
        List<ArticleListVO> articleVOList = articleService.queryList();
        return ResultWrapper.successWithData(articleVOList);
    }

    /**
     * 根据用户Id查询文章用户文章列表
     * @param userId
     * @return
     */
    @ApiOperation("根据用户Id查询文章用户文章列表")
    @GetMapping("/queryUserArticleList/{userId}")
    public ResultWrapper<List<ArticleListVO>> queryUserArticleList(
            @PathVariable("userId") String userId){
        /**
         * 根据用户Id查询文章用户文章列表
         */
        List<ArticleListVO> articleVOList = articleService.queryUserArticleList(userId);

        return ResultWrapper.successWithData(articleVOList);

    }


}
