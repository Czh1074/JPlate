<template>
    <div class="app-container">

        <!--查询表单-->
        <el-form :inline="true" class="demo-form-inline">

            <el-form-item label="主题">
                <el-input v-model="trendQuery.project" placeholder="主题" />
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

            <el-table-column prop="project" label="主题" width="220" align="center" />
            <el-table-column prop="introduction" label="动态内容" align="center" show-overflow-tooltip />
            <!-- 采用阿里云api以缩略图进行渲染，加快渲染速度 -->
            <el-table-column label="图片1" width="140" align="center">
                <template slot-scope="scope">
                    <img style="width: 100px; height: 100px"
                        :src="scope.row.picture1+'?x-oss-process=image/resize,m_fill,w_100,quality,q_60'" />
                </template>
            </el-table-column>
            <el-table-column prop="viewCount" label="访问量" width="100" align="center" sortable />
            <el-table-column prop="viewCount" label="点击量" width="80" align="center" />
            <el-table-column prop="viewCount" label="收藏量" width="80" align="center" />

            <el-table-column prop="gmtCreate" label="添加时间" width="170" align="center" />

            <el-table-column label="操作" width="130" align="center">
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

    //引入调用trend.js文件
    import trend from '@/api/trend/trend'

    export default {
        //写代码的核心位置
        data() { //定义变量和初始值
            return {
                list: null,
                page: 1,
                limit: 6,
                total: 0,
                trendQuery: {
                    project: "",
                    exchange: 0
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
                trend.getTrendListPage(this.page, this.limit, this.trendQuery)
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
            resetData() { //清空的方法
                //表单的数据清空
                this.trendQuery = {}

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
                    trend.delTrendById(id).then(() => {
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