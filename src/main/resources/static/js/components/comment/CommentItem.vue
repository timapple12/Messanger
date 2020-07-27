<template>
    <v-list-title>
        <v-list-content>
            <v-list-tile-title>
                {{comment.title}}
            </v-list-tile-title>
        </v-list-content>
    </v-list-title>
</template>

<script>
    export default {
        name: "CommentItem",
        props:['comment']
    }
</script>

<style scoped>

</style>