<template>
    <v-layout align:space-around justify-start column>
        <message-form :messages="messages" :messageEd="message"/>
        <message-row v-for="message in sortedMessages" :message="message" :editText="editText" :messages="messages" :deleteMessage="deleteMessage"/>
    </v-layout>
</template>

<script>
    import MessageRow from 'components/messages/MessageRow.vue'
    import MessageForm from 'components/messages/MessageForm.vue'
    export default {
        props: ["messages"],
        data(){
            return{
                message:null
            }
        },
        components:{
          MessageRow, MessageForm
        },
        computed:{
          sortedMessages(){
              return this.messages.sort((a,b)=>-(a.id-b.id))
          }
        },
        methods:{
            editText(message) {
                this.message=message
            },
             deleteMessage(message){
                 this.$resource('/message{/id}').remove({id: message.id}).then(result=>{
                     if(result.ok){
                         this.messages.splice(this.messages.indexOf(this.message),1)
                     }
                 })
            }
        }
    }
</script>


<style>

</style>