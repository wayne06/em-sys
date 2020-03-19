<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 课程安排
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" icon="el-icon-circle-plus-outline" class="handle-del mr10" @click="handleAdd">新增课程</el-button>
                <el-input v-model="keyword" placeholder="课程名称 或 教师姓名" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table
                    :data="tableData"
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
                        <div class="handle-box">
                            <el-button type="primary" icon="el-icon-circle-plus-outline" class="handle-del mr10" @click="handleAddStu" plain>添加学生</el-button>
                            <el-button type="primary" icon="el-icon-delete" class="handle-del mr10" @click="rmAllSelection" plain>批量移除</el-button>
                            <el-input v-model="keyword" placeholder="学生姓名 或 手机号码" class="handle-input mr10"></el-input>
                            <el-button type="primary" icon="el-icon-search" @click="handleSearchStu" plain>搜索</el-button>
                        </div>
                        <el-table
                                :data="innerTableData"
                                height="400"
                                border
                                class="table"
                                ref="moviesTable"
                                header-cell-class-name="table-header"
                                @selection-change="handleSelectionChange"
                                @row-click="clickRow"
                        >
                            <el-table-column type="selection" width="55" align="center"></el-table-column>
                            <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                            <el-table-column prop="name" label="姓名"></el-table-column>
                            <el-table-column prop="gender" label="性别"></el-table-column>
                            <el-table-column prop="school" label="学校"></el-table-column>
                            <el-table-column prop="gradeAndClass" label="年级班级"></el-table-column>
                            <el-table-column prop="parentName" label="家长姓名"></el-table-column>
                            <el-table-column prop="telephone" label="联系方式"></el-table-column>
                            <el-table-column prop="address" label="家庭住址"></el-table-column>
                            <el-table-column prop="subject" label="报名科目"></el-table-column>
                            <el-table-column prop="lessonCount" label="总课时"></el-table-column>
                            <el-table-column label="报名日期">
                                <template slot-scope="scope">{{scope.row.registeredAt.substring(0, 10)}}</template>
                            </el-table-column>
                            <el-table-column prop="expense" label="学费"></el-table-column>
                            <el-table-column prop="discount" label="减免情况"></el-table-column>
                            <el-table-column prop="refund" label="退费"></el-table-column>
                            <el-table-column prop="remark" label="备注"></el-table-column>
                            <el-table-column label="操作" width="180" align="center">
                                <template slot-scope="scope">
                                    <el-button
                                            type="text"
                                            icon="el-icon-delete"
                                            class="red"
                                            @click="rmStu(scope.$index, scope.row)"
                                    >移除班级</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </template>


                </el-table-column>
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="courseName" label="课程名称"></el-table-column>
                <el-table-column prop="teacherName" label="教师姓名"></el-table-column>
                <el-table-column prop="teacherNumber" label="联系电话"></el-table-column>
                <el-table-column prop="lessonCount" label="课时安排"></el-table-column>
                <el-table-column label="上课时间区间">
                    <template slot-scope="scope">
                        {{scope.row.beginAt.substring(11, 16)}} - {{scope.row.endAt.substring(11, 16)}}
                    </template>
                </el-table-column>
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

        <!-- 编辑课程弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="45%" @close="clear" top="3vh">
            <el-form ref="form" :model="form" label-width="110px" label-position="left">
                <el-form-item label="课程姓名">
                    <el-input v-model="form.courseName"></el-input>
                </el-form-item>
                <el-form-item label="教师姓名">
                    <el-input v-model="form.teacherName"></el-input>
                </el-form-item>
                <el-form-item label="联系电话">
                    <el-input v-model="form.teacherNumber"></el-input>
                </el-form-item>
                <el-form-item label="课时安排">
                    <el-input v-model="form.lessonCount"></el-input>
                </el-form-item>
                <el-form-item label="上课时间区间">
                    <el-time-picker v-model="form.beginAt" format="HH:mm"></el-time-picker>
                    至
                    <el-time-picker v-model="form.endAt" format="HH:mm"></el-time-picker>
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

        <!-- 新增课程弹出框 -->
        <el-dialog title="新增" :visible.sync="addVisible" width="45%" @close="clear" top="3vh">
            <el-form ref="form" :model="form" label-width="110px" label-position="left">
                <el-form-item label="课程姓名">
                    <el-input v-model="form.courseName"></el-input>
                </el-form-item>
                <el-form-item label="教师姓名">
                    <el-input v-model="form.teacherName"></el-input>
                </el-form-item>
                <el-form-item label="联系电话">
                    <el-input v-model="form.teacherNumber"></el-input>
                </el-form-item>
                <el-form-item label="课时安排">
                    <el-input v-model="form.lessonCount"></el-input>
                </el-form-item>
                <el-form-item label="上课时间区间">
                    <el-time-picker v-model="form.beginAt" format="HH:mm"></el-time-picker>
                    至
                    <el-time-picker v-model="form.endAt" format="HH:mm"></el-time-picker>
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


        <!-- 添加学生弹出框 -->
        <el-dialog title="添加学生" :visible.sync="addStuVisible" width="80%" top="3vh" @close="refreshInnerData">
            <div class="handle-box">
                <el-input v-model="keyword2" placeholder="学生姓名 或 手机号码" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearchInStuAdd" plain>搜索</el-button>
            </div>
            <el-table
                    :data="studentData"
                    height="666"
                    border
                    class="table"
                    ref="moviesTable"
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
                <el-table-column prop="subject" label="报名科目"></el-table-column>
                <el-table-column prop="lessonCount" label="总课时"></el-table-column>
                <el-table-column label="报名日期">
                    <template slot-scope="scope">{{scope.row.registeredAt.substring(0, 10)}}</template>
                </el-table-column>
                <el-table-column prop="expense" label="学费"></el-table-column>
                <el-table-column prop="discount" label="减免情况"></el-table-column>
                <el-table-column prop="refund" label="退费"></el-table-column>
                <el-table-column prop="remark" label="备注"></el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button
                                type="primary"
                                icon="el-icon-circle-plus-outline"
                                class="handle-del mr10"
                                @click="handleAddSingle(scope.$index, scope.row)"
                                plain
                        >添加到班级</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: "Course",
        data () {
            return {
                tableData: [],
                innerTableData: [],
                multipleSelection: [],
                delList: [],
                editVisible: false,
                addVisible: false,
                form: {
                    courseName: '',
                    teacherName: '',
                    teacherNumber: '',
                    lessonCount: '',
                    beginAt: '',
                    endAt: '',
                    remark: ''
                },
                idx: -1,
                keyword: '',
                expands: [],
                getRowKeys (row) {
                    return row.id;
                },
                period: '',
                addStuVisible: false,
                studentData: [],
                tempRowId: -1,
                keyword2: '',
                pageTotal: 0
            };
        },
        created () {
            this.getData();
        },
        methods: {
            getData() {
                var _this = this
                this.$axios.get('/course/all').then(resp => {
                    if (resp && resp.status === 200) {
                        _this.tableData = resp.data;
                    }
                })
                this.$axios.get('/course/count').then(resp => {
                    if (resp && resp.status === 200) {
                        _this.pageTotal = resp.data
                    }
                })
            },
            // 编辑操作
            handleEdit(index, row) {
                this.idx = index;
                this.form = row;
                this.editVisible = true;
            },
            // 保存编辑
            saveEdit() {
                var id = this.tableData[this.idx].id;
                console.log(id);
                this.$axios.post('/course/update', {
                    id: id,
                    courseName: this.form.courseName,
                    teacherName: this.form.teacherName,
                    teacherNumber: this.form.teacherNumber,
                    lessonCount: this.form.lessonCount,
                    beginAt: this.form.beginAt,
                    endAt: this.form.endAt,
                    remark: this.form.remark
                }).then(resp => {
                    if (resp && resp.status === 200) {
                        this.$message.success('修改成功');
                        this.editVisible = false;
                        this.getData();
                    }
                })
            },
            clear () {
                this.form = {
                    courseName: '',
                    teacherName: '',
                    teacherNumber: '',
                    lessonCount: '',
                    beginAt: '',
                    endAt: '',
                    remark: ''
                }
            },
            // 新增操作
            handleAdd() {
                this.addVisible = true;
            },

            // 保存新增
            save() {
                console.log(this.period);
                this.$axios.post('/course/add', {
                    courseName: this.form.courseName,
                    teacherName: this.form.teacherName,
                    teacherNumber: this.form.teacherNumber,
                    lessonCount: this.form.lessonCount,
                    beginAt: this.form.beginAt,
                    endAt: this.form.endAt,
                    remark: this.form.remark
                }).then(resp => {
                    if (resp && resp.status === 200) {
                        this.$message.success('新增成功');
                        this.addVisible = false;
                        this.getData();
                    }
                })
            },
            handleSearch () {
                console.log(this.keyword);
                var _this = this;
                this.$axios.post('/course/byname', {remark: this.keyword}).then(resp => {
                    if (resp && resp.status === 200) {
                        _this.tableData = resp.data
                    }
                })

            },
            handleDelete (index, row) {
                console.log(this.tableData[index].id)
                // 二次确认删除
                this.$confirm('确定要删除该课程吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('/course/del', {id: this.tableData[index].id}).then(resp => {
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
            expandChange(row) {
                this.tempRowId = row.id;
                let _this = this;
                let temp = this.expands;
                this.expands = [];
                this.expands.push(row.id);
                if (temp.length === 1 && temp[0] === row.id) {
                    this.expands = [];
                } else {
                    this.$axios.post('/course/getStuByCourse', {id: row.id}).then(resp => {
                        if (resp && resp.status === 200) {
                            _this.innerTableData = resp.data;
                        }
                    })
                }

            },
            handleAddStu () {
                let _this = this;
                this.addStuVisible = true;
                // console.log(this.tempRowId);
                this.$axios.post('/course/getStuNoInCourse', {id: this.tempRowId}).then(resp => {
                    if (resp && resp.status === 200) {
                        _this.studentData = resp.data;
                    }
                })
            },
            handleAddSingle (index, row) {
                console.log(this.tempRowId);
                console.log(row.id);

                this.$axios.post('/course/addStuToCourse',
                    {
                        id: this.tempRowId,
                        courseName: row.id
                    }).then(resp => {
                    if (resp && resp.status === 200) {
                        this.$message.success('添加成功');
                        this.handleAddStu()
                    }
                });
            },
            refreshInnerData () {
                this.keyword2 = '';
                let _this = this;
                this.$axios.post('/course/getStuByCourse', {id: this.tempRowId}).then(resp => {
                    if (resp && resp.status === 200) {
                        _this.innerTableData = resp.data;
                    }
                })
            },
            rmStu (index, row) {
                this.$axios.post('/course/rmStuFromCourse',
                    {
                        id: this.tempRowId,
                        courseName: row.id
                    }).then(resp => {
                    if (resp && resp.status === 200) {
                        this.$message.success('移除成功');
                        this.refreshInnerData();
                    }
                });
            },
            handleSearchInStuAdd () {
                console.log(this.keyword2);
                var _this = this;
                this.$axios.post('/course/searchStuNoInCourse',
                    {
                        id: _this.tempRowId,
                        remark: this.keyword2
                    }).then(resp => {
                    if (resp && resp.status === 200) {
                        _this.studentData = resp.data
                    }
                })
            },
            handleSelectionChange (val) {
                this.multipleSelection = val;
            },
            rmAllSelection () {
                const ids = [];
                const length = this.multipleSelection.length;
                if (length === 0) {
                    this.$message({
                        type: 'info',
                        message: '没有选择要删除的项目'
                    });
                    return;
                }
                let str = '';
                this.delList = this.delList.concat(this.multipleSelection);
                for (let i = 0; i < length; i++) {
                    str += ' ' + this.multipleSelection[i].name + ' ';
                    ids.push(this.multipleSelection[i].id);
                }

                console.log(length);
                console.log(ids.toString());

                this.$confirm(`确定要删除${str}吗？`, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('/course/rmStuBatch',
                        {
                            id: this.tempRowId,
                            remark: ids.toString()
                        }).then(resp => {
                        if (resp && resp.status === 200) {
                            this.$message.success('删除成功');
                            this.refreshInnerData();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    })
                });
                this.multipleSelection = [];
            },
            handleSearchStu () {

            },
            clickRow (row) {
                this.$refs.moviesTable.toggleRowSelection(row);
            },
            rowClick (row, column, event) {
                // let nodeName = event.target.nodeName;
                // Array.prototype.remove = function (val) {
                //     let index = this.indexOf(val);
                //     if (index > -1) {
                //         this.splice(index, 1);
                //     }
                // };
                // if (nodeName === 'SPAN') {
                //     return;
                // }
                // if (this.expands.indexOf(row.id) < 0) {
                //     this.expands.push(row.id);
                // } else {
                //     this.expands.remove(row.id);
                // }
                // console.log(this.expands)
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
</style>
