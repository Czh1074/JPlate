<template>
    <div class="app-container">

        <!--查询表单-->
        <el-form :inline="true" class="demo-form-inline">

            <el-form-item label="兼职名称">
                <el-input v-model="messageQuery.name" placeholder="兼职名称" />
            </el-form-item>

            <el-form-item label="最低薪资">
                <el-input v-model="messageQuery.low" placeholder="最低薪资" />
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

            <el-table-column prop="name" label="兼职名称" width="80" align="center" />
            <el-table-column prop="introduction" label="兼职内容" align="center" />

            <el-table-column prop="address" label="地址" width="290" align="center" />
            <el-table-column prop="salary" label="薪资" width="90" align="center" sortable />
            <el-table-column prop="contact" label="联系方式" width="120" align="center" />
            <el-table-column prop="contactName" label="联系人" width="160" align="center" />

            <el-table-column prop="gmtCreate" label="添加时间" width="155" align="center" />

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

    //引入调用message.js文件
    import message from '@/api/message/message'

    export default {
        //写代码的核心位置
        data() { //定义变量和初始值
            return {
                list: null,
                page: 1,
                limit: 6,
                total: 0,
                messageQuery: {
                    name: "",
                    low: ""
                }
            }
        },
        created() { //页面渲染之前执行，一般调用methos定义的方法
            this.getlist()
        },
        methods: { //具体的创建方法，调用message.js定义的方法

            //家教信息列表的方法
            getlist(page = 1) {
                this.page = page
                message.getMessageListPage(this.page, this.limit, this.messageQuery)
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
                this.messageQuery = {}

                //查询所有数据
                this.getlist()
            },
            //删除兼职信息的方法
            removeDataById(id) {
                // debugger
                // console.log(memberId)
                this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => { //确定执行删除成功
                    message.delMessageById(id).then(() => {
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