import Vue from 'vue'
import 'api/resource'
import App from 'pages/App.vue'
import '@babel/polyfill'
import { connect } from "./util/ws"
import router from 'router/router'
import store from 'store/store'

import Vuetify from "vuetify"
import 'vuetify/dist/vuetify.min.css'


if (dataFront.profile) {
    connect();
}

Vue.use(Vuetify)

new Vue({
    vuetify: new Vuetify({}),
    el: '#app',
    render:a=>a(App),
    router,
    store
})
