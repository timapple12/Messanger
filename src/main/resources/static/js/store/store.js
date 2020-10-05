import Vue from 'vue'
import Vuex from 'vuex'
import messagesApi from 'api/messages'
import commentApi from 'api/comment'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        messages,
        profile,
        ...dataFront
    },
    getters: {
        sortedMessages: state => state.messages.sort((a, b) => -(a.id - b.id))
    },
    mutations: {

        /** Messages*/

        addMessageMutation(state, message) {

            state.messages.push(message)
        },
        updateMessageMutation(state, message) {
            const updateIndex = state.messages.findIndex(item => item.id === message.id)

            state.messages = [
                ...state.messages.slice(0, updateIndex),
                message,
                ...state.messages.slice(updateIndex + 1)
            ]
        },
        removeMessageMutation(state, message) {
            const deletionIndex = state.messages.findIndex(item => item.id === message.id)

            if (deletionIndex > -1) {
                state.messages = [
                    ...state.messages.slice(0, deletionIndex),
                    ...state.messages.slice(deletionIndex + 1)
                ]
            }
        },

        /** Comments */

        addCommentMutation(state, comment) {
            const updateIndex = state.messages.findIndex(item => item.id === comment.message.id)
            const message = state.messages[updateIndex]

            if (message.comments === null) {
                state.messages = [
                    ...state.messages.slice(0, updateIndex),
                    {
                        ...message,
                        comments: [
                            comment
                        ]
                    },
                    ...state.messages.slice(updateIndex + 1)
                ]
            } else {
                state.messages = [
                    ...state.messages.slice(0, updateIndex),
                    {
                        ...message,
                        comments: [
                            ...message.comments,
                            comment
                        ]
                    },
                    ...state.messages.slice(updateIndex + 1)
                ]
            }
        },

        /** Pages */

        addMessagePageMutation(state, messages) {
            const reducedMessages = state.messages
                .concat(messages)
                .reduce((res, val) => {             // Reduce a duplicates
                    if(val){
                        res[val.id] = val
                        return res                  // Returns Map
                    }
                }, {})                              // Start value
            if(reducedMessages){
                state.messages = Object.values(reducedMessages)
            }
        },
        updateTotalPagesMutation(state, totalPages) {
            state.totalPages = totalPages
        },
        updateCurrentPageMutation(state, currentPage) {
            state.currentPage = currentPage
        }


    },
    actions: {

        /** Messages*/

        async addMessageAction({commit, state}, message) {
            const result = await messagesApi.add(message)
        },

        async updateMessageAction({commit}, message) {
            const result = await messagesApi.update(message)
            const data = await result.json()
            commit('updateMessageMutation', data)
        },
        async removeMessageAction({commit}, message) {
            const result = await messagesApi.remove(message.id)

            if (result.ok) {
                commit('removeMessageMutation', message)
            }
        },

        /** Comments*/

        async addCommentAction({commit, state}, comment) {
            await commentApi.add(comment)
        },
        async loadNextPageAction({commit, state}) {
            const result = await messagesApi.page(state.currentPage+1)
            const data = await result.json()

            commit('addMessagePageMutation', data.messages)
            commit('updateTotalPagesMutation', data.totalPages)
            commit('updateCurrentPageMutation', data.currentPage)
        }
    }
})