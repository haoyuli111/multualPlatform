package cn.lichuachua.mp.mpserver.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author 李歘歘
 */
@Data
public class InformArticleForm {
    @NotEmpty(message = "举报文章不能为空")
    private String articleId;

    @NotEmpty(message = "举报人不能为空")
    private String informerId;

    @NotEmpty(message = "被举报者不能为空")
    private String informedId;

}
