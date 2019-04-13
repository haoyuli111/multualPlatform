package cn.lichuachua.mp.mpserver.service;

import cn.lichuachua.mp.core.support.service.IBaseService;
import cn.lichuachua.mp.mpserver.entity.TeamMember;
import cn.lichuachua.mp.mpserver.entity.TeamMemberPK;
import cn.lichuachua.mp.mpserver.form.RemoveMember;
import cn.lichuachua.mp.mpserver.vo.TeamMemberVO;

import javax.validation.Valid;
import java.util.List;

public interface ITeamMemberService extends IBaseService<TeamMember, TeamMemberPK>{
    /**
     * 加入队伍
     * @param teamId
     * @param userId
     */
    void joinTeam(String teamId, String userId);

    /**
     * 退出队伍
     * @param teamId
     * @param userId
     */
    void exitTeam(String teamId, String userId);

    /**
     * 根据队伍Id查询出队伍里面的人
     * @param teamId
     * @return
     */
    List<TeamMemberVO> queryList(String teamId);

    /**
     * 将某人移除队伍
     * @param removeMember
     * @param userId
     */
    void remove(@Valid RemoveMember removeMember, String userId);


}
