const { createApp } = Vue

createApp({
    data() {
        return {
            client: {},
            cards: [],
            accounts: [],
            cardNumber: "",
            cardCvv: "",
            amount: null,
            description: "",
        }
    },
    created() {
        this.loadDataClient();

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
        deleteCaracter() {
            let text = this.cardNumber
            let text2 = text.substring(text.lenght, text.length - 1)
            this.cardNumber = text2
        },
        turnCard() {
            let card = document.getElementsByClassName("card__container");
            card[0].classList.toggle("turnCard");
        },
        formatDate(date) {
            let month = date.slice(5, 7)
            let year = date.slice(2, 4)
            return month + "/" + year
        },
        showCardNumber() {
            if (this.cardNumber.length != 0) {
                return this.cardNumber
            } else {
                return "####-####-####-####"
            }
        },
        cardNumberFormat() {
            let flag1 = true;
            if (this.cardNumber.length == 4 && flag1) {
                let first = this.cardNumber + "-"
                this.cardNumber = first
                flag1 = true;
            }
            else if (this.cardNumber.length == 9 && flag1) {
                let first = this.cardNumber + "-"
                this.cardNumber = first
                flag1 = true;
            }
            else if (this.cardNumber.length == 14 && flag1) {
                let first = this.cardNumber + "-"
                this.cardNumber = first
                flag1 = false;
            }
        },
        sendPayment() {
            Swal.fire({
                title: 'Are you sure you want to perform this operation?',
                showConfirmButton: true,
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes',
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.post("/api/cards/pay", {
                        "cardNumber": this.cardNumber,
                        "cardCvv": this.cardCvv,
                        "amount": this.amount,
                        "description": this.description
                    }).then(() => window.location.href = "/web/accounts.html")
                        .catch(error => {
                            console.error(error);
                            if(error.response.status == 401){
                                Swal.fire({
                                    icon: 'error',
                                    title: error.response.data,
                                })
                            }else{
                                    Swal.fire({
                                        icon: 'error',
                                        title: "Please fill in the data correctly",
                                    })
                            }
                        })
                }
            })
        }

        },

    }).mount('#app')