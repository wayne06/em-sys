<template>
    <div>
<!--        <div class="crumbs">-->
<!--            <el-breadcrumb separator="/">-->
<!--                <el-breadcrumb-item>-->
<!--                    <i class="el-icon-lx-cascades"></i> 教师管理-->
<!--                </el-breadcrumb-item>-->
<!--            </el-breadcrumb>-->
<!--        </div>-->
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" icon="el-icon-circle-plus-outline" class="handle-del mr10" @click="handleAdd">新增教师</el-button>
                <el-input v-model="keyword" placeholder="教师姓名 或 手机号码" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table
                    :data="tableData"
                    height="650"
                    border
                    class="table"
                    header-cell-class-name="table-header"
            >
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="name" label="教师姓名"></el-table-column>
                <el-table-column prop="gender" label="性别"></el-table-column>
                <el-table-column prop="telephone" label="联系方式"></el-table-column>
                <el-table-column prop="type" label="类型"></el-table-column>
                <el-table-column prop="profession" label="专业"></el-table-column>
                <el-table-column prop="level" label="级别"></el-table-column>
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
        <el-dialog title="编辑" :visible.sync="editVisible" width="30%" @close="clear" top="10vh">
            <el-form ref="form" :model="form" label-width="90px" label-position="left" size="mini">
                <el-form-item label="教师姓名">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-radio v-model="form.gender" label="男">男</el-radio>
                    <el-radio v-model="form.gender" label="女">女</el-radio>
                </el-form-item>
                <el-form-item label="联系方式">
                    <el-input v-model="form.telephone"></el-input>
                </el-form-item>
                <el-form-item label="类型">
                    <el-radio v-model="form.type" label="全职">全职</el-radio>
                    <el-radio v-model="form.type" label="兼职">兼职</el-radio>
                </el-form-item>
                <el-form-item label="专业">
                    <el-input v-model="form.profession"></el-input>
                </el-form-item>
                <el-form-item label="级别">
                    <el-radio v-model="form.level" label="A">A</el-radio>
                    <el-radio v-model="form.level" label="B">B</el-radio>
                    <el-radio v-model="form.level" label="C">C</el-radio>
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
        <el-dialog title="新增" :visible.sync="addVisible" width="30%" @close="clear" top="10vh" :close-on-click-modal="false">
            <el-form ref="form" :model="form" label-width="90px" label-position="left" size="mini">
                <el-form-item label="教师姓名">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-radio v-model="form.gender" label="男">男</el-radio>
                    <el-radio v-model="form.gender" label="女">女</el-radio>
                </el-form-item>
                <el-form-item label="联系方式">
                    <el-input v-model="form.telephone"></el-input>
                </el-form-item>
                <el-form-item label="类型">
                    <el-radio v-model="form.type" label="全职">全职</el-radio>
                    <el-radio v-model="form.type" label="兼职">兼职</el-radio>
                </el-form-item>
                <el-form-item label="专业">
                    <el-input v-model="form.profession"></el-input>
                </el-form-item>
                <el-form-item label="级别">
                    <el-radio v-model="form.remark" label="A">A</el-radio>
                    <el-radio v-model="form.remark" label="B">B</el-radio>
                    <el-radio v-model="form.remark" label="C">C</el-radio>
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
        name: "teacher",
        data() {
            return {
                tableData: [],
                editVisible: false,
                addVisible: false,
                form: {
                    name: '',
                    gender: '',
                    telephone: '',
                    type: '',
                    profession: '',
                    level: '',
                    remark: ''
                },
                idx: -1,
                keyword: '',
                pageTotal: 0,
                options: []
            };
        },
        created () {
            this.getData();
        },
        methods: {
            getData() {
                let _this = this
                this.$axios.get('/teacher/all').then(resp => {
                    if (resp && resp.status === 200) {
                        _this.tableData = resp.data;
                    }
                })
                this.$axios.get('/teacher/count').then(resp => {
                    if (resp && resp.status === 200) {
                        _this.pageTotal = resp.data;
                    }
                })
            },
            // 删除操作
            handleDelete(index, row) {
                // 二次确认删除
                this.$confirm('确定要删除吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('/teacher/del', {id: this.tableData[index].id}).then(resp => {
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
                this.$axios.post('/teacher/update', {
                    id: id,
                    name: _this.form.name,
                    gender: _this.form.gender,
                    telephone: _this.form.telephone,
                    type: _this.form.type,
                    profession: _this.form.profession,
                    level: _this.form.level,
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
                    telephone: '',
                    type: '',
                    profession: '',
                    level: '',
                    remark: ''
                }
                this.getData();
            },
            // 新增操作
            handleAdd() {
                this.addVisible = true;
            },

            // 保存新增
            save() {
                let _this = this;
                this.$axios.post('/teacher/add', {
                    name: _this.form.name,
                    gender: _this.form.gender,
                    telephone: _this.form.telephone,
                    type: _this.form.type,
                    profession: _this.form.profession,
                    level: _this.form.level,
                    remark: _this.form.remark
                }).then(resp => {
                    if (resp && resp.status === 200) {
                        this.$message.success('新增成功');
                        this.addVisible = false;
                        this.getData();
                    }
                })
            },
            handleSearch () {
                let _this = this;
                this.$axios.post('/teacher/bynameorphone', {name: this.keyword}).then(resp => {
                    if (resp && resp.status === 200) {
                        _this.tableData = resp.data
                    }
                })
            },
            expandChange (arr) {
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
