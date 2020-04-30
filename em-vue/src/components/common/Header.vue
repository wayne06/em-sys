<template>
    <div class="header">
        <!-- 折叠按钮 -->
        <div class="collapse-btn" @click="collapseChage">
            <i v-if="!collapse" class="el-icon-s-fold"></i>
            <i v-else class="el-icon-s-unfold"></i>
        </div>
        <div class="logo">西港启智培训中心</div>
        <div class="header-right">
            <div class="header-user-con">
                <!-- 全屏显示 -->
                <div class="btn-fullscreen" @click="handleFullScreen">
                    <el-tooltip effect="dark" :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
                        <i class="el-icon-rank"></i>
                    </el-tooltip>
                </div>

                <!-- 用户头像 -->
                <div class="user-avator">
                    <img src="../../assets/img/img.jpg" />
                </div>

                <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                    <span class="el-dropdown-link">
                        {{username}}
                        <i class="el-icon-caret-bottom"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item divided command="changepass">修改密码</el-dropdown-item>
                        <el-dropdown-item divided command="profile">个人信息</el-dropdown-item>
                        <el-dropdown-item divided command="loginout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>
        <div>
            <el-dialog title="修改密码" :visible.sync="changepassVisible" width="32%" top="10vh" @close="clear1">
                <el-form ref="form1" :model="form1" label-width="130px" label-position="left" size="mini" :rules="rules1">
                    <el-form-item label="原始密码" prop="old">
                        <el-input type="password" placeholder="请输入原始密码" v-model="form1.old"></el-input>
                    </el-form-item>
                    <el-form-item label="新密码" prop="new1">
                        <el-input type="password" placeholder="请输入新的密码" v-model="form1.new1"></el-input>
                    </el-form-item>
                    <el-form-item label="确认新密码" prop="new2">
                        <el-input type="password" placeholder="请再次输入新的密码" v-model="form1.new2"></el-input>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="changepassVisible = false">取 消</el-button>
                    <el-button type="primary" @click="savePass">确 定</el-button>
                </span>
            </el-dialog>
        </div>
        <div>
            <el-dialog title="个人信息" :visible.sync="profileVisible" width="32%" top="10vh" @close="clear2">
                <el-form ref="form2" :model="form2" label-width="130px" label-position="left" size="mini">
                    <el-form-item label="用户名" prop="username">
                        <el-input placeholder="请输入用户名" v-model="form2.username"></el-input>
                    </el-form-item>
                    <el-form-item label="姓名" prop="name">
                        <el-input placeholder="请输入姓名" v-model="form2.name"></el-input>
                    </el-form-item>
                    <el-form-item label="电话" prop="phone">
                        <el-input placeholder="请再次输入联系电话" v-model="form2.phone"></el-input>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="profileVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveProfile">确 定</el-button>
                </span>
            </el-dialog>
        </div>
    </div>

