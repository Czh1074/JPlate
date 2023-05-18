import request from '@/utils/request'

export default {

    //公告列表（条件查询分页）
    //current 当前页、limit 每页记录数、noticeQuery 条件对象
    getNoticeListPage(current, limit, noticeQuery) {
        return request({

            url: `/noticeService/notice/pageNoticeCondition/${current}/${limit}`,
            method: 'post',
            //noticeQuery条件对象，后端使用RequestBody获取数据
            //data就是表示把对象转换为json进行传递到接口里面
            data: noticeQuery
        })
    },
    //删除公告信息的方法
    delNoticeById(id) {
        return request({

            url: `/noticeService/notice/${id}`,
            method: 'delete',
        })
    },
    //添加公告信息的方法
    addNotice(obj) {
        return request({
            url: `/noticeService/notice/save`,
            method: 'post',
            data: obj
        })
    },
}