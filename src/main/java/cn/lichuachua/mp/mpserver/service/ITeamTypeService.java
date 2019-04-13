package cn.lichuachua.mp.mpserver.service;

import cn.lichuachua.mp.core.support.service.IBaseService;
import cn.lichuachua.mp.mpserver.entity.TeamType;
import cn.lichuachua.mp.mpserver.vo.TeamTypeVO;

import java.util.List;

/**
 * @author 李歘歘
 */
public interface ITeamTypeService extends IBaseService<TeamType, String> {

    /**
     * 队伍类型列表
     * @return
     */
    List<TeamTypeVO> queryList();
}
