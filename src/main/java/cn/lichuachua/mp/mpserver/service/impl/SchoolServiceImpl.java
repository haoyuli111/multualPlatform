package cn.lichuachua.mp.mpserver.service.impl;

import cn.lichuachua.mp.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp.mpserver.entity.School;
import cn.lichuachua.mp.mpserver.entity.User;
import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;
import cn.lichuachua.mp.mpserver.enums.SchoolStatusEnum;
import cn.lichuachua.mp.mpserver.exception.SchoolException;
import cn.lichuachua.mp.mpserver.service.ISchoolService;
import cn.lichuachua.mp.mpserver.service.IUserService;
import cn.lichuachua.mp.mpserver.vo.SchoolVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SchoolServiceImpl extends BaseServiceImpl<School, Integer> implements ISchoolService {
    @Autowired
    private IUserService userService;

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


    /**
     * 根据schoolId查询schoolName
     * @param userId
     * @param schoolId
     * @return
     */
    @Override
    public String querySchoolName(String userId, Integer schoolId){
        /**
         * 如果用户的学校不为空
         * 根据academyId查询出academyName
         */
        /**
         * 查询用户的学校信息
         */
        Optional<User> userOptional = userService.selectByKey(userId);
        if (userOptional.get().getSchoolId()==null) {
            return null;
        }else {
            School school = new School();
            school.setSchoolId(schoolId);
            school.setStatus(SchoolStatusEnum.NORMAL.getStatus());
            Optional<School> schoolOptional = selectOne(Example.of(school));
            /**
             * 学校存在--学校名
             * 学校不存在，显示null
             */
            if (!schoolOptional.isPresent()){
                return null;
            }else {
                return schoolOptional.get().getSchoolName();
            }
        }
    }


}
