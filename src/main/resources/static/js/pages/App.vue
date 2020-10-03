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
                <v-btn text
                        v-if="profile"
                        :disabled="$route.path === '/'"
                        @click="showMessages">
                    Messages
                </v-btn>
                <v-spacer></v-spacer>
                <v-btn text
                       v-if="profile"
                       :disabled="$route.path === '/profile'"
                        @click="showProfile">
                    {{profile.username}}
                </v-btn>
                <v-btn icon href="/logout">Logout>
                    <v-icon>mdi-heart</v-icon>

                </v-btn>
            </v-toolbar>
        </v-card>

        <v-content>
           <router-view></router-view>
        </v-content>

    </v-app>
</template>
<script>
    import { mapState, mapMutations } from 'vuex'

    import { addHandler } from "../util/ws";
    import router from "../router/router";
    export default {
        computed: mapState(['profile']),
        methods:{
            ...mapMutations(['addMessageMutation', 'updateMessageMutation', 'removeMessageMutation', 'addCommentMutation']),
            showMessages(){
                this.$router.push('/')
            },
            showProfile(){
                this.$router.push('/profile')
            }
        },
        created(){
            addHandler(data=>{
                if(data.objectType ==='MESSAGE'){
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addMessageMutation(data.mess)
                            break;
                        case 'UPDATE':
                            this.updateMessageMutation(data.mess)
                            break;
                        case 'REMOVE':
                            this.removeMessageMutation(data.mess)
                            break;
                        default:console.error('Event type is unknown')
                    }
                }else if (data.objectType === 'COMMENT') {
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addCommentMutation(data.mess)
                            break
                        default:
                            console.error('Event type is unknown')
                    }
                }else{
                    console.error('ObjectType  is unknown')
                }
            })

        },
        beforeMount() {
            if(!this.profile){
                this.$router.replace('/auth')
            }
        }
    }
</script>


<style>

</style>