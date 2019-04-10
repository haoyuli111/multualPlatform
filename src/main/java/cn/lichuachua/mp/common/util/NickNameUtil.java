package cn.lichuachua.mp.common.util;

import net.bytebuddy.utility.RandomString;

/**
 * @author 李歘歘
 * 昵称生成器
 */

public class NickNameUtil {
        final static String NICK_NAME = "MP-";
    public static String genNick() {
        String nickName = new String();
        nickName = NICK_NAME + RandomString.make(6);
        return nickName;
    }

}
