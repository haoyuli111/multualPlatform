package cn.lichuachua.mp.mpserver.web.controller;

import cn.lichuachua.mp.core.support.web.controller.BaseController;
import cn.lichuachua.mp.mpserver.dto.UserInfoDTO;
import cn.lichuachua.mp.mpserver.entity.ArticleType;
import cn.lichuachua.mp.mpserver.service.IArticleTypeService;
import cn.lichuachua.mp.mpserver.vo.ArticleTypeVO;
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
@Api(value = "ArticleTypeController", tags = {"文章类型API"})
@RestController
@RequestMapping(value = "/article/type")
public class ArticleTypeController extends BaseController<UserInfoDTO> {
    @Autowired
    private IArticleTypeService articleTypeService;

    /**
     * 查询所有文章类型列表
     * @return
     */
    @ApiOperation("查询所有文章类型列表")
    @GetMapping("/queryArticleTypeList")
    public ResultWrapper<List<ArticleTypeVO>> queryArticleTypeList(){
        List<ArticleTypeVO> articleTypeList = articleTypeService.queryArticleTypeList();
        return ResultWrapper.successWithData(articleTypeList);
    }

}