</template>
<script>
import bus from '../common/bus';
import {createRouter} from '../../router'
export default {
    data() {
        return {
            collapse: false,
            fullscreen: false,
            name: 'unknown',
            message: 2,
            changepassVisible: false,
            profileVisible: false,
            form1: {},
            form2: {},
            rules1: {
                old: [
                    { required: true, message: '必填项', trigger: 'blur' },
                ],
                new1: [
                    { required: true, message: '必填项', trigger: 'blur' },
                ],
                new2: [
                    { required: true, message: '必填项', trigger: 'blur' }
                ]
            }
        };
    },
    computed: {
        username() {
            // let username = localStorage.getItem('ms_username');
            // return username ? username : this.name;

            let username = this.$store.state.username;
            return username ? username : this.name;
        }
    },
    methods: {
        // 用户名下拉菜单选择事件
        handleCommand(command) {
            if (command == 'loginout') {
                let _this = this
                this.$axios.get('/user/logout').then(resp => {
                    if (resp && resp.status === 200) {
                        _this.$store.commit('logout')
                        _this.$router.replace('/login')
                        const newRouter = createRouter()
                        _this.$router.matcher = newRouter.matcher
                    }
                }).catch(failResponse => {})
            };
            if (command == 'changepass') {
                this.changepassVisible = true;
            };
            if (command == 'profile') {
                this.$axios.get('/user/profile').then(resp => {
                    if (resp && resp.status === 200) {
                        this.form2 = resp.data;
                    }
                })

                this.profileVisible = true;
            };
        },
        savePass() {
            this.$refs['form1'].validate((valid) => {
                if (valid) {
                    if (this.form1.new1 != this.form1.new2) {
                        this.$message.error('两次密码不一致');
                        return;
                    }
                    this.$axios.post('/user/changePass', {
                        password: this.form1.old,
                        name: this.form1.new1
                    }).then(resp => {
                        if (resp && resp.status === 200) {
                            if (resp.data === "success") {
                                this.$message.success('密码修改成功');
                                this.changepassVisible = false;
                            } else {
                                this.$message.error('原密码不正确');
                                return;
                            }
                        }
                    })
                }
            })
        },
        saveProfile() {

        },
        clear1() {
            this.form1 = {
                old : '',
                new1 : '',
                new2 : ''
            }
        },
        clear2() {

        },
        // 侧边栏折叠
        collapseChage() {
            this.collapse = !this.collapse;
            bus.$emit('collapse', this.collapse);
        },
        // 全屏事件
        handleFullScreen() {
            let element = document.documentElement;
            if (this.fullscreen) {
                if (document.exitFullscreen) {
                    document.exitFullscreen();
                } else if (document.webkitCancelFullScreen) {
                    document.webkitCancelFullScreen();
                } else if (document.mozCancelFullScreen) {
                    document.mozCancelFullScreen();
                } else if (document.msExitFullscreen) {
                    document.msExitFullscreen();
                }
            } else {
                if (element.requestFullscreen) {
                    element.requestFullscreen();
                } else if (element.webkitRequestFullScreen) {
                    element.webkitRequestFullScreen();
                } else if (element.mozRequestFullScreen) {
                    element.mozRequestFullScreen();
                } else if (element.msRequestFullscreen) {
                    // IE11
                    element.msRequestFullscreen();
                }
            }
            this.fullscreen = !this.fullscreen;
        }
    },
    mounted() {
        if (document.body.clientWidth < 1500) {
            this.collapseChage();
        }
    }
};
</script>
<style scoped>
.header {
    position: relative;
    box-sizing: border-box;
    width: 100%;
    height: 70px;
    font-size: 22px;
    color: #fff;
}
.collapse-btn {
    float: left;
    padding: 0 21px;
    cursor: pointer;
    line-height: 70px;
}
.header .logo {
    float: left;
    width: 250px;
    line-height: 70px;
}
.header-right {
    float: right;
    padding-right: 50px;
}
.header-user-con {
    display: flex;
    height: 70px;
    align-items: center;
}
.btn-fullscreen {
    transform: rotate(45deg);
    margin-right: 5px;
    font-size: 24px;
}
.btn-bell,
.btn-fullscreen {
    position: relative;
    width: 30px;
    height: 30px;
    text-align: center;
    border-radius: 15px;
    cursor: pointer;
}
.btn-bell-badge {
    position: absolute;
    right: 0;
    top: -2px;
    width: 8px;
    height: 8px;
    border-radius: 4px;
    background: #f56c6c;
    color: #fff;
}
.btn-bell .el-icon-bell {
    color: #fff;
}
.user-name {
    margin-left: 10px;
}
.user-avator {
    margin-left: 20px;
}
.user-avator img {
    display: block;
    width: 40px;
    height: 40px;
    border-radius: 50%;
}
.el-dropdown-link {
    color: #fff;
    cursor: pointer;
}
.el-dropdown-menu__item {
    text-align: center;
}
</style>
