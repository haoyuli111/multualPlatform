package cn.lichuachua.mp.mpserver.service;

import cn.lichuachua.mp.core.support.service.IBaseService;
import cn.lichuachua.mp.mpserver.entity.ArticleType;
import cn.lichuachua.mp.mpserver.vo.ArticleTypeVO;

import java.util.List;

public interface IArticleTypeService extends IBaseService<ArticleType, Integer> {

    /**
     * 查询所有文章类型列表
     * @return
     */
    List<ArticleTypeVO> queryArticleTypeList();

}
