<template>
    <div>
        <div class="container">
            <div class="handle-box">
                <el-select
                        v-model="value"
                        placeholder="选择我的班级"
                        class="handle-select mr10"
                        style="width: 300px"
                        @change="select_status"
                >
                    <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
                <el-button type="success" icon="el-icon-folder-opened" @click="saveScore" style="float:right">保存</el-button>
            </div>
            <vxe-table
                    border
                    resizable
                    class="table"
                    :data="tableData"
                    :edit-config="{trigger: 'click', mode: 'cell'}"
                    @edit-actived="editActivedEvent"
                    @edit-closed="editClosedEvent">
                <vxe-table-column field="name" title="姓名" ></vxe-table-column>
                <vxe-table-column field="exam1" title="月考一" :edit-render="{name: '$input', props: {type: 'number'}}"></vxe-table-column>
                <vxe-table-column field="examm" title="期中考" :edit-render="{name: '$input', props: {type: 'number'}}"></vxe-table-column>
                <vxe-table-column field="exam2" title="月考二" :edit-render="{name: '$input', props: {type: 'number'}}"></vxe-table-column>
                <vxe-table-column field="exame" title="期末考" :edit-render="{name: '$input', props: {type: 'number'}}"></vxe-table-column>
                <vxe-table-column field="remark" title="备注" :edit-render="{name: 'input', props: {type: 'text'}}"></vxe-table-column>
            </vxe-table>
        </div>

    </div>
</template>

<script>
    export default {
        name: "score",
        data () {
            return {
                tableData: [],
                tempCourseId: -1,
                value: '',
                options: []
            }
        },
        created () {
            this.getData();
        },
        methods: {
            getData () {
                this.$axios.get('/course/selection').then(resp => {
                    if (resp && resp.status === 200) {
                        this.options = resp.data;
                    }
                });
            },
            editActivedEvent ({ row, column }, event) {
            },
            editClosedEvent ({ row, column }, event) {
            },
            select_status (val) {
                this.tempCourseId = val;
                this.getTableData(val);
            },
            getTableData (courseId) {
                this.$axios.post('/score/getByCourseId', {
                    courseId: courseId
                }).then(resp => {
                    if (resp && resp.status === 200) {
                        this.tableData = resp.data;
                    }
                })
            },
            saveScore () {
                this.tableData.forEach(data => {
                   this.$axios.post('/score/update', {
                       id: data.id,
                       exam1: data.exam1,
                       exam2: data.exam2,
                       examm: data.examm,
                       exame: data.exame,
                       remark: data.remark
                   }).then(resp => {
                       if (resp && resp.status === 200) {
                           this.$message.success('保存' + data.name + '成功');
                           this.getTableData(this.tempCourseId);
                       }
                   })
                });
            }
        }
    }
</script>

<style scoped>
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
    .vxe-icon--edit-outline:before {
        border-style: none;
    }
</style>
