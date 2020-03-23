<template>
    <div>
        <div class="container">
            <div class="handle-box">
                <el-select
                        v-model="value"
                        placeholder="选择我的班级"
                        class="handle-select mr10"
                        style="width: 300px"
                        @change="select_status"
                >
                    <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
                <el-button
                        type="primary"
                        icon="el-icon-circle-plus-outline"
                        class="handle-del mr10"
                        @click="handleAdd"
                        :disabled="this.value == ''"
                >添加记录
                </el-button>
            </div>
            <el-table
                    :data="tableData"
                    height="550"
                    border
                    class="table"
                    header-cell-class-name="table-header">
                <el-table-column prop="atDate" label="上课日期"></el-table-column>
                <el-table-column prop="atTime" label="上课时间"></el-table-column>
                <el-table-column prop="outline" label="内容提纲"></el-table-column>
                <el-table-column prop="feedback" label="学生反馈"></el-table-column>
                <el-table-column prop="nextOutline" label="下节提纲"></el-table-column>
                <el-table-column prop="requirement" label="课后要求"></el-table-column>
                <el-table-column prop="homework" label="作业布置"></el-table-column>
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
        </div>

        <!-- 新增弹出框 -->
        <el-dialog title=添加记录 :visible.sync="addVisible" width="50%" @close="clear" top="3vh">
            <el-form ref="form" :model="form" label-width="90px" label-position="left" size="mini">
                <el-form-item label="上课时间">
                    <el-date-picker
                            v-model="form.atDate"
                            type="date"
                            placeholder="选择日期"
                            value-format="yyyy-MM-dd"
                            style="width: 38%; margin-right: 20px;"
                    ></el-date-picker>
                    <el-time-picker
                            is-range
                            v-model="form.period"
                            range-separator="至"
                            start-placeholder="开始时间"
                            end-placeholder="结束时间"
                            placeholder="选择时间范围"
                            value-format="HH:mm"
                            format="HH:mm"
                            style="width: 58%">
                    </el-time-picker>
                </el-form-item>
                <el-form-item label="内容提纲">
                    <el-input type="textarea" rows="5" v-model="form.outline"></el-input>
                </el-form-item>
                <el-form-item label="学生反馈">
                    <el-input type="textarea" rows="2" v-model="form.feedback"></el-input>
                </el-form-item>
                <el-form-item label="下节提纲">
                    <el-input type="textarea" rows="5" v-model="form.nextOutline"></el-input>
                </el-form-item>
                <el-form-item label="课后要求">
                    <el-input type="textarea" rows="3" v-model="form.requirement"></el-input>
                </el-form-item>
                <el-form-item label="作业布置">
                    <el-input type="textarea" rows="3" v-model="form.homework"></el-input>
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
    </div>
</template>

<script>
    export default {
        name: "score"
    }
</script>

<style scoped>

</style>
