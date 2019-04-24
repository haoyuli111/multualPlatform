package cn.lichuachua.mp.mpserver.web.controller;

import cn.lichuachua.mp.core.support.web.controller.BaseController;
import cn.lichuachua.mp.mpserver.dto.UserInfoDTO;
import cn.lichuachua.mp.mpserver.form.ArticleChangeForm;
import cn.lichuachua.mp.mpserver.form.ArticlePublishForm;
import cn.lichuachua.mp.mpserver.service.IArticleLikeService;
import cn.lichuachua.mp.mpserver.service.IArticleService;
import cn.lichuachua.mp.mpserver.util.FileUtil;
import cn.lichuachua.mp.mpserver.vo.ArticleListVO;
import cn.lichuachua.mp.mpserver.vo.ArticleVO;
import cn.lichuachua.mp.mpserver.wrapper.FileWrapper;
import cn.lichuachua.mp.mpserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     * @param file
     * @param bindingResult
     * @return
     */
    @ApiOperation("发布文章")
    @PostMapping("/publish")
    public ResultWrapper publish(
            @Valid ArticlePublishForm articlePublishForm,
            MultipartFile file,
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
         * 上传文件
         */
        if (file!=(null)){
            //文件路径
            String filePath = "/static/accessory/";
            //文件名
            String fileName = file.getOriginalFilename();
            /**
             * 调用上传文件方法
             */
            try {
                FileUtil.uploadFile(file.getBytes(), filePath, fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            articleService.publish(articlePublishForm, userId, fileName);
        }else {
            articleService.publish(articlePublishForm, userId, null);
        }
        /**
         * 发布文章
         */

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
     * 分页获取文章列表
     * @param pageable
     * @return
     */
    @ApiOperation("分页获取文章列表")
    @GetMapping("/queryListByPage/{pageable}")
    public ResultWrapper<List<ArticleListVO>> queryListByPage(
            @PageableDefault(page = 0,value = 10, sort = {"createdAt"},direction = Sort.Direction.DESC) Pageable pageable){
        /**
         * 获取文章列表
         */
        List<ArticleListVO> articleListVOList = articleService.queryListByPage(pageable);
        return ResultWrapper.successWithData(articleListVOList);
    }

    /**
     * 根据用户Id查询文章用户文章列表
     * @param userId
     * @return
     */
    @ApiOperation("根据用户Id查询文章用户文章列表")
    @GetMapping("/queryUserArticleList/{userId}")
    public ResultWrapper<List<ArticleListVO>> queryUserArticleList(
            @PathVariable("userId") String userId,
            @PageableDefault(page = 0,value = 10, sort = {"createdAt"},direction = Sort.Direction.DESC) Pageable pageable){
        /**
         * 根据用户Id查询文章用户文章列表
         */
        List<ArticleListVO> articleVOList = articleService.queryUserArticleList(userId,pageable);
        return ResultWrapper.successWithData(articleVOList);

    }


    /**
     * 根据文章Id查询当前作者的其他文章
     * @param articleId
     * @return
     */
    @ApiOperation("根据文章Id查询当前作者的其他文章")
    @GetMapping("/queryArticleListByArticleId/{articleId}")
    public ResultWrapper<List<ArticleListVO>> queryArticleListByArticleId(
            @PathVariable(value = "articleId") String articleId,
            @PageableDefault(page = 0,value = 10, sort = {"createdAt"},direction = Sort.Direction.DESC) Pageable pageable){
            List<ArticleListVO> articleListVOList = articleService.queryArticleListByArticleId(articleId,pageable);
            return ResultWrapper.successWithData(articleListVOList);
    }

    /**
     * 下载资料
     * @param articleId
     * @return
     */
    @ApiOperation("下载附件")
    @GetMapping("/download/{articleId}")
    public ResultWrapper<String> download(
            @PathVariable(value = "articleId") String articleId){
        /**
         * 查询出当前的登录的用户Id
         */
        String userId = getCurrentUserInfo().getUserId();
        /**
         * 根据资料的Id查询出文件名字
         */
        String fileName = articleService.download(articleId, userId);
        return ResultWrapper.successWithData(fileName);
    }

    @ApiOperation("文章图片上传")
    @PostMapping(value = "editormdPic",produces = "application/json;charset=UTF-8")
    public FileWrapper editormdPic (@RequestParam(value = "editormd-image-file", required = true) MultipartFile file) throws Exception{
        /**
         * 上传图片
         */
        String filePath = "/static/articleImg/";
        String fileName = file.getOriginalFilename();
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return FileWrapper.success("C:/Users/Administrator/Desktop/Mp/mutualPlatform/target/classes"+fileName);
    }



}
