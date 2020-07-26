<template>
    <v-card>
        <v-flex v-if="type === 'href'" xs12 sm6 offset-sm3>
            <v-img v-if="message.cover" :src="message.cover" aspect-ratio="2.75"></v-img>
            <v-card-title>
                <div>
                    <h3>
                        <a :href="message.link">{{message.title || message.link}}</a>
                    </h3>
                </div>
                <div v-if="message.description">{{message.description}}</div>
            </v-card-title>
        </v-flex>

        <v-flex v-if="type === 'image'" xs12 sm6 offset-sm3>
            <a :href="message.link">
                <v-img v-if="message.cover" :src="message.cover" aspect-ratio="2.75"></v-img>
                {{message.link}}
            </a>
        </v-flex>

        <v-flex v-if="type === 'youtube'" xs12 sm6 offset-sm3>
            <br/>
            <you-tube :src="message.link"><br/></you-tube>
        </v-flex>
        <br/>
    </v-card>
</template>


<script>
    import YouTube from './YouTube.vue'
    export default {
        name: 'Media',
        components: { YouTube },
        props: ['message'],
        data() {
            return {
                type: 'href'
            }
        },
        beforeMount(){
            if(this.message.link.indexOf('youtu')>-1||this.message.link.indexOf('youtube')>-1){
                this.type='youtube'
            }else if(this.message.link.match(/\.(jpeg|jpg|gif|png)$/) !==null){
                this.type = 'image'
            }else{
                this.type = 'href'
            }
        }
    }
</script>


<style>

</style>