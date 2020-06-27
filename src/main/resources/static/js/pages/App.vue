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
                <v-spacer></v-spacer>
                <div>
                    <!--{{profile}}&nbsp;-->
                </div>
                <v-btn icon href="/logout">Logout>
                    <v-icon>exit_to_app</v-icon>

                </v-btn>
            </v-toolbar>
        </v-card>

        <v-content>
            <v-container v-if="!profile">
                 Authorize by
                <a href="/login">Google</a>
            </v-container>
            <v-container>
                <div v-if="profile">
                    <messages-list :messages="messages" />
                </div>
            </v-container>
        </v-content>

    </v-app>
</template>
<script>
    import MessagesList from 'components/messages/Messages.vue'
    import { addHandler } from "../util/ws";
    export default {
        components:{
          MessagesList
        },
        data() {
            return {
                messages: dataFront.messages,
                profile: dataFront.profile
            }
        },

        created(){
            addHandler(data=>{
                if(data.objectType==='MESSAGE'){
                    let index = this.messages.findIndex(item => item.id === data.mess.id)
                    switch (data.eventType) {
                        case 'CREATE':
                        case 'UPDATE':
                            if(index>-1){
                                this.messages.splice(index,1,data.mess)
                            }else{
                                this.messages.push(data.mess)
                            }
                            break;
                        case 'REMOVE':
                            this.messages.splice(index,1)
                            break;
                        default:console.error('Event type is unknown')
                    }
                }else{
                    console.error('ObjectType  is unknown')
                }
            })

        }
    }
</script>


<style>

</style>