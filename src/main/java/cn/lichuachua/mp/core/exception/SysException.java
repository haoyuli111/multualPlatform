package cn.lichuachua.mp.core.exception;

import cn.lichuachua.mp.common.enums.BaseErrorCodeEnum;

/**
 * @author 李歘歘
 * 通用异常类
 */
public class SysException extends RuntimeException {

    protected int code;

    public SysException() {

    }

    public SysException(Throwable cause) {
        super(cause);
    }

    public SysException(String message) {
        super(message);
    }

    public SysException(String message, Throwable cause) {
        super(message, cause);
    }

    public SysException(int code, String message) {
        super(message);
        this.code = code;
    }

    public SysException(int code, String format, Object... args) {
        super(String.format(format, args));
        this.code = code;
    }

    public SysException(BaseErrorCodeEnum codeEnum, Object... args) {
        super(String.format(codeEnum.getMessage(), args));
        this.code = codeEnum.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
