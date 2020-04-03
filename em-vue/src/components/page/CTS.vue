<template>
    <div class="">
<!--        <div class="crumbs">-->
<!--            <el-breadcrumb separator="/">-->
<!--                <el-breadcrumb-item><i class="el-icon-lx-copy"></i> tab选项卡</el-breadcrumb-item>-->
<!--            </el-breadcrumb>-->
<!--        </div>-->
        <div class="container">
            <el-tabs v-model="message">
                <el-tab-pane :label="`待处理`" name="first">

                    <el-steps :active="active"  finish-status="success" style="padding-top: 15px; padding-bottom: 15px">
                        <el-step title="报名信息提交"></el-step>
                        <el-step title="审批"></el-step>
                        <el-step title="课程安排提交"></el-step>
                        <el-step title="审批"></el-step>
                        <el-step title="完成"></el-step>
                    </el-steps>

                    <el-table
                            :data="tableData1"
                            :show-header="true"
                            height="666"
                            border
                            class="table"
                            header-cell-class-name="table-header"
                            :row-key="getRowKeys"
                            :expand-row-keys="expands"
                            @expand-change="expandChange"
                    >
                        <el-table-column type="expand" width="55" align="center">
                            <template slot-scope="props">
                                <el-table
                                        :data="innerTableData1"
                                        height="400"
                                        border
                                        class="table"
                                        header-cell-class-name="table-header"
                                >
                                    <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                                    <el-table-column prop="name" label="姓名"></el-table-column>
                                    <el-table-column prop="gender" label="性别"></el-table-column>
                                    <el-table-column prop="school" label="学校"></el-table-column>
                                    <el-table-column prop="gradeAndClass" label="年级班级"></el-table-column>
                                    <el-table-column prop="parentName" label="家长姓名"></el-table-column>
                                    <el-table-column prop="telephone" label="联系方式"></el-table-column>
                                    <el-table-column prop="address" label="家庭住址"></el-table-column>
                                    <el-table-column prop="remark" label="备注"></el-table-column>
                                </el-table>
                            </template>
                        </el-table-column>

                        <el-table-column prop="courseName" label="课程名称"></el-table-column>
                        <el-table-column prop="teacherName" label="教师姓名"></el-table-column>
                        <el-table-column prop="telephone" label="联系电话"></el-table-column>
                        <el-table-column prop="lessonCount" label="课时安排"></el-table-column>
                        <el-table-column prop="period" label="上课日期区间"></el-table-column>
                        <el-table-column prop="remark" label="备注"></el-table-column>
                        <el-table-column label="操作" width="180" align="center">
                            <template slot-scope="scope">
                                <el-button
                                        type="text"
                                        icon="el-icon-edit"
                                        @click="handleEdit(scope.$index, scope.row)"
                                >编辑</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-tab-pane>
                <el-tab-pane :label="`已处理`" name="second">
                    <template v-if="message === 'second'">

                    </template>
                </el-tab-pane>
            </el-tabs>
        </div>

        <el-dialog title="编辑" :visible.sync="editVisible" width="40%" @close="clear" top="20vh">
            <el-form ref="form" :model="form" label-width="100px" label-position="left" size="mini">
                <el-form-item label="选择教师">
                    <el-select v-model="form.option" placeholder="请选择" style="width: 100%" @change="select_status">
                        <el-option
                                v-for="item in form.options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="课时安排">
                    <el-input v-model="form.lessonCount"></el-input>
                </el-form-item>
                <el-form-item label="上课日期区间">
                    <el-date-picker
                            style="width: 100%"
                            v-model="form.period"
                            type="daterange"
                            range-separator="至"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期"
                            value-format="yyyy-MM-dd">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="form.remark"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: 'cts',
        data() {
            return {
                message: 'first',
                showHeader: false,
                tableData1: [],
                tableData2: [],
                innerTableData1: [],
                innerTableData2: [],
                expands: [],
                tempRowId: -1,
                tempCourseId: -1,
                getRowKeys (row) {
                    return row.id;
                },
                editVisible: false,
                addVisible: false,
                form: {},
                options: [],
                value: '',
                idx: -1,
                active: 0
            }
        },
        created() {
            this.getData();
        },
        methods: {
            getData() {
                let _this = this
                this.$axios.get('/teachercourse/getAll').then(resp => {
                    if (resp && resp.status === 200) {
                        _this.tableData1 = resp.data;
                    }
                });
            },
            expandChange (row) {
                console.log(row)
                this.tempRowId = row.id;
                this.tempCourseId = row.courseId;
                let _this = this;
                let temp = this.expands;
                this.expands = [];
                this.expands.push(row.id);
                if (temp.length === 1 && temp[0] === row.id) {
                    this.expands = [];
                } else {
                    this.$axios.post('/student/getStuByCourse', {id: row.courseId}).then(resp => {
                        if (resp && resp.status === 200) {
                            _this.innerTableData1 = resp.data;
                        }
                    })
                }
            },
            handleEdit (index, row) {
                this.idx = index;
                this.form = row;
                this.tempRowId = row.id;
                this.tempCourseId = row.courseId;



                    this.$axios.get('/teacher/selection').then(resp => {
                    if (resp && resp.status === 200) {
                        this.options = resp.data;
                        this.form.options = resp.data;

                        this.form.option = row.teacherId.toString();
                        this.form.period = row.period.split(' : ');

                        this.editVisible = true;
                    }
                });
            },
            select_status () {
                this.$forceUpdate();
            },
            save () {
                console.log(this.tempRowId);
                console.log(this.tempCourseId);
                this.$axios.post('/teachercourse/update', {
                    id: this.tempRowId,
                    teacherId: this.form.option,
                    courseId: this.tempCourseId,
                    lessonCount: this.form.lessonCount,
                    period: this.form.period[0] + " : " + this.form.period[1],
                    remark: this.form.remark
                }).then(resp => {
                    if (resp && resp.status === 200) {
                        this.$message.success('添加成功');
                        this.editVisible = false;
                        this.getData();
                    }
                })

            },
            clear () {
                this.form = {
                    option: '',
                    lessonCount: '',
                    period: '',
                    remark: ''
                };
                this.getData();
            }
        },
        computed: {

        }
    }

</script>

<style>
.message-title{
    cursor: pointer;
}
.handle-row{
    margin-top: 30px;
}
</style>

