package cn.lichuachua.mp.mpserver.exception;

import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;

/**
 * @author 李歘歘
 */
public class FollowException extends BaseException {
    public FollowException(int code, String message) {super(code, message);}

    public FollowException(ErrorCodeEnum codeEnum, Object... args){super(codeEnum, args);}
}
