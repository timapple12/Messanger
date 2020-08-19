<template>
    <v-app>
        <v-card
                color="grey lighten-4"
                flat
                height="60px"
                tile
        >
            <v-toolbar dense>
                <v-toolbar-title>Spring RestTest Application</v-toolbar-title>
                <v-btn text :disabled="$route.path === '/'" href="/">Messages</v-btn>
                <v-spacer></v-spacer>
                <v-btn text :disabled="$route.path === '/profile'" href="/profile" v-if="profile">{{profile.name}}
                </v-btn>
                <v-btn icon href="/logout">
                    <v-icon>exit_to_app</v-icon>
                </v-btn>
            </v-toolbar>
        </v-card>

        <v-content>
            <router-view></router-view>
        </v-content>

    </v-app>
</template>
<script>
    import {mapState, mapMutations} from 'vuex'
    import {addHandler} from "../util/ws";

    export default {

        methods: mapMutations(['addMessageMutation', 'removeMessageMutation', 'updateMessageMutation', 'addCommentMutation']),
        computed: mapState(['profile']),
        created() {
            addHandler(data => {
                if (data.objectType === 'MESSAGE') {
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addMessageMutation(data.mess)
                            break
                        case 'UPDATE':
                            this.updateMessageMutation(data.mess)
                            break
                        case 'REMOVE':
                            this.removeMessageMutation(data.mess)
                            break
                        default:
                            console.error('Event type is unknown')
                    }
                } else if (data.objectType === 'COMMENT') {
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addCommentMutation(data.mess)
                            break
                        default:
                            console.error('Event type is unknown')
                    }
                } else {
                    console.error('ObjectType  is unknown')
                }
            })

        },
        beforeMount() {
            if (!this.profile) {
                this.$router.replace('auth')
            }
        }
    }
</script>


<style>

</style>