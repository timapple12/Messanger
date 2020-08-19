<template xmlns:v-on="http://www.w3.org/1999/xhtml">
    <v-card class="my-2">
        <v-card-text>
            <div>
                <v-avatar v-if="message.author && message.author.userData" color="indigo"
                          size="36px">
                    <img :src="message.author.userData" :alt="message.author.username">
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

        <media v-if="message.link" :message="message"></media>

        <v-card-actions>
            <v-btn v-on:click="edit" small>
                <v-icon>edit</v-icon>
            </v-btn>
            <v-btn v-on:click="del" small>
                <v-icon>delete</v-icon>
            </v-btn>
        </v-card-actions>
        <comment-list
                :comments="message.comments"
                :message-id="message.id">
        </comment-list>
    </v-card>
</template>


<script>
    import {mapActions} from 'vuex'
    import Media from 'components/media/Media.vue'
    import CommentList from "../comment/CommentList.vue";

    export default {
        props: ['message', 'editText'],
        components: {CommentList, Media},
        methods: {
            ...mapActions(['removeMessageAction']),
            edit() {
                this.editText(this.message)
            },
            del() {
                this.removeMessageAction(this.message)
            }
        },
        computed: {
            authorName() {
               return  this.message.author ? this.message.author.username : 'unauthorized'
            }
        }
    }
</script>


<style>

</style>