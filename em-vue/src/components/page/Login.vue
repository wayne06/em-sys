<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">启智培训中心</div>
            <el-form :model="loginForm" :rules="rules" ref="login" label-width="0px" class="ms-content">
                <el-form-item prop="username">
                    <el-input v-model="loginForm.username" placeholder="请输入用户名">
                        <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input
                        type="password"
                        placeholder="请输入密码"
                        v-model="loginForm.password"
                        @keyup.enter.native="submitForm()"
                    >
                        <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="login">登录</el-button>
                </div>
                <p class="login-tips">Tips : 新用户请点击右边注册按钮。
                    <el-link href="/register" style="float: right;">点我注册</el-link>
                </p>


            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    data: function() {
        return {
            loginForm: {
                username: '',
                password: ''
            },
            rules: {
                username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
                password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
            },
            responseResult: []
        }
    },
    methods: {
        login () {
            console.log("login")
            let _this = this
            this.$axios
                .post('/user/login', {
                    username: this.loginForm.username,
                    password: this.loginForm.password
                })
                .then(resp => {
                    if (resp && resp.data === 'Login success') {
                        _this.$store.commit('login', _this.loginForm.username)
                        let path = this.$route.query.redirect
                        this.$router.replace({path: path === '/' || path === undefined ? '/' : path})
                    } else {
                        this.$message('用户名或密码错误')
                    }
                })
                .catch(failResponse => {
                })
        }
    },
};
</script>

<style scoped>
.login-wrap {
    position: relative;
    width: 100%;
    height: 100%;
    background-image: url(../../assets/img/login-bg.jpg);
    background-size: 100%;
}
.ms-title {
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    color: #fff;
    border-bottom: 1px solid #ddd;
}
.ms-login {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 350px;
    margin: -190px 0 0 -175px;
    border-radius: 5px;
    background: rgba(255, 255, 255, 0.3);
    overflow: hidden;
}
.ms-content {
    padding: 30px 30px;
}
.login-btn {
    text-align: center;
}
.login-btn button {
    width: 100%;
    height: 36px;
    margin-bottom: 10px;
}
.login-tips {
    font-size: 12px;
    line-height: 30px;
    color: #fff;
}
</style>
