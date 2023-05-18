import request from '@/utils/request'

export function login(username, password) {
  return request({
    url: '/userService/user/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

export function getInfo(token) {
  return request({
    url: '/userService/user/getUserInfo',
    method: 'post',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/userService/user/login',
    method: 'post'
  })
}

//用户列表（条件查询分页）
//current 当前页、limit 每页记录数、userQuery 条件对象
export function getUserListPage(current, limit, username) {
  return request({

    url: `/userService/user/pageUserCondition/${current}/${limit}/${username}`,
    method: 'post',
  })
}



