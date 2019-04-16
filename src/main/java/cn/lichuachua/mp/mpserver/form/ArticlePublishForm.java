package cn.lichuachua.mp.mpserver.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author 李歘歘
 */
@Data
public class ArticlePublishForm {

    /**
     * 文章标题
     */
    @NotEmpty(message = "请填写文章标题")
    private String title;

    /**
     * 文章内容
     */
    @NotEmpty(message = "请填写文章内容")
    private String content;

    @NotNull(message = "请选择文章类型")
    private Integer articleType;

    @NotNull(message = "请选择文章的可视范围")
    private Integer visual;

}
