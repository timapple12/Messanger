<template>
<div>
    <div v-if="!profile">
        Authorize by
        <a href="/login">Google</a>
    </div>
    <div v-else>
        <div>
            {{profile.username}}&nbsp;
            <a href="/logout">Logout</a>
        </div>
        <messages-list :messages="messages" />
        </div>
    </div>
</template>
<script>
    import MessagesList from 'components/messages/Messages.vue'
    export default {
        components:{
          MessagesList
        },
        data() {
            return {
                messages: dataFront.messages,
                profile: dataFront.profile
            }
        }
    }
</script>


<style>

</style>