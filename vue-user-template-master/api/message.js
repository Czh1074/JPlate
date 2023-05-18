import request from '@/utils/request'


export default {
    //分页兼职信息查询方法
    getMessagePageList(page, limit, exchange) {
        return request({
            url: `/msgService/message/pageMessageFrontList/${page}/${limit}/${exchange}`,
            method: 'post'
        })
    },
    //收藏功能实现，后端收藏夹以及更新collect字段
    updateCollect(favoriteId, userId, infoId, collect, infoType) {
        return request({
            url: `/msgService/message/addFavorite/${favoriteId}/${userId}/${infoId}/${collect}/${infoType}`,
            method: 'post',
        })
    },
    //查询兼职详细信息
    getMessageInfo(messageId, userId) {
        return request({
            url: `/msgService/message/getMessageInfoById/${messageId}/${userId}`,
            method: 'post'
        })
    },
    //添加兼职详情信息
    addMessage(message) {
        return request({
            url: `/msgService/message/addMessage`,
            method: 'post',
            data: message

        })
    }
}