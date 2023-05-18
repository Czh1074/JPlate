import request from '@/utils/request'


export default {
    //分页动态信息查询方法
    getTrendPageList(page, limit, exchange) {
        return request({
            url: `/trendService/trend/pageTrendFrontList/${page}/${limit}/${exchange}`,
            method: 'post'
        })
    },
    //按照类别分页查询动态
    pageTrendCondition(page, limit, trendQuery) {
        return request({
            url: `/trendService/trend/pageTrendCondition/${page}/${limit}`,
            method: 'post',
            data: trendQuery
        })
    },
    //收藏功能实现，后端收藏夹以及更新collect字段
    updateCollect(favoriteId, userId, infoId, collect, infoType) {
        return request({
            url: `/trendService/trend/addFavorite/${favoriteId}/${userId}/${infoId}/${collect}/${infoType}`,
            method: 'post',
        })
    },
    //查询动态详细信息:添加userId可以使得我们在判断该用户是否收藏该文章
    getTrendInfo(trendId, userId) {
        return request({
            url: `/trendService/trend/getTrendInfoById/${trendId}/${userId}`,
            method: 'post'
        })
    },
    //添加动态详情信息
    addTrend(trend) {
        return request({
            url: `/trendService/trend/addTrend`,
            method: 'post',
            data: trend

        })
    },
}