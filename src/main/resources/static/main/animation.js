const sign_in_btn = document.querySelector("#sign-in-btn");
const sign_up_btn = document.querySelector("#sign-up-btn");
const container = document.querySelector(".container1");
const reg = document.querySelector("#registerImage");
// var nuevaHojaDeEstilo = document.createElement("style");
// document.head.;
// nuevaHojaDeEstilo.textContent = "svg#freepik_stories-sign-up:not(.animated) .animable {opacity: 0;}svg#freepik_stories-sign-up.animated #freepik--background-simple--inject-62 {animation: 1.5s 1 forwards cubic-bezier(.36, -0.01, .5, 1.38) slideUp;animation-delay: 1s;}svg#freepik_stories-sign-up.animated #freepik--Checklists--inject-62 {animation: 1s 1 forwards cubic-bezier(.36, -0.01, .5, 1.38) slideDown;animation-delay: 1s;}svg#freepik_stories-sign-up.animated #freepik--Envelopes--inject-62 {animation: 1s 1 forwards cubic-bezier(.36, -0.01, .5, 1.38) slideUp;animation-delay: 1s;}svg#freepik_stories-sign-up.animated #freepik--Floor--inject-62 {animation: 1s 1 forwards cubic-bezier(.36, -0.01, .5, 1.38) slideDown;animation-delay: 1s;}svg#freepik_stories-sign-up.animated #freepik--Plants--inject-62 {animation: 1s 1 forwards cubic-bezier(.36, -0.01, .5, 1.38) slideUp;animation-delay: 1s;}svg#freepik_stories-sign-up.animated #freepik--Screen--inject-62 {animation: 1s 1 forwards cubic-bezier(.36, -0.01, .5, 1.38) slideDown;animation-delay: 1s;}svg#freepik_stories-sign-up.animated #freepik--Bricks--inject-62 {animation: 1s 1 forwards cubic-bezier(.36, -0.01, .5, 1.38) slideUp;animation-delay: 1s;}svg#freepik_stories-sign-up.animated #freepik--Information--inject-62 {    animation: 1s 1 forwards cubic-bezier(.36, -0.01, .5, 1.38) slideDown;    animation-delay: 1s;}svg#freepik_stories-sign-up.animated #freepik--Mail--inject-62 {    animation: 1s 1 forwards cubic-bezier(.36, -0.01, .5, 1.38) slideUp; animation-delay: 1s;}svg#freepik_stories-sign-up.animated #freepik--Character--inject-62 {    animation: 1s 1 forwards cubic-bezier(.36, -0.01, .5, 1.38) slideDown;    animation-delay: 1s;}svg#freepik_stories-sign-up.animated #freepik--speech-bubble--inject-62 {    animation: 1s 1 forwards cubic-bezier(.36, -0.01, .5, 1.38) slideUp;   animation-delay: 1s;}@keyframes slideUp {   0% {opacity: 0;transform: translateY(30px);  }  100% { opacity: 1; transform: inherit;}}@keyframes slideDown {0% {opacity: 0;transform: translateY(-30px);}    100% {        opacity: 1;        transform: translateY(0);    }}";
sign_up_btn.addEventListener("click", () => {
    container.classList.add("sign-up-mode");
    reg.classList.add("animate__animated", "animate__fadeInUp", "animate__delay-1s")

});

sign_in_btn.addEventListener("click", () => {
    container.classList.remove("sign-up-mode");
    reg.classList.remove("animate__animated", "animate__fadeInUp", "animate__delay-1s")
    reg.classList.add("animate__animated", "animate__backOutDown")

});
