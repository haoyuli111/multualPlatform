package cn.lichuachua.mp.mpserver.web.controller;

import cn.lichuachua.mp.core.support.web.controller.BaseController;
import cn.lichuachua.mp.mpserver.dto.UserInfoDTO;
import cn.lichuachua.mp.mpserver.form.ArticleCommentForm;
import cn.lichuachua.mp.mpserver.service.IArticleCommentService;
import cn.lichuachua.mp.mpserver.vo.ArticleCommentVO;
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
@Api(value = "ArticleCommentController", tags = {"评论API"})
@RestController
@RequestMapping(value = "/artircle/comment")
public class ArticleCommentController extends BaseController<UserInfoDTO> {
    @Autowired
        private IArticleCommentService articleCommentService;

    /**
     * 发布评论
     * @param articleCommentForm
     * @param bindingResult
     * @return
     */
    @ApiOperation("发布评论")
    @PostMapping("/publish")
    public ResultWrapper publish(
            @Valid ArticleCommentForm articleCommentForm ,
            BindingResult bindingResult){
        /**
         * 验证参数
         */
        validateParams(bindingResult);
        /**
         *获取当前登录的用户Id
         */
        String userId = getCurrentUserInfo().getUserId();
        articleCommentService.publish(articleCommentForm, userId);
        return ResultWrapper.success();
    }

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    @ApiOperation("删除评论")
    @DeleteMapping("/delete/{commentId}")
    public ResultWrapper deleteComment(
            @PathVariable("commentId") String commentId){
        /**
         * 获取当前登录的用户Id
         */
        String userId = getCurrentUserInfo().getUserId();
        articleCommentService.deleteComment(commentId, userId);
        return ResultWrapper.success();
    }

    /**
     * 查看评论列表
     * @param articleId
     * @return
     */
    @ApiOperation("评论列表")
    @GetMapping("/query/{articleId}")
    public ResultWrapper<List<ArticleCommentVO>> queryComment(
            @PathVariable ("articleId") String articleId){
        List<ArticleCommentVO> articleCommentVOList = articleCommentService.queryComment(articleId);
        return ResultWrapper.successWithData(articleCommentVOList);

    }

}
