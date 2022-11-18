const { createApp } = Vue

createApp({
    data() {
        return {
            message: 'Hello Vue!'
        }
    },
    created(){

    },
    methods: {
        loadData(URL){
            axios.post('/api/login',"email=melba@mindhub.com&password=melba",{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => console.log('signed in!!!'))
        }
    }
}).mount('#app')