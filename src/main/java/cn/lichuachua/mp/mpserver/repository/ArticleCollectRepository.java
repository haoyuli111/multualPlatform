package cn.lichuachua.mp.mpserver.repository;

import cn.lichuachua.mp.mpserver.entity.ArticleCollect;
import cn.lichuachua.mp.mpserver.entity.ArticleCollectPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCollectRepository extends JpaRepository<ArticleCollect, ArticleCollectPK> {
}
