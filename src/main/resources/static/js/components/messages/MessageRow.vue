<template xmlns:v-on="http://www.w3.org/1999/xhtml">
    <v-card class="my-2">
        <v-card-text>
            <b>{{message.text}}</b>
        </v-card-text>

        <media v-if="message.link" :message = "message"></media>

        <v-card-actions>
            <v-btn v-on:click="edit" small>
                <v-icon>edit</v-icon>
            </v-btn>
            <v-btn v-on:click="del" small>
                <v-icon>delete</v-icon>
            </v-btn>
        </v-card-actions>
    </v-card>
</template>


<script>
    import { mapActions } from 'vuex'
    import Media from 'components/media/Media.vue'
    export default {
        props: ['message', 'editText'],
        components: { Media },
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