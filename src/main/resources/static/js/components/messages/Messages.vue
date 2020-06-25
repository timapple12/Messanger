<template>
    <div>
        <message-form :messages="messages" :messageEd="message"/>
        <message-row v-for="message in messages" :message="message" :editText="editText" :messages="messages" :deleteMessage="deleteMessage"/>
    </div>
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