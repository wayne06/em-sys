import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        username: window.localStorage.getItem('username') == null ? '' : JSON.parse(window.localStorage.getItem('username' || '[]')),
        adminMenus: []
    },
    mutations: {
        initAdminMenu (state, menus) {
            state.adminMenus = menus
        },
        login (state, data) {
            state.username = data
            window.localStorage.setItem('username', JSON.stringify(data))
        },
        logout (state) {
            state.username = ''
            window.localStorage.removeItem('username')
            state.adminMenus = []
            console.log(state)
        }
    }
})
