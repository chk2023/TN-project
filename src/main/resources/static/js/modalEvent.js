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
}

function hideModal() {
    const $modal = document.getElementById('commentModal');
    $modal.style.display = "none";
}
