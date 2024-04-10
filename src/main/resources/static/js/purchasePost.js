function handlePurchaseBtn() {
    document.querySelectorAll(".purchaseBtn").forEach(btn => {
        btn.addEventListener('click', (e) => {

            console.log("버튼 동작 중");

            var postCode = parseInt(e.currentTarget.getAttribute('data-postCode'));
            purchasePaidPost(postCode);
            console.log("postCode:",postCode);

        })
    });
}

// function handlePurchaseBtn() {
//     document.querySelectorAll(".purchaseBtn").forEach(btn => {
//         btn.addEventListener('click', function clickPurchaseBtn(e) {
//
//             console.log("버튼 동작 중");
//
//             var postCode = parseInt(e.currentTarget.getAttribute('data-postCode'));
//             purchasePaidPost(postCode);
//             console.log("postCode:",postCode);
//
//         })
//     });
// }


function purchasePaidPost(postCode) {

    fetch('/getPaidPostInfo', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            postCode: postCode
        }),
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('서버 응답이 실패했습니다.');
            }
        })
        .then(data => {
            var postPrice = data.post_price;
        })
        .catch(error => {
            console.log(error);
            alert('구매에 실패했습니다.');
        });
}


window.onload = function () {
    document.querySelectorAll(".purchaseBtn").forEach(btn => {
        var postPrice = parseInt(btn.getAttribute('data-postPrice'));
        if (postPrice <= 0) {
            btn.style.display = 'none';
        }
    });
};
