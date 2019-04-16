package cn.lichuachua.mp.mpserver.service.impl;

import cn.lichuachua.mp.core.support.service.impl.BaseServiceImpl;
import cn.lichuachua.mp.mpserver.entity.Announcement;
import cn.lichuachua.mp.mpserver.enums.AnnouncementStatusEnum;
import cn.lichuachua.mp.mpserver.service.IAnnouncementService;
import cn.lichuachua.mp.mpserver.service.IArticleTypeService;
import cn.lichuachua.mp.mpserver.vo.AnnouncementListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李歘歘
 */
@Service
public class AnnouncementServiceImpl extends BaseServiceImpl<Announcement, String> implements IAnnouncementService {

    @Autowired
    private IArticleTypeService articleTypeService;

    /**
     * 查询公告列表
     * @return
     */
    @Override
    public List<AnnouncementListVO> queryList(){
        List<Announcement> announcementList = selectAll();
        List<AnnouncementListVO> announcementListVOList = new ArrayList<>();
        for (Announcement announcement : announcementList){
            AnnouncementListVO announcementListVO = new AnnouncementListVO();
            if (announcement.getStatus().equals(AnnouncementStatusEnum.NORMAL.getStatus())){
                announcementListVO.setTitle(announcement.getTitle());
                announcementListVO.setContent(announcement.getContent());
                announcementListVO.setPublisherNick(announcement.getPublisherNick());
                /**
                 * 调用根据typeId查询typeName
                 */
                announcementListVO.setAnnouncenmentType(articleTypeService.queryTypeName(announcement.getAnnouncementType()));
                announcementListVO.setAccessory(announcement.getAccessory());
                BeanUtils.copyProperties(announcement, announcementListVO);
                announcementListVOList.add(announcementListVO);
            }
        }
        return announcementListVOList;
    }

}
