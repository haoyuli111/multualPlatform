package cn.lichuachua.mp.mpserver.entity;

import lombok.Data;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;

/**
 * @author  李歘歘
 *  联合主键
 */
@Data
public class ArticleCollectPK implements Serializable {
    /**
     * 文章Id
     */
    private String articleId;
    /**
     * 用户Id
     */
    private String userId;
}
