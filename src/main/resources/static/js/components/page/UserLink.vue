<template>
    <div>
        <router-link
                :to="`/user/${user.id}`">

            <v-avatar v-if="user.userData"
                      :size="`${size}px`">
                <div>
                <img :src="user.userData"></div>
            </v-avatar>

            <v-avatar v-else
                      :size="`${size}px`"
                      color="indigo">
                <v-icon dark>account_circle</v-icon>
            </v-avatar>

            {{ authorName }}
        </router-link>
    </div>
</template>

<script>
    export default {
        name: "UserLink",
        props: ['user', 'size'],
        computed: {
            authorName() {
                return this.user ? this.user.username : 'unknown user';
            }
        }
    }
</script>

<style scoped>

</style>