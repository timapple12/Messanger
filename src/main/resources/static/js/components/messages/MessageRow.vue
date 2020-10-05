<template xmlns:v-on="http://www.w3.org/1999/xhtml">
    <v-card class="my-2">
        <v-card-text>
            <user-link :user="message.user" size="36"></user-link>

            <div>
                <b>{{message.text}}</b>
            </div>
        </v-card-text>
        <media v-if="message.link" :message="message"/>
        <v-card>
            <v-btn v-on:click="edit" small rounded>
                <v-icon>edit</v-icon>
            </v-btn>
            <v-btn v-on:click="del" small rounded>
                <v-icon>delete</v-icon>
            </v-btn>
        </v-card>
        <comment-list
                :comments="message.comments"
                :message-id="message.id">
        </comment-list>
    </v-card>
</template>


<script>
    import {mapActions} from 'vuex'
    import Media from "../media/Media.vue";
    import CommentList from "../comment/CommentList.vue";
    import UserLink from "../page/UserLink.vue";

    export default {
        components: {UserLink, CommentList, Media},
        props: ['message', 'editText'],

        methods: {
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