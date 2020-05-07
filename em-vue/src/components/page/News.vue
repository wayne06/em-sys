<template>
    <div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" icon="el-icon-circle-plus-outline" class="handle-del mr10" @click="handleAdd">新增通知</el-button>
<!--                <el-input v-model="keyword" placeholder="教师姓名 或 手机号码" class="handle-input mr10"></el-input>-->
<!--                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>-->
            </div>
            <el-table
                    :data="tableData"
                    height="650"
                    border
                    class="table"
                    header-cell-class-name="table-header"
            >
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="title" label="标题"></el-table-column>
                <el-table-column prop="link" label="链接">
                    <template slot-scope="scope">
                        <el-link class="message-title"
                                 target="_blank"
                                 :href="scope.row.link">{{scope.row.link}}</el-link>
                    </template>
                </el-table-column>
                <el-table-column prop="createdAt" label="创建时间"></el-table-column>
                <el-table-column prop="enabledAt" label="发布时间"></el-table-column>
                <el-table-column prop="priority" label="重要度"></el-table-column>
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
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="30%" @close="clear" top="10vh">
            <el-form ref="form" :model="form" label-width="90px" label-position="left" size="mini">
                <el-form-item label="标题">
                    <el-input v-model="form.title"></el-input>
                </el-form-item>
                <el-form-item label="链接">
                    <el-input v-model="form.link"></el-input>
                </el-form-item>
                <el-form-item label="重要度">
                    <el-radio v-model="form.priority" label="普通">普通</el-radio>
                    <el-radio v-model="form.priority" label="重要">重要</el-radio>
                </el-form-item>
                <el-form-item label="置顶">
                    <el-switch
                            style="display: block"
                            v-model="form.top"
                            active-color="#13ce66"
                            inactive-color="#ff4949"
                            active-text="置顶"
                            inactive-text="取消置顶">
                    </el-switch>
                </el-form-item>
                <el-form-item label="发布">
                    <el-switch
                            style="display: block"
                            v-model="form.enabled"
                            active-color="#13ce66"
                            inactive-color="#ff4949"
                            active-text="发布"
                            inactive-text="暂存">
                    </el-switch>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 新增弹出框 -->
        <el-dialog title="新增" :visible.sync="addVisible" width="30%" @close="clear" top="10vh" :close-on-click-modal="false">
            <el-form ref="form" :model="form" label-width="90px" label-position="left" size="mini" :rules="rules">
                <el-form-item label="标题" prop="title">
                    <el-input v-model="form.title"></el-input>
                </el-form-item>
                <el-form-item label="链接" prop="link">
                    <el-input v-model="form.link"></el-input>
                </el-form-item>
                <el-form-item label="重要度">
                    <el-radio v-model="form.priority" label="普通">普通</el-radio>
                    <el-radio v-model="form.priority" label="重要">重要</el-radio>
                </el-form-item>
                <el-form-item label="置顶">
                    <el-switch
                            style="display: block"
                            v-model="form.top"
                            active-color="#13ce66"
                            inactive-color="#ff4949"
                            active-text="置顶"
                            inactive-text="取消置顶">
                    </el-switch>
                </el-form-item>
                <el-form-item label="发布">
                    <el-switch
                            style="display: block"
                            v-model="form.enabled"
                            active-color="#13ce66"
                            inactive-color="#ff4949"
                            active-text="发布"
                            inactive-text="暂存">
                    </el-switch>
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
        name: "news",
        data() {
            return {
                tableData: [],
                editVisible: false,
                addVisible: false,
                form: {},
                idx: -1,
                keyword: '',
                pageTotal: 0,
                options: [],
                rules: {
                    title: [
                        { required: true, message: '必填项', trigger: 'blur' },
                    ],
                    link: [
                        { required: true, message: '必填项', trigger: 'blur' },
                    ]
                }
            };
        },
        created () {
            this.getData();
        },
        methods: {
            getData() {
                let _this = this
                this.$axios.get('/news/all').then(resp => {
                    if (resp && resp.status === 200) {
                        console.log(resp.data)
                        _this.tableData = resp.data;
                    }
                })
            },
            // 删除操作
            handleDelete(index, row) {
                console.log(row)
                // 二次确认删除
                this.$confirm('确定要删除吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('/news/del', {id: row.id}).then(resp => {
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
                this.$refs['form'].validate((valid) => {
                    if (valid) {
                        let _this = this;
                        console.log(this.form)
                        this.$axios.post('/news/update', {
                            id: _this.form.id,
                            title: _this.form.title,
                            link: _this.form.link,
                            priority: _this.form.priority,
                            top: _this.form.top,
                            enabled: _this.form.enabled
                        }).then(resp => {
                            if (resp && resp.status === 200) {
                                this.$notify({
                                    title: '更新成功',
                                    type: 'success',
                                    offset: 100
                                });
                                this.editVisible = false;
                                this.getData();
                            }
                        })
                    } else {
                        return false;
                    }
                })
            },
            clear () {
                this.$refs['form'].resetField();
                this.form = {
                    title: '',
                    link: '',
                    priority: '',
                    top: 0,
                    enabled: 0,
                }
                this.getData();
            },
            // 新增操作
            handleAdd() {
                this.addVisible = true;
                this.form.priority = "普通";
            },

            // 保存新增
            save() {
                this.$refs['form'].validate((valid) => {
                    if (valid) {
                        let _this = this;
                        this.$axios.post('/news/add', {
                            title: _this.form.title,
                            link: _this.form.link,
                            priority: _this.form.priority,
                            top: _this.form.top,
                            enabled: _this.form.enabled
                        }).then(resp => {
                            if (resp && resp.status === 200) {
                                this.$notify({
                                    title: '添加成功',
                                    type: 'success',
                                    offset: 100
                                });
                                this.addVisible = false;
                                this.getData();
                            }
                        })
                    } else {
                        return false;
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
