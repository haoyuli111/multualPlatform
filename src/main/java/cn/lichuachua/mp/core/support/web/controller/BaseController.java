package cn.lichuachua.mp.core.support.web.controller;

import cn.lichuachua.mp.common.constant.SysConstant;
import cn.lichuachua.mp.common.enums.BaseErrorCodeEnum;
import cn.lichuachua.mp.common.util.ThreadLocalMap;
import cn.lichuachua.mp.core.exception.SysException;
import org.springframework.validation.BindingResult;

/**
 * @author 李歘歘
 * 通用Controller类
 * @param <T> 当前登录的Java用户
 */
public class BaseController<T> {

    protected T getCurrentUserInfo(){
        T currentUser = (T) ThreadLocalMap.get(SysConstant.THREAD_LOCAL_KEY_LOGIN_USER);

        if (null==currentUser) {
            throw new SysException(BaseErrorCodeEnum.NO_USER_INFO_FOUND);
        }
        return currentUser;
    }

    protected void validateParams(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new SysException(BaseErrorCodeEnum.PARAM_ERROR,bindingResult.getFieldError().getDefaultMessage());
        }
    }

}
