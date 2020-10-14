import Vue from 'vue'
import VueRouter from 'vue-router'
import Messages from 'components/messages/Messages.vue'
import Auth from 'pages/Auth.vue'
import Profile from 'pages/Profile.vue'
import Subs from "../pages/Subs.vue";

Vue.use(VueRouter)

const routes = [
    { path: '/', component: Messages },
    { path: '/auth', component: Auth },
    { path: '/user/:id?', component: Profile },
    { path: '/subscriptions/:id', component: Subs },
    { path: '*', component: Messages }

]

export default new VueRouter({
    mode: 'history',                // it removes '#' from path
    routes                          // the same routes: routes

})