package cn.lichuachua.mp.mpserver.repository;

import cn.lichuachua.mp.mpserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 李歘歘
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>{
}
