const { createApp } = Vue

createApp({
  data() {
    return {
      data: [],
      newClient: {
        firstName: '',
        lastName: '',
        email: '', 
      },
      clients: [],
      backupClients: [],
      dataJson: ``,
      id:''
    }
  },
  created(){

    this.loadData('/rest/clients')

  },
  methods: {
    loadData(url){
      axios.get(url)
      .then(response =>  {
        this.data = response.data
        this.clients = this.data._embedded.clients
        this.backupClients = this.clients
      }
      )
      .catch(error => console.log(error));
    },
    addClient (){
      if(this.newClient.firstName.length && this.newClient.lastName.length && this.newClient.email.length){
        this.axiosPost('/rest/clients')
        this.newClient.firstName = ''
        this.newClient.lastName = ''
        this.newClient.email = ''
      }
    },
    axiosPost(url){
      axios.post(url, this.newClient)
      .then(()=> this.loadData('/rest/clients'))
      .catch(error => console.log(error));
    },
    // axiosDelete(CLIENT){
    //   axios.delete(CLIENT._links.self.href)
    //   .then(()=> this.loadData('/rest/clients'))
    //   .catch(error => console.log(error));
    // },    
    showData(CLIENT){
      this.newClient.firstName = CLIENT.firstName
      this.newClient.lastName = CLIENT.lastName
      this.newClient.email = CLIENT.email
      this.id = CLIENT._links.self.href
    },
    axiosPut(){
      console.log(this.newClient);
      axios.put(this.id, this.newClient)
      .then(()=> this.loadData('/rest/clients'))
      .catch(error => console.log(error))
      this.newClient.firstName = ''
      this.newClient.lastName = ''
      this.newClient.email = ''
    },

  }
}).mount('#app')