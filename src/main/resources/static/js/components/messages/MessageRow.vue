<template xmlns:v-on="http://www.w3.org/1999/xhtml">
    <v-card class="my-2">
        <v-card-text>
            <div>
                <v-avatar v-if="message.user.userData"
                          size="36px">
                    <img :src="message.user.userData">
                </v-avatar>

                <v-avatar v-else
                          size="36px"
                          color="indigo">
                    <v-icon dark>account_circle</v-icon>
                </v-avatar>

                {{ authorName }}
            </div>
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
    import messages from "../../api/messages";

    export default {
        components: {CommentList, Media},
        props: ['message', 'editText'],
        computed: {
            authorName() {
                return this.message.user ? this.message.user.username : 'unknown user';
            }
        },
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