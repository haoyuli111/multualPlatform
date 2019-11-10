package cn.lichuachua.mp.mpserver.web.controller;

import cn.lichuachua.mp.core.support.web.controller.BaseController;
import cn.lichuachua.mp.mpserver.dto.TokenInfo;
import cn.lichuachua.mp.mpserver.dto.UserInfoDTO;
import cn.lichuachua.mp.mpserver.entity.Follow;
import cn.lichuachua.mp.mpserver.entity.User;
import cn.lichuachua.mp.mpserver.form.*;
import cn.lichuachua.mp.mpserver.service.*;
import cn.lichuachua.mp.mpserver.util.FileUtil;
import cn.lichuachua.mp.mpserver.vo.*;
import cn.lichuachua.mp.mpserver.wrapper.ResultWrapper;
import com.aliyuncs.exceptions.ClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

/**
 * @author 李歘歘
 * 用户接口
 */
@CrossOrigin(origins = "http://www.lichuachua.com:8082", maxAge = 3600)
@Api(value = "UserController", tags = {"用户API"})
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController<UserInfoDTO> {

    @Autowired
    private IUserService userService;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IFollowService followService;

    @Autowired
    private IArticleCollectService articleCollectService;

    /**
     * 用户注册
     * @param userRegisterForm
     * @param bindingResult
     * @return
     */
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public ResultWrapper register(
            @Valid UserRegisterForm userRegisterForm,
            BindingResult bindingResult)  {
        /**
         * 验证参数
         */
        validateParams(bindingResult);

        /**
         * 检测验证码和手机号
         */
        userService.verification(userRegisterForm.getMobile(),userRegisterForm.getCode());

        /**
         * 注册
         */
        userService.register(userRegisterForm);

        return ResultWrapper.success();
    }


    /**
     * 用户登录
     * @param userLoginForm
     * @param bindingResult
     * @return
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResultWrapper login(
            @RequestBody UserLoginForm userLoginForm,
            BindingResult bindingResult) {
        /**
         * 验证参数
         */
        validateParams(bindingResult);

        /**
         * 登录
         */
        TokenInfo tokenInfo = userService.login(userLoginForm);
        /**
         * 返回token
         */
        return ResultWrapper.successWithData(tokenInfo);


    }

    /**
     * 修改用户信息
     * @param userInforForm
     * @param bindingResult
     * @return
     */
    @ApiOperation("/修改个人信息")
    @PutMapping("/infor")
    public ResultWrapper infor(
            @Valid UserInforForm userInforForm,
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
         * 修改个人信息
         */
        userService.infor(userInforForm, userId);
        return ResultWrapper.success();
    }

    /**
     * 发送验证码
     * @param sendCodeForm
     * @param bindingResult
     * @return
     * @throws ClientException
     */
    @ApiOperation("发送验证码")
    @PostMapping("/sendCode")
    public ResultWrapper sendCode(
            @Valid SendCodeForm sendCodeForm,
            BindingResult bindingResult) throws ClientException {
        /**
         * 验证参数
         */
        validateParams(bindingResult);
        /**
         * 发送验证码，并存储
         */
        userService.sendCode(sendCodeForm.getMobile());

        return ResultWrapper.success();

    }

    /**
     * 查询用户
     * @param mobile
     * @return
     */
    @ApiOperation("根据手机号查询用户")
    @GetMapping("/search/{mobile}")
    public ResultWrapper<UserVO> search(
            @PathVariable(value = "mobile") String mobile){
        UserVO userVO = userService.search(mobile);
        return ResultWrapper.successWithData(userVO);
    }


    /**
     * 退出登录
     * @return
     */
    @ApiOperation("退出登录")
    @PutMapping("/logout")
    public ResultWrapper logout(){

        /**
         * 获取当前登录的用户
         */
        String userId = getCurrentUserInfo().getUserId();
        userService.logout(userId);
        return ResultWrapper.success();
    }

    /**
     * 忘记密码
     * @param forgetPasswordForm
     * @param bindingResult
     * @return
     */
    @ApiOperation("忘记密码")
    @PutMapping("/forget")
    public ResultWrapper forget(
            @Valid  ForgetPasswordForm forgetPasswordForm,
            BindingResult bindingResult ){
        /**
         * 检验参数
         */
        validateParams(bindingResult);
        /**
         * 检测手机号和验证码
         */
        userService.verification(forgetPasswordForm.getMobile(),forgetPasswordForm.getCode());
        /**
         * 修改密码
         */
        userService.forget(forgetPasswordForm);
        return ResultWrapper.success();
    }

    /**
     * 修改密码
     * @param changePasswordForm
     * @param bindingResult
     * @return
     */
    @ApiOperation("修改密码")
    @PutMapping("/changePassword")
    public ResultWrapper changePassword(
            @Valid ChangePasswordForm changePasswordForm,
            BindingResult bindingResult){
        /**
         * 验证参数
         */
        validateParams(bindingResult);
        /**
         * 获取当前登录的用户
         */
        String userId = getCurrentUserInfo().getUserId();
        /**
         * 修改密码
         */
        userService.changePassword(changePasswordForm, userId);
        return ResultWrapper.success();
    }

    /**
     * 获取收藏列表
     * @return
     */
    @ApiOperation("获取我的收藏列表")
    @PostMapping("/queryMyCollectList")
    public ResultWrapper<List<ArticleCollectVO>> queryMyCollectList(
            @PageableDefault(page = 0,value = 10, sort = {"createdAt"},direction = Sort.Direction.DESC) Pageable pageable){
        /**
         * 获取当前登录的用户
         */
        String userId = getCurrentUserInfo().getUserId();
        List<ArticleCollectVO> articleCollectVOList = articleCollectService.queryMyCollectList(userId,pageable);
        return ResultWrapper.successWithData(articleCollectVOList);
    }


    /**
     * 查询我的文章列表
     * @return
     */
    @ApiOperation("查询我的文章列表")
    @GetMapping("/queryMyArticleList")
    public ResultWrapper<List<MyArticleListVO>> queryMyArticleList(
            @PageableDefault(page = 0,value = 10, sort = {"createdAt"},direction = Sort.Direction.DESC) Pageable pageable){
        /**
         * 获取当前登录的用户
         */
        String userId = getCurrentUserInfo().getUserId();
        /**
         * 查看我的文章列表
         */
        List<MyArticleListVO> myArticleListVOList = articleService.queryMyArticleList(userId,pageable);

        return ResultWrapper.successWithData(myArticleListVOList);

    }

    /**
     * 获取我关注的人的列表
     * @return
     */
    @ApiOperation("获取我关注的人的列表")
    @GetMapping("/queryMyFollowList")
    public ResultWrapper<List<FollowVO>> queryMyFollowList(
            @PageableDefault(page = 0,value = 10, sort = {"createdAt"},direction = Sort.Direction.DESC) Pageable pageable){
        /**
         * 获取当前登录的用户
         */
        String userId = getCurrentUserInfo().getUserId();
        /**
         * 获取我关注的人的列表
         */
        List<FollowVO> followVOList = followService.queryMyFollowList(userId,pageable);
        return ResultWrapper.successWithData(followVOList);
    }

    /**
     * 获取关注我的人的列表
     * @return
     */
    @ApiOperation("获取关注我的人的列表")
    @GetMapping("/queryFollowedMeList")
    public ResultWrapper<List<FollowVO>> queryFollowedMeList(
            @PageableDefault(page = 0,value = 10, sort = {"createdAt"},direction = Sort.Direction.DESC) Pageable pageable){
        /**
         * 获取当前登录的用户
         */
        String userId = getCurrentUserInfo().getUserId();
        /**
         * 获取我关注的人的列表
         */
        List<FollowVO> followVOList = followService.queryFollowedMeList(userId, pageable);
        return ResultWrapper.successWithData(followVOList);
    }


    /***
     * 获取当前登录用户的信息
     * @return
     */
    @ApiOperation("获取当前登录用户的信息")
    @GetMapping("/queryMyInformation")
    public ResultWrapper<UserInforVO> queryMyInformation(){
        /**
         * 获取当前登录用户Id
         */
        String userId = getCurrentUserInfo().getUserId();
        UserInforVO userInforVO = userService.queryMyInformation(userId);
        return ResultWrapper.successWithData(userInforVO);
    }

    /**
     *
     * @param file
     * @return
     */
    @ApiOperation("更换头像")
    @PutMapping("/updateAvatar/{file}")
    public ResultWrapper updateAvatar(
            @PathVariable(value = "file") MultipartFile file) {
        String filePath = "/static/avatar/";
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newFileName = new Date().getTime() + "." + suffix;
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, newFileName);
            FileUtil.uploadFile1(file.getBytes(), filePath, newFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 获取当前登录用户
         */
        String userId= getCurrentUserInfo().getUserId();
        userService.updateAvatar(newFileName, userId);
        return ResultWrapper.success();
    }

    /**
     * 显示我创建的队伍列表
     * @return
     */
    @ApiOperation("显示我创建的队伍列表")
    @GetMapping("queryMyTeamList")
    public ResultWrapper<List<MyTeamListVO>> queryMyTeamList(){
        /**
         * 获取当前登录的用户Id
         */
        String uesId = getCurrentUserInfo().getUserId();
        List<MyTeamListVO> myTeamListVOList = userService.queryMyTeamList(uesId);
        return ResultWrapper.successWithData(myTeamListVOList);
    }

    /**
     * 显示我加入的队伍列表
     * @return
     */
    @ApiOperation("显示我加入的队伍列表")
    @GetMapping("queryMyJoinTeamList")
    public ResultWrapper<List<MyTeamListVO>> queryMyJoinTeamList(){
        /**
         * 获取当前登录的用户Id
         */
        String uesId = getCurrentUserInfo().getUserId();
        List<MyTeamListVO> myTeamListVOList = userService.queryMyJoinTeamList(uesId);
        return ResultWrapper.successWithData(myTeamListVOList);
    }




}
