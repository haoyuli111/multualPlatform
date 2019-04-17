package cn.lichuachua.mp.mpserver.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 李歘歘
 */
@Data
public class ArticleVO {

    private String publisherId;

    private String publisherNick;

    private String publisherAvatar;

    private String articleType;

    private String title;

    private String content;

    private String accessory;

    private Date updatedAt;

    List<ArticleLikeVO> articleLikeVOList = new ArrayList<>();

    List<ArticleCommentVO> articleCommentVOList = new ArrayList<>();


}
