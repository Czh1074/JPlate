<template>
    <div class="app-container">

        <!--查询表单-->
        <el-form :inline="true" class="demo-form-inline">

            <el-form-item label="主题">
                <el-input v-model="noticeQuery.project" placeholder="主题" />
            </el-form-item>

            <!-- <el-form-item label="年级">
                <el-input v-model="eduMessageQuery.grade" placeholder="年级" />
            </el-form-item> -->


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

            <el-table-column prop="project" label="主题" width="220" align="center" show-overflow-tooltip />
            <el-table-column label="公告内容" align="center" show-overflow-tooltip>
                <template slot-scope="scope">
                    <div v-html='scope.row.introduction'></div>
                </template>
            </el-table-column>

                <el-table-column prop="appendix1" label="附件1" width="140" align="center" show-overflow-tooltip>
                    <!-- <template slot-scope="scope">
                    <el-link :href="scope.row.appendix1" type="primary">下载</el-link>
                </template> -->
                </el-table-column>
                <el-table-column prop="viewCount" label="访问量" width="80" align="center" show-overflow-tooltip />

                <el-table-column prop="gmtCreate" label="添加时间" width="195" align="center" sortable />

                <el-table-column label="操作" width="200" align="center">
                    <template slot-scope="scope">
                        <el-button type="danger" size="mini" icon="el-icon-delete"
                            @click="removeDataById(scope.row.id)">删除
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

    //引入调用notice.js文件
    import notice from '@/api/notice/notice'

    export default {
        //写代码的核心位置
        data() { //定义变量和初始值
            return {
                list: null,
                page: 1,
                limit: 6,
                total: 0,
                noticeQuery: {
                    project: ""
                }
            }
        },
        created() { //页面渲染之前执行，一般调用methos定义的方法
            this.getlist()
        },
        methods: { //具体的创建方法，调用notice.js定义的方法

            //公告信息列表的方法
            getlist(page = 1) {
                this.page = page
                notice.getNoticeListPage(this.page, this.limit, this.noticeQuery)
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
            // fetchData(page = 1) {//条件查询
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
            resetData() { //清空的方法
                //表单的数据清空
                this.noticeQuery = {}

                //查询所有数据
                this.getlist()
            },
            //删除家教信息的方法
            removeDataById(id) {
                // debugger
                // console.log(memberId)
                this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => { //确定执行删除成功
                    notice.delNoticeById(id).then(() => {
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