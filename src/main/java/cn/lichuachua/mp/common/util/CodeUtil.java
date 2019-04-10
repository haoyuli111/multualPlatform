package cn.lichuachua.mp.common.util;

import cn.lichuachua.mp.mpserver.repository.redis.RedisRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

import static cn.lichuachua.mp.common.constant.SysConstant.REGISTERED_VERIFICATON_MAX;
import static cn.lichuachua.mp.common.constant.SysConstant.REGISTERED_VERIFICATON_MIN;

/**
 * @author 李歘歘
 */
public class CodeUtil {

    @Autowired
    RedisRepositoryImpl redisRepository = new RedisRepositoryImpl();

    /**
     * 生成验证码
     */
    public static String smsCode(){
        Random random = new Random();
        int codeNum = random.nextInt(REGISTERED_VERIFICATON_MAX) + REGISTERED_VERIFICATON_MIN;
        return String.valueOf(codeNum);
    }

}
