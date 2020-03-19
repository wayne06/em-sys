<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 报名信息
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" icon="el-icon-circle-plus-outline" class="handle-del mr10" @click="handleAdd">新增学生</el-button>
                <el-input v-model="keyword" placeholder="学生姓名 或 手机号码" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table
                    :data="tableData"
                    height="650"
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
                            <el-button type="primary" icon="el-icon-circle-plus-outline" class="handle-del mr10" @click="handleAddSignInfo" plain>添加报名信息</el-button>
                        </div>
                        <el-table
                                :data="innerTableData"
                                height="200"
                                border
                                class="table"
                                ref="moviesTable"
                                header-cell-class-name="table-header"
                                @row-click="clickRow"
                        >
<!--                            <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>-->
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
                                            type="text"
                                            icon="el-icon-edit"
                                            @click="handleEdit(scope.$index, scope.row)"
                                    >编辑</el-button>
                                    <el-button
                                            type="text"
                                            icon="el-icon-delete"
                                            class="red"
                                            @click="handleDelete(scope.$index, scope.row)"
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
                                type="text"
                                icon="el-icon-edit"
                                @click="handleEdit(scope.$index, scope.row)"
                        >编辑</el-button>
                        <el-button
                                type="text"
                                icon="el-icon-delete"
                                class="red"
                                @click="handleDelete(scope.$index, scope.row)"
                        >删除</el-button>
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
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="30%" @close="clear" top="3vh">
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
                <el-form-item label="报名科目">
                    <el-cascader
                            :options="options"
                            v-model="form.option"
                            :props="{ expandTrigger: 'hover' }"
                            style="width: 100%"
                            @change="expandChange"
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
                            style="width: 100%"></el-date-picker>
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
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 新增弹出框 -->
        <el-dialog title="新增学生" :visible.sync="addVisible" width="30%" @close="clear" top="3vh">
            <el-form ref="form" :model="form" label-width="90px" label-position="left" size="mini">
                <el-form-item label="学生姓名">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-radio v-model="form.gender" label="男">男</el-radio>
                    <el-radio v-model="form.gender" label="女">女</el-radio>
<!--                    <el-input v-model="form.gender"></el-input>-->
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
<!--                    <el-input v-model="form.registeredAt"></el-input>-->
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
                <el-button @click="addVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: 'student',
        data() {
            return {
                tableData: [],
                innerTableData: [],
                editVisible: false,
                addVisible: false,
                form: {},
                idx: -1,
                keyword: '',
                pageTotal: 0,
                options: [],
                tempRowId: -1,
                expands: [],
                getRowKeys (row) {
                    return row.id;
                }
            };
        },
        created () {
            this.getData();
        },
        methods: {
            getData() {
                let _this = this
                this.$axios.get('/student/all').then(resp => {
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
            handleAddSignInfo () {

            },
            // 删除操作
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
            // 编辑操作
            handleEdit(index, row) {
                this.idx = index;
                this.form = row;
                this.$axios.get('/course/tree').then(resp => {
                    if (resp && resp.status === 200) {
                        this.options = resp.data;
                        this.form.option = this.form.subject.split(',');
                        this.editVisible = true;
                    }
                })
            },
            // 保存编辑
            saveEdit() {
                let _this = this;
                let id = _this.tableData[this.idx].id;
                let subject_str = _this.form.option[0]+','+_this.form.option[1]+','+_this.form.option[2];
                this.$axios.post('/student/update', {
                    id: id,
                    name: _this.form.name,
                    gender: _this.form.gender,
                    school: _this.form.school,
                    gradeAndClass: _this.form.gradeAndClass,
                    parentName: _this.form.parentName,
                    telephone: _this.form.telephone,
                    address: _this.form.address,
                    subject: subject_str,
                    lessonCount: _this.form.lessonCount,
                    registeredAt: _this.form.registeredAt,
                    expense: _this.form.expense,
                    discount: _this.form.discount,
                    refund: _this.form.refund,
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
                    subject: '',
                    lessonCount: '',
                    registeredAt: '',
                    expense: '',
                    discount: '',
                    refund: '',
                    remark: ''
                }
                this.getData();
            },
            // 新增操作
            handleAdd() {
                this.$axios.get('/course/tree').then(resp => {
                    if (resp && resp.status === 200) {
                        this.options = resp.data;
                        this.form.options = resp.data;
                        this.addVisible = true;
                    }
                })
            },

            // 保存新增
            save() {
                let subject_str = this.form.subject[0]+','+this.form.subject[1]+','+this.form.subject[2];
                this.$axios.post('/student/add', {
                    name: this.form.name,
                    gender: this.form.gender,
                    school: this.form.school,
                    gradeAndClass: this.form.gradeAndClass,
                    parentName: this.form.parentName,
                    telephone: this.form.telephone,
                    address: this.form.address,
                    subject: subject_str,
                    lessonCount: this.form.lessonCount,
                    registeredAt: this.form.registeredAt,
                    expense: this.form.expense,
                    discount: this.form.discount,
                    refund: this.form.refund,
                    remark: this.form.remark
                }).then(resp => {
                    if (resp && resp.status === 200) {
                        this.$message.success('新增成功');
                        this.addVisible = false;
                        this.getData();
                    }
                })
            },
            handleSelectionChange (val) {
                this.multipleSelection = val;
            },
            handleSearch () {
                var _this = this;
                this.$axios.post('/student/bynameorphone', {name: this.keyword}).then(resp => {
                    if (resp && resp.status === 200) {
                        _this.tableData = resp.data
                    }
                })
            },
            clickRow (row, column, event) {
                let nodeName = event.target.nodeName;
                if (nodeName === 'SPAN') {
                    return;
                }
                this.$refs.moviesTable.toggleRowSelection(row);
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
                    this.$axios.post('/coursestudent/getCoursesByStu', {id: row.id}).then(resp => {
                        if (resp && resp.status === 200) {
                            _this.innerTableData = resp.data;
                        }
                    })
                }
            }
        }
    };
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

