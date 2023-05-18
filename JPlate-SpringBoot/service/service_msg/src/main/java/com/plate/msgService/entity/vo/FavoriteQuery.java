package com.plate.msgService.entity.vo;


import com.plate.msgService.entity.Favorite;
import lombok.Data;

import java.io.Serializable;

/**
 * create by chenzhihui on 2022/5/3.
 **/
@Data
public class FavoriteQuery implements Serializable {
    Favorite favorite;
    String infoId;
    String userId;
}
