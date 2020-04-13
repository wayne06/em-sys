<template>
    <div>
        <div class="container">
            <el-tabs v-model="message">
                <el-tab-pane :label="`数据`" name="rawdata">
                    <div class="schart-box">
                        <el-table
                                :data="dayData"
                                height="600"
                                border
                                class="table"
                                header-cell-class-name="table-header">
                            <el-table-column min-width="100px" prop="period" label="日期"></el-table-column>
                            <el-table-column prop="income" label="收费"></el-table-column>
                            <el-table-column prop="discount" label="减免"></el-table-column>
                            <el-table-column prop="refund" label="退费"></el-table-column>
                        </el-table>
                    </div>
                    <div class="schart-box">
                        <el-table
                                :data="weekData"
                                height="600"
                                border
                                class="table"
                                header-cell-class-name="table-header">
                            <el-table-column prop="period" label="周次"></el-table-column>
                            <el-table-column prop="income" label="收费"></el-table-column>
                            <el-table-column prop="discount" label="减免"></el-table-column>
                            <el-table-column prop="refund" label="退费"></el-table-column>
                        </el-table>
                    </div>
                    <div class="schart-box">
                        <el-table
                                :data="monthData"
                                height="600"
                                border
                                class="table"
                                header-cell-class-name="table-header">
                            <el-table-column prop="period" label="月份"></el-table-column>
                            <el-table-column prop="income" label="收费"></el-table-column>
                            <el-table-column prop="discount" label="减免"></el-table-column>
                            <el-table-column prop="refund" label="退费"></el-table-column>
                        </el-table>
                    </div>
                    <div class="schart-box">
                        <el-table
                                :data="yearData"
                                height="600"
                                border
                                class="table"
                                header-cell-class-name="table-header">
                            <el-table-column prop="period" label="年份"></el-table-column>
                            <el-table-column prop="income" label="收费"></el-table-column>
                            <el-table-column prop="discount" label="减免"></el-table-column>
                            <el-table-column prop="refund" label="退费"></el-table-column>
                        </el-table>
                    </div>
                </el-tab-pane>
                <el-tab-pane :label="`图表`" name="graph">
                    <template v-if="message === 'graph'">
                        <div class="schart-box">
                            <schart class="schart" canvasId="bar" :options="options1"></schart>
                        </div>
                        <div class="schart-box">
                            <schart class="schart" canvasId="line" :options="options2"></schart>
                        </div>
                    </template>
                </el-tab-pane>
            </el-tabs>

        </div>
    </div>
</template>

<script>
import Schart from 'vue-schart';
export default {
    name: 'financialstatement',
    components: {
        Schart
    },
    data() {
        return {
            message: 'rawdata',
            dayData: [],
            weekData: [],
            monthData: [],
            yearData: [],
            options1: {
                type: 'line',
                title: {
                    text: '报名人数周次图'
                },
                labels: [],
                datasets: []
            },
            options2: {
                type: 'bar',
                title: {
                    text: '报名缴费周次图'
                },
                labels: [],
                datasets: []
            }
        };
    },
    created() {
        this.getTableData();
    },
    methods: {
        getTableData () {
            this.$axios.get('/statistics/data').then(resp => {
                if (resp && resp.status === 200) {
                    this.dayData = resp.data.dayData;
                    this.weekData = resp.data.weekData;
                    this.monthData = resp.data.monthData;
                    this.yearData = resp.data.yearData;
                }
            });
            this.$axios.get('/statistics/graph').then(resp => {
                if (resp && resp.status === 200) {
                    this.options1.labels = resp.data.stuCount.labels;
                    this.options1.datasets = resp.data.stuCount.datasets;
                    this.options2.labels = resp.data.income.labels;
                    this.options2.datasets = resp.data.income.datasets;
                }
            });
        }
    }
};
</script>

<style scoped>
.schart-box {
    display: inline-block;
    margin: 40px 20px 20px 20px;
}
.schart {
    width: 600px;
    height: 600px;
}
.content-title {
    clear: both;
    font-weight: 400;
    line-height: 50px;
    margin: 10px 0;
    font-size: 22px;
    color: #1f2f3d;
}
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}

.table {
    width: 100%;
    font-size: 14px;
}

.red {
    color: #ff0000;
}

.mr10 {
    margin-right: 10px;
}

.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>
