package com.plate.favService.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.plate.favService.entity.Favorite;
import com.plate.favService.mapper.FavoriteMapper;
import com.plate.favService.service.FavoriteService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author czh
 * @since 2022-05-03
 */
@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {

}
