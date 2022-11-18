const { createApp } = Vue

createApp({
    data() {
        return {
            transactions: [],
            accounts: [],
            transactionMore: {},
            client: {},
            error: "",
            account: {}
        }
    },
    created() {
        const urlParams = new URLSearchParams(window.location.search)
        const id = urlParams.get('id')
        this.loadData('/api/accounts/' + id)
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
        loadData(url) {
            axios.get(url)
                .then(response => {
                    this.account = response.data
                    this.transactions = this.account.transactions.sort(this.order)
                })
                .catch(error => console.error(error))
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
        findTransactionById(ID) {
            this.transactionMore = this.transactions.find(transaction => transaction.id == ID)
        },
        logout() {
            axios.post('/api/logout').then(() => window.location.href = '/index.html')
        },
    },

}).mount('#app')