package cn.lichuachua.mp.mpserver.service.impl;

import cn.lichuachua.mp.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp.mpserver.entity.Academy;
import cn.lichuachua.mp.mpserver.entity.School;
import cn.lichuachua.mp.mpserver.enums.AcademyStatusEnum;
import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp.mpserver.enums.SchoolStatusEnum;
import cn.lichuachua.mp.mpserver.exception.AcademyException;
import cn.lichuachua.mp.mpserver.service.IAcademyService;
import cn.lichuachua.mp.mpserver.service.ISchoolService;
import cn.lichuachua.mp.mpserver.vo.AcademyVO;
import cn.lichuachua.mp.mpserver.vo.SchoolVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AcademyServiceImpl extends BaseServiceImpl<Academy, Integer> implements IAcademyService {
    /**
     * 查询学院列表
     * @return
     */
    @Override
    public List<AcademyVO> queryList(){
        List<Academy> academyList = selectAll();
        List<AcademyVO> academyVOList = new ArrayList<>();
        for (Academy academy : academyList){
            if (academy.getStatus().equals(AcademyStatusEnum.NORMAL.getStatus())){
                AcademyVO academyVO = new AcademyVO();
                academyVO.setAcademyId(academy.getAcademyId());
                academyVO.setAcademyName(academy.getAcademyName());
                BeanUtils.copyProperties(academy, academyVO);
                academyVOList.add(academyVO);
            }
        }
        return academyVOList;
    }

    /**
     * academyId根据查询academyName
     * @param academyId
     * @return
     */
    @Override
    public String queryAcademyName(Integer academyId){
        Academy academy = new Academy();
        academy.setStatus(AcademyStatusEnum.NORMAL.getStatus());
        academy.setAcademyId(academyId);
        Optional<Academy> academyOptional = selectOne(Example.of(academy));
        if (!academyOptional.isPresent()){
            return null;
        }
        return academyOptional.get().getAcademyName();
    }
}
