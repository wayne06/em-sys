<template>
    <div class="sidebar">
        <el-menu
                class="sidebar-el-menu"
                :default-active="onRoutes"
                :collapse="collapse"
                background-color="#324157"
                text-color="#bfcbd9"
                active-text-color="#20a0ff"
                unique-opened
                router
        >
<!--            <template v-for="(item,i) in adminMenus">-->
<!--                <template v-if="item.children.length === 0">-->
<!--                    <el-menu-item :key="item.path" :index="item.path">-->
<!--                        <i :class="item.iconCls"></i>-->
<!--                        {{ item.nameZh }}-->
<!--                    </el-menu-item>-->
<!--                </template>-->
<!--                <template v-else>-->
<!--                    &lt;!&ndash;index 没有用但是必需字段且为 string &ndash;&gt;-->
<!--                    <el-submenu :key="i" :index="i + ''">-->
<!--                        <span slot="title">-->
<!--                            <i :class="item.iconCls"></i>-->
<!--                            {{item.nameZh}}-->
<!--                        </span>-->
<!--                        <el-menu-item v-for="child in item.children" :key="child.path" :index="child.path">-->
<!--                            <i :class="child.iconCls"></i>-->
<!--                            {{ child.nameZh }}-->
<!--                        </el-menu-item>-->
<!--                    </el-submenu>-->
<!--                </template>-->
<!--            </template>-->
                <template v-for="item in adminMenus">
                    <template v-if="item.children.length === 0">
                        <el-menu-item :key="item.path" :index="item.path">
                            <i :class="item.iconCls"></i>
                            <span slot="title">{{item.nameZh}}</span>
                        </el-menu-item>
                    </template>
                    <template v-else>
                        <!--index 没有用但是必需字段且为 string -->
                        <el-submenu :key="item.id" :index="item.id + ''">
                            <template slot="title">
                                <i :class="item.iconCls"></i>
                                <span slot="title">{{item.nameZh}}</span>
                            </template>
                            <el-menu-item v-for="child in item.children" :key="child.path" :index="child.path">
                                <i :class="child.iconCls"></i>
                                <span slot="title">{{child.nameZh}}</span>
                            </el-menu-item>
                        </el-submenu>
                    </template>
                </template>
        </el-menu>
    </div>
</template>

<script>
    import bus from '../common/bus';

    export default {
        name: 'Sidebar',
        data() {
            return {
                collapse: false
            }
        },
        computed: {
            adminMenus() {
                return this.$store.state.adminMenus
            },
            onRoutes() {
                return this.$route.path.replace('/', '');
            }
        },
        created() {
            // 通过 Event Bus 进行组件间通信，来折叠侧边栏
            bus.$on('collapse', msg => {
                this.collapse = msg;
                bus.$emit('collapse-content', msg);
            });
        }
    };
</script>

<style scoped>
    .sidebar {
        display: block;
        position: absolute;
        left: 0;
        top: 70px;
        bottom: 0;
        overflow-y: scroll;
    }

    .sidebar::-webkit-scrollbar {
        width: 0;
    }

    .sidebar-el-menu:not(.el-menu--collapse) {
        width: 250px;
    }

    .sidebar > ul {
        height: 100%;
    }
</style>
