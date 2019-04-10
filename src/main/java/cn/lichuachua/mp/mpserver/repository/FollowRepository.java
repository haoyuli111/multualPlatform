package cn.lichuachua.mp.mpserver.repository;

import cn.lichuachua.mp.mpserver.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 李歘歘
 */
@Repository
public interface FollowRepository extends JpaRepository<Follow, String> {
}
