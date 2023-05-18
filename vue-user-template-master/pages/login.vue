<template>
    <!-- <div class="logo">
        <img src="https://jmu-plate.oss-cn-beijing.aliyuncs.com/%E6%AF%95%E8%AE%BE%E5%9B%BE%E7%89%87/background.jpg;">
    </div> -->
    <div class="main">
        <div class="title">
            <a class="active" href="/login">登录</a>
            <span>·</span>
            <a href="/register">注册</a>
        </div>

        <div class="sign-up-container">
            <el-form ref="userForm" :model="user">

                <el-form-item class="input-prepend restyle" prop="email"
                    :rules="[{ required: true, message: '请输入学号', trigger: 'blur' },{validator: checkPhone, trigger: 'blur'}]">
                    <div>
                        <el-input type="text" placeholder="请输入学号" v-model="user.email">
                            <template slot="append">@jmu.edu.cn</template>
                        </el-input>
                        <i class="iconfont icon-phone" />
                    </div>
                </el-form-item>

                <el-form-item class="input-prepend" prop="password"
                    :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
                    <div>
                        <el-input type="password" placeholder="密码" v-model="user.password" />
                        <i class="iconfont icon-password" />
                    </div>
                </el-form-item>

                <div class="btn">
                    <input type="button" class="sign-in-button" value="登录" @click="submitLogin()">
                </div>
            </el-form>
            <!-- 更多登录方式 -->
            <!-- <div class="more-sign">
                <h6>社交帐号登录</h6>
                <ul>
                    <li><a id="weixin" class="weixin" target="_blank"
                            href="http://qy.free.idcfengye.com/api/ucenter/weixinLogin/login"><i
                                class="iconfont icon-weixin" /></a></li>
                    <li><a id="qq" class="qq" target="_blank" href="#"><i class="iconfont icon-qq" /></a></li>
                </ul>
            </div> -->
        </div>

    </div>
</template>

<script>
    import '~/assets/css/sign.css'
    import '~/assets/css/iconfont.css'
    import cookie from 'js-cookie'
    import loginApi from '@/api/login'
    export default {
        layout: 'sign',

        data() {
            return {
                user: {
                    email: '',
                    username: '',
                    password: ''
                },
                loginInfo: {}
            }
        },

        methods: {
            submitLogin() {
                this.user.username = this.user.email + "@jmu.edu.cn"
                loginApi.submitLogin(this.user).then(response => {
                    if (response.data.success) {

                        //把token存在cookie中、也可以放在localStorage中
                        //第一个参数是cookie的名称，第二个是获取到token的值，第三个是作用范围
                        cookie.set('plate_token', response.data.data.token, { domain: 'localhost' })
                        //登录成功根据token获取用户信息
                        loginApi.getLoginInfo().then(response => {

                            this.userInfo = response.data.data.item
                            //将用户信息记录cookie
                            cookie.set('plate_user', JSON.stringify(this.userInfo), { domain: 'localhost' })
                            //跳转页面
                            window.location.href = "/";
                        })
                    }
                })
            },

            checkPhone(rule, value, callback) {
                //debugger
                if (!(/^\d{12}$/.test(value))) {
                    return callback(new Error('学号格式不正确'))
                }
                return callback()
            }
        }
    }
</script>
<style>
    .el-form-item__error {
        z-index: 9999999;
    }
</style>