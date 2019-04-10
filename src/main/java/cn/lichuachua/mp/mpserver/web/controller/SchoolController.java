package cn.lichuachua.mp.mpserver.web.controller;

import cn.lichuachua.mp.core.support.web.controller.BaseController;
import cn.lichuachua.mp.mpserver.dto.UserInfoDTO;
import cn.lichuachua.mp.mpserver.service.ISchoolService;
import cn.lichuachua.mp.mpserver.vo.SchoolVO;
import cn.lichuachua.mp.mpserver.wrapper.ResultWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 李歘歘
 */
@Api(value = "SchoolController", tags = {"学校类API"})
@RestController
@RequestMapping(value = "/school")
public class SchoolController extends BaseController<UserInfoDTO> {
    @Autowired
    private ISchoolService schoolService;

    /**
     * 查询学校列表
     * @return
     */
    @ApiOperation("查询学校列表")
    @GetMapping("/queryList")
    public ResultWrapper<List<SchoolVO>> queryList(){
        List<SchoolVO> schoolVOList = schoolService.queryList();
        return ResultWrapper.successWithData(schoolVOList);
    }

}
