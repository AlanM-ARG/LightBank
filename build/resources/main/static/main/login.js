const { createApp } = Vue

createApp({
    data() {
        return {
            emailLOG: "",
            passwordLOG: "",
            firstName: "",
            lastName: "",
            emailREG: "",
            passwordREG: "",
            error: "",
        }
    },
    created() {
        
    },
    methods: {
        login() {
            axios.post('/api/login', "email=" + this.emailLOG + "&password=" + this.passwordLOG)
                .then(() => window.location.href = 'web/accounts.html')
                .catch((error) => {
                    this.error = error.response.data
                    console.log(error);
                    if (this.error.status == 401) {
                        if(this.emailLOG == ""){
                            Swal.fire({
                                icon: 'error',
                                title: "The email is missing",
                            })
                        }else if(this.passwordLOG == ""){
                            Swal.fire({
                                icon: 'error',
                                title: "The password is missing",
                            })
                        }else {
                            Swal.fire({
                                icon: 'error',
                                title: 'Invalid email or password',
                            })
                        }
                    }
                })
        },
        register() {
            axios.post('/api/clients', "firstName=" + this.firstName + "&lastName=" + this.lastName + "&email=" + this.emailREG + "&password=" + this.passwordREG).then(() => {
                this.emailLOG = this.emailREG
                this.passwordLOG = this.passwordREG
                this.login()
            }).catch((error) => {
                console.log(error);
                this.error = error.response.data
                    Swal.fire({
                        icon: 'error',
                        title: `${this.error}`,
                    })
            })
        },
    },

}).mount('#app')