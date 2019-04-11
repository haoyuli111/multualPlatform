package cn.lichuachua.mp.mpserver.repository;

import cn.lichuachua.mp.mpserver.entity.Academy;
import cn.lichuachua.mp.mpserver.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 李歘歘
 */
@Repository
public interface AcademyRepository extends JpaRepository<Academy, Integer> {
}
