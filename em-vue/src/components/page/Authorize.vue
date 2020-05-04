<template>
    <div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="keyword" placeholder="教师姓名 或 手机号码" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table
                    fit="false"
                    :data="tableData"
                    height="650"
                    border
                    class="table"
                    header-cell-class-name="table-header"
            >
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="name" label="姓名"></el-table-column>
                <el-table-column prop="username" label="用户名"></el-table-column>
                <el-table-column prop="telephone" label="电话"></el-table-column>
                <el-table-column prop="role" label="职能"></el-table-column>
                <el-table-column prop="enabled" label="状态"></el-table-column>
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
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑用户" :visible.sync="editVisible" width="30%" @close="clear" top="10vh">
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

    </div>
</template>

<script>
    export default {
        name: "authorize",
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
                this.$axios.get('/user/all').then(resp => {
                    if (resp && resp.status === 200) {
                        _this.tableData = resp.data;
                    }
                });
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
                            this.$notify({
                                title: '删除成功',
                                type: 'success',
                                offset: 100
                            });
                            this.getData()
                        }
                    })
                }).catch(() => {
                    this.$notify({
                        title: '已取消删除',
                        type: 'info',
                        offset: 100
                    });
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
                        this.$notify({
                            title: '添加成功',
                            type: 'success',
                            offset: 100
                        });
                        // this.$message.success('新增成功');
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
