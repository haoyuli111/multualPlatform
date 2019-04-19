package cn.lichuachua.mp.mpserver.service;

import cn.lichuachua.mp.core.support.service.IBaseService;
import cn.lichuachua.mp.mpserver.dto.TokenInfo;
import cn.lichuachua.mp.mpserver.entity.User;
import cn.lichuachua.mp.mpserver.form.*;
import cn.lichuachua.mp.mpserver.vo.ArticleListVO;
import cn.lichuachua.mp.mpserver.vo.MyTeamListVO;
import cn.lichuachua.mp.mpserver.vo.UserInforVO;
import cn.lichuachua.mp.mpserver.vo.UserVO;
import com.aliyuncs.exceptions.ClientException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author 李歘歘
 * 用户业务类接口
 */

public interface IUserService extends IBaseService<User, String> {
    /**
     * 用户注册
     * @param userRegisterForm
     */
    void register(@Valid UserRegisterForm userRegisterForm);
    /**
     * 登录
     * @param userLoginForm
     * @return
     */
    TokenInfo login(@Valid UserLoginForm userLoginForm);

    /**
     * 退出登录
     * @param userId
     */
    void logout(String userId);

    /**
     * 发送验证码
     * @param
     */
    void sendCode(String mobile) throws ClientException;

    /**
     * 检测验证码和手机号
     * @param mobile
     * @param code
     */
    void verification(@Pattern(message = "手机号不合法", regexp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$") @NotEmpty(message = "请填写手机号") String mobile, @NotEmpty(message = "验证码不为空") String code);



    /**
     * 修改信息
     * @param userInforForm
     */
    void infor(@Valid UserInforForm userInforForm, String userId);

    /**
     * 查询用户
     * @param mobile
     * @return
     */
    UserVO search(String mobile);

    /**
     * 忘记密码
     * @param forgetPasswordForm
     */
    void forget(@Valid ForgetPasswordForm forgetPasswordForm);

    /**
     * 修改密码
     * @param changePasswordForm
     * @param userId
     */
    void changePassword(@Valid ChangePasswordForm changePasswordForm, String userId);

    /**
     * 更换头像
     * @param fileName
     * @param userId
     */
    void updateAvatar(String fileName, String userId);

    /**
     * 查询当前登录的用户
     * @param userId
     * @return
     */
    UserInforVO queryMyInformation(String userId);

    /**
     * 显示我创建的队伍列表
     * @param uesId
     * @return
     */
    List<MyTeamListVO> queryMyTeamList(String uesId);

    /**
     * 显示我加入的队伍列表
     * @param uesId
     * @return
     */
    List<MyTeamListVO> queryMyJoinTeamList(String uesId);


}
