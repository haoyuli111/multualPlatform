package cn.lichuachua.mp.mpserver.service.impl;

import cn.lichuachua.mp.mpserver.dto.TokenInfo;
import cn.lichuachua.mp.mpserver.repository.redis.IRedisRepository;
import cn.lichuachua.mp.mpserver.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 李歘歘
 */
@Service
public class TokenServiceImpl implements ITokenService{

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    private IRedisRepository redisRepository;

    @Override
    public TokenInfo getTokenInfo(String accessToken) {
        TokenInfo tokenInfo = redisRepository.findTokenInfoByAccessToken(accessToken);
        return tokenInfo;
    }
}
