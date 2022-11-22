const { createApp } = Vue

createApp({
    data() {
        return {
            transactions: [],
            accounts: [],
            transactionMore: {},
            client: {},
            error: "",
            account: {},
            idAccount: "",
            startDate: "",
            endDate: "",
        }
    },
    created() {
        let urlParams = new URLSearchParams(window.location.search)
        let id = urlParams.get('id')
        this.idAccount = id
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
        downloadPDF(){
            axios.get('/api/pdf/generate?startDate=' + this.startDate + '&endDate=' + this.endDate + '&idAccount=' + this.idAccount)
            .then(response => {
                console.log(response) 
                let url = window.URL.createObjectURL(new  Blob([response.data]));
                window.location.href = url
            })
        },
        createPdf() {
            if(this.startDate.length != 0 && this.endDate.length != 0){
                return '/api/pdf/generate?startDate=' + this.startDate + '&endDate=' + this.endDate + '&idAccount=' + this.idAccount
            }
            return '/api/pdf/generate/all?idAccount=' + this.idAccount
        },
        formattedBalance(number) {
            return new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'ARS' }).format(number)
        },
        findTransactionById(id) {
            this.transactionMore = this.transactions.find(transaction => transaction.id == id)
        },
        logout() {
            axios.post('/api/logout').then(() => window.location.href = '/index.html')
        },
    },

}).mount('#app')