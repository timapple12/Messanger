<template xmlns:v-on="http://www.w3.org/1999/xhtml">
    <v-layout>
        <v-text-field label="New message"
                      placeholder="Enter a message"
                      v-model="text"
                      @keyup.enter="save"/>
        <v-btn v-on:click="save">
            Save
        </v-btn>
    </v-layout>
</template>

<script>
    import { mapActions } from 'vuex'

    export default {
        props: ['messageEd'],
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
            ...mapActions(['updateMessageAction', 'addMessageAction']),
           async save() {
                let message = {id:this.id, text: this.text};
                if (this.id) {
                    this.updateMessageAction(message)
                }else {
                   this.addMessageAction(message)
                    this.text = ''
                    this.id=''
                }


            }
        }
    }
</script>


<style></style>