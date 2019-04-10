package cn.lichuachua.mp.mpserver.web.interceptor;

import cn.lichuachua.mp.common.constant.SysConstant;
import cn.lichuachua.mp.common.util.JsonUtil;
import cn.lichuachua.mp.common.util.ThreadLocalMap;
import cn.lichuachua.mp.mpserver.constant.mutualPlatformConstant;
import cn.lichuachua.mp.mpserver.dto.TokenInfo;
import cn.lichuachua.mp.mpserver.dto.UserInfoDTO;
import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp.mpserver.service.ITokenService;
import cn.lichuachua.mp.mpserver.wrapper.ResultWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 李歘歘
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private ITokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String accessToken = null;

        accessToken = request.getHeader(mutualPlatformConstant.HTTP_HEADER_MP_ACCESS_TOKEN);

        if (null == accessToken) {
            accessToken = request.getParameter(mutualPlatformConstant.PQE_PARAM_MP_ACCESS_TOKEN);
        }


        ResultWrapper  resultWrapper =null;
        response.setHeader("Content-Type", "application/json;charset=utf-8");

        /**
         * 用户没有传递token，返回错误信息给用户
         */
        if (null == accessToken) {
            ThreadLocalMap.remove(SysConstant.THREAD_LOCAL_KEY_LOGIN_USER);
            resultWrapper = ResultWrapper.failure(ErrorCodeEnum.UNAUTHORIZED);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            try {
                response.getWriter().println(JsonUtil.toJson(resultWrapper));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }

        /**
         * token错误
         */
        TokenInfo tokenInfo = tokenService.getTokenInfo(accessToken);
        if (null == tokenInfo) {
            ThreadLocalMap.remove(SysConstant.THREAD_LOCAL_KEY_LOGIN_USER);
            resultWrapper = ResultWrapper.failure(ErrorCodeEnum.BAD_ACCESS_TOKEN);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                response.getWriter().println(JsonUtil.toJson(resultWrapper));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }

        /**
         * 将UserInfo设置到ThreadLocal
         */
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserId(tokenInfo.getUserId());
        ThreadLocalMap.put(SysConstant.THREAD_LOCAL_KEY_LOGIN_USER, userInfoDTO);
        return true;
    }
}
