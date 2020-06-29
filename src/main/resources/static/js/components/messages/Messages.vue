<template>
    <v-layout align:space-around justify-start column>
        <message-form  :messageEd="message"/>
        <message-row v-for="message in sortedMessages" :message="message" :editText="editText" :messages="messages" :deleteMessage="deleteMessage"/>
    </v-layout>
</template>

<script>
    import { mapGetters } from 'vuex'
    import MessageRow from 'components/messages/MessageRow.vue'
    import MessageForm from 'components/messages/MessageForm.vue'
    import messagesApi from "../../api/messages";
    export default {
        data(){
            return{
                message:null
            }
        },
        components:{
          MessageRow, MessageForm
        },
        computed: mapGetters(['sortedMessages']),
        methods:{
            editText(message) {
                this.message=message
            },
             deleteMessage(message){
                 messagesApi.remove(message.id).then(result=>{
                     if(result.ok){
                        // this.messages.splice(this.messages.indexOf(this.message), 1)
                     }
                 })
            }
        }
    }
</script>


<style>

</style>