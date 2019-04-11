package cn.lichuachua.mp.mpserver.repository;

import cn.lichuachua.mp.mpserver.entity.ArticleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleTypeRepository extends JpaRepository<ArticleType, Integer> {

}
