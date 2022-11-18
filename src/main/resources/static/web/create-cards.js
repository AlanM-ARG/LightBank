const { createApp } = Vue

createApp({
    data() {
        return {
            client: {},
            cards: [],
            accounts: [],
            type: "",
            color: ""
        }
    },
    created() {
        this.loadDataClient();
        this.date()
        this.getAccounts();
    },
    methods: {
        order(a, b) {
            return a.id - b.id
        },
        loadDataClient() {
            axios.get("/api/clients/current")
                .then(response => {
                    this.client = response.data
                }
                )
                .catch(error => console.error(error));
        },
        getCards() {
            axios.get("/api/clients/current/cards")
                .then(response => {
                    this.cards = response.data.sort(this.order)
                })
                .catch(error => console.error(error));
        },
        getAccounts() {
            axios.get("/api/clients/current/accounts")
                .then(response => {
                    this.accounts = response.data.sort(this.order)
                })
                .catch(error => console.log(error));
        },
        formatDate(date) {
            let month = date.slice(5, 7)
            let year = date.slice(2, 4)
            return month + "/" + year
        },
        logout() {
            axios.post('/api/logout').then(() => window.location.href = '/index.html')
        },
        
        createCards() {
            axios.post('/api/clients/current/cards', "cardType=" + this.type + "&cardColor=" + this.color)
                .then(() => window.location.href = "/web/cards.html")
                .catch(error => {
                    if (this.type == "") {
                        Swal.fire({
                            icon: 'error',
                            title: "The card type is missing"
                        })
                    }else if (this.color == "") {
                        Swal.fire({
                            icon: 'error',
                            title: "The color type is missing"
                        })
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: error.response.data
                        })
                    }
                })
        },
        date() {
            let now = new Date();
            let month = now.getMonth() + 1;
            let year = now.getFullYear();
            year = ("" + year).slice(2, 4)
            return month + "/" + year
        },
        datePlusYears() {
            let now = new Date();
            let month = now.getMonth() + 1;
            let year = now.getFullYear() + 5;
            year = ("" + year).slice(2, 4)
            return month + "/" + year
        }

    },

}).mount('#app')