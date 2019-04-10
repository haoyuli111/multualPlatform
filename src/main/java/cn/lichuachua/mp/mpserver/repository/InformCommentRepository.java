package cn.lichuachua.mp.mpserver.repository;

import cn.lichuachua.mp.mpserver.entity.InformArticle;
import cn.lichuachua.mp.mpserver.entity.InformComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 李歘歘
 */
@Repository
public interface InformCommentRepository extends JpaRepository<InformComment, String> {
}
