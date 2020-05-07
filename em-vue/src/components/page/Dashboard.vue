<template>
    <div>
        <el-row :gutter="20" style="margin-bottom: 1px">
            <el-col :span="8">
                <el-card shadow="hover" class="mgb20" style="height:370px;">
                    <div class="user-info">
                        <img src="../../assets/img/img.jpg" class="user-avator" alt />
                        <div class="user-info-cont">
                            <div class="user-info-name">{{name}}</div>
                            <div>{{role}}</div>
                        </div>
                    </div>
                    <div class="user-info-list">{{one}}</div>
                </el-card>
            </el-col>
            <el-col :span="8">
                <el-card shadow="hover" class="mgb20" style="height:370px;">
                        <el-calendar v-model="value"/>
                </el-card>
            </el-col>
            <el-col :span="8">
                <el-card shadow="hover" class="mgb20" style="height:370px;">
<!--                    <div id="he-plugin-standard"></div>-->
<!--                    <iframe width="280" scrolling="no" height="25" frameborder="0" allowtransparency="true" src="http://i.tianqi.com/index.php?c=code&id=34&icon=1&num=3"></iframe>-->
                    <iframe width="420" scrolling="no" height="60" frameborder="0" allowtransparency="true" src="//i.tianqi.com/index.php?c=code&id=12&bdc=%23&icon=1&num=5&site=12"></iframe>
                </el-card>
            </el-col>
        </el-row>


        <el-row :gutter="20">

            <el-col :span="12">
                <el-card shadow="hover" style="height:400px;">
                    <div slot="header" class="clearfix">
                        <span>新闻通知</span>
                    </div>
                    <el-table :data="news" :show-header="false" style="width: 100%">
                        <el-table-column>
                            <template slot-scope="scope">
                                <el-link class="message-title"
                                         target="_blank"
                                         :href="scope.row.link">
                                        <span v-if="scope.row.priority === '重要'">【{{scope.row.priority}}】{{scope.row.title}}</span>
                                        <span v-else > {{scope.row.title}}</span>
                                </el-link>

                            </template>
                        </el-table-column>
                        <el-table-column width="60">
                            <template slot-scope="scope">
                                <div>{{scope.row.enabledAt.substring(5,10)}}</div>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>

            <el-col :span="12">
                <el-card shadow="hover" style="height:400px;">
                    <div slot="header" class="clearfix">
                        <span>待办事项</span>
                        <el-button style="float: right; padding: 3px 0" type="text" @click="handleAdd()">添加</el-button>
                    </div>
                    <el-table :show-header="false" :data="todoList" style="width:100%;">
                        <el-table-column width="40">
                            <template slot-scope="scope">
                                <el-checkbox v-model="scope.row.status" @change="update(scope.$index, scope.row)"></el-checkbox>
                            </template>
                        </el-table-column>
                        <el-table-column>
                            <template slot-scope="scope">
                                <div class="todo-item" :class="{'todo-item-del': scope.row.status}">{{scope.row.title}}</div>
                            </template>
                        </el-table-column>
                        <el-table-column width="143">
                            <template slot-scope="scope">
                                <div class="todo-item" :class="{'todo-item-del': scope.row.status}">{{scope.row.time}}</div>
                            </template>
                        </el-table-column>
                        <el-table-column width="60">
                            <template slot-scope="scope">
                                <i class="el-icon-delete" @click="del(scope.$index, scope.row)"></i>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>

        </el-row>

        <el-dialog title="添加代办事项" :visible.sync="addVisible" width="28%" @close="clear" top="10vh">
            <el-form ref="form" :model="form" label-width="90px" label-position="left" size="mini" :rules="rules">
                <el-form-item label="代办事项" prop="title">
                    <el-input placeholder="请输入要添加的代办事项" v-model="form.title"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
    WIDGET = {
        CONFIG: {
            "layout": 1,
            "width": "550",
            "height": "150",
            "background": 1,
            "dataColor": "FFFFFF",
            "language": "zh",
            "borderRadius": 5,
            "key": "3658ef0a7d2145d396eb59eb6d549664"
        }
    }
</script>
<script src="https://widget.heweather.net/standard/static/js/he-standard-common.js?v=1.1"></script>

