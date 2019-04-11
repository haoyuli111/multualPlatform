package cn.lichuachua.mp.mpserver.repository;

import cn.lichuachua.mp.mpserver.entity.ArticleCollect;
import cn.lichuachua.mp.mpserver.entity.ArticleCollectPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 李歘歘
 */
@Repository
public interface ArticleCollectRepository extends JpaRepository<ArticleCollect, ArticleCollectPK> {
}
