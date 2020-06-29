import Vue from 'vue'
import 'api/resource'
import App from 'pages/App.vue'
import '@babel/polyfill'
import { connect } from "./util/ws";
import 'vuetify/dist/vuetify.min.css'
import Vuetify from "vuetify";
import store from 'jsStore/store'

if (dataFront.profile) {
    connect();
}

Vue.use(Vuetify)

new Vue({
    el: '#app',
    store,
    render:a=>a(App),
    vuetify: new Vuetify({})

})
