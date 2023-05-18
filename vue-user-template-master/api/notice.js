import request from '@/utils/request'


export default {
    //分页公告信息查询方法
    getNoticePageList(page, limit) {
        return request({
            url: `/noticeService/notice/pageNoticeFrontList/${page}/${limit}`,
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