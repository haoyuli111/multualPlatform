package cn.lichuachua.mp.mpserver.service.impl;

import cn.lichuachua.mp.common.util.CodeUtil;
import cn.lichuachua.mp.common.util.NickNameUtil;
import cn.lichuachua.mp.common.util.TokenUtil;
import cn.lichuachua.mp.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp.mpserver.dto.TokenInfo;
import cn.lichuachua.mp.mpserver.dto.VerificationCodeInfo;
import cn.lichuachua.mp.mpserver.entity.*;
import cn.lichuachua.mp.mpserver.enums.*;
import cn.lichuachua.mp.mpserver.exception.UserException;
import cn.lichuachua.mp.mpserver.form.*;
import cn.lichuachua.mp.mpserver.repository.redis.IRedisRepository;
import cn.lichuachua.mp.mpserver.service.IAcademyService;
import cn.lichuachua.mp.mpserver.service.ISchoolService;
import cn.lichuachua.mp.mpserver.service.IUserService;
import cn.lichuachua.mp.mpserver.util.SendCodeUtil;
import cn.lichuachua.mp.mpserver.vo.ArticleListVO;
import cn.lichuachua.mp.mpserver.vo.UserVO;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author 李歘歘
 * 用户业务类接口实现类
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User,String> implements IUserService {

    @Autowired
    private IRedisRepository redisRepository;

    @Autowired
    private ISchoolService schoolService;

    @Autowired
    private IAcademyService academyService;

    /**
     * 用户注册
     */
    @Override
    public void register(UserRegisterForm userRegisterForm){

        User user = new User();
        user.setMobile(userRegisterForm.getMobile());

        /**
         * 检验手机是否被注册过
         */
        Optional<User> userOptional = selectOne(Example.of(user));
        if (userOptional.isPresent()){
            throw new UserException(ErrorCodeEnum.MOBILE_REGISTERED);
        }

        /**
         * 检查两次密码是否相同
         */
        if (!userRegisterForm.getPassword2().equals(userRegisterForm.getConfirmPassword())){
            throw new UserException((ErrorCodeEnum.TWO_PASSWORD_NO_EQUALS));
        }

        /**
         *  保存用户信息到数据库，完成注册
         */
        user.setPassword(userRegisterForm.getPassword2());
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setVisual(UserVisualEnum.VISUAL.getStatus());
        user.setStatus(UserStatusEnum.NORMAL.getStatus());
        user.setUserNick(NickNameUtil.genNick());
        save(user);
    }





    /**
     * 检测验证码和手机号,正确则将验证码和手机号的关系删除，不正确则返回验证码不正确，
     * @param mobile
     * @param code
     */
    @Override
    public void verification(String mobile, String code){

        /**
         * 根据手机号查出验证码
         */
        String smsCode = redisRepository.findVerificationCodeByMobile(mobile);
        System.out.println(smsCode);
        /**
         * 手机号和验证码不存在提示验证码异常
         */
        if (smsCode == null){
            throw new UserException(ErrorCodeEnum.VERIFICATION_CODE_EXCEPTION);
        }

        /**
         * 验证码存在，正确则删除，不正确则提示验证码错误
         */
        if (smsCode.equals(code)){
            redisRepository.deleteVerificationCodeByMobile(mobile);
        }else {
            throw new UserException(ErrorCodeEnum.VERIFICATION_CODE_ERROR);
        }

    }

    /**
     * 修改用户信息
     * @param userInforForm
     * @param userId
     */
    @Override
    public void infor(UserInforForm userInforForm, String userId){
        /**
         * 根据userId更改个人信息
         */
        User user = new User();
        user.setUserId(userId);
        Optional<User> userOptional = selectOne(Example.of(user));
        if (!userOptional.isPresent()){
            throw new UserException(ErrorCodeEnum.ERROR_USER);
        }
        user.setUserNick(userInforForm.getUserNick());
        user.setUserName(userInforForm.getUserName());
        user.setUserNumber(userInforForm.getUserNumber());
        user.setUserEmail(userInforForm.getUserEmail());
        user.setVisual(userInforForm.getVisual());
        user.setSchoolId(userInforForm.getSchoolId());
        user.setAcademyId(userInforForm.getAcademyId());
        user.setStatus(UserStatusEnum.NORMAL.getStatus());
        user.setCreatedAt(userOptional.get().getCreatedAt());
        user.setMobile(userOptional.get().getMobile());
        user.setPassword(userOptional.get().getPassword());
        user.setUpdatedAt(new Date());
        update(user);
    }

    /**
     * 用户登录
     */
    @Override
    public TokenInfo login(UserLoginForm userLoginForm) {
        /**
         * 查找用户
         */
        User user = new User();
        user.setMobile(userLoginForm.getMobile());
        Optional<User> optionalUser = selectOne(Example.of(user));
        /**
         * 查找该用户并且状态正常则允许登录
         */
        if (!optionalUser.isPresent()){
            throw new UserException(ErrorCodeEnum.MOBILE_NOT_REGISTERED);
        }else {
            user.setStatus(UserStatusEnum.NORMAL.getStatus());
            Optional<User> optionalUser1 = selectOne(Example.of(user));
            if (!optionalUser1.isPresent()){
                throw new UserException(ErrorCodeEnum.MOBILE_BANNED);
            }else {
                user = optionalUser1.get();
            }
        }
        /**
         * 判断密码是否正确
         */
        if (!user.getPassword().equals(userLoginForm.getPassword())){
            throw new UserException(ErrorCodeEnum.PASSWORD_ERROR);
        }
        /**
         * 生成accessToken
         */
        String accessToken = TokenUtil.genToken();

        /**
         * 将accessToken和用户信息存入Redis ，并删除旧的Token
         */
        String userId = user.getUserId();
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setUserId(userId);
        tokenInfo.setAccessToken(accessToken);


        //获取旧的Token并删除，---------通知客户端在其他地方登录
        String olderAccessToken = redisRepository.findAccessTokenByUserId(userId);
        if (null !=olderAccessToken){
            //删除旧的Token
            redisRepository.deleteAccessToken(olderAccessToken);
        }

        //保存新的Token，更新当前用户使用的Token
        redisRepository.saveAccessToken(tokenInfo);
        redisRepository.saveUserAccessToken(userId,accessToken);


        /**
         * 返回登录结果
         */
        return  tokenInfo;
    }

    /**
     * 发送验证码
     * @param
     * @throws ClientException
     */
    @Override
    public void sendCode(String mobile) throws ClientException {
        /**
         * 生成验证码
         */
        String smsCode = CodeUtil.smsCode();

        /**
         * 发送验证码
         */
        SendCodeUtil.sendSms(mobile, smsCode);

        /**
         * 存储手机号和短信验证码的关系到redis
         */
        VerificationCodeInfo verificationCodeInfo = new VerificationCodeInfo();
        verificationCodeInfo.setTelephone(mobile);
        verificationCodeInfo.setCode(smsCode);

        //保存验证码和手机号到redis
        redisRepository.saveVerificationCode(verificationCodeInfo);
    }



    /**
     * 退出登录
     * @param userId
     */
    @Override
    public void logout(String userId) {
        User user = new User();
        user.setUserId(userId);
        Optional<User> userOptional = selectOne(Example.of(user));
        /**
         * 查找到该用户
         */
        if (userOptional.isPresent()){
            /**
             * 获取并
             * 删除token
             */
            String token = redisRepository.findAccessTokenByUserId(userId);
            redisRepository.deleteAccessToken(token);
            redisRepository.deleteUserAccessToken(userId);
        }else {
            throw new UserException(ErrorCodeEnum.ERROR_USER);
        }
    }


    /**
     * 根据手机号查询用户
     * @param mobile
     * @return
     */
    @Override
    public UserVO search(String mobile){
        User user = new User();
        user.setMobile(mobile);
        user.setStatus(UserStatusEnum.NORMAL.getStatus());
        user.setVisual(UserVisualEnum.VISUAL.getStatus());
        Optional<User> userOptional = selectOne(Example.of(user));
        if (!userOptional.isPresent()) {
            throw new UserException(ErrorCodeEnum.NO_VISUAL_USER_OR_NO_EXIT);
        }
                UserVO userVO = new UserVO();
                userVO.setUserId(userOptional.get().getUserId());
                userVO.setMobile(userOptional.get().getMobile());
                userVO.setAcademyName(academyService.queryAcademyName(userOptional.get().getAcademyId()));
                userVO.setSchoolName(schoolService.querySchoolName(userOptional.get().getSchoolId()));
                userVO.setUserAvatar(userOptional.get().getUserAvatar());
                userVO.setUserName(userOptional.get().getUserName());
                userVO.setUserEmail(userOptional.get().getUserEmail());
                userVO.setUserNumber(userOptional.get().getUserNumber());
                userVO.setUserNick(userOptional.get().getUserNick());

                return userVO;
    }


    /**
     * 忘记密码
     * @param forgetPasswordForm
     */
    @Override
    public void forget(ForgetPasswordForm forgetPasswordForm ){
        /**
         * 检测该手机号是否注册过,并且账号没有被封禁
         */
        User user = new User();
        user.setMobile(forgetPasswordForm.getMobile());
        user.setStatus(UserStatusEnum.NORMAL.getStatus());
        Optional<User> userOptional = selectOne(Example.of(user));
        if (!userOptional.isPresent()){
            throw new UserException(ErrorCodeEnum.ERROR_USER_OR_MOBILE_DELETE);
        }
        /**
         * 检测密码和重复密码是否一样
         */
        if (!forgetPasswordForm.getPassword().equals(forgetPasswordForm.getConfirmPassword())){
            throw new UserException(ErrorCodeEnum.TWO_PASSWORD_NO_EQUALS);
        }
        user.setUserId(userOptional.get().getUserId());
        user.setVisual(userOptional.get().getVisual());
        user.setPassword(forgetPasswordForm.getPassword());
        user.setUpdatedAt(new Date());
        user.setUserAvatar(userOptional.get().getUserAvatar());
        user.setCreatedAt(userOptional.get().getCreatedAt());
        user.setAcademyId(userOptional.get().getAcademyId());
        user.setSchoolId(userOptional.get().getSchoolId());
        user.setUserEmail(userOptional.get().getUserEmail());
        user.setUserNumber(userOptional.get().getUserNumber());
        user.setUserName(userOptional.get().getUserName());
        user.setUserNick(userOptional.get().getUserNick());
        update(user);
    }

    /**
     * 修改密码
     * @param changePasswordForm
     * @param userId
     */
    @Override
    public void changePassword(ChangePasswordForm changePasswordForm, String userId) {
        /**
         * 判断该用户和原密码以及状态是否正确是否正确
         */
        User user = new User();
        user.setUserId(userId);
        user.setPassword(changePasswordForm.getFormerPassword());
        user.setStatus(UserStatusEnum.NORMAL.getStatus());
        Optional<User> userOptional = selectOne(Example.of(user));
        if (!userOptional.isPresent()){
            throw new UserException(ErrorCodeEnum.FORMER_PASSWORD_ERROR);
        }
        /**
         * 判断新密码和确认密码是否一致
         */
        if (!changePasswordForm.getPassword().equals(changePasswordForm.getConfirmPassword())){
            throw new UserException(ErrorCodeEnum.TWO_PASSWORD_NO_EQUALS);
        }
        user.setMobile(userOptional.get().getMobile());
        user.setVisual(userOptional.get().getVisual());
        user.setPassword(changePasswordForm.getPassword());
        user.setUpdatedAt(new Date());
        user.setUserAvatar(userOptional.get().getUserAvatar());
        user.setCreatedAt(userOptional.get().getCreatedAt());
        user.setAcademyId(userOptional.get().getAcademyId());
        user.setSchoolId(userOptional.get().getSchoolId());
        user.setUserEmail(userOptional.get().getUserEmail());
        user.setUserNumber(userOptional.get().getUserNumber());
        user.setUserName(userOptional.get().getUserName());
        user.setUserNick(userOptional.get().getUserNick());
        update(user);
    }

    /**
     * 获取当前登录用户的信息
     * @param userId
     * @return
     */
    @Override
    public UserVO queryMyInformation(String userId){
        User user = new User();
        user.setUserId(userId);
        user.setStatus(UserStatusEnum.NORMAL.getStatus());
        Optional<User> userOptional = selectOne(Example.of(user)) ;
        if (!userOptional.isPresent()){
            throw new UserException(ErrorCodeEnum.ERROR_USER);
        }
        UserVO userVO = new UserVO();
        userVO.setUserNick(userOptional.get().getUserNick());
        userVO.setUserNumber(userOptional.get().getUserNumber());
        userVO.setUserEmail(userOptional.get().getUserEmail());
        userVO.setUserName(userOptional.get().getUserName());
        userVO.setUserAvatar(userOptional.get().getUserAvatar());
        userVO.setMobile(userOptional.get().getMobile());
        userVO.setUserId(userOptional.get().getUserId());
        userVO.setAcademyName(academyService.queryAcademyName(userOptional.get().getAcademyId()));
        userVO.setSchoolName(schoolService.querySchoolName(userOptional.get().getSchoolId()));
        return userVO;

    }

    /**
     * 更换头像
     * @param fileName
     * @param userId
     */
    @Override
    public void updateAvatar(String fileName, String userId){
        User user = new User();
        user.setUserId(userId);
        Optional<User> userOptional = selectOne(Example.of(user));
        if (!userOptional.isPresent()){
            throw new UserException(ErrorCodeEnum.ERROR_USER);
        }
        user.setMobile(userOptional.get().getMobile());
        user.setVisual(userOptional.get().getVisual());
        user.setPassword(userOptional.get().getPassword());
        user.setUpdatedAt(new Date());
        user.setUserAvatar(fileName);
        user.setCreatedAt(userOptional.get().getCreatedAt());
        user.setAcademyId(userOptional.get().getAcademyId());
        user.setSchoolId(userOptional.get().getSchoolId());
        user.setUserEmail(userOptional.get().getUserEmail());
        user.setUserNumber(userOptional.get().getUserNumber());
        user.setUserName(userOptional.get().getUserName());
        user.setUserNick(userOptional.get().getUserNick());
        user.setStatus(UserStatusEnum.NORMAL.getStatus());
        update(user);
    }

}
