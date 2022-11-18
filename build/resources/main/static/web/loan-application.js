const { createApp } = Vue

createApp({
    data() {
        return {
            client: {},
            accounts: [],
            loans: [],
            loanPayments: [],
            loan: {},
            destinationAccountNumber: "",
            loanRequested: null,
            payments: 0,
            amount: null,
        }
    },
    created() {
        this.loadDataClient()
        this.loanDataLoans()
        this.getAccounts ()
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
        loanDataLoans() {
            axios.get('/api/loans')
                .then(response => {
                    this.loans = response.data.sort(this.order)
                })
        },
        getAccounts() {
            axios.get("/api/clients/current/accounts")
                .then(response => {
                    this.accounts = response.data.sort(this.order)
                })
                .catch(error => console.log(error));
        },
        loanPaymentsSelected() {
            if (this.loanRequested != null) {
                this.loan = this.loans.find(loan => loan.id == this.loanRequested)
                let payments = this.loan.payments
                return payments
            }
            return null
        },
        payment() {
            if (this.amount != null && this.payments != 0) {
                if (this.loan.name == "Mortgage") {
                    let interest = this.amount * 1.2
                    let payment = interest / this.payments
                    return payment
                } else if (this.loan.name == "Personal") {
                    let interest = this.amount * 1.1
                    let payment = interest / this.payments
                    return payment
                } else if (this.loan.name == "Automobile") {
                    let interest = this.amount * 1.15
                    let payment = interest / this.payments
                    return payment
                }
            }
            return 0
        },
        formattedBalance(number) {
            return new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'ARS' }).format(number)
        },
        loanApplication() {
            axios.post("/api/loans", { "id": this.loanRequested, "amount": this.amount, "payments": this.payments, "destinationAccountNumber": this.destinationAccountNumber })
                .then(() => window.location.reload())
                .catch(error => {
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