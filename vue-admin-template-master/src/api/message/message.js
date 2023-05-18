import request from '@/utils/request'

export default {

    //兼职列表（条件查询分页）
    //current 当前页、limit 每页记录数、messageQuery 条件对象
    getMessageListPage(current, limit, messageQuery) {
        return request({

            url: `/msgService/message/pageMessageCondition/${current}/${limit}`,
            method: 'post',
            //messageQuery条件对象，后端使用RequestBody获取数据
            //data就是表示把对象转换为json进行传递到接口里面
            data: messageQuery
        })
    },
    //删除家教信息的方法
    delMessageById(id) {
        return request({

            url: `/msgService/message/${id}`,
            method: 'delete',
        })
    },
}


