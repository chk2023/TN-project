function like() {

console.log('likeBtns', document.querySelectorAll(".likeBtn"));

document.querySelectorAll(".likeBtn").forEach(btn => btn.addEventListener('click', (e) => {

    var likeBtn = e.currentTarget;
    fetch('/post/like', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            postCode: likeBtn.dataset.postCode,
            isPrivate: false,
        }),
    })
        .then(response => {
            /* 추가 */
            if (response.ok) {
                return response.json(); // JSON 데이터로 파싱하여 반환
            } else {
                throw new Error('서버 응답이 실패했습니다.1');
            }
        })
        .then(result => {
            console.log('result', result); // 서버 응답 확인
            // 좋아요 아이콘 변경
            // var likeBtn = document.querySelector('[data-post-code=' + postCode +']');

            //var likeImg = likeBtn.querySelector("img");

            var handleLikeCount = likeBtn.querySelector(".likeCount");


            if (/*likeImg &&*/ handleLikeCount) {
                if (result) {
                    //likeImg.src = "/images/icon_like_active.png";
                    likeBtn.classList.add("active");
                    handleLikeCount.textContent = parseInt(handleLikeCount.textContent) + 1;
                } else {
                    //likeImg.src = "/images/icon_like.png";
                    likeBtn.classList.remove("active");
                    handleLikeCount.textContent = parseInt(handleLikeCount.textContent) - 1;
                }

            } else {
                console.error("이미지를 찾을 수 없습니다.");
            }


        })
        .catch((err) => {
            console.log(err);
        });
}));
}