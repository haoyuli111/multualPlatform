package cn.lichuachua.mp.mpserver.web.controller;

import cn.lichuachua.mp.core.support.web.controller.BaseController;
import cn.lichuachua.mp.mpserver.dto.UserInfoDTO;
import cn.lichuachua.mp.mpserver.entity.TeamType;
import cn.lichuachua.mp.mpserver.service.ITeamTypeService;
import cn.lichuachua.mp.mpserver.vo.TeamTypeVO;
import cn.lichuachua.mp.mpserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 李歘歘
 */
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@Api(value = "TeamTypeController", tags = {"队伍类型API"})
@RestController
@RequestMapping(value = "/teamType")
public class TeamTypeController extends BaseController<UserInfoDTO> {
    @Autowired
    private ITeamTypeService teamTypeService;


    /**
     * 队伍类型列表
     * @return
     */
    @ApiOperation("队伍类型列表")
    @GetMapping("/queryList")
    public ResultWrapper<List<TeamTypeVO>> queryList(){
        List<TeamTypeVO> teamTypeVOList = teamTypeService.queryList();
        return ResultWrapper.successWithData(teamTypeVOList);
    }
}
