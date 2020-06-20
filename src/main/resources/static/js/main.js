function getIndex(list,id) {
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
    watch:{                             //if messageEd changes watch function will be called.
      messageEd:
          function(newVal, lastVal){
            this.text=newVal.text;
            this.id=newVal.id;
          }
    },
    template: '<div>' +
        '<input type="text" placeholder="Enter a message" v-model="text"/>' +        // v-model 'says' that all what we get from input will be written into var 'text'
        '<input type="button" value="Save" v-on:click="save"/>' +                    /* v-on:click we declare that method in breaks will be called // may be cut to @click*/
        '</div>',
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
Vue.component('message-row', {
    props: ['message', 'editText', 'messages'],
    template: '<div>{{message.text}} <span>' +
        '<input type="button" value="Edit" v-on:click="edit"/>' +
        '<input type="button" value="Delete" v-on:click="delet"/>' +
        '</span></div>',
    methods:{
        edit:function () {
            this.editText(this.message);
        },
        delet:function () {
            messageApi.remove({id: this.message.id}).then(result=>{
                if(result.ok){
                    this.messages.splice(this.messages.indexOf(this.message),1)
                }
            })
        }
    }
});
Vue.component('messages-list', {
    props: ["messages"],
    data:function(){
        return{
            message:null
        }
    },
    template: '<div>' +
            '<message-form :messages="messages" :messageEd="message"/>' +
        '<message-row v-for="message in messages" :message="message" :editText="editText" :messages="messages"/>' +
        '</div>',

    methods:{
        editText:function (message) {
            this.message=message;
        }
    }
});
var app = new Vue({
    el: '#app',
    template:
        '<div>' +
        '<div v-if="!profile">Authorize by <a href="/login">Google</a></div>' +
        '<div v-else>' +
        '<div>{{profile.name}}&nbsp;<a href="/logout">Logout</a></div>' +
        '<messages-list :messages="messages" />' +
        '</div>' +
        '</div>',
    data: {
        messages: dataFront.messages,
        profile: dataFront.profile
    },
   /* created: function () {
        messageApi.get().then(result =>
            result.json().then(result2 =>
                result2.forEach(message => this.messages.push(message))
            )
        )
    }*/
});