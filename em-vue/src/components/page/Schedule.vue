<template>
    <div class="">
        <div class="container">

            <el-tabs v-model="message">
                <el-tab-pane :label="`处理中(${this.pData.length})`" name="processing">
                    <el-table :data="pData" :show-header="false" style="width: 100%">

                        <el-table-column @click="toMessageData">
                            <template slot-scope="scope">
                                <span class="message-title" @click="toMessageData(scope.$index, scope.row)">{{scope.row.username}}-创建的报名信息： {{scope.row.title}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="createdAt" width="180"></el-table-column>
                    </el-table>
                    <!--                    <div class="handle-row">-->
                    <!--                        <el-button type="primary" @click="createSignInfo">新建报名信息</el-button>-->
                    <!--                    </div>-->

                </el-tab-pane>

                <el-tab-pane :label="`已提交(${this.cData.length})`" name="committed">
                    <template v-if="message === 'committed'">
                        <el-table :data="cData" :show-header="false" style="width: 100%">

                            <el-table-column @click="toMessageData">
                                <template slot-scope="scope">
                                    <span class="message-title" @click="toMessageData(scope.$index, scope.row)">{{scope.row.username}}-创建的报名信息： {{scope.row.title}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="createdAt" width="180"></el-table-column>
                        </el-table>
                    </template>
                </el-tab-pane>

                <el-tab-pane :label="`已完成(${this.fData.length})`" name="finished">
                    <template v-if="message === 'finished'">
                        <el-table :data="fData" :show-header="false" style="width: 100%">

                            <el-table-column @click="toMessageData">
                                <template slot-scope="scope">
                                    <span class="message-title" @click="toMessageData(scope.$index, scope.row)">{{scope.row.username}}-创建的报名信息： {{scope.row.title}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="createdAt" width="180"></el-table-column>
                        </el-table>
                    </template>
                </el-tab-pane>

                <el-dialog :title="this.dTitle" :visible.sync="messageVisible" width="90%" @close="clearOutter"
                           top="3vh">

                    <el-row :gutter="10">
                        <el-col :span="4">
                            <el-card shadow="hover" class="mgb20" style="height:750px;">
                                <el-timeline :reverse=true>
                                    <el-timeline-item
                                            v-for="(activity, index) in activities"
                                            :key="index"
                                            :timestamp="activity.timestamp"
                                            color="rgba(8, 151, 255, 1)">
                                        <p class="timeline-content">{{activity.content}}</p>
                                        <p class="timeline-name" v-if="activity.name">
                                            <span>操作者：{{activity.name}}</span>
                                        </p>
                                        <p class="timeline-name" v-if="activity.feedback">
                                            <span>意见：{{activity.feedback}}</span>
                                        </p>
                                    </el-timeline-item>
                                </el-timeline>
                            </el-card>
                        </el-col>

                        <el-col :span="20">
                            <el-card shadow="hover" class="mgb20" style="height:750px;">

                                <div class="handle-box">
                                    <el-button :disabled="active==2?false:true" type="success"
                                               icon="el-icon-s-promotion" @click="submitTeacherCourse"
                                               style="float:right; margin-bottom: 20px"
                                               v-loading.fullscreen.lock="fullscreenLoading"
                                    >提交
                                    </el-button>
                                </div>

                                <el-table
                                        :data="tableData"
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
                                                    :data="innerTableData"
                                                    height="400"
                                                    border
                                                    class="table"
                                                    header-cell-class-name="table-header"
                                            >
                                                <el-table-column prop="id" label="ID" width="55"
                                                                 align="center"></el-table-column>
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

                                    <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                                    <el-table-column prop="courseName" label="课程名称"></el-table-column>
                                    <el-table-column prop="teacherName" label="教师姓名"></el-table-column>
                                    <el-table-column prop="telephone" label="联系电话"></el-table-column>
                                    <el-table-column prop="lessonCount" label="课时安排"></el-table-column>
                                    <el-table-column prop="period" label="上课日期区间"></el-table-column>
                                    <el-table-column prop="remark" label="备注"></el-table-column>
                                    <el-table-column label="操作" width="180" align="center">
                                        <template slot-scope="scope">
                                            <el-button
                                                    :disabled="active==2?false:true"
                                                    type="text"
                                                    icon="el-icon-edit"
                                                    @click="handleEdit(scope.$index, scope.row)"
                                            >编辑
                                            </el-button>
                                        </template>
                                    </el-table-column>
                                </el-table>
                                <div class="pagination">
                                    <el-pagination
                                            background
                                            layout="total"
                                            :total="pageTotal"
                                    ></el-pagination>
                                </div>

                                <!-- 编辑弹出框 -->
                                <el-dialog title="编辑" :visible.sync="editVisible" width="32%" @close="clear" top="10vh"
                                           append-to-body>
                                    <el-form ref="form" :model="form" label-width="130px" label-position="left"
                                             size="mini" :rules="rules">
                                        <el-form-item label="选择教师" prop="option">
                                            <el-select v-model="form.option" placeholder="请选择教师" style="width: 100%"
                                                       @change="select_status">
                                                <el-option
                                                        v-for="item in form.options"
                                                        :key="item.value"
                                                        :label="item.label"
                                                        :value="item.value"
                                                >
                                                </el-option>
                                            </el-select>
                                        </el-form-item>
                                        <el-form-item label="课时安排" prop="lessonCount">
                                            <el-input placeholder="请输入课时数" v-model="form.lessonCount"></el-input>
                                        </el-form-item>
                                        <el-form-item label="上课日期区间" prop="period">
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
                                            <el-input placeholder="请输入备注信息" v-model="form.remark"></el-input>
                                        </el-form-item>
                                    </el-form>
                                    <span slot="footer" class="dialog-footer">
                                        <el-button @click="editVisible = false">取 消</el-button>
                                        <el-button type="primary" @click="save">确 定</el-button>
                                    </span>
                                </el-dialog>
                            </el-card>
                        </el-col>
                    </el-row>
                </el-dialog>

            </el-tabs>

        </div>
    </div>
</template>

<script>
    export default {
        name: 'schedule',
        data() {
            return {
                pData: [],
                cData: [],
                fData: [],
                message: 'processing',
                showHeader: false,
                messageVisible: false,
                expands: [],
                getRowKeys(row) {
                    return row.id;
                },
                tableData: [],
                innerTableData: [],
                keyword: '',
                active: 0,
                fullscreenLoading: false,
                editVisible: false,
                addVisible: false,
                addSignUpVisible: false,
                editSignUpVisible: false,
                form: {},
                idx: -1,
                pageTotal: 0,
                options: [],
                tempRowId: -1,
                dTitle: '',
                mid: -1,
                activities: [],
                rules: {
                    option: [
                        {required: true, message: '必填项', trigger: 'blur'},
                    ],
                    lessonCount: [
                        {required: true, message: '必填项', trigger: 'blur'},
                    ],
                    period: [
                        {required: true, message: '必填项', trigger: 'blur'},
                    ]
                }
            }
        },
        methods: {
            handleRead(index) {
                const item = this.unread.splice(index, 1);
                this.read = item.concat(this.read);
            },
            handleDel(index) {
                const item = this.read.splice(index, 1);
                this.recycle = item.concat(this.recycle);
            },
            handleRestore(index) {
                const item = this.recycle.splice(index, 1);
                this.read = item.concat(this.read);
            },
            getData() {
                let _this = this
                this.$axios.get('/signup/collectByStatus').then(resp => {
                    if (resp && resp.status === 200) {
                        _this.pData = resp.data.data2;
                        _this.cData = resp.data.data6;
                        _this.fData = resp.data.data4;
                    }
                })
            },
            toMessageData(index, row) {
                this.mid = row.id;
                this.active = row.status;
                this.getMessageData();
                this.dTitle = row.title;
                this.messageVisible = true;
            },
            getMessageData() {
                let _this = this
                this.$axios.post('/teachercourse/getByMid', {
                    signupId: this.mid
                }).then(resp => {
                    if (resp && resp.status === 200) {
                        _this.activities = resp.data.activities;
                        console.log(resp.data.tableData)
                        _this.tableData = resp.data.tableData;
                    }
                })
            },
            // 编辑操作
            handleEdit(index, row) {
                // console.log("row")
                // console.log(row)
                this.idx = index;
                this.form = row;
                this.tempRowId = row.id;
                this.tempCourseId = row.courseId;

                this.$axios.get('/teacher/selection').then(resp => {
                    if (resp && resp.status === 200) {
                        this.options = resp.data;
                        this.form.options = resp.data;

                        if (row.teacherId === null) {
                            this.form.option = null;
                        } else {
                            this.form.option = row.teacherId.toString();
                            console.log(this.form.option)
                        }

                        if (row.period === null) {
                            this.form.period = null;
                        } else {
                            this.form.period = row.period.split(' : ');
                            console.log(this.form.period)
                        }

                        this.editVisible = true;
                    }
                });
            },
            // 保存新增
            save() {
                console.log(111)
                console.log(this.form.option)
                console.log(111)
                this.$refs['form'].validate((valid) => {
                    if (valid) {
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
                                this.getMessageData();
                            }
                        })
                    } else {
                        console.log(222)
                        console.log(this.form)
                        console.log(222)
                        return false;
                    }
                });


            },
            clearOutter() {
                this.getData();
            },
            clear() {
                this.$refs['form'].resetFields();
                this.form = {
                    option: '',
                    lessonCount: '',
                    period: '',
                    remark: ''
                };
                this.getMessageData();
            },
            clearInner(row) {
                this.form = {
                    name: '',
                    gender: '',
                    school: '',
                    gradeAndClass: '',
                    parentName: '',
                    telephone: '',
                    address: '',
                    remark: ''
                }
                this.refreshInnerData(this.tempRowId);
            },
            expandChange(row) {
                this.tempRowId = row.id;
                let _this = this;
                let temp = this.expands;
                this.expands = [];
                this.expands.push(row.id);
                if (temp.length === 1 && temp[0] === row.id) {
                    this.expands = [];
                } else {
                    this.refreshInnerData(row.courseId);
                }
            },
            refreshInnerData(courId) {
                let _this = this;
                this.$axios.post('/student/getStuByCourseIdAndSignUpId', {
                    signupId: this.mid,
                    courseId: courId
                }).then(resp => {
                    if (resp && resp.status === 200) {
                        _this.innerTableData = resp.data;
                    }
                })
            },
            submitTeacherCourse() {
                this.$confirm('确定提交吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.fullscreenLoading = true;
                    this.$axios.post('/signup/submit2', {
                        id: this.mid
                    }).then(resp => {
                        if (resp && resp.status === 200) {
                            if (resp.data.length == 0) {
                                this.$message.success('提交成功');
                                this.fullscreenLoading = false;
                                this.active = 3;
                                this.getMessageData();
                            } else {
                                this.$message({
                                    showClose: true,
                                    type: 'error',
                                    message: '提交失败：编号为 [ ' + resp.data + ' ] 的课程未添加排课信息',
                                    duration: 0
                                });
                                this.fullscreenLoading = false;
                            }
                        }
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消提交'
                    })
                });
            },
            select_status() {
                this.$forceUpdate();
            },
        },
        created() {
            this.getData();
        },
        computed: {
            unreadNum() {
                return this.unread.length;
            }
        }
    }

</script>

<style>
    .message-title {
        cursor: pointer;
    }

    .handle-row {
        margin-top: 30px;
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
        font-size: 13px;
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

