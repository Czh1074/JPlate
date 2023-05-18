import request from '@/utils/request'


export default {
    //分页收藏夹兼职信息查询方法
    getMessageFavoritePageList(page, limit, userId, infoType) {
        return request({
            url: `/favService/favorite/getMessageFavoritePageList/${page}/${limit}/${userId}/${infoType}`,
            method: 'post'
        })
    },
    //收藏功能实现，后端收藏夹以及更新collect字段
    updateCollect(type, noticeId, userId) {
        return request({
            url: `/noticeService/notice/updateNoticeFrontList/${type}/${noticeId}/${userId}`,
            method: 'post'
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