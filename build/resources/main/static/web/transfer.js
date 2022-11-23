const { createApp } = Vue

createApp({
    data() {
        return {
            transactions: [],
            accounts: [],
            transactionMore: {},
            client: {},
            errorr: "",
            typeOfTransfer: "",
            sourceAccountNumber: "",
            destinationAccountNumber: "",
            sourceAccount: {},
            amount: 0,
            description: ""

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
        balanceMax() {
            if (this.sourceAccountNumber != "") {
                let sourceAccount = this.accounts.find(account => account.number == this.sourceAccountNumber)
                let balance = sourceAccount.balance
                return balance
            } else {
                return 0
            }
        },
        transfer() {
            axios.post('/api/transactions', "amount=" + this.amount + "&description=" + this.description + "&sourceAccountNumber=" + this.sourceAccountNumber + "&destinationAccountNumber=" + this.destinationAccountNumber)
                .then(() => {
                    this.sourceAccount = this.accounts.find(account => account.number == this.sourceAccountNumber)
                    let accountId = this.sourceAccount.id
                    this.sourceAccountNumber = ""
                    this.destinationAccountNumber = ""
                    this.amount = 0
                    this.description = ""
                    window.location.href = "/web/account.html?id=" + accountId
                })
                .catch(error => {
                    if (error.response.status == 403) {
                        Swal.fire({
                            icon: 'error',
                            title: error.response.data,
                        })
                    }
                    // if (this.amount == null) {
                    //     Swal.fire({
                    //         icon: 'error',
                    //         title: 'The amount is missing',
                    //     })
                    // }

                })
        },
        logout() {
            axios.post('/api/logout').then(() => window.location.href = '/index.html')
        },
    },

}).mount('#app')