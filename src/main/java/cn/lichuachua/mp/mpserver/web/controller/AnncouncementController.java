package cn.lichuachua.mp.mpserver.web.controller;

import cn.lichuachua.mp.core.support.web.controller.BaseController;
import cn.lichuachua.mp.mpserver.dto.UserInfoDTO;
import cn.lichuachua.mp.mpserver.entity.Announcement;
import cn.lichuachua.mp.mpserver.service.IAnnouncementService;
import cn.lichuachua.mp.mpserver.vo.AnnouncementListVO;
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
@Api(value = "AnncouncementController", tags ={"公告类API"})
@RestController
@RequestMapping(value = "/announcement")
public class AnncouncementController extends BaseController<UserInfoDTO> {

    @Autowired
    private IAnnouncementService announcementService;


    /**
     * 查询公告列表
     * @return
     */
    @ApiOperation("查询出公告列表")
    @GetMapping("/queryList")
    public ResultWrapper<List<AnnouncementListVO>> queryList(){

        List<AnnouncementListVO> announcementListVOList = announcementService.queryList();
        return ResultWrapper.successWithData(announcementListVOList);
    }

}
