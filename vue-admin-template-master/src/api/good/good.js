import request from '@/utils/request'

export default {

    //讲师列表（条件查询分页）
    //current 当前页、limit 每页记录数、eduMessageQuery 条件对象
    getGoodListPage(current, limit, goodQuery) {
        return request({

            url: `/goodService/good/pageGoodCondition/${current}/${limit}`,
            method: 'post',
            //goodQuery条件对象，后端使用RequestBody获取数据
            //data就是表示把对象转换为json进行传递到接口里面
            data: goodQuery
        })
    },
    //删除商品信息的方法
    delGoodById(id) {
        return request({

            url: `/goodService/good/${id}`,
            method: 'delete',
        })
    },
}


