import request from '@/utils/request'


export default {
    //分页商品信息查询方法
    getGoodPageList(page, limit) {
        return request({
            url: `/goodService/good/pageGoodFrontList/${page}/${limit}`,
            method: 'post'
        })
    },
    //按照类别分页查询商品
    selectGoodBygoodQuery(page, limit, goodQuery) {
        return request({
            url: `/goodService/good/selectGoodBygoodQuery/${page}/${limit}`,
            method: 'post',
            data: goodQuery
        })
    },
    //收藏功能实现，后端收藏夹以及更新collect字段
    updateCollect(favoriteId, userId, infoId, collect, infoType) {
        return request({
            url: `/goodService/good/addFavorite/${favoriteId}/${userId}/${infoId}/${collect}/${infoType}`,
            method: 'post',
        })
    },
    //查询商品详细信息
    getGoodInfo(goodId, userId) {
        return request({
            url: `/goodService/good/getGoodInfoById/${goodId}/${userId}`,
            method: 'post'
        })
    },
    //添加商品详情信息
    addGood(good) {
        return request({
            url: `/goodService/good/addGood`,
            method: 'post',
            data: good

        })
    }
}