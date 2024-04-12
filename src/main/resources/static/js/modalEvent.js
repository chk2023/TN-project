/* 댓글 모달을 불러오는 이벤트 */
function loadCommentModal() {
    const $modal = document.getElementById('commentModal');
    if ($modal.style.display === "block") {
        return;
    }
    showModal();
}

function showModal() {
    const $modal = document.getElementById('commentModal');
    $modal.style.display = "block";

    // fetch("/comments/load")
    //     .then(response => {
    //         if (!response.ok) {
    //             throw new Error("댓글을 불러오는 데 실패했습니다.");
    //         }
    //         return response.json(); // JSON 형식으로 데이터를 파싱하여 반환합니다.
    //     })
    //     .then(comments => {
    //         // 받은 댓글 데이터(comments)를 modal에 표시하는 코드를 작성합니다.
    //         console.log(comments); // 받은 댓글 데이터를 콘솔에 출력합니다.
    //     })
    //     .catch(error => {
    //         console.error("오류 발생", error);
    //         alert("댓글을 불러오는 데 실패했습니다.");
    //     });
}

function hideModal() {
    const $modal = document.getElementById('commentModal');
    $modal.style.display = "none";
}
