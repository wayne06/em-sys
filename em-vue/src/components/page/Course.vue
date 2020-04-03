<template>
    <div>
<!--        <div class="crumbs">-->
<!--            <el-breadcrumb separator="/">-->
<!--                <el-breadcrumb-item>-->
<!--                    <i class="el-icon-lx-cascades"></i> 课程管理-->
<!--                </el-breadcrumb-item>-->
<!--            </el-breadcrumb>-->
<!--        </div>-->
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" icon="el-icon-circle-plus-outline" class="handle-del mr10" @click="handleAdd">新增课程</el-button>
<!--                <el-select v-model="query.address" placeholder="地址" class="handle-select mr10">-->
<!--                    <el-option key="1" label="广东省" value="广东省"></el-option>-->
<!--                    <el-option key="2" label="湖南省" value="湖南省"></el-option>-->
<!--                </el-select>-->
                <el-input v-model="keyword" placeholder="班型" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <el-button type="primary" @click="clearFilter">清除筛选条件</el-button>
            </div>
            <el-table
                    v-loading="loading"
                    element-loading-text="拼命加载中"
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.6)"
                    :data="tableData"
                    height="560"
                    border
                    class="table"
                    header-cell-class-name="table-header"
                    ref="filterTable"
            >
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>

                <el-table-column
                        prop="term"
                        label="学期"
                        :filters="this.filters.term"
                        :filter-method="filterHandler"
                ></el-table-column>

                <el-table-column
                        prop="type"
                        label="班型"
                        :filters="this.filters.type"
                        :filter-method="filterHandler"
                ></el-table-column>

                <el-table-column
                        prop="grade"
                        label="年级"
                        :filters="this.filters.grade"
                        :filter-method="filterHandler"
                ></el-table-column>
                <el-table-column
                        prop="subject"
                        label="学科"
                        :filters="this.filters.subject"
                        :filter-method="filterHandler"
                ></el-table-column>
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

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 新增课程弹出框 -->
        <el-dialog title="新增" :visible.sync="addVisible" width="45%" @close="clear" top="3vh">
            <el-form ref="form" :model="form" label-width="110px" label-position="left">
                <el-form-item label="课程联级">
                    <el-cascader
                            :options="options"
                            v-model="form.options"
                            @change="handleChange"
                    ></el-cascader>
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
        name: "Course",
        data () {
            return {
                tableData: [],
                options: [],
                editVisible: false,
                addVisible: false,
                form: {
                    term: '',
                    type: '',
                    grade: '',
                    subject: '',
                    remark: '',
                    options: []
                },
                idx: -1,
                keyword: '',
                pageTotal: 0,
                filters: {},
                loading: true
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
                        this.tableData = resp.data;
                        this.loading = false;
                    }
                });
                this.$axios.get('/course/count').then(resp => {
                    if (resp && resp.status === 200) {
                        _this.pageTotal = resp.data;
                    }
                });
                this.$axios.get('/course/filters').then(resp => {
                    if (resp && resp.status === 200) {
                        _this.filters = resp.data;
                        console.log(resp.data)
                    }
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
            },
            clear () {
            },
            // 新增操作
            handleAdd() {
                var _this = this
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
            },
            handleSearch () {
            },
            handleDelete (index, row) {
            },
            handleChange (value) {
                console.log(value)
            },
            filterHandler (value, row, column) {
                const property = column['property'];
                return row[property] === value;
            },
            clearFilter() {
                this.$refs.filterTable.clearFilter();
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
