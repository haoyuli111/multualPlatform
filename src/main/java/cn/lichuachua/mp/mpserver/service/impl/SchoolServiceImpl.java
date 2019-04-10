package cn.lichuachua.mp.mpserver.service.impl;

import cn.lichuachua.mp.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp.mpserver.entity.School;
import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp.mpserver.enums.SchoolStatusEnum;
import cn.lichuachua.mp.mpserver.exception.SchoolException;
import cn.lichuachua.mp.mpserver.service.ISchoolService;
import cn.lichuachua.mp.mpserver.vo.SchoolVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SchoolServiceImpl extends BaseServiceImpl<School, Integer> implements ISchoolService {
    /**
     * 查询学校列表
     * @return
     */
    @Override
    public List<SchoolVO> queryList(){
        List<School> schoolList = selectAll();
        List<SchoolVO> schoolVOList = new ArrayList<>();
        for (School school : schoolList){
            if (school.getStatus().equals(SchoolStatusEnum.NORMAL.getStatus())){
                SchoolVO schoolVO = new SchoolVO();
                schoolVO.setSchoolId(school.getSchoolId());
                schoolVO.setSchoolName(school.getSchoolName());
                BeanUtils.copyProperties(school, schoolVO);
                schoolVOList.add(schoolVO);
            }
        }
        return schoolVOList;
    }


    @Override
    public String querySchoolName(Integer schoolId){
        School school = new School();
        school.setSchoolId(schoolId);
        school.setStatus(SchoolStatusEnum.NORMAL.getStatus());
        Optional<School> schoolOptional = selectOne(Example.of(school));
        if (!schoolOptional.isPresent()){
            return null;
        }
        return schoolOptional.get().getSchoolName();
    }


}
