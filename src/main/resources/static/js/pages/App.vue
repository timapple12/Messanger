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
    import { getIndex } from "../util/collections";

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
                var index = getIndex(this.messages,data.id)
                if(index>-1){
                    this.messages.splice(index,1, data)
                }else {
                    this.messages.push(data)
                }

            })

        }
    }
</script>


<style>

</style>