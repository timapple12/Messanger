import Vue from 'vue'

const profile = Vue.resource('/profile/{id}')

export default {
    get: id => profile.get({id}),
    changeSub: channelId => Vue.http.post(`/profile/change-subscription/${channelId}`),
    subscriptionList: channelId => Vue.http.get(`/profile/get-subscribers/${channelId}`),
    changeStatus: subId => Vue.http.post(`/profile/change-status/${subId}`)
}