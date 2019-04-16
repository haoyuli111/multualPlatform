package cn.lichuachua.mp.mpserver.service.impl;

import cn.lichuachua.mp.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp.mpserver.entity.ArticleType;
import cn.lichuachua.mp.mpserver.service.IArticleService;
import cn.lichuachua.mp.mpserver.service.IArticleTypeService;
import cn.lichuachua.mp.mpserver.vo.ArticleTypeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeServiceImpl extends BaseServiceImpl<ArticleType, Integer> implements IArticleTypeService {

    /**
     * 查询所有文章类型列表
     * @return
     */
    @Override
    public List<ArticleTypeVO> queryArticleTypeList(){
        List<ArticleType> articleTypeList = selectAll();
        List<ArticleTypeVO> articleTypeVOList = new ArrayList<>();
        for (ArticleType articleType : articleTypeList){
            ArticleTypeVO articleTypeVO = new ArticleTypeVO();
            articleTypeVO.setTypeId(articleType.getTypeId());
            articleTypeVO.setTypeName(articleType.getTypeName());
            BeanUtils.copyProperties(articleType,articleTypeVO);
            articleTypeVOList.add(articleTypeVO);
        }
        return articleTypeVOList;
    }


    /**
     * @根据typeId 查询出typeName
     * @param typeId
     * @return
     */
    @Override
    public String queryTypeName(Integer typeId) {
        /**
         * 根据typeId取出对应的类型名
         */
        ArticleType articleType = new ArticleType();
        articleType.setTypeId(typeId);
        Optional<ArticleType> articleTypeOptional = selectOne(Example.of(articleType));
        if (articleTypeOptional.isPresent()){
            return articleTypeOptional.get().getTypeName();
        }else {
            return null;
        }
    }
}
