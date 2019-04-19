package cn.lichuachua.mp.mpserver.service.impl;

import cn.lichuachua.mp.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp.mpserver.entity.TeamType;
import cn.lichuachua.mp.mpserver.enums.TeamTypeEnum;
import cn.lichuachua.mp.mpserver.service.ITeamTypeService;
import cn.lichuachua.mp.mpserver.vo.TeamTypeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 李歘歘
 */
@Service
public class TeamTypeServiceImpl extends BaseServiceImpl<TeamType, String> implements ITeamTypeService {

    /**
     * 队伍类型列表
     * @return
     */
    @Override
    public List<TeamTypeVO> queryList(){
        List<TeamType> teamTypeList = selectAll();
        List<TeamTypeVO> teamTypeVOList = new ArrayList<>();
        for (TeamType teamType : teamTypeList){
            if (teamType.getStatus().equals(TeamTypeEnum.NORMAL.getStatus())){
                TeamTypeVO teamTypeVO = new TeamTypeVO();
                teamTypeVO.setTypeId(teamType.getTypeId());
                teamTypeVO.setTypeName(teamType.getTypeName());
                BeanUtils.copyProperties(teamType, teamTypeVO);
                teamTypeVOList.add(teamTypeVO);
            }
        }
        return teamTypeVOList;
    }


    /**
     * 根据typeId查询typeName
     * @param typeId
     * @return
     */
    @Override
    public String queryTypeName(Integer typeId){
        /**
         * 查看该类型是否存在
         */
        TeamType teamType = new TeamType();
        teamType.setTypeId(typeId);
        teamType.setStatus(TeamTypeEnum.NORMAL.getStatus());
        Optional<TeamType> teamTypeOptional = selectOne(Example.of(teamType));
        if (teamTypeOptional.isPresent()){
            return teamTypeOptional.get().getTypeName();
        }else {
            return null;
        }
    }

}
