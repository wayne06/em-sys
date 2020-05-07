<template>
    <div>
        <div class="container">
            <div class="handle-box">
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
<!--                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>-->
                <el-table-column prop="username" label="用户名"></el-table-column>
                <el-table-column prop="name" label="姓名"></el-table-column>
                <el-table-column prop="phone" label="电话"></el-table-column>
                <el-table-column prop="role" label="职能"></el-table-column>
                <el-table-column prop="createdAt" label="注册日期"></el-table-column>
                <el-table-column prop="enabled" label="状态">
                    <template slot-scope="scope">
                        <span v-if="scope.row.enabled" style="color: #13ce66">已激活</span>
                        <span v-else style="color: red">禁用中</span>
                    </template>
                </el-table-column>
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
        <el-dialog title="编辑用户" :visible.sync="editVisible" width="39%" @close="clear" top="10vh">
            <el-form ref="form" :model="form" label-width="90px" label-position="left" size="mini">
                <el-form-item label="用户名">
                    <el-input v-model="form.username" disabled></el-input>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="form.name" disabled></el-input>
                </el-form-item>
                <el-form-item label="电话">
                    <el-input v-model="form.phone" disabled></el-input>
                </el-form-item>
                <el-form-item label="注册日期">
                    <el-input v-model="form.createdAt" disabled></el-input>
                </el-form-item>
                <el-form-item label="职能">
                    <el-radio-group v-model="form.roleId">
                        <el-radio :label="2">课程顾问</el-radio>
                        <el-radio :label="3">学管师</el-radio>
                        <el-radio :label="4">教师</el-radio>
                        <el-radio :label="5">校长</el-radio>
                        <el-radio :label="6">财务</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="状态">
                    <el-switch
                            style="display: block"
                            v-model="form.enabled"
                            active-color="#13ce66"
                            inactive-color="#ff4949"
                            active-text="激活"
                            inactive-text="禁用">
                    </el-switch>
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
                console.log(this.form)
                this.editVisible = true;
            },
            // 保存编辑
            saveEdit() {
                console.log(this.form);

                this.$axios.post('/user/update', {
                    id: this.form.id,
                    roleId: this.form.roleId,
                    enabled: this.form.enabled
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
