import request from '@/utils/request'

export default {

    //用户列表（条件查询分页）
    //current 当前页、limit 每页记录数、userQuery 条件对象
    getUserListPage(current, limit, username) {
        return request({

            url: `/userService/user/pageUserCondition/${current}/${limit}/${username}`,
            method: 'post',
        })
    },
    //删除用户信息的方法
    delUserById(id) {
        return request({

            url: `/userService/user/${id}`,
            method: 'delete',
        })
    },
}