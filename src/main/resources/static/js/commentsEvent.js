window.onload = function () {
    const modifyCommentsButtons = document.querySelectorAll(".modifyComments");

    modifyCommentsButtons.forEach(function (button) {
        button.addEventListener("click", function () {
            const $cmtCode = document.querySelector('.comment-code').value;
            const $cmtText = prompt("댓글을 수정하세요: ", "");
            console.log($cmtCode);

            if ($cmtText != null && $cmtText.trim() !== "") {
                fetch("/comments/update", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        cmtCode: $cmtCode,
                        cmtText: $cmtText
                    })
                })
                .then(res => {
                    if (!res.ok) {
                        throw new Error("댓글 수정에 실패하였습니다.");
                    }
                    document.querySelector('.cmtContent').innerText = $cmtText;
                    console.log("댓글이 성공적으로 수정되었습니다.");
                })
                .catch(error => {
                    console.error("오류 발생", error);
                    alert("댓글 수정에 실패하였습니다. 다시 시도해주세요.")
                })
            }
        });
    });
}