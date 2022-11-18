const { createApp } = Vue

createApp({
  data() {
    return {
      clientURL: "/api/clients/current",
      client: {},
      accounts: [],
      loans: [],
    }
  },
  created() {
    this.loadData(this.clientURL);
    this.getAccounts();
  },
  methods: {
    order(a, b) {
      return a.id - b.id
    },
    loadData(url) {
      axios.get(url)
        .then(response => {
          this.client = response.data
          this.loans = this.client.loans.sort(this.order)
        })
        .catch(error => console.log(error));
    },
    getAccounts() {
      axios.get("/api/clients/current/accounts")
        .then(response => {
          this.accounts = response.data.sort(this.order)
        })
        .catch(error => console.log(error));
    },
    disableAccount(id) {
      Swal.fire({
          title: 'Are you sure you want to delete the account?',
          icon: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#d33',
          confirmButtonText: 'Yes, delete account!'
      })
          .then((result) => {
              if (result.isConfirmed) {
                  Swal.fire(
                      'Deleted!',
                      'Your account has been deleted.',
                      'success'
                  )
                  axios.patch("/api/clients/current/accounts/" + id)
                      .then(() => this.getAccounts())
                      .catch(error => console.error(error))
              }
          })
  },
    formattedBalance(number) {
      return new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'ARS' }).format(number)
    },
    logout() {
      axios.post('/api/logout').then(() => window.location.href = '/index.html')
    },
    createAccount() {
      axios.post("/api/clients/current/accounts").then(() => this.getAccounts())
    },

  },
}).mount('#app')