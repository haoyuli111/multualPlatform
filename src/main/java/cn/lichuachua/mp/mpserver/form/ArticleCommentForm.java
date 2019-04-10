package cn.lichuachua.mp.mpserver.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author 李宇豪
 */
@Data
public class ArticleCommentForm {
    /**
     *父评论Id
     */
    @NotEmpty(message = "父评论Id不能为空")
    private String parentId;

    /**
     * 评论的内容
     */
    @NotEmpty(message = "评论的内容不能为空")
    private String content;



}
