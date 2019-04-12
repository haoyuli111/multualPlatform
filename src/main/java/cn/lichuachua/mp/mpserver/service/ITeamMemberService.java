package cn.lichuachua.mp.mpserver.service;

import cn.lichuachua.mp.core.support.service.IBaseService;
import cn.lichuachua.mp.mpserver.entity.TeamMember;
import cn.lichuachua.mp.mpserver.entity.TeamMemberPK;
import cn.lichuachua.mp.mpserver.vo.TeamMemberVO;

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


    List<TeamMemberVO> queryList(String teamId);
}
