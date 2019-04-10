package cn.lichuachua.mp.mpserver.repository;

import cn.lichuachua.mp.mpserver.entity.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * 李歘歘
 */
@Repository
public interface ArticleLikeRepository extends JpaRepository<ArticleLike, String> {
}
