<template xmlns:v-on="http://www.w3.org/1999/xhtml">
    <div>
        <input type="text" placeholder="Enter a message" v-model="text"/>
        <input type="button" value="Save" v-on:click="save"/>
    </div>
</template>

<script>
    function getIndex(list,id) {
        for(var i=0;i<list.length;i++){
            if(list[i].id===id){
                return i
            }
        }
        return -1;
    }
    export default {
        props: ['messages','messageEd'],
        data(){
            return {
                text: ''
            }
        },
        watch:{
            messageEd(newVal, lastVal){
                    this.text=newVal.text
                    this.id=newVal.id
                }
        },
        methods: {

            save() {
                const message = {text: this.text};
                if (this.id) {
                    this.$resource('/message{/id}').update({id: this.id}, message).then(data=>{
                        data.json().then(result=>{
                            const index=getIndex(result.id)
                            this.messages.splice(index,1,result)
                            this.text = ''
                            this.id=''
                        })
                    })
                }else {
                    this.$resource('/message{/id}').save({}, message).then(result =>
                        result.json().then(data => {
                            this.messages.push(data)
                            this.text = ''
                        }))
                }
            }
        }
    }
</script>


<style></style>