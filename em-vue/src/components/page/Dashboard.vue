<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="8">
                <el-card shadow="hover" class="mgb20" style="height:400px;">
                    <div slot="header" class="clearfix">
                        <span>欢迎回来</span>
                    </div>
                    <div class="user-info">
                        <img src="../../assets/img/img.jpg" class="user-avator" alt />
                        <div class="user-info-cont">
                            <div class="user-info-name">{{name}}</div>
                            <div>{{role}}</div>
                        </div>
                    </div>
                    <div class="user-info-list">{{one}}</div>
                </el-card>

                <el-card shadow="hover" style="height:325px;">
                    <div slot="header" class="clearfix">
                        <span>登录情况</span>
                    </div>
                    Vue
                    <el-progress :percentage="71.3" color="#42b983"></el-progress>JavaScript
                    <el-progress :percentage="24.1" color="#f1e05a"></el-progress>CSS
                    <el-progress :percentage="13.7"></el-progress>HTML
                    <el-progress :percentage="5.9" color="#f56c6c"></el-progress>
                </el-card>
            </el-col>
            <el-col :span="8">
                <el-card shadow="hover" class="mgb20" style="height:750px;">
                    <el-calendar v-model="value"/>
                </el-card>
            </el-col>
            <el-col :span="8">
                <el-card shadow="hover" class="mgb20" style="height:195px;">
                    <div id="he-plugin-standard"></div>
                </el-card>

                <el-card shadow="hover" style="height:535px;">
                    <div slot="header" class="clearfix">
                        <span>新闻通知</span>
                    </div>
                    <el-table :data="unread" :show-header="false" style="width: 100%">
                        <el-table-column>
                            <template slot-scope="scope">
<!--                                <span class="message-title">{{scope.row.title}}</span>-->
                                <el-link class="message-title"
                                         target="_blank"
                                         href="https://mp.weixin.qq.com/s/nSW9AfNa0YkAEyF5NiFDfw">疫情“打破”家庭教育舒适区：你如何履行责任，直接决定孩子的成长空间！</el-link>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
        </el-row>

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
            }]
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
                this.one = resp.data
            }
        })
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
        }
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
