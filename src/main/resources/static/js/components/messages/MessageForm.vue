<template xmlns:v-on="http://www.w3.org/1999/xhtml">
    <v-layout>
        <v-text-field label="New message" placeholder="Enter a message" v-model="text"/>
        <v-btn v-on:click="save">
            Save
        </v-btn>
    </v-layout>
</template>

<script>
    import messagesApi from "../../api/messages";

    export default {
        props: ['messages','messageEd'],
        data(){
            return {
                text: ''
            }
        },
        watch:{
            messageEd(newVal, lastVal){
                    this.text=newVal.text
                    this.id=newVal.id
                }
        },
        methods: {
            save() {
                let message = {id:this.id, text: this.text};
                if (this.id) {
                    messagesApi.update(message).then(data=>{
                        data.json().then(result=>{
                            const index=this.messages.findIndex(item=>item.id===data.id)
                            this.messages.splice(index,1,result)

                        })
                    })
                }else {
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
                }
                this.text = ''
                this.id=''
            }
        }
    }
</script>


<style></style>