import Vue from 'vue'
import App from 'pages/App.vue'
import VueResource from 'vue-resource'
Vue.use(VueResource)
new Vue({
    el: '#app',
    render:a=>a(App)

})


/*function getIndex(list,id) {
    for(var i=0;i<list.length;i++){
        if(list[i].id===id){
            return i;
        }
    }
    return -1;
}
var messageApi = Vue.resource('message{/id}');                                          // we're getting all data from web page with path /message/'id is not obligatory'
Vue.component('message-form', {
    props: ['messages','messageEd'],
    data: function () {
        return {
            text: ''
        }
    },
    watch:{
      messageEd:
          function(newVal, lastVal){
            this.text=newVal.text;
            this.id=newVal.id;
          }
    },
    methods: {
        save: function () {
            var message = {text: this.text};
            if (this.id) {
                messageApi.update({id: this.id}, message).then(data=>{
                    data.json().then(result=>{
                        var index=getIndex(result.id);
                        this.messages.splice(index,1,result);
                        this.text = '';
                        this.id='';
                    })
                })
            }else {
                messageApi.save({}, message).then(result =>
                    result.json().then(data => {
                        this.messages.push(data);
                        this.text = '';
                    }))
            }
        }
    }

});
*/
