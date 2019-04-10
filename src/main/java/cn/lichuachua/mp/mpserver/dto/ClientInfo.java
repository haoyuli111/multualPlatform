package cn.lichuachua.mp.mpserver.dto;

import lombok.Data;

/**
 * @author 李歘歘
 */
@Data
public class ClientInfo {

    private String accessToken;

    private String userId;

    private Long mostSignBits;

    private Long leastSignBits;
}
