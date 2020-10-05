import Vue from 'vue'

const profile = Vue.resource('/profile/{id}')

export default {
    get: id => profile.get({id}),
    changeSub: chanelId => Vue.http.post(`/profile/change-subscription/${chanelId}`)
}