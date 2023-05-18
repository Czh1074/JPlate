import request from '@/utils/request'

export default {
    //根据教育邮箱发送邮箱验证码
    sendCode(username) {
        return request({
            url: `/userService/user/sendSimpleMail/${username}`,
            method: 'post'
        })
    },
    //用户注册
    submitRegister(formItem) {
        return request({
            url: `/userService/user/register`,
            method: 'post',
            data: formItem
        })
    }
}