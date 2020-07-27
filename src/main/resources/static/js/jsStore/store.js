import Vue from 'vue'
import Vuex from 'vuex'
import messagesApi from 'api/messages'
import commentApi from "../api/comment";

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        messages: messages,
        profile: dataFront.profile
    },
    getters: {
        sortedMessages: state => (state.messages || []).sort((a, b) => -(a.id - b.id))
    },
    mutations: {
        addMessageMutation(state, message) {
            state.messages = [
                ...state.messages,
                message
            ]
        },
        updateMessageMutation(state, message) {
            const index = state.messages.findIndex(item => item.id === message.id)
            state.messages = [
                ...state.messages.slice(0, index),
                message,
                ...state.messages.slice(index + 1)
            ]
        },
        removeMessageMutation(state, message) {
            const index = state.messages.findIndex(item => item.id === message.id)

            if (index > -1) {
                state.messages = [
                    ...state.messages.slice(0, index),
                    ...state.messages.slice(index + 1)
                ]
            }
        },

        async addCommentMutation({commit, state}, comment) {
            const updateIndex = state.messages.findIndex(item => item.id === comment.message.id)
            const message = state.messages[updateIndex]

            state.messages = [
                ...state.messages.slice(0, updateIndex),
                {
                    ...message,
                    comments: [
                        ...message.comments,
                        comment
                    ]
                },
                ...state.messages.slice(index + 1)
            ]

        }
    },
    actions: {
        async addMessageAction({commit, state}, message) {
            const result = await messagesApi.add(message)
            const data = await result.json()
            const index = state.messages.findIndex(item => item.id === data.id)
            if (index > -1) {
                commit('updateMessageMutation', data)
            } else {
                commit('addMessageMutation', data)
                this.text = ''
            }
        },
        async updateMessageAction({commit}, message) {
            const data = await messagesApi.update(message)
            const result = await data.json()
            commit('updateMessageMutation', result)

        },
        async removeMessageAction({commit}, message) {
            const result = await messagesApi.remove(message.id)
            if (result.ok) {
                commit('removeMessageMutation', message)
            }
        },
        async addCommentAction({commit, state}, comment){
            const response = await commentApi.add(comment)
            const data = await response.json()
            commit('addCommentMutation', comment)

        }
    }

})