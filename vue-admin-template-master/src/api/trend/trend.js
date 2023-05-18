import request from '@/utils/request'

export default {

    //董涛列表（条件查询分页）
    //current 当前页、limit 每页记录数、ntrendQuery 条件对象
    getTrendListPage(current, limit, trendQuery) {
        return request({

            url: `/trendService/trend/pageTrendCondition/${current}/${limit}`,
            method: 'post',
            //trendQuery条件对象，后端使用RequestBody获取数据
            //data就是表示把对象转换为json进行传递到接口里面
            data: trendQuery
        })
    },
    //删除动态信息的方法
    delTrendById(id) {
        return request({

            url: `/trendService/trend/${id}`,
            method: 'delete',
        })
    },
}