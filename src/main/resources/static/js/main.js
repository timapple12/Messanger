import Vue from 'vue'
import 'api/resource'
import App from 'pages/App.vue'
import '@babel/polyfill'
import { connect } from "./util/ws"
import router from 'router/router'
import store from 'store/store'
import 'vuetify/dist/vuetify.min.css'
import Vuetify from "vuetify"

if (dataFront.profile) {
    connect();
}

Vue.use(Vuetify)

new Vue({
    el: '#app',
    render:a=>a(App),
    router,
    store,
    vuetify: new Vuetify({})

})
