<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">启智培训中心</div>
            <el-form :model="registerForm" :rules="rules" ref="login" label-width="0px" class="ms-content">
                <el-form-item prop="username">
                    <el-input v-model="registerForm.username" placeholder="请输入用户名">
                        <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" placeholder="请输入密码" v-model="registerForm.password" @keyup.enter.native="submitForm()">
                        <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="register">注册</el-button>
                </div>
                <p class="login-tips">Tips : 用户名和密码随便填。
                    <el-link href="/login" style="float: right">返回登录</el-link>
                </p>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    name: 'Register',
    data: function() {
        return {
            checked: true,
            registerForm: {
                username: '',
                password: ''
            },
            rules: {
                username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
                password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
            },
            loading: false
        };
    },
    methods: {
        register () {
            let _this = this
            this.$axios.post('/user/register', {
                username: this.registerForm.username,
                password: this.registerForm.password
            }).then(resp => {
                if (resp && resp.status === 200) {
                    this.$alert('注册成功', '提示', {
                        confirmButtonText: '确定'
                    })
                    _this.$router.replace('/login')
                } else {
                    this.$alert(resp.data.message, '提示', {
                        confirmButtonText: '确定'
                    })
                }
            }).catch(failResponse => {})
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
