<template>
    <div class="">
        <div class="container">

            <el-tabs v-model="message">
                <el-tab-pane :label="`处理中(${this.pData.length})`" name="processing">
                    <el-table :data="pData" :show-header="false" style="width: 100%">

                        <el-table-column  @click="toMessageData">
                            <template slot-scope="scope">
                                <span class="message-title" @click="toMessageData(scope.$index, scope.row)">{{scope.row.username}}-创建的报名信息： {{scope.row.title}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="createdAt" width="180"></el-table-column>
                        <el-table-column width="180" align="center">
                            <template slot-scope="scope">
                                <el-button
                                        :disabled="active==0?false:true"
                                        type="text"
                                        icon="el-icon-delete"
                                        class="red"
                                        @click="delMsg(scope.$index, scope.row)"
                                >删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="handle-row">
                        <el-button type="primary" @click="createSignInfo">新建报名信息</el-button>
                    </div>

                </el-tab-pane>

                <el-tab-pane :label="`已提交(${this.cData.length})`" name="committed">
                    <template v-if="message === 'committed'">
                        <el-table :data="cData" :show-header="false" style="width: 100%">

                            <el-table-column  @click="toMessageData">
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

                            <el-table-column  @click="toMessageData">
                                <template slot-scope="scope">
                                    <span class="message-title" @click="toMessageData(scope.$index, scope.row)">{{scope.row.username}}-创建的报名信息： {{scope.row.title}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="createdAt" width="180"></el-table-column>
                        </el-table>
                    </template>
                </el-tab-pane>

                <el-dialog :title="this.dTitle" :visible.sync="messageVisible" width="90%" @close="clear" top="3vh">
                    <el-steps :active="active"  finish-status="success" style="padding-top: 0px; padding-bottom: 25px">
                        <el-step title="报名信息提交"></el-step>
                        <el-step title="审批"></el-step>
                        <el-step title="课程安排提交"></el-step>
                        <el-step title="审批完成"></el-step>
                    </el-steps>

                    <div class="handle-box">
                        <el-button :disabled="active==0?false:true" type="primary" icon="el-icon-circle-plus-outline" class="handle-del mr10" @click="handleAdd">新增学生</el-button>
                        <el-input v-model="keyword" placeholder="学生姓名 或 手机号码" class="handle-input mr10"></el-input>
                        <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                        <el-button :disabled="active==0?false:true" type="success" icon="el-icon-s-promotion" @click="submitCourseStu" style="float:right" v-loading.fullscreen.lock="fullscreenLoading">提交</el-button>
                    </div>

                    <el-table
                            :data="tableData"
                            height="550"
                            border
                            class="table"
                            ref="moviesTable"
                            header-cell-class-name="table-header"
                            :row-key="getRowKeys"
                            :expand-row-keys="expands"
                            @expand-change="expandChange"
                    >
                        <el-table-column type="expand" width="55" align="center">
                            <template slot-scope="props">
                                <div class="handle-box">
                                    <el-button :disabled="active==0?false:true" type="primary" icon="el-icon-circle-plus-outline" class="handle-del mr10" @click="handleAddSignInfo" plain>添加报名信息</el-button>
                                </div>
                                <el-table
                                        :data="innerTableData"
                                        height="200"
                                        border
                                        class="table"
                                        header-cell-class-name="table-header"
                                >
                                    <el-table-column prop="courseName" label="报名课程"></el-table-column>
                                    <el-table-column prop="lessonCount" label="总课时"></el-table-column>
                                    <el-table-column prop="registeredAt" label="报名日期"></el-table-column>
                                    <el-table-column prop="endAt" label="预计结束日期"></el-table-column>
                                    <el-table-column prop="expense" label="学费"></el-table-column>
                                    <el-table-column prop="discount" label="减免"></el-table-column>
                                    <el-table-column prop="refund" label="退费"></el-table-column>
                                    <el-table-column prop="remark" label="备注"></el-table-column>
                                    <el-table-column label="操作" width="180" align="center">
                                        <template slot-scope="scope">
                                            <el-button
                                                    :disabled="active==0?false:true"
                                                    type="text"
                                                    icon="el-icon-edit"
                                                    @click="handleEditSignInfo(scope.$index, scope.row)"
                                            >编辑</el-button>
                                            <el-button
                                                    :disabled="active==0?false:true"
                                                    type="text"
                                                    icon="el-icon-delete"
                                                    class="red"
                                                    @click="handleDelSignInfo(scope.$index, scope.row)"
                                            >删除</el-button>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </template>


                        </el-table-column>


                        <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                        <el-table-column prop="name" label="姓名"></el-table-column>
                        <el-table-column prop="gender" label="性别"></el-table-column>
                        <el-table-column prop="school" label="学校"></el-table-column>
                        <el-table-column prop="gradeAndClass" label="年级班级"></el-table-column>
                        <el-table-column prop="parentName" label="家长姓名"></el-table-column>
                        <el-table-column prop="telephone" label="联系方式"></el-table-column>
                        <el-table-column prop="address" label="家庭住址"></el-table-column>
                        <el-table-column prop="remark" label="备注"></el-table-column>
                        <el-table-column label="操作" width="180" align="center">
                            <template slot-scope="scope">
                                <el-button
                                        :disabled="active==0?false:true"
                                        type="text"
                                        icon="el-icon-edit"
                                        @click="handleEdit(scope.$index, scope.row)"
                                >编辑</el-button>
                                <el-button
                                        :disabled="active==0?false:true"
                                        type="text"
                                        icon="el-icon-delete"
                                        class="red"
                                        @click="handleDelete(scope.$index, scope.row)"
                                >移除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>

                    <!-- 新增弹出框 -->
                    <el-dialog title="新增学生" :visible.sync="addVisible" width="30%" @close="clear1" top="10vh" append-to-body>
                        <el-form ref="form1" :model="form1" label-width="90px" label-position="left" size="mini" :rules="rules1">
                            <el-form-item label="学生姓名" prop="name">
                                <el-input v-model="form1.name" placeholder="请输入学生姓名"></el-input>
                            </el-form-item>
                            <el-form-item label="性别" prop="gender">
                                <el-radio v-model="form1.gender" label="男">男</el-radio>
                                <el-radio v-model="form1.gender" label="女">女</el-radio>
                            </el-form-item>
                            <el-form-item label="学校">
                                <el-input v-model="form1.school" placeholder="请输入学校名称"></el-input>
                            </el-form-item>
                            <el-form-item label="年级班级">
                                <el-input v-model="form1.gradeAndClass" placeholder="请输入年级班级"></el-input>
                            </el-form-item>
                            <el-form-item label="家长姓名">
                                <el-input v-model="form1.parentName" placeholder="请输入家长姓名"></el-input>
                            </el-form-item>
                            <el-form-item label="联系方式" prop="telephone">
                                <el-input v-model="form1.telephone" placeholder="请输入手机号码"></el-input>
                            </el-form-item>
                            <el-form-item label="家庭住址">
                                <el-input v-model="form1.address" placeholder="请输入家庭住址"></el-input>
                            </el-form-item>
                            <el-form-item label="备注">
                                <el-input v-model="form1.remark" placeholder="请输入备注信息"></el-input>
                            </el-form-item>
                        </el-form>
                        <span slot="footer" class="dialog-footer">
                                <el-button @click="addVisible = false">取 消</el-button>
                                <el-button type="primary" @click="save">确 定</el-button>
                            </span>
                    </el-dialog>

                    <!-- 编辑弹出框 -->
                    <el-dialog title="编辑" :visible.sync="editVisible" width="30%" @close="clear1" top="10vh" append-to-body>
                        <el-form ref="form1" :model="form1" label-width="90px" label-position="left"  size="mini" :rules="rules1">
                            <el-form-item label="学生姓名" prop="name">
                                <el-input v-model="form1.name" placeholder="请输入学生姓名"></el-input>
                            </el-form-item>
                            <el-form-item label="性别" prop="gender">
                                <el-radio v-model="form1.gender" label="男">男</el-radio>
                                <el-radio v-model="form1.gender" label="女">女</el-radio>
                            </el-form-item>
                            <el-form-item label="学校">
                                <el-input v-model="form1.school" placeholder="请输入学校名称"></el-input>
                            </el-form-item>
                            <el-form-item label="年级班级">
                                <el-input v-model="form1.gradeAndClass" placeholder="请输入年级班级"></el-input>
                            </el-form-item>
                            <el-form-item label="家长姓名">
                                <el-input v-model="form1.parentName" placeholder="请输入家长姓名"></el-input>
                            </el-form-item>
                            <el-form-item label="联系方式" prop="telephone">
                                <el-input v-model="form1.telephone" placeholder="请输入手机号码"></el-input>
                            </el-form-item>
                            <el-form-item label="家庭住址">
                                <el-input v-model="form1.address" placeholder="请输入家庭住址"></el-input>
                            </el-form-item>
                            <el-form-item label="备注">
                                <el-input v-model="form1.remark" placeholder="请输入备注信息"></el-input>
                            </el-form-item>
                        </el-form>
                        <span slot="footer" class="dialog-footer">
                                <el-button @click="editVisible = false">取 消</el-button>
                                <el-button type="primary" @click="saveEdit">确 定</el-button>
                            </span>
                    </el-dialog>

                    <!-- 新增报名信息弹出框 -->
                    <el-dialog title="新增报名信息" :visible.sync="addSignUpVisible" width="30%" @close="clear2" top="10vh" append-to-body>
                        <el-form ref="form2" :model="form2" label-width="105px" label-position="left" size="mini" :rules="rules2">
                            <el-form-item label="报名科目" prop="subject">
                                <el-cascader
                                        :options="options"
                                        v-model="form2.subject"
                                        :props="{ expandTrigger: 'hover' }"
                                        style="width: 100%"
                                        placeholder="请选择报名科目"
                                ></el-cascader>
                            </el-form-item>
                            <el-form-item label="总课时">
                                <el-input v-model="form2.lessonCount" placeholder="请输入总课时数"></el-input>
                            </el-form-item>
                            <el-form-item label="报名日期" prop="registeredAt">
                                <el-date-picker
                                        v-model="form2.registeredAt"
                                        type="date"
                                        placeholder="请选择报名日期"
                                        value-format="yyyy-MM-dd"
                                        style="width: 100%"
                                ></el-date-picker>
                            </el-form-item>
                            <el-form-item label="预计结束日期">
                                <el-date-picker
                                        v-model="form2.endAt"
                                        type="date"
                                        placeholder="请选择预计结束日期"
                                        value-format="yyyy-MM-dd"
                                        style="width: 100%"
                                ></el-date-picker>
                            </el-form-item>
                            <el-form-item label="学费" prop="expense">
                                <el-input v-model="form2.expense" placeholder="请输入学费金额"></el-input>
                            </el-form-item>
                            <el-form-item label="减免情况">
                                <el-input v-model="form2.discount" placeholder="请输入减免金额（默认0）"></el-input>
                            </el-form-item>
                            <el-form-item label="退费">
                                <el-input v-model="form2.refund" placeholder="请输入退费金额（默认0）"></el-input>
                            </el-form-item>
                            <el-form-item label="备注">
                                <el-input v-model="form2.remark" placeholder="请输入备注信息"></el-input>
                            </el-form-item>
                        </el-form>
                        <span slot="footer" class="dialog-footer">
                                <el-button @click="addSignUpVisible = false">取 消</el-button>
                                <el-button type="primary" @click="saveSignUpInfo">确 定</el-button>
                            </span>
                    </el-dialog>

                    <!-- 编辑报名信息弹出框 -->
                    <el-dialog title="更新报名信息" :visible.sync="editSignUpVisible" width="30%" @close="clear2" top="10vh" append-to-body>
                        <el-form ref="form2" :model="form2" label-width="105px" label-position="left" size="mini" :rules="rules2">
                            <el-form-item label="报名科目" prop="option">
                                <el-cascader
                                        :options="options"
                                        v-model="form2.option"
                                        :props="{ expandTrigger: 'hover' }"
                                        style="width: 100%"
                                        placeholder="请选择报名科目"
                                ></el-cascader>
                            </el-form-item>
                            <el-form-item label="总课时">
                                <el-input v-model="form2.lessonCount" placeholder="请输入总课时数"></el-input>
                            </el-form-item>
                            <el-form-item label="报名日期" prop="registeredAt">
                                <el-date-picker
                                        v-model="form2.registeredAt"
                                        type="date"
                                        placeholder="请选择报名日期"
                                        value-format="yyyy-MM-dd"
                                        style="width: 100%"
                                ></el-date-picker>
                            </el-form-item>
                            <el-form-item label="预计结束日期">
                                <el-date-picker
                                        v-model="form2.endAt"
                                        type="date"
                                        placeholder="请选择预计结束日期"
                                        value-format="yyyy-MM-dd"
                                        style="width: 100%"
                                ></el-date-picker>
                            </el-form-item>
                            <el-form-item label="学费" prop="expense">
                                <el-input v-model="form2.expense" placeholder="请输入学费金额"></el-input>
                            </el-form-item>
                            <el-form-item label="减免情况">
                                <el-input v-model="form2.discount" placeholder="请输入减免金额（默认0）"></el-input>
                            </el-form-item>
                            <el-form-item label="退费">
                                <el-input v-model="form2.refund" placeholder="请输入退费金额（默认0）"></el-input>
                            </el-form-item>
                            <el-form-item label="备注">
                                <el-input v-model="form2.remark" placeholder="请输入备注信息"></el-input>
                            </el-form-item>
                        </el-form>
                        <span slot="footer" class="dialog-footer">
                                <el-button @click="editSignUpVisible = false">取 消</el-button>
                                <el-button type="primary" @click="saveEditSignUpInfo">确 定</el-button>
                            </span>
                    </el-dialog>

                </el-dialog>

            </el-tabs>

        </div>
    </div>
</template>

<script>
    export default {
        name: 'signupmessage1',
        data() {
            return {
                pData: [],
                cData: [],
                fData: [],
                message: 'processing',
                showHeader: false,
                messageVisible: false,
                expands: [],
                getRowKeys (row) {
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
                form1: {},
                form2: {},
                idx: -1,
                pageTotal: 0,
                options: [],
                tempRowId: -1,
                dTitle: '',
                mid: -1,
                disabled: true,
                rules1: {
                    name: [
                        { required: true, message: '必填项', trigger: 'blur' },
                    ],
                    gender: [
                        { required: true, message: '必填项', trigger: 'blur' },
                    ],
                    telephone: [
                        { required: true, message: '必填项', trigger: 'blur' }
                    ]
                },
                rules2: {
                    option: [
                        { type: 'array', required: true, message: '必填项', trigger: 'blur' },
                    ],
                    subject: [
                        { required: true, message: '必填项', trigger: 'blur' },
                    ],
                    registeredAt: [
                        { required: true, message: '必填项', trigger: 'blur' },
                    ],
                    expense: [
                        { required: true, message: '必填项', trigger: 'blur' }
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
                        _this.pData = resp.data.data0;
                        _this.cData = resp.data.data5;
                        _this.fData = resp.data.data4;
                    }
                })
            },
            createSignInfo() {
                this.$axios.post('/signup/add', {}).then(resp => {
                    if (resp && resp.status === 200) {
                        this.$message.success('新增成功');
                        this.getData();
                    }
                })
            },
            clear1() {
                this.$refs['form1'].resetFields();
                this.form1 = {
                    name: '',
                    gender: '',
                    school: '',
                    gradeAndClass: '',
                    parentName: '',
                    telephone: '',
                    address: '',
                    remark: ''
                };
                this.getMessageData();
            },
            clear2() {
                this.$refs['form2'].resetFields();
                this.form2 = {
                    subject: '',
                    lessonCount: '',
                    registeredAt: '',
                    endAt: '',
                    expense: '',
                    discount: '',
                    refund: '',
                    remark: ''
                };
                this.refreshInnerData(this.tempRowId);
            },
            toMessageData (index, row) {
                this.mid = row.id;
                this.active = row.status;
                this.getMessageData();
                this.dTitle = row.title;
                this.messageVisible = true;
            },
            getMessageData () {
                let _this = this
                this.$axios.post('/student/byMid', {signupId: this.mid}).then(resp => {
                    if (resp && resp.status === 200) {
                        _this.tableData = resp.data;
                    }
                })
            },
            handleDelete(index, row) {
                // 二次确认删除
                this.$confirm('确定移除吗？（系统会保留学生信息，仅删除未提交的报名信息）', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('/student/rm', {
                        studentId: this.tableData[index].id,
                        signupId: this.mid
                    }).then(resp => {
                        if (resp && resp.status === 200) {
                            this.$message.success('删除成功');
                            this.getMessageData();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    })
                });
            },
            delMsg(index, row) {
                this.$confirm('确定要删除吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('/signup/del', {
                        id: row.id
                    }).then(resp => {
                        if (resp && resp.status === 200) {
                            this.$message.success('删除成功');
                            this.getData()
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    })
                });
            },
            // 编辑操作
            handleEdit(index, row) {
                this.idx = index;
                this.form1 = row;
                this.editVisible = true;
            },
            // 保存编辑
            saveEdit() {
                this.$refs['form1'].validate((valid) => {
                    if (valid) {
                        let _this = this;
                        let id = _this.tableData[this.idx].id;
                        this.$axios.post('/student/update', {
                            id: id,
                            name: _this.form1.name,
                            gender: _this.form1.gender,
                            school: _this.form1.school,
                            gradeAndClass: _this.form1.gradeAndClass,
                            parentName: _this.form1.parentName,
                            telephone: _this.form1.telephone,
                            address: _this.form1.address,
                            remark: _this.form1.remark
                        }).then(resp => {
                            if (resp && resp.status === 200) {
                                this.$message.success('修改成功');
                                this.editVisible = false;
                                this.$set(this.tableData, this.idx, this.form1)
                                this.getMessageData();
                            }
                        })
                    } else {
                        return false;
                    }
                });
            },
            clear () {
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
                this.getData();
            },
            clearInner (row) {
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
            // 新增操作
            handleAdd() {

                this.addVisible = true;
            },
            // 保存新增
            save() {
                this.$refs['form1'].validate((valid) => {
                    if (valid) {
                        this.$axios.post('/student/add', {
                            name: this.form1.name,
                            gender: this.form1.gender,
                            school: this.form1.school,
                            gradeAndClass: this.form1.gradeAndClass,
                            parentName: this.form1.parentName,
                            telephone: this.form1.telephone,
                            address: this.form1.address,
                            remark: this.form1.remark
                        }).then(resp => {
                            if (resp && resp.status === 200) {
                                this.$axios.post('/coursestudent/add', {
                                    signupId: this.mid,
                                    courseId: 0,
                                    studentId: resp.data.id,
                                }).then(resp => {
                                    if (resp && resp.status === 200) {
                                        this.$message.success('新增成功');
                                        this.addVisible = false;
                                        this.getMessageData();
                                    }
                                })
                            }
                        })
                    } else {
                        return false;
                    }
                });
            },
            handleSearch () {
                let _this = this;
                this.$axios.post('/student/bynameorphone', {
                    remark: this.keyword,
                    signupId: this.mid
                }).then(resp => {
                    if (resp && resp.status === 200) {
                        _this.tableData = resp.data
                    }
                })
            },
            expandChange (row) {
                this.tempRowId = row.id;
                let _this = this;
                let temp = this.expands;
                this.expands = [];
                this.expands.push(row.id);
                if (temp.length === 1 && temp[0] === row.id) {
                    this.expands = [];
                } else {
                    this.refreshInnerData(row.id);
                }
            },
            handleAddSignInfo () {
                this.$axios.get('/course/tree').then(resp => {
                    if (resp && resp.status === 200) {
                        this.options = resp.data;
                        this.form.options = resp.data;
                        this.addSignUpVisible = true;
                    }
                })
            },
            saveSignUpInfo (index, row) {
                this.$refs['form2'].validate((valid) => {
                    if (valid) {
                        this.$axios.post('/coursestudent/add', {
                            signupId: this.mid,
                            courseId: this.form2.subject[3],
                            studentId: this.tempRowId,
                            lessonCount: this.form2.lessonCount,
                            registeredAt: this.form2.registeredAt,
                            endAt: this.form2.endAt,
                            expense: this.form2.expense,
                            discount: this.form2.discount,
                            refund: this.form2.refund,
                            remark: this.form2.remark
                        }).then(resp => {
                            if (resp && resp.status === 200) {
                                this.$message.success('新增成功');
                                this.addSignUpVisible = false;
                                this.refreshInnerData(this.tempRowId);
                            }
                        })
                    } else {
                        return false;
                    }
                })
            },
            refreshInnerData (stuId) {
                let _this = this;
                this.$axios.post('/coursestudent/getCoursesByStuAndSignUpId', {
                    studentId: stuId,
                    signupId: this.mid
                }).then(resp => {
                    if (resp && resp.status === 200) {
                        _this.innerTableData = resp.data;
                    }
                })
            },
            handleEditSignInfo (index, row) {
                console.log(row)
                this.idx = index;
                this.form2 = row;
                let courseArr = this.form2.courseName.split('/');
                this.$axios.post('/course/getId', {
                    term: courseArr[0],
                    type: courseArr[1],
                    grade: courseArr[2],
                    subject: courseArr[3]
                }).then(resp => {
                    if (resp && resp.status === 200) {
                        courseArr[3] = resp.data+"";
                        this.form2.option = courseArr;
                    }
                });
                this.$axios.get('/course/tree').then(resp => {
                    if (resp && resp.status === 200) {
                        this.options = resp.data;
                        this.editSignUpVisible = true;
                    }
                })
            },
            saveEditSignUpInfo () {
                this.$refs['form2'].validate((valid) => {
                    if (valid) {
                        let id = this.innerTableData[this.idx].id;
                        this.$axios.post('/coursestudent/update', {
                            id: id,
                            courseId: this.form2.option[3],
                            studentId: this.tempRowId,
                            lessonCount: this.form2.lessonCount,
                            registeredAt: this.form2.registeredAt,
                            endAt: this.form2.endAt,
                            expense: this.form2.expense,
                            discount: this.form2.discount,
                            refund: this.form2.refund,
                            remark: this.form2.remark
                        }).then(resp => {
                            if (resp && resp.status === 200) {
                                this.$message.success('修改成功');
                                this.editSignUpVisible = false;
                                this.refreshInnerData(this.tempRowId);
                            }
                        })
                    } else {
                        return false;
                    }
                })
            },
            handleDelSignInfo (index, row) {
                // 二次确认删除
                this.$confirm('确定要删除吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('/coursestudent/del', {
                        id: this.innerTableData[this.idx].id
                    }).then(resp => {
                        if (resp && resp.status === 200) {
                            this.$message.success('删除成功');
                            this.refreshInnerData(this.tempRowId)
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    })
                });
            },
            submitCourseStu () {
                this.$confirm('确定提交吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.fullscreenLoading = true;
                    this.$axios.post('/signup/submit', {
                        id: this.mid
                    }).then(resp => {
                        if (resp && resp.status === 200) {
                            console.log(resp.data)
                            if (resp.data.length == 0) {
                                console.log("length==0")
                                this.$message.success('提交成功');
                                this.fullscreenLoading = false;
                                this.active = 1;

                            } else {
                                console.log("length!=0")
                                this.$message({
                                    showClose: true,
                                    type: 'error',
                                    message: '提交失败：编号为 [ '+resp.data+' ] 的学生未添加报名课程',
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
            }
        },
        created() {
            this.getData();
        },
        computed: {
            unreadNum(){
                return this.unread.length;
            }
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

