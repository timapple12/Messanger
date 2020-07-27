import Vue from 'vue'

const comment=Vue.resource('/message/{id}')

export default {
    add: message=>comment.save({},message)
}