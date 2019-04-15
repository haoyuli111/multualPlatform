package cn.lichuachua.mp.mpserver.service;

import cn.lichuachua.mp.core.support.service.IBaseService;
import cn.lichuachua.mp.mpserver.entity.Academy;
import cn.lichuachua.mp.mpserver.entity.School;
import cn.lichuachua.mp.mpserver.vo.AcademyVO;
import cn.lichuachua.mp.mpserver.vo.SchoolVO;

import java.util.List;

public interface IAcademyService extends IBaseService<Academy, Integer> {

    /**
     * 查询学院列表
     * @return
     */
    List<AcademyVO> queryList();


    /**
     * academyId根据查询academyName
     * @param academyId
     * @return
     */
    String queryAcademyName(String userId, Integer academyId);

}
