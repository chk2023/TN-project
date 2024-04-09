
document.querySelectorAll(".purchaseBtn").forEach(btn => btn.addEventListener('click', (e) => {

    var postCode = e.currentTarget.getAttribute('data-postCode');
    purchasePaidPost(postCode);
}));

function purchasePaidPost(postCode) {

    fetch('/getPaidPostInfo', {
        method: 'GET',
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
            console.error(error);
            alert('구매해 실패했습니다.');
        });

    // $.ajax({
    //     url: '/getPaidPostInfo',
    //     method: "GET",
    //     data: { postCode: postCode },
    //     success: function(response) {
    //         var postPrice = response.post_price;
    //     },
    //     error: function(xhr, status, error) {
    //         alert("구매에 실패하였습니다. 에러 내용: " + error);
    //     }
    // })
}