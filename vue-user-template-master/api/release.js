import request from '@/utils/request'


export default {
    //发布中心动态信息查询方法
    getTrendPageList(page, limit, userId) {
        return request({
            url: `/trendService/trend/getTrendPageList/${page}/${limit}/${userId}`,
            method: 'post'
        })
    },
    //发布中心商品信息查询方法
    getGoodPageList(page, limit, userId) {
        return request({
            url: `/goodService/good/getGoodPageList/${page}/${limit}/${userId}`,
            method: 'post'
        })
    },
    //发布中心商品信息查询方法
    getMessagePageList(page, limit, userId) {
        return request({
            url: `/msgService/message/getMessagePageList/${page}/${limit}/${userId}`,
            method: 'post'
        })
    },
    //删除动态信息
    deleteTrendById(id) {
        return request({
            url: `/trendService/trend/${id}`,
            method: 'delete'
        })
    },
    //删除商品信息
    deleteGoodById(id) {
        return request({
            url: `/goodService/good/${id}`,
            method: 'delete'
        })
    },
    //删除兼职信息
    deleteMessageById(id) {
        return request({
            url: `/msgService/message/${id}`,
            method: 'delete'
        })
    },
    //查询兼职详细信息
    getNoticeInfo(noticeId) {
        return request({
            url: `/noticeService/notice/getNoticeInfoById/${noticeId}`,
            method: 'post'
        })
    }
}