package cn.lichuachua.mp.mpserver.service;

import cn.lichuachua.mp.mpserver.dto.TokenInfo;

/**
 * @author 李歘歘
 */
public interface ITokenService {
    /**
     * 从redis获取token信息
     * @param accessToken
     * @return
     */
    TokenInfo getTokenInfo(String accessToken);
}
