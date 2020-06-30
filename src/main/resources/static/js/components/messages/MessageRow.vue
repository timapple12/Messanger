<template xmlns:v-on="http://www.w3.org/1999/xhtml">
    <v-card class="my-2">
        <v-card-text>
            <b>{{message.text}}</b>
        </v-card-text>

        <v-card>
            <v-btn v-on:click="edit" small rounded>
                <v-icon>edit</v-icon>
            </v-btn>
            <v-btn v-on:click="del" small rounded>
                <v-icon>delete</v-icon>
            </v-btn>
        </v-card>
    </v-card>
</template>


<script>
    import { mapActions } from 'vuex'
    export default {
        props: ['message', 'editText'],
        methods:{
            ...mapActions(['removeMessageAction']),
            edit() {
                this.editText(this.message)
            },
            del() {
               this.removeMessageAction(this.message)
            }
        }
    }
</script>


<style>

</style>