package cn.lichuachua.mp.mpserver.exception;


import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;

/**
 * @author 李宇豪
 */
public class ArticleCommentException extends BaseException {

    public ArticleCommentException(int code, String message) {
        super(code, message);
    }

    public ArticleCommentException(ErrorCodeEnum codeEnum, Object... args) {
        super(codeEnum, args);
    }
}
