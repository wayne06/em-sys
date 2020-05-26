<template>
    <div class="">
        <div class="container">

            <el-tabs v-model="message">
                <el-tab-pane :label="`待处理(${this.pData.length})`" name="processing">
                    <el-table :data="pData" :show-header="false" style="width: 100%">

                        <el-table-column  @click="toMessageData">
                            <template slot-scope="scope">
                                <span class="message-title" @click="toMessageData(scope.$index, scope.row)">{{scope.row.createdBy}}-创建的报名信息： {{scope.row.title}}</span>
                            </template>
                        </el-table-column>
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
                                    <span class="message-title" @click="toMessageData(scope.$index, scope.row)">{{scope.row.createdBy}}-创建的报名信息： {{scope.row.title}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="createdAt" width="180"></el-table-column>
                        </el-table>
                    </template>
                </el-tab-pane>

                <el-tab-pane :label="`全部(${this.fData.length})`" name="finished">
                    <template v-if="message === 'finished'">
                        <el-table :data="fData" :show-header="false" style="width: 100%">

                            <el-table-column  @click="toMessageData">
                                <template slot-scope="scope">
                                    <span class="message-title" @click="toMessageData2(scope.$index, scope.row)">{{scope.row.createdBy}}-创建的报名信息： {{scope.row.title}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column>
                                <template slot-scope="scope">
                                    <span v-if="scope.row.status === 0" style="color: #13ce66">报名信息 - 待提交</span>
                                    <span v-else-if="scope.row.status === 1" style="color: #13ce66">报名信息 - 待审核</span>
                                    <span v-else-if="scope.row.status === 2" style="color: #13ce66">排课信息 - 待提交</span>
                                    <span v-else-if="scope.row.status === 3" style="color: #13ce66">排课信息 - 待审核</span>
                                    <span v-else style="color: red">完 成</span>
                                </template>
                            </el-table-column>
                        </el-table>
                    </template>
                </el-tab-pane>

                <el-dialog :title="this.dTitle" :visible.sync="messageVisible" width="90%" top="3vh" @close="getData">
                    <el-row :gutter="10">
                        <el-col :span="4">
                            <el-card shadow="hover" class="mgb20" style="height:750px;">
                                <el-timeline :reverse=true>
                                    <el-timeline-item
                                            v-for="(activity, index) in activities"
                                            :key="index"
                                            :timestamp="activity.timestamp"
                                            color="rgba(8, 151, 255, 1)">
                                        <p class="timeline-content">{{activity.content}}</p>
                                        <p class="timeline-name">操作者：{{activity.name}}</p>
                                        <p class="timeline-name" v-if="activity.feedback">
                                            <span>意见：{{activity.feedback}}</span>
                                        </p>
                                    </el-timeline-item>
                                </el-timeline>
                            </el-card>
                        </el-col>

                        <el-col :span="20">
                            <el-card shadow="hover" class="mgb20" style="height:750px;">
                                <div class="handle-box">
                                    <el-button :disabled="active==0?false:true" type="primary" icon="el-icon-circle-plus-outline" class="handle-del mr10" @click="handleAdd">新增学生</el-button>
                                    <el-input v-model="keyword" placeholder="学生姓名 或 手机号码" class="handle-input mr10"></el-input>
                                    <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                                    <el-button :disabled="active==0?false:true" type="success" icon="el-icon-s-promotion" @click="submitCourseStu" style="float:right" v-loading.fullscreen.lock="fullscreenLoading">提交</el-button>
                                </div>

                                <el-table
                                        :data="tableData"
                                        height="666"
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
                                                    ref="innerTable"
                                                    :data="innerTableData"
                                                    height="500"
                                                    border
                                                    class="table"
                                                    header-cell-class-name="table-header"
                                            >
                                                <el-table-column label="报名课程" min-width="10">
                                                    <template slot-scope="scope">
                                                        <div v-html="scope.row.courses"></div>
                                                    </template>
                                                </el-table-column>
                                                <el-table-column prop="lessonCount" label="总课时" min-width="3"></el-table-column>
                                                <el-table-column prop="registeredAt" label="报名日期" min-width="5"></el-table-column>
                                                <el-table-column prop="endAt" label="预计结束日期" min-width="5"></el-table-column>
                                                <el-table-column prop="expense" label="学费" min-width="3"></el-table-column>
                                                <el-table-column prop="discount" label="减免" min-width="3"></el-table-column>
                                                <el-table-column prop="refund" label="退费" min-width="3"></el-table-column>
                                                <el-table-column prop="remark" label="备注" min-width="5"></el-table-column>
                                                <el-table-column label="操作" width="180" align="center" min-width="3">
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

                                    <el-table-column prop="id" label="ID" align="center" min-width="3"></el-table-column>
                                    <el-table-column prop="name" label="姓名" min-width="4"></el-table-column>
                                    <el-table-column prop="gender" label="性别" min-width="3"></el-table-column>
                                    <el-table-column prop="school" label="学校" min-width="7"></el-table-column>
                                    <el-table-column prop="gradeAndClass" label="年级班级" min-width="5"></el-table-column>
                                    <el-table-column prop="parentName" label="家长姓名" min-width="4"></el-table-column>
                                    <el-table-column prop="telephone" label="联系方式" min-width="6"></el-table-column>
                                    <el-table-column prop="address" label="家庭住址" min-width="10"></el-table-column>
                                    <el-table-column prop="remark" label="备注" min-width="8"></el-table-column>
                                    <el-table-column label="操作" align="center" min-width="8">
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
                                <el-dialog title="新增报名信息" :visible.sync="addSignUpVisible" width="30%" @close="clear21" top="10vh" append-to-body>
                                    <el-form ref="form21" :model="form21" label-width="105px" label-position="left" size="mini" :rules="rules2">
                                        <el-form-item label="报名科目" prop="option">
                                            <el-cascader
                                                    :options="options"
                                                    v-model="form21.option"
                                                    :props="{ expandTrigger: 'hover', multiple: true}"
                                                    style="width: 100%"
                                                    placeholder="请选择报名科目"
                                            ></el-cascader>
                                        </el-form-item>
                                        <el-form-item label="总课时">
                                            <el-input v-model="form21.lessonCount" placeholder="请输入总课时数"></el-input>
                                        </el-form-item>
                                        <el-form-item label="报名日期" prop="registeredAt">
                                            <el-date-picker
                                                    v-model="form21.registeredAt"
                                                    type="date"
                                                    placeholder="请选择报名日期"
                                                    value-format="yyyy-MM-dd"
                                                    style="width: 100%"
                                            ></el-date-picker>
                                        </el-form-item>
                                        <el-form-item label="预计结束日期">
                                            <el-date-picker
                                                    v-model="form21.endAt"
                                                    type="date"
                                                    placeholder="请选择预计结束日期"
                                                    value-format="yyyy-MM-dd"
                                                    style="width: 100%"
                                            ></el-date-picker>
                                        </el-form-item>
                                        <el-form-item label="学费" prop="expense">
                                            <el-input v-model="form21.expense" placeholder="请输入学费金额"></el-input>
                                        </el-form-item>
                                        <el-form-item label="减免情况">
                                            <el-input v-model="form21.discount" placeholder="请输入减免金额（默认0）"></el-input>
                                        </el-form-item>
                                        <el-form-item label="退费">
                                            <el-input v-model="form21.refund" placeholder="请输入退费金额（默认0）"></el-input>
                                        </el-form-item>
                                        <el-form-item label="备注">
                                            <el-input v-model="form21.remark" placeholder="请输入备注信息"></el-input>
                                        </el-form-item>
                                    </el-form>
                                    <span slot="footer" class="dialog-footer">
                                    <el-button @click="addSignUpVisible = false">取 消</el-button>
                                    <el-button type="primary" @click="saveSignUpInfo">确 定</el-button>
                                </span>
                                </el-dialog>

                                <!-- 编辑报名信息弹出框 -->
                                <el-dialog title="更新报名信息" :visible.sync="editSignUpVisible" width="30%" @close="clear22" top="10vh" append-to-body>
                                    <el-form ref="form22" :model="form22" label-width="105px" label-position="left" size="mini" :rules="rules2">
                                        <el-form-item label="报名科目" prop="option">
                                            <el-cascader
                                                    :options="options"
                                                    v-model="form22.option"
                                                    :props="{ expandTrigger: 'hover', multiple: true }"
                                                    style="width: 100%"
                                                    placeholder="请选择报名科目"
                                            ></el-cascader>
                                        </el-form-item>
                                        <el-form-item label="总课时">
                                            <el-input v-model="form22.lessonCount" placeholder="请输入总课时数"></el-input>
                                        </el-form-item>
                                        <el-form-item label="报名日期" prop="registeredAt">
                                            <el-date-picker
                                                    v-model="form22.registeredAt"
                                                    type="date"
                                                    placeholder="请选择报名日期"
                                                    value-format="yyyy-MM-dd"
                                                    style="width: 100%"
                                            ></el-date-picker>
                                        </el-form-item>
                                        <el-form-item label="预计结束日期">
                                            <el-date-picker
                                                    v-model="form22.endAt"
                                                    type="date"
                                                    placeholder="请选择预计结束日期"
                                                    value-format="yyyy-MM-dd"
                                                    style="width: 100%"
                                            ></el-date-picker>
                                        </el-form-item>
                                        <el-form-item label="学费" prop="expense">
                                            <el-input v-model="form22.expense" placeholder="请输入学费金额"></el-input>
                                        </el-form-item>
                                        <el-form-item label="减免情况">
                                            <el-input v-model="form22.discount" placeholder="请输入减免金额（默认0）"></el-input>
                                        </el-form-item>
                                        <el-form-item label="退费">
                                            <el-input v-model="form22.refund" placeholder="请输入退费金额（默认0）"></el-input>
                                        </el-form-item>
                                        <el-form-item label="备注">
                                            <el-input v-model="form22.remark" placeholder="请输入备注信息"></el-input>
                                        </el-form-item>
                                    </el-form>
                                    <span slot="footer" class="dialog-footer">
                                        <el-button @click="editSignUpVisible = false">取 消</el-button>
                                        <el-button type="primary" @click="saveEditSignUpInfo">确 定</el-button>
                                    </span>
                                </el-dialog>
                            </el-card>
                        </el-col>
                    </el-row>
                </el-dialog>

                <el-dialog :title="this.dTitle" :visible.sync="messageVisible2" width="90%" @close="clearOutter" top="3vh">

                    <el-row :gutter="10">

                        <el-col :span="4">
                            <el-card shadow="hover" class="mgb20" style="height:730px;">

                                <el-timeline :reverse=true>
                                    <el-timeline-item
                                            v-for="(activity, index) in activities"
                                            :key="index"
                                            :timestamp="activity.timestamp"
                                            color="rgba(8, 151, 255, 1)"
                                            :icon="activity.content==='完成'?'el-icon-more':''"
                                            :size="activity.content==='完成'?'large':'normal'"
                                    >
                                        <p class="timeline-content">{{activity.content}}</p>
                                        <p class="timeline-name" v-if="activity.name">
                                            <span>操作者：{{activity.name}}</span>
                                        </p>
                                        <p class="timeline-name" v-if="activity.feedback">
                                            <span>意见：{{activity.feedback}}</span>
                                        </p>
                                    </el-timeline-item>
                                </el-timeline>

                            </el-card>
                        </el-col>

                        <el-col :span="20">
                            <el-card shadow="hover" class="mgb20" style="height:730px;">
                                <el-table
                                        :data="tableData"
                                        :show-header="true"
                                        height="666"
                                        border
                                        class="table"
                                        header-cell-class-name="table-header"
                                        :row-key="getRowKeys"
                                        :expand-row-keys="expands"
                                >
                                    <el-table-column type="expand" width="55" align="center">
                                        <template slot-scope="props">
                                            <el-table
                                                    :data="innerTableData"
                                                    height="400"
                                                    border
                                                    class="table"
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
                                                <el-table-column prop="remark" label="备注"></el-table-column>
                                            </el-table>
                                        </template>
                                    </el-table-column>

                                    <el-table-column prop="courseName" label="课程名称"></el-table-column>
                                    <el-table-column prop="teacherName" label="教师姓名"></el-table-column>
                                    <el-table-column prop="telephone" label="联系电话"></el-table-column>
                                    <el-table-column prop="lessonCount" label="课时安排"></el-table-column>
                                    <el-table-column prop="period" label="上课日期区间"></el-table-column>
                                    <el-table-column prop="remark" label="备注"></el-table-column>
                                </el-table>
                            </el-card>
                        </el-col>
                    </el-row>
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
                messageVisible2: false,
                form: {},
                form1: {},
                form21: {},
                form22: {},
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
                    // option: [
                    //     { type: 'array', required: true, message: '必填项', trigger: 'blur' },
                    // ],
                    option: [
                        { required: true, message: '必填项', trigger: 'blur' },
                    ],
                    registeredAt: [
                        { required: true, message: '必填项', trigger: 'blur' },
                    ],
                    expense: [
                        { required: true, message: '必填项', trigger: 'blur' }
                    ]
                },
                activities: []
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
                        _this.pData = resp.data.signProcessing;
                        _this.cData = resp.data.signSubmitted;
                        _this.fData = resp.data.allSignup;
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
            clear21() {
                this.$refs['form21'].resetFields();
                this.form21 = {
                    option: '',
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
            clear22() {
                this.$refs['form22'].resetFields();
                this.form22 = {
                    option: '',
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
            toMessageData2 (index, row) {
                this.mid = row.id;
                this.active = row.status;
                this.getMessageData2();
                this.dTitle = row.title;
                this.messageVisible2 = true;
            },
            getMessageData () {
                let _this = this
                this.$axios.post('/signup/getById', {
                    id: this.mid
                }).then(resp => {
                    if (resp && resp.status === 200) {
                        _this.activities = resp.data.activities;
                        _this.tableData = resp.data.tableData;
                    }
                })
            },
            getMessageData2 () {
                let _this = this
                this.$axios.post('/teachercourse/getByMid', {
                    signupId: this.mid
                }).then(resp => {
                    if (resp && resp.status === 200) {
                        _this.activities = resp.data.activities;
                        _this.tableData = resp.data.tableData;
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
            clearOutter () {
                this.keyword = '';
                this.getData();
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
                                this.$axios.post('/signdetail/add', {
                                    signupId: this.mid,
                                    courses: "0",
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
            expandChange2 (row) {
                this.tempRowId = row.id;
                let _this = this;
                let temp = this.expands;
                this.expands = [];
                this.expands.push(row.id);
                if (temp.length === 1 && temp[0] === row.id) {
                    this.expands = [];
                } else {
                    this.refreshInnerData2(row.courseId);
                }
            },
            handleAddSignInfo () {
                this.$axios.get('/course/tree').then(resp => {
                    if (resp && resp.status === 200) {
                        this.options = resp.data;
                        this.form21.options = resp.data;
                        this.addSignUpVisible = true;
                    }
                })
            },
            saveSignUpInfo (index, row) {
                // console.log(this.expands)

                this.$refs['form21'].validate((valid) => {
                    if (valid) {
                        console.log(this.form21)
                        let courses = "";
                        let ids = "";
                        for (let i=0; i<this.form21.option.length; i++) {
                            courses = courses + this.form21.option[i] + ";";
                            ids = ids + this.form21.option[i][3].split(":")[1] + ",";
                        }
                        this.$axios.post('/signdetail/add', {
                            signupId: this.mid,
                            courses: courses,
                            studentId: this.tempRowId,
                            lessonCount: this.form21.lessonCount,
                            registeredAt: this.form21.registeredAt,
                            endAt: this.form21.endAt,
                            expense: this.form21.expense,
                            discount: this.form21.discount,
                            refund: this.form21.refund,
                            remark: this.form21.remark
                        }).then(resp => {
                            if (resp && resp.status === 200) {
                                let signdetail = resp.data;
                                this.$axios.post('/coursestudent/add', {
                                    signdetailId: signdetail,
                                    signupId: this.mid,
                                    studentId: this.tempRowId,
                                    courseIds: ids
                                }).then(resp => {
                                    this.$message.success('新增成功');
                                    this.addSignUpVisible = false;
                                    this.refreshInnerData(this.tempRowId);
                                })
                            }
                        })
                    } else {
                        return false;
                    }
                })
            },
            sleep(numberMillis) {
                var now = new Date();
                var exitTime = now.getTime() + numberMillis;
                while (true) {
                    now = new Date();
                    if (now.getTime() > exitTime) {
                        return;
                    }
                }
            },
            refreshInnerData (stuId) {
                let _this = this;
                this.$axios.post('/signdetail/getCoursesByStuAndSignUpId', {
                    studentId: stuId,
                    signupId: this.mid
                }).then(resp => {
                    if (resp && resp.status === 200) {
                        _this.innerTableData = resp.data;
                    }
                });
            },
            refreshInnerData2 (courId) {
                let _this = this;
                this.$axios.post('/student/getStuByCourseIdAndSignUpId', {
                    signupId: this.mid,
                    courseId: courId
                }).then(resp => {
                    if (resp && resp.status === 200) {
                        _this.innerTableData = resp.data;
                    }
                })
            },
            handleEditSignInfo (index, row) {
                this.$axios.post('/signdetail/getById', {
                    id: row.id
                }).then(resp => {
                    if (resp && resp.status === 200) {
                        let courses = resp.data.courses;
                        courses = courses.substring(0, courses.length - 1);
                        let courseArr = courses.split(";");
                        for (let i=0; i<courseArr.length; i++) {
                            courseArr[i] = courseArr[i].split(",");
                        }
                        this.form22 = row;
                        this.form22.option = courseArr;
                        console.log(courseArr)
                    }
                })
                this.$axios.get('/course/tree').then(resp => {
                    if (resp && resp.status === 200) {
                        this.options = resp.data;
                        this.editSignUpVisible = true;
                    }
                })
            },
            saveEditSignUpInfo () {
                console.log(this.form22)
                this.$refs['form22'].validate((valid) => {
                    if (valid) {
                        console.log(this.form22)
                        let courses = "";
                        let ids = "";
                        for (let i=0; i<this.form22.option.length; i++) {
                            courses = courses + this.form22.option[i] + ";";

                            console.log(ids)
                            console.log(this.form22.option[i][3])
                            ids = ids + this.form22.option[i][3].split(":")[1] + ",";
                        }
                        this.$axios.post('/signdetail/update', {
                            id: this.form22.id,
                            courses: courses,
                            studentId: this.tempRowId,
                            lessonCount: this.form22.lessonCount,
                            registeredAt: this.form22.registeredAt,
                            endAt: this.form22.endAt,
                            expense: this.form22.expense,
                            discount: this.form22.discount,
                            refund: this.form22.refund,
                            remark: this.form22.remark
                        }).then(resp => {
                            if (resp && resp.status === 200) {
                                this.$axios.post('/coursestudent/update', {
                                    signdetailId: this.form22.id,
                                    signupId: this.mid,
                                    studentId: this.tempRowId,
                                    courseIds: ids
                                }).then(resp => {
                                    this.$message.success('修改成功');
                                    this.editSignUpVisible = false;
                                    this.refreshInnerData(this.tempRowId);
                                })
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
                    this.$axios.post('/signdetail/del', {
                        id: row.id
                    }).then(resp => {
                        if (resp && resp.status === 200) {
                            this.$message.success('删除成功');
                            this.refreshInnerData(this.tempRowId)
                        }
                    })
                }).catch(() => {
                    console.log("fail")
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
                            console.log(111)
                            console.log(resp.data)
                            if (resp.data === "") {
                                console.log(222)
                                this.$message({
                                    showClose: true,
                                    type: 'error',
                                    message: '提交失败：还未添加报名信息，不能提交',
                                    duration: 3000
                                });
                                this.fullscreenLoading = false;
                            } else if (resp.data.length == 0) {
                                this.$message.success('提交成功');
                                this.fullscreenLoading = false;
                                this.active = 1;
                                this.getMessageData();
                            } else {
                                this.$message({
                                    showClose: true,
                                    type: 'error',
                                    message: '提交失败：编号为 [ '+resp.data+' ] 的学生未添加报名课程',
                                    duration: 3000
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

.timeline-content {
    font-size: 13px;
    color: rgba(0, 0, 0, 0.7);
}
.timeline-name {
    color: rgba(0, 0, 0, 0.5);
    font-size: 12px;
    margin-top: 4px;
}


</style>

