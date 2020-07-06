<template xmlns:v-if="http://www.w3.org/1999/xhtml">
    <v-container>
        <v-layout justify-space-around>
            <v-flex sm3>
                <div>User profile</div>
                <v-layout row justify-space-between>
                    <v-flex>
                        <v-img :src="profile.picture" ></v-img>
                    </v-flex>
                    <v-flex>
                        <v-layout column>
                            <v-flex>{{profile.name}}</v-flex>
                            <v-flex>{{profile.email}}</v-flex>
                        </v-layout>
                    </v-flex>
                </v-layout>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import { mapState } from "vuex";

    export default{
        name: 'Profile',
        computed: mapState(['profile'])
    }
</script>


<style>
    img{
        max-width: 10%;
        max-height: 10%;
    }
</style>