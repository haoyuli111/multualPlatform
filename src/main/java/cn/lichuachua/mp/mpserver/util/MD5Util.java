package cn.lichuachua.mp.mpserver.util;

import org.springframework.util.DigestUtils;

/**
 * @author 李歘歘
 */

public class MD5Util {
    /*
     * 将一个字符串MD5加密，方式很多，我们使用的是Spring包下
     */
    public static String getMd5Simple(String password){
        String md502 = DigestUtils.md5DigestAsHex(password.getBytes());
        return md502;

    }

    public static void main(String[] args){
        System.out.println(getMd5Simple("1122"));
    }

}
