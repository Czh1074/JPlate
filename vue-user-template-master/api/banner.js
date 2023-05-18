import request from '@/utils/request'

export default {
    //查询前两条banne的信息
    getListBanner() {
        return request({
            url: `/trendService/trend/index`,
            method: 'post'
        })
    },
    getGoodList() {
        return request({
            url: `/goodService/good/index`,
            method: 'post'
        })
    }
}