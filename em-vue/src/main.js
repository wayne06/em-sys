import Vue from 'vue';
import App from './App.vue';
import router from './router';
import ElementUI from 'element-ui';
import VueI18n from 'vue-i18n';
import { messages } from './components/common/i18n';
// import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
import './assets/css/theme-green/index.css'; // 浅绿色主题
import './assets/css/icon.css';
import './components/common/directives';
import 'babel-polyfill';

import 'xe-utils'
import VXETable from "vxe-table";
import 'vxe-table/lib/index.css';

import store from './store';

import vueConfig from '../vue.config.js'

ElementUI.Dialog.props.closeOnClickModal.default = false;

// 设置反向代理：前端请求默认发送到http://localhost:8443/api
var axios = require('axios')
// axios.defaults.baseURL = 'http://139.159.183.141:8443/api'
// axios.defaults.baseURL = 'http://localhost:8443/api'
axios.defaults.baseURL = vueConfig.devServer.proxy["/api"].target + '/api';

axios.defaults.withCredentials = true

// 全局注册：之后可在其他组件中通过 this.$axios 发送数据
Vue.prototype.$axios = axios

Vue.config.productionTip = false;
Vue.use(VueI18n);
Vue.use(ElementUI, {
    size: 'small'
});
Vue.use(VXETable);
const i18n = new VueI18n({
    locale: 'zh',
    messages
});

//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
    console.log(vueConfig.devServer.proxy["/api"].target)
    console.log(axios.defaults.baseURL)
    document.title = `${to.meta.title} | 启智培训中心`;
    if (store.state.username) {
        initAdminMenu(router, store)
    }
    if (to.meta.requireAuth) {
        if (store.state.username) {
            axios.get('/user/authentication').then(resp => {
                if (resp.data) next()
            })
        } else {
            next({
                path: 'login',
                query: {redirect: to.fullPath}
            })
        }
    } else {
        next()
    }
});

const initAdminMenu = (router, store) => {
    // 防止重复触发加载菜单操作
    if (store.state.adminMenus.length > 0) {
        return
    }
    axios.get('/menu/byCurrentUser').then(resp => {
        if (resp && resp.status === 200) {
            let fmtRoutes = formatRoutes(resp.data)
            router.addRoutes(fmtRoutes)
            store.commit('initAdminMenu', fmtRoutes)
        }
    })
}

const formatRoutes = (routes) => {
    let fmtRoutes = []
    routes.forEach(route => {
        if (route.children) {
            route.children = formatRoutes(route.children)
        }

        let fmtRoute = {
            path: route.path,
            // component: resolve => {
            //     require(['./components/page/' + route.component + '.vue'], resolve)
            // },
            component: require('./components/page/' + route.component + '.vue'),
            name: route.name,
            nameZh: route.nameZh,
            iconCls: route.iconCls,
            children: route.children
        }
        fmtRoutes.push(fmtRoute)
    })
    return fmtRoutes
}

new Vue({
    router,
    store,
    components: {App},
    i18n,
    render: h => h(App)
}).$mount('#app');
