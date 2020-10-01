<template>
    <iframe width="560"
            height="315"
            :src="target"
            frameborder="0"
            allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
            allowfullscreen></iframe>
</template>


<script>
    export default {
        name: 'YouTube',
        props: ['src'],
        computed:{
            target(){
                let ur = this.src
                let splitUrl = this.src.split('/')
                let url = splitUrl[splitUrl.length-1].toString().replace("watch?v=",'')
                //let a =ur.match(/(?:https?:\/\/)?(?:www\.)?youtu\.?be(?:\.com)?\/?.*(?:watch|embed)?(?:.*v=|v\/|\/)([\w\-_]+)/)
                return 'https://www.youtube.com/embed/'+url
            }
        }
    }
</script>

<style>
</style>