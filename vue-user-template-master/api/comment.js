import request from '@/utils/request'

export default {

    getCommentPageList(page, limit, trendId) {
        return request({
            url: `/trendService/trend/getCommentList/${page}/${limit}/${trendId}`,
            method: 'post',
        })
    },
    addComment(comment) {
        return request({
            url: `/trendService/trend/addComment`,
            method: 'post',
            data: comment
        })
    }
}