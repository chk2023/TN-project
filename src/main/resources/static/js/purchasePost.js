function handlePurchaseBtn() {
    document.querySelectorAll(".purchaseBtn").forEach(btn => {
        btn.addEventListener('click', (e) => {

            console.log("버튼 동작 중", e.currentTarget.getAttribute('data-post-code'));

            var postCode = parseInt(e.currentTarget.getAttribute('data-post-code'));
            purchasePaidPost(postCode);
            console.log("postCode:",postCode);

        })
    });
}

function handleClickPost() {
    document.querySelectorAll(".clickPost").forEach(btn => {
        btn.addEventListener('click', (e) => {

            // var postCode = parseInt(e.currentTarget.getAttribute('data-post-code'));
            // console.log("postCode:",postCode);
            // clickPost(postCode);

            var postCodeAttr = e.currentTarget.getAttribute('data-post-code');
            console.log(postCodeAttr);
            if (!isNaN(postCodeAttr)) {
                console.log("글 코드 확인 :", postCodeAttr); // postCode 출력을 이동
                clickPost(postCodeAttr);
            } else {
                alert("유효하지 않은 postCode입니다.");
            }
        })
    })
}

            function clickPost(postCode) {
            if (postCode) {
                fetch('/post/detail', { // postCode를 body에 포함하여 요청
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ postCode: postCode })
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('서버 응답이 실패했습니다.');
                        }
                    })
                    .catch(error => {
                        console.error('오류 발생:', error);
                    });
            } else {
                alert("글의 postCode가 없습니다.");
            }
        }


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
            console.log(postCode);
            if (response.ok) {
                console.log("동작 확인");
                return response.json();
            } else {
                throw new Error('서버 응답이 실패했습니다.');
            }
        })
        .then(data => {
            alert("구매 성공");
            console.log(data);
            // 받은 데이터를 처리
        })
        .catch(error => {
            console.log(error);
            alert('구매에 실패했습니다.');
        });
}


window.onload = function () {
    handlePurchaseBtn();
    document.querySelectorAll(".purchaseBtn").forEach(btn => {
        var postPrice = parseInt(btn.getAttribute('data-postPrice'));
        if (postPrice <= 0) {
            btn.style.display = 'none';
        }
    });
};
