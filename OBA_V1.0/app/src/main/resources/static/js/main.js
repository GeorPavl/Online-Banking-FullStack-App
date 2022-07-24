
// Get Transaction Type:
const transactType = document.querySelector("#transact-type");

// Get Transaction Forms:
const paymentCard = document.querySelector(".payment-card");
const transferCard = document.querySelector(".transfer-card");
const depositCard = document.querySelector(".deposit-card");
const withdrawCard = document.querySelector(".withdraw-card");

// Check For Transaction Type Evenet Listener:
transactType.addEventListener("change",() =>{

    // Check For Transaction Type and Display Form:
    switch(transactType.value){
        case "payment":
            paymentCard.style.display = "block";
            transferCard.style.display = "none";
            depositCard.style.display = "none";
            withdrawCard.style.display = "none";
        break;

        case "transfer":
            paymentCard.style.display = "none";
            transferCard.style.display = "block";
            depositCard.style.display = "none";
            withdrawCard.style.display = "none";
        break;

        case "deposit":
            paymentCard.style.display = "none";
            transferCard.style.display = "none";
            depositCard.style.display = "block";
            withdrawCard.style.display = "none";
        break;

        case "withdraw":
            paymentCard.style.display = "none";
            transferCard.style.display = "none";
            depositCard.style.display = "none";
            withdrawCard.style.display = "block";
        break;

    }
    // End Of Check For Transaction Type and Display Form.

});
// End Of Check For Transaction Type Evenet Listener.