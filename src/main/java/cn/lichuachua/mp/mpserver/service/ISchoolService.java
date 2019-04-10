package cn.lichuachua.mp.mpserver.service;

import cn.lichuachua.mp.core.support.service.IBaseService;
import cn.lichuachua.mp.mpserver.entity.School;
import cn.lichuachua.mp.mpserver.vo.SchoolVO;

import java.util.List;

public interface ISchoolService extends IBaseService<School, Integer> {
    /**
     * 查询学校列表
     * @return
     */
    List<SchoolVO> queryList();

    /**
     * 根据schoolId查询schoolName
     * @param schoolId
     * @return
     */
    String querySchoolName(Integer schoolId);
}
