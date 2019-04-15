package cn.lichuachua.mp.mpserver.service.impl;

import cn.lichuachua.mp.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp.mpserver.entity.Academy;
import cn.lichuachua.mp.mpserver.entity.School;
import cn.lichuachua.mp.mpserver.entity.User;
import cn.lichuachua.mp.mpserver.enums.AcademyStatusEnum;
import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp.mpserver.enums.SchoolStatusEnum;
import cn.lichuachua.mp.mpserver.exception.AcademyException;
import cn.lichuachua.mp.mpserver.service.IAcademyService;
import cn.lichuachua.mp.mpserver.service.ISchoolService;
import cn.lichuachua.mp.mpserver.service.IUserService;
import cn.lichuachua.mp.mpserver.vo.AcademyVO;
import cn.lichuachua.mp.mpserver.vo.SchoolVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AcademyServiceImpl extends BaseServiceImpl<Academy, Integer> implements IAcademyService {

    @Autowired
    private IUserService userService;

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
     * 根据userId和academyId查询academyName
     * @param userId
     * @param academyId
     * @return
     */
    @Override
    public String queryAcademyName(String userId, Integer academyId){
        /**
         * 如果用户的学院不为空
         * 根据academyId查询出academyName
         */
        /**
         * 查询用户的学院信息
         */
        Optional<User> userOptional = userService.selectByKey(userId);
        if (userOptional.get().getAcademyId()==null){
            return null;
        }else {
            Academy academy = new Academy();
            academy.setStatus(AcademyStatusEnum.NORMAL.getStatus());
            academy.setAcademyId(academyId);
            Optional<Academy> academyOptional = selectOne(Example.of(academy));
            /**
             * 学校存在--学校名
             * 学校不存在，显示null
             */
            if (!academyOptional.isPresent()){
                return null;
            }else {
                return academyOptional.get().getAcademyName();
                }
            }
        }

}
