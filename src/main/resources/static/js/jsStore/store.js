import Vue from 'vue'
import Vuex from 'vuex'
import messagesApi from 'api/messages'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        messages: dataFront.messages,
        profile: dataFront.profile
    },
    getters:{
        sortedMessages: state=>state.messages.sort((a,b)=>-(a.id-b.id))

    },
    mutations: {
        addMessageMutation(state,message){
            state.messages=[
                ...state.messages,
                message
            ]
        },
        updateMessageMutation(state,message){
            const index =  state.messages.findIndex(item=>item.id === message.id)
            state.messages = [
                ...state.messages.slice(0, index),
                message,
                ...state.messages.slice(index+1)
            ]
        },
        removeMessageMutation(state,message){
            const index =  state.messages.findIndex(item=>item.id === message.id)

            if(index>-1){
                state.messages = [
                    ...state.messages.slice(0, index),
                    ...state.messages.slice(index+1)
                ]
            }
        }

    },
    actions: {
        async addMessageAction({commit},message){
            messagesApi.add(message).then(result =>
                result.json().then(data => {
                    const index=this.messages.findIndex(item=>item.id===data.id)
                    if(index>-1){
                        this.messages.splice(index,1,data)
                    }else{
                        this.messages.push(data)
                        this.text = ''
                    }

                })
            )
        },
        async updateMessageAction({commit},message){

        },
        async removeMessageAction({commit},message){

        }

    }
})