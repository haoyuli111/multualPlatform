package cn.lichuachua.mp.mpserver.web.controller;

import cn.lichuachua.mp.core.support.web.controller.BaseController;
import cn.lichuachua.mp.mpserver.dto.UserInfoDTO;
import cn.lichuachua.mp.mpserver.entity.Academy;
import cn.lichuachua.mp.mpserver.service.IAcademyService;
import cn.lichuachua.mp.mpserver.service.ISchoolService;
import cn.lichuachua.mp.mpserver.vo.AcademyVO;
import cn.lichuachua.mp.mpserver.vo.SchoolVO;
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
@Api(value = "AcademyController", tags = {"学院类API"})
@RestController
@RequestMapping(value = "/academy")
public class AcademyController extends BaseController<UserInfoDTO> {
    @Autowired
    private IAcademyService academyService;

    /**
     * 查询学院列表
     * @return
     */
    @ApiOperation("查询学院列表")
    @GetMapping("/queryList")
    public ResultWrapper<List<AcademyVO>> queryList(){
        List<AcademyVO> academyVOList = academyService.queryList();
        return ResultWrapper.successWithData(academyVOList);
    }
}
