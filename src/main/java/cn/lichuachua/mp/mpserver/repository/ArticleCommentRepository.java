package cn.lichuachua.mp.mpserver.repository;

import cn.lichuachua.mp.mpserver.entity.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, String> {
}
