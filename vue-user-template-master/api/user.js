import request from '@/utils/request'


export default {
    //收藏功能实现，后端收藏夹以及更新collect字段
    updateCollect(favoriteId, userId, infoId, collect, infoType) {
        return request({
            url: `/msgService/message/addFavorite/${favoriteId}/${userId}/${infoId}/${collect}/${infoType}`,
            method: 'post',
        })
    },
    //查询用户详细信息
    getUser(userId) {
        return request({
            url: `/userService/user/getUser/${userId}`,
            method: 'post'
        })
    },
    //修改用户详细信息
    updateUser(user) {
        return request({
            url: `/userService/user/updateUser`,
            method: 'post',
            data: user
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