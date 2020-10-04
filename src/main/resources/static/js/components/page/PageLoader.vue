<template>
    
</template>

<script>
    import { mapActions } from 'vuex'
    export default {
        name: "PageLoader",
        methods: mapActions(['loadNextPageAction']),
        mounted() { const data = document.documentElement
            window.onscroll = () =>{

                const isBottom = window.scrollY + window.innerHeight === data.offsetHeight

                if(isBottom){
                    this.loadNextPageAction()
                }
            }
        },

        beforeDestroy() {
            window.onscroll = null
        }
    }
</script>

<style scoped>

</style>