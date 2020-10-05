<template>
    <v-container>
        <v-layout justify-space-around>
            <v-flex sm3>
                <div>User profile</div>
                <v-layout row justify-space-between>
                    <v-flex>
                        <v-img :src="profile.userData"></v-img>
                    </v-flex>
                    <v-flex>
                        <v-layout column>
                            <v-flex>{{profile.username}}</v-flex>
                            <v-flex>{{profile.subscribers && profile.subscribers.length}} subscribers</v-flex>
                        </v-layout>
                    </v-flex>
                </v-layout>
                <v-btn
                        v-if="!isMyProfile"
                        @click="changeSub"
                >
                    {{isSubscribed ? 'Unsubscribe':'Subscribe'}}
                </v-btn>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import profile from 'api/profile'

    export default {
        name: 'Profile',
        data() {
            return {
                profile: {}
            }
        },
        computed: {
            isMyProfile() {
                return !this.$route.params.id ||
                    this.$route.params.id === this.$store.state.profile.id
            },
            isSubscribed() {
                return this.profile.subscribers &&
                    this.profile.subscribers.find(sub => {
                        return sub.id === this.$store.state.profile.id
                    })
            }
        },
        watch: {
            '$route'() {                               // If $route changes we update profile page
                this.getProfile()
            }
        },
        methods: {
            async changeSub() {
                const data = await profile.changeSub(this.profile.id)
                this.profile = await data.json()
            },
            async getProfile() {
                const id = this.$route.params.id ||this.$store.state.profile.id
                const data = await profile.get(id)

                this.profile = await data.json()
                this.$forceUpdate()
            }
        },
        beforeMount() {
            this.getProfile()
        }
    }
</script>


<style>
    img {
        max-width: 100%;
        max-height: 100%;
    }
</style>