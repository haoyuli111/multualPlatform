package cn.lichuachua.mp.mpserver.repository;

import cn.lichuachua.mp.mpserver.entity.TeamMember;
import cn.lichuachua.mp.mpserver.entity.TeamMemberPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 李歘歘
 */
@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, TeamMemberPK> {
}
