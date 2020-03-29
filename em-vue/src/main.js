import Vue from 'vue';
import App from './App.vue';
import router from './router';
import ElementUI from 'element-ui';
import VueI18n from 'vue-i18n';
import { messages } from './components/common/i18n';
import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
// import './assets/css/theme-green/index.css'; // 浅绿色主题
import './assets/css/icon.css';
import './components/common/directives';
import 'babel-polyfill';

import 'xe-utils'
import VXETable from "vxe-table";
import 'vxe-table/lib/index.css'

import store from './store'

ElementUI.Dialog.props.closeOnClickModal.default = false;

// 设置反向代理：前端请求默认发送到http://localhost:8443/api
var axios = require('axios')
axios.defaults.baseURL = 'http://localhost:8443/api'
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
    document.title = `${to.meta.title} | 启智培训中心`;
    // if (store.state.user.username && to.path.startsWith('/admin')) {
    //     console.log(111)
    //     initAdminMenu(router, store)
    // }
    if (to.meta.requireAuth) {
        // console.log(to.meta.requireAuth)
        if (store.state.username) {
            // console.log(store.state.user)
            axios.get('/user/authentication').then(resp => {
                if (resp.data) next()
                // console.log(resp)
            })
        } else {
            console.log(333)
            next({
                path: 'login',
                query: {redirect: to.fullPath}
            })
        }
    } else {
        console.log(222)
        next()
    }
});

new Vue({
    router,
    store,
    i18n,
    render: h => h(App)
}).$mount('#app');
