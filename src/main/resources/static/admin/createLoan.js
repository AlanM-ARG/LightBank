const { createApp } = Vue

createApp({
    data() {
        return {
            client: {},
            accounts: [],
            name: "",
            maxAmount: 0,
            percentage: 0,
            payments: []
        }
    },
    created() {
        this.loadDataClient()
        this.getAccounts()
    },
    methods: {
        order(a, b) {
            return a.id - b.id
        },
        loadDataClient() {
            axios.get('/api/clients/current')
                .then(response => {
                    this.client = response.data
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
        formattedBalance(number) {
            return new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'ARS' }).format(number)
        },
        createLoan() {
            axios.post("/api/loans/create", "name=" + this.name + "&maxAmount=" + this.maxAmount + "&percentage=" + this.percentage + "&payments=" + this.payments)
                .then(response => console.log(response))
                .catch(error => {
                    console.error(error);
                    Swal.fire({
                        icon: 'error',
                        title: error.response.data,
                    })
                })
        },
        logout() {
            axios.post('/api/logout').then(() => window.location.href = '/index.html')
        },
    },
}).mount('#app')