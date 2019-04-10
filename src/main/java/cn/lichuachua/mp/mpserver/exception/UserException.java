package cn.lichuachua.mp.mpserver.exception;

import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;

/**
 * @author 李歘歘
 */
public class UserException extends BaseException {
    public UserException(int code, String message) {
        super(code, message);
    }
    public UserException(ErrorCodeEnum codeEnum, Object... args) {
        super(codeEnum, args);
    }
}
