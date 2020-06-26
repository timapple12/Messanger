<template xmlns:v-on="http://www.w3.org/1999/xhtml">
    <div>
        <input type="text" placeholder="Enter a message" v-model="text"/>
        <input type="button" value="Save" v-on:click="save"/>
    </div>
</template>

<script>
    import { sendMessage } from "../../util/ws";

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
                sendMessage({id: this.id, text: this.text})
                this.text = ''
                this.id=''
                /*const message = {text: this.text};
                if (this.id) {
                    this.$resource('/message{/id}').update({id: this.id}, message).then(data=>{
                        data.json().then(result=>{
                            const index=getIndex(result.id)
                            this.messages.splice(index,1,result)

                        })
                    })
                }else {
                    this.$resource('/message{/id}').save({}, message).then(result =>
                        result.json().then(data => {
                            this.messages.push(data)
                            this.text = ''
                        }))
                }*/
            }
        }
    }
</script>


<style></style>