<script>
import Schart from 'vue-schart';
import bus from '../common/bus';
export default {
    name: 'dashboard',
    data() {
        return {
            value: new Date(),
            name: this.$store.state.username,
            one: '',
            message: 'first',
            showHeader: false,
            unread: [{
                date: '2018-04-19 20:00:00',
                title: '',
            }],
            todoList: [],
            news: [],
            addVisible: false,
            form: {},
            rules: {
                title: [
                    {required: true, message: '不能为空', trigger: 'blur'},
                ]
            }
        };
    },
    components: {
        Schart
    },
    mounted() {
        window.WIDGET = {
            CONFIG: {
                "layout": 1,
                "width": "490",
                "height": "155",
                "background": 1,
                "dataColor": "FFFFFF",
                "language": "zh",
                "borderRadius": 5,
                "key": "3658ef0a7d2145d396eb59eb6d549664"
            }
        },
        (function (d) {
            var c = d.createElement('link')
            c.rel = 'stylesheet'
            c.href = 'https://widget.heweather.net/standard/static/css/he-standard.css?v=1.3'
            var s = d.createElement('script')
            s.src = 'https://widget.heweather.net/standard/static/js/he-standard.js?v=1.3'
            var sn = d.getElementsByTagName('script')[0]
            sn.parentNode.insertBefore(c, sn)
            sn.parentNode.insertBefore(s, sn)
        })(document)
    },
    computed: {
        role() {
            return this.name === 'admin' ? '管理员' : '普通用户';
        }
    },
    created() {
        // this.handleListener();
        // this.changeDate();
        this.$axios.get('/user/one').then(resp => {
            if (resp && resp.status === 200) {
                this.one = resp.data;
            }
        });
        this.getTodoList();
        this.getNews();
    },
    // activated() {
    //     this.handleListener();
    // },
    // deactivated() {
    //     window.removeEventListener('resize', this.renderChart);
    //     bus.$off('collapse', this.handleBus);
    // },
    methods: {
        changeDate() {
            const now = new Date().getTime();
            this.data.forEach((item, index) => {
                const date = new Date(now - (6 - index) * 86400000);
                item.name = `${date.getFullYear()}/${date.getMonth() + 1}/${date.getDate()}`;
            });
        },
        getTodoList () {
            this.$axios.get('/todo/all').then(resp => {
                if (resp && resp.status === 200) {
                    this.todoList = resp.data;
                }
            });
        },
        getNews () {
            this.$axios.get('/news/allForHomepage').then(resp => {
                if (resp && resp.status === 200) {
                    this.news = resp.data;
                }
            })
        },
        handleAdd () {
            this.addVisible = true;
        },
        save () {
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    this.$axios.post('/todo/add', {
                        title: this.form.title
                    }).then(resp => {
                        if (resp && resp.status === 200) {
                            this.getTodoList();
                            this.addVisible = false;
                        }
                    })
                } else {
                    return true;
                }
            })

        },
        clear () {
            this.$refs['form'].resetFields();
            this.form.title = '';
        },
        del (index, row) {
            this.$axios.post('/todo/del', {
                id: row.id
            }).then(resp => {
                if (resp && resp.status === 200) {
                    this.getTodoList();
                }
            })
        },
        update (index, row) {
            console.log("@change")
            this.$axios.post('/todo/update', {
                id: row.id
            }).then(resp => {
                if (resp && resp.status === 200) {
                    this.getTodoList();
                }
            })
        },
        // update () {
        //     console.log("@change")
        // }
        // handleListener() {
        //     bus.$on('collapse', this.handleBus);
        //     // 调用renderChart方法对图表进行重新渲染
        //     window.addEventListener('resize', this.renderChart);
        // },
        // handleBus(msg) {
        //     setTimeout(() => {
        //         this.renderChart();
        //     }, 200);
        // },
        // renderChart() {
        //     this.$refs.bar.renderChart();
        //     this.$refs.line.renderChart();
        // }
    }
};
</script>


<style scoped>
.el-row {
    margin-bottom: 20px;
}

.grid-content {
    display: flex;
    align-items: center;
    height: 100px;
}

.grid-cont-right {
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: #999;
}

.grid-num {
    font-size: 30px;
    font-weight: bold;
}

.grid-con-icon {
    font-size: 50px;
    width: 100px;
    height: 100px;
    text-align: center;
    line-height: 100px;
    color: #fff;
}

.grid-con-1 .grid-con-icon {
    background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
    background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-3 .grid-con-icon {
    background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
    color: rgb(242, 94, 67);
}

.user-info {
    display: flex;
    align-items: center;
    padding-bottom: 20px;
    border-bottom: 2px solid #ccc;
    margin-bottom: 20px;
}

.user-avator {
    width: 120px;
    height: 120px;
    border-radius: 50%;
}

.user-info-cont {
    padding-left: 50px;
    flex: 1;
    font-size: 14px;
    color: #999;
}

.user-info-cont div:first-child {
    font-size: 30px;
    color: #222;
}

.user-info-list {
    padding-top: 10px;
    font-size: 14px;
    color: #999;
    line-height: 30px;
}

.user-info-list span {
    margin-left: 70px;
}

.mgb20 {
    margin-bottom: 20px;
}

.todo-item {
    font-size: 14px;
}

.todo-item-del {
    text-decoration: line-through;
    color: #999;
}

.schart {
    width: 100%;
    height: 300px;
}

/*#he-plugin-standard {*/
/*    width: 150%;*/
/*}*/




</style>
