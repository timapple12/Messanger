<template xmlns:v-on="http://www.w3.org/1999/xhtml">
    <v-card class="my-2">
        <v-card-text>
            <b>{{message.text}}</b>
        </v-card-text>

        <v-card-action>
            <v-btn v-on:click="edit" small round>
                <v-icon>edit</v-icon>
            </v-btn>
            <v-btn v-on:click="del" small round>
                <v-icon>delete</v-icon>
            </v-btn>
        </v-card-action>
    </v-card>
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