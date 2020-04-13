<template>
    <div class="">
        <div class="container">

            <el-tabs v-model="message">
                <el-tab-pane :label="`处理中(${this.pData.length})`" name="processing">
                    <el-table :data="pData" :show-header="false" style="width: 100%">

                        <el-table-column  @click="toMessageData">
                            <template slot-scope="scope">
                                <span class="message-title" @click="toMessageData(scope.$index, scope.row)">{{scope.row.title}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="createdAt" width="180"></el-table-column>
                        <el-table-column label="操作" width="180" align="center">
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
                                    <span class="message-title" @click="toMessageData(scope.$index, scope.row)">{{scope.row.title}}</span>
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
                                    <span class="message-title" @click="toMessageData(scope.$index, scope.row)">{{scope.row.title}}</span>
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
                                >删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>

                    <!-- 新增弹出框 -->
                    <el-dialog title="新增学生" :visible.sync="addVisible" width="30%" @close="clear1" top="10vh" append-to-body>
                        <el-form ref="form" :model="form" label-width="90px" label-position="left" size="mini" :rules="rules">
                            <el-form-item label="学生姓名" prop="name">
                                <el-input v-model="form.name"></el-input>
                            </el-form-item>
                            <el-form-item label="性别" prop="gender">
                                <el-radio v-model="form.gender" label="男">男</el-radio>
                                <el-radio v-model="form.gender" label="女">女</el-radio>
                            </el-form-item>
                            <el-form-item label="学校">
                                <el-input v-model="form.school"></el-input>
                            </el-form-item>
                            <el-form-item label="年级班级">
                                <el-input v-model="form.gradeAndClass"></el-input>
                            </el-form-item>
                            <el-form-item label="家长姓名">
                                <el-input v-model="form.parentName"></el-input>
                            </el-form-item>
                            <el-form-item label="联系方式" prop="telephone">
                                <el-input v-model="form.telephone"></el-input>
                            </el-form-item>
                            <el-form-item label="家庭住址">
                                <el-input v-model="form.address"></el-input>
                            </el-form-item>
                            <el-form-item label="备注">
                                <el-input v-model="form.remark"></el-input>
                            </el-form-item>
                        </el-form>
                        <span slot="footer" class="dialog-footer">
                                <el-button @click="addVisible = false">取 消</el-button>
                                <el-button type="primary" @click="save">确 定</el-button>
                            </span>
                    </el-dialog>

                    <!-- 编辑弹出框 -->
                    <el-dialog title="编辑" :visible.sync="editVisible" width="30%" @close="clear" top="10vh" append-to-body>
                        <el-form ref="form" :model="form" label-width="90px" label-position="left">
                            <el-form-item label="学生姓名">
                                <el-input v-model="form.name"></el-input>
                            </el-form-item>
                            <el-form-item label="性别">
                                <el-radio v-model="form.gender" label="男">男</el-radio>
                                <el-radio v-model="form.gender" label="女">女</el-radio>
                            </el-form-item>
                            <el-form-item label="学校">
                                <el-input v-model="form.school"></el-input>
                            </el-form-item>
                            <el-form-item label="年级班级">
                                <el-input v-model="form.gradeAndClass"></el-input>
                            </el-form-item>
                            <el-form-item label="家长姓名">
                                <el-input v-model="form.parentName"></el-input>
                            </el-form-item>
                            <el-form-item label="联系方式">
                                <el-input v-model="form.telephone"></el-input>
                            </el-form-item>
                            <el-form-item label="家庭住址">
                                <el-input v-model="form.address"></el-input>
                            </el-form-item>
                            <el-form-item label="备注">
                                <el-input v-model="form.remark"></el-input>
                            </el-form-item>
                        </el-form>
                        <span slot="footer" class="dialog-footer">
                                <el-button @click="editVisible = false">取 消</el-button>
                                <el-button type="primary" @click="saveEdit">确 定</el-button>
                            </span>
                    </el-dialog>

                    <!-- 新增报名信息弹出框 -->
                    <el-dialog title="新增报名信息" :visible.sync="addSignUpVisible" width="30%" @close="clearInner" top="10vh" append-to-body>
                        <el-form ref="form" :model="form" label-width="105px" label-position="left" size="mini">
                            <el-form-item label="报名科目">
                                <el-cascader
                                        :options="options"
                                        v-model="form.subject"
                                        :props="{ expandTrigger: 'hover' }"
                                        style="width: 100%"
                                ></el-cascader>
                            </el-form-item>
                            <el-form-item label="总课时">
                                <el-input v-model="form.lessonCount"></el-input>
                            </el-form-item>
                            <el-form-item label="报名日期">
                                <el-date-picker
                                        v-model="form.registeredAt"
                                        type="date"
                                        placeholder="选择日期"
                                        value-format="yyyy-MM-dd"
                                        style="width: 100%"
                                ></el-date-picker>
                            </el-form-item>
                            <el-form-item label="预计结束日期">
                                <el-date-picker
                                        v-model="form.endAt"
                                        type="date"
                                        placeholder="选择日期"
                                        value-format="yyyy-MM-dd"
                                        style="width: 100%"
                                ></el-date-picker>
                            </el-form-item>
                            <el-form-item label="学费">
                                <el-input v-model="form.expense"></el-input>
                            </el-form-item>
                            <el-form-item label="减免情况">
                                <el-input v-model="form.discount"></el-input>
                            </el-form-item>
                            <el-form-item label="退费">
                                <el-input v-model="form.refund"></el-input>
                            </el-form-item>
                            <el-form-item label="备注">
                                <el-input v-model="form.remark"></el-input>
                            </el-form-item>
                        </el-form>
                        <span slot="footer" class="dialog-footer">
                                <el-button @click="addSignUpVisible = false">取 消</el-button>
                                <el-button type="primary" @click="saveSignUpInfo">确 定</el-button>
                            </span>
                    </el-dialog>

                    <!-- 编辑报名信息弹出框 -->
                    <el-dialog title="更新报名信息" :visible.sync="editSignUpVisible" width="30%" @close="clearInner" top="10vh" append-to-body>
                        <el-form ref="form" :model="form" label-width="105px" label-position="left" size="mini">
                            <el-form-item label="报名科目">
                                <el-cascader
                                        :options="options"
                                        v-model="form.option"
                                        :props="{ expandTrigger: 'hover' }"
                                        style="width: 100%"
                                ></el-cascader>
                            </el-form-item>
                            <el-form-item label="总课时">
                                <el-input v-model="form.lessonCount"></el-input>
                            </el-form-item>
                            <el-form-item label="报名日期">
                                <el-date-picker
                                        v-model="form.registeredAt"
                                        type="date"
                                        placeholder="选择日期"
                                        value-format="yyyy-MM-dd"
                                        style="width: 100%"
                                ></el-date-picker>
                            </el-form-item>
                            <el-form-item label="预计结束日期">
                                <el-date-picker
                                        v-model="form.endAt"
                                        type="date"
                                        placeholder="选择日期"
                                        value-format="yyyy-MM-dd"
                                        style="width: 100%"
                                ></el-date-picker>
                            </el-form-item>
                            <el-form-item label="学费">
                                <el-input v-model="form.expense"></el-input>
                            </el-form-item>
                            <el-form-item label="减免情况">
                                <el-input v-model="form.discount"></el-input>
                            </el-form-item>
                            <el-form-item label="退费">
                                <el-input v-model="form.refund"></el-input>
                            </el-form-item>
                            <el-form-item label="备注">
                                <el-input v-model="form.remark"></el-input>
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
        name: 'signupmessage',
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
                idx: -1,
                pageTotal: 0,
                options: [],
                tempRowId: -1,
                dTitle: '',
                mid: -1,
                disabled: true,
                rules: {
                    name: [
                        { required: true, message: '请输入学生姓名', trigger: 'blur' },
                    ],
                    gender: [
                        { required: true, message: '请选择学生性别', trigger: 'blur' },
                    ],
                    telephone: [
                        { required: true, message: '请输入联系电话', trigger: 'blur' }
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
                        _this.cData = resp.data.data2;
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
                this.$refs['form'].resetFields();
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
                this.$axios.get('/student/count').then(resp => {
                    if (resp && resp.status === 200) {
                        _this.pageTotal = resp.data;
                    }
                })
            },
            handleDelete(index, row) {
                // 二次确认删除
                this.$confirm('确定要删除吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('/student/del', {id: this.tableData[index].id}).then(resp => {
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
                this.form = row;
                this.editVisible = true;
            },
            // 保存编辑
            saveEdit() {
                let _this = this;
                let id = _this.tableData[this.idx].id;
                this.$axios.post('/student/update', {
                    id: id,
                    name: _this.form.name,
                    gender: _this.form.gender,
                    school: _this.form.school,
                    gradeAndClass: _this.form.gradeAndClass,
                    parentName: _this.form.parentName,
                    telephone: _this.form.telephone,
                    address: _this.form.address,
                    remark: _this.form.remark
                }).then(resp => {
                    if (resp && resp.status === 200) {
                        this.$message.success('修改成功');
                        this.editVisible = false;
                        this.$set(this.tableData, this.idx, this.form)
                        this.getData();
                    }
                })
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
                this.$refs['form'].validate((valid) => {
                    if (valid) {
                        this.$axios.post('/student/add', {
                            name: this.form.name,
                            gender: this.form.gender,
                            school: this.form.school,
                            gradeAndClass: this.form.gradeAndClass,
                            parentName: this.form.parentName,
                            telephone: this.form.telephone,
                            address: this.form.address,
                            remark: this.form.remark
                        }).then(resp => {
                            if (resp && resp.status === 200) {
                                this.$message.success('新增成功');
                                this.addVisible = false;
                                this.getMessageData();
                            }
                        })
                    } else {
                        return false;
                    }
                });
            },
            handleSearch () {
                var _this = this;
                this.$axios.post('/student/bynameorphone', {name: this.keyword}).then(resp => {
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
                    // this.$axios.post('/coursestudent/getCoursesByStu', {id: row.id}).then(resp => {
                    //     if (resp && resp.status === 200) {
                    //         _this.innerTableData = resp.data;
                    //     }
                    // })
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
                this.$axios.post('/coursestudent/add', {
                    signupId: this.mid,
                    courseId: this.form.subject[3],
                    studentId: this.tempRowId,
                    lessonCount: this.form.lessonCount,
                    registeredAt: this.form.registeredAt,
                    endAt: this.form.endAt,
                    expense: this.form.expense,
                    discount: this.form.discount,
                    refund: this.form.refund,
                    remark: this.form.remark
                }).then(resp => {
                    if (resp && resp.status === 200) {
                        this.$message.success('新增成功');
                        this.addSignUpVisible = false;
                        this.refreshInnerData(this.tempRowId);
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
                this.idx = index;
                this.form = row;
                let courseArr = this.form.courseName.split('/');
                this.$axios.post('/course/getId', {
                    term: courseArr[0],
                    type: courseArr[1],
                    grade: courseArr[2],
                    subject: courseArr[3]
                }).then(resp => {
                    if (resp && resp.status === 200) {
                        courseArr[3] = resp.data+"";
                        this.form.option = courseArr;
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
                let id = this.innerTableData[this.idx].id;
                this.$axios.post('/coursestudent/update', {
                    id: id,
                    courseId: this.form.option[3],
                    studentId: this.tempRowId,
                    lessonCount: this.form.lessonCount,
                    registeredAt: this.form.registeredAt,
                    endAt: this.form.endAt,
                    expense: this.form.expense,
                    discount: this.form.discount,
                    refund: this.form.refund,
                    remark: this.form.remark
                }).then(resp => {
                    if (resp && resp.status === 200) {
                        this.$message.success('修改成功');
                        this.editSignUpVisible = false;
                        this.refreshInnerData(this.tempRowId);
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
                this.fullscreenLoading = true;
                this.$axios.post('/signup/submit', {
                    id: this.mid
                }).then(resp => {
                    if (resp && resp.status === 200) {

                        this.$axios.post('/score/initial', {

                        }).then(resp => {
                            if (resp && resp.status === 200) {
                                // this.$message.success('提交成功');
                                this.$message.success({
                                    message: '提交成功',
                                    offset: 116,
                                    duration: 3000
                                });
                                this.fullscreenLoading = false;
                                this.active = 1;
                            }
                        });
                    }
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

