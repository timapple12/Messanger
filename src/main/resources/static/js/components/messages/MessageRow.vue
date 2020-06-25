<template xmlns:v-on="http://www.w3.org/1999/xhtml">
    <div>{{message.text}} <span>
        <input type="button" value="Edit" v-on:click="edit"/>
        <input type="button" value="Delete" v-on:click="del"/>
        </span></div>
</template>


<script>
    export default {
        props: ['message', 'editText', 'messages','deleteMessage'],
        methods:{
            edit() {
                this.editText(this.message)
            },
            del() {
               this.deleteMessage(this.message)
            }
        }
    }
</script>


<style>

</style>