package cn.lichuachua.mp.mpserver.service;

import cn.lichuachua.mp.core.support.service.IBaseService;
import cn.lichuachua.mp.mpserver.entity.Announcement;
import cn.lichuachua.mp.mpserver.vo.AnnouncementListVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author 李歘歘
 */

public interface IAnnouncementService extends IBaseService<Announcement, String> {

    /**
     * 查询公告列表
     * @return
     */
    List<AnnouncementListVO> queryList(Pageable pageable);
}
