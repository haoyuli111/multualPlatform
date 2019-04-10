package cn.lichuachua.mp.mpserver.repository.redis;

import cn.lichuachua.mp.mpserver.dto.ClientInfo;
import cn.lichuachua.mp.mpserver.dto.TokenInfo;
import cn.lichuachua.mp.mpserver.dto.VerificationCodeInfo;

/**
 * @author 李歘歘
 */
public interface IRedisRepository {
    /**
     * 保存Tokrn信息
     * @param tokenInfo
     */
    void saveAccessToken(TokenInfo tokenInfo);

    /**
     * 保存token和用户的对应信息
     * @param userId
     * @param accessToken
     */
    void saveUserAccessToken(String userId, String accessToken);

    /**
     * 根据用户Id查Token
     * @param userId
     * @return
     */
    String findAccessTokenByUserId(String userId);

    /**
     * 根据 token  查看Token详情
     * @param accessToken
     * @return
     */
    TokenInfo findTokenInfoByAccessToken(String accessToken);

    /**
     * 删除Token
     * @param accessToken
     */
    void deleteAccessToken(String accessToken);

    /**
     * 删除Token和用户的关系
     * @param userId
     */
    void deleteUserAccessToken(String userId);

    /**
     * 保存连接进来的客户端信息
     * @param userId
     * @param clientInfo
     */
    void saveClientInfo(String userId, ClientInfo clientInfo);

    /**
     * 删除客户端连接信息
     * @param userId
     */
    void deleteClientInfo(String userId);

    /**
     * 根据userId从Redis查询客户端连接信息
     * @param userId
     * @return
     */
    ClientInfo findClientInfoByUserId(String userId);

    /**
     * 保存手机号和验证码
     * @param verificationCodeInfo
     */
    void saveVerificationCode(VerificationCodeInfo verificationCodeInfo);

    /**
     * 根据手机号查找验证码
     * @param mobile
     * @return
     */
    String findVerificationCodeByMobile(String mobile);


    /**
     * 根据手机号删除验证码和手机号
     * @param mobile
     */
    void deleteVerificationCodeByMobile(String mobile);

}
