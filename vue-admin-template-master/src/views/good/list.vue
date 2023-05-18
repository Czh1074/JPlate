<template>
    <div class="app-container">

        <!--查询商品表单-->
        <el-form :inline="true" class="demo-form-inline">

            <el-form-item label="商品类别">
                <el-select v-model="goodQuery.sort" placeholder="商品类别">
                    <el-option v-for="item in good_type" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="价格">
                <el-col :span="11">
                    <el-input :span="11" placeholder="最低价格" v-model="goodQuery.low" style="width: 100%;"></el-input>
                </el-col>
                <el-col class="line" :span="1">&nbsp;-</el-col>
                <el-col :span="11">
                    <el-input :span="11" placeholder="最高价格" v-model="goodQuery.high" style="width: 100%;"></el-input>
                </el-col>
            </el-form-item>

            <el-button type="primary" icon="el-icon-search" @click="getlist()">查询</el-button>
            <el-button type="default" @click="resetData()">清空</el-button>
        </el-form>

        <!-- 表格 -->
        <el-table :data="list" border fit highlight-current-row>

            <el-table-column label="序号" width="60" align="center">
                <template slot-scope="scope">
                    {{ (page - 1) * limit + scope.$index + 1 }}
                </template>
            </el-table-column>

            <el-table-column prop="name" label="名称" width="150" align="center" />
            <el-table-column prop="sort" label="类别" width="80" align="center" />

            <el-table-column prop="price" label="价格" width="80" align="center" sortable />
            <!-- 采用阿里云api以缩略图进行渲染，加快渲染速度 -->
            <el-table-column label="图片" width="160" align="center">
                <template slot-scope="scope">
                    <img style="width: 100px; height: 100px"
                        :src="scope.row.picture+'?x-oss-process=image/resize,m_fill,w_100,quality,q_60'" />
                </template>
            </el-table-column>
            <el-table-column prop="introduction" label="简介" align="center" />
            <el-table-column prop="contact" label="联系方式" width="160" align="center" />

            <el-table-column prop="hot" label="热度" width="125" align="center" sortable>
                <template slot-scope="scope">
                    {{scope.row.viewCount + scope.row.collectCount * 2}}
                </template>
            </el-table-column>

            <el-table-column label="操作" width="200" align="center">
                <template slot-scope="scope">
                    <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页 -->
        <el-pagination :current-page="page" :page-size="limit" :total="total"
            style="padding: 30px 0; text-align: center;" layout="total, prev, pager, next, jumper"
            @current-change="getlist" />
    </div>
</template>



<script>

    //引入调用good.js文件
    import good from '@/api/good/good'

    export default {
        //写代码的核心位置
        data() { //定义变量和初始值
            return {
                list: null,
                page: 1,
                limit: 6,
                total: 0,
                goodQuery: {
                    sort: "",
                    low: "",
                    high: ""
                },
                good: {
                    name: "",
                    sort: "",
                    price: "",
                    picture: "",
                    introduction: "",
                    contact: "",
                    hot: ""
                },
                good_type: [{
                    value: '1',
                    label: '服装'
                }, {
                    value: '2',
                    label: '鞋包'
                }, {
                    value: '3',
                    label: '美妆'
                }, {
                    value: '4',
                    label: '数码'
                }, {
                    value: '5',
                    label: '其他'
                }, {
                    value: '',
                    label: '默认'
                }],
            }
        },
        created() { //页面渲染之前执行，一般调用methos定义的方法
            this.getlist()
        },
        methods: { //具体的创建方法，调用good.js定义的方法

            //家教信息列表的方法
            getlist(page = 1) {
                this.page = page
                good.getGoodListPage(this.page, this.limit, this.goodQuery)
                    .then(response => { //请求成功
                        if (response.success === true) {
                            this.list = response.data.rows
                            this.total = response.data.total
                        }
                    })
                    .catch(error => {//请求失败
                        console.log(error)
                    })
            },
            // //家教信息列表的方法
            // getlist(page = 1) {
            //     this.page = page
            //     eduMessage.getEduMessageListPage(this.page, this.limit, this.eduMessageQuery)
            //         .then(response => { //请求成功
            //             if (response.success === true) {
            //                 this.list = response.data.rows
            //                 this.total = response.data.total
            //             }
            //         })
            //         .catch(error => {//请求失败
            //             console.log(error)
            //         })
            // },
            // // fetchData(page = 1) {//条件查询
            // //     this.page = page
            // //     eduMessage.getEduMessageListPage(this.page, this.limit, this.eduMessageQuery)
            // //         .then(response => { //请求成功
            // //             if (response.success === true) {
            // //                 this.list = response.data.rows
            // //                 this.total = response.data.total
            // //             }
            // //         })
            // //         .catch(error => {//请求失败
            // //             console.log(error)
            // //         })
            // // },
            resetData() { //清空的方法
                //表单的数据清空
                this.goodQuery = {}

                //查询所有数据
                this.getlist()
            },
            //删除商品信息的方法
            removeDataById(id) {
                // debugger
                // console.log(memberId)
                this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => { //确定执行删除成功
                    good.delGoodById(id).then(() => {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        })
                        //回到列表
                        this.getlist()
                    })
                })
            }
        }
    }

</script>