import request from '@/utils/request'
export default {
    //登录
    submitLogin(userInfo) {
        return request({
            url: `/userService/user/login`,
            method: 'post',
            data: userInfo
        })
    },
    //根据token获取用户信息
    getLoginInfo() {
        return request({
            url: `/userService/user/getUserInfo`,
            method: 'post',
        })
    }
}