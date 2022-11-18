const { createApp } = Vue

createApp({
    data() {
        return {
            client: {},
            cards: [],
            accounts: [],
            cardsDebit: [],
            cardsCredit: [],
        }
    },
    created() {
        this.loadData();
        this.getCards();
        this.getAccounts();
    },
    methods: {
        order(a, b) {
            return a.id - b.id
        },
        loadData() {
            axios.get("/api/clients/current")
                .then(response => {
                    this.client = response.data
                })
                .catch(error => console.error(error));
        },
        getCards() {
            axios.get("/api/clients/current/cards")
                .then(response => {
                    this.cards = response.data.sort(this.order)
                    this.cardsCredit = this.cards.filter(card => card.type == "CREDIT").sort(this.order)
                    this.cardsDebit = this.cards.filter(card => card.type == "DEBIT").sort(this.order)
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
        date(cardDate){
            let currentDate = new Date();
            // let currentDate = new Date('2027-11-17');
            console.log(currentDate);
            let cardDate1 = new Date(cardDate)
            console.log(cardDate1);
            if(currentDate > cardDate1){
                return true
            }else{
                return false
            }
        },
        formatDate(date) {
            let month = date.slice(5, 7)
            let year = date.slice(2, 4)
            return month + "/" + year
        },
        disableCard(id) {
            Swal.fire({
                title: 'Are you sure you want to delete the card?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, delete card!'
            })
                .then((result) => {
                    if (result.isConfirmed) {
                        Swal.fire(
                            'Deleted!',
                            'Your card has been deleted.',
                            'success'
                        )
                        axios.patch("/api/clients/current/cards/" + id)
                            .then(() => this.getCards())
                            .catch(error => console.error(error))
                    }
                })
        },
        logout() {
            axios.post('/api/logout').then(() => window.location.href = '/index.html')
        }
    },

}).mount('#app')