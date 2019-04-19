package cn.lichuachua.mp.mpserver.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 李歘歘
 */
@Data
public class MyArticleListVO {
    private String articleId;

    private String articleType;

    private String title;

    private String content;

    private String accessory;

    private Date updatedAt;


}
