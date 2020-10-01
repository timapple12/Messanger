<template>
    <v-container>
        <v-layout space-around justify-start column>
            <message-form :messageEd="message"/>
            <message-row v-for="message in sortedMessages" :message="message" :editText="editText"/>
        </v-layout>
    </v-container>
</template>

<script>
    import {mapGetters} from 'vuex'
    import MessageRow from 'components/messages/MessageRow.vue'
    import MessageForm from 'components/messages/MessageForm.vue'
    import messagesApi from "../../api/messages";

    export default {
        data() {
            return {
                message: null
            }
        },
        components: {
            MessageRow, MessageForm
        },
        computed: mapGetters(['sortedMessages']),
        methods: {
            editText(message) {
                this.message = message
            },
        }
    }
</script>


<style>

</style>