window.onload = function () {

    /* 댓글 작성 */
    if (document.getElementById("writeComments")) {
        const $writeComments = document.getElementById("writeComments");

        $writeComments.onclick = function () {
            const $cmt = document.querySelector("#cmt").value;

            if ($cmt != null && $cmt.trim() !== "") {
                fetch("/comments/write", {
                    method: "POST", headers: {
                        "Content-Type": "application/json"
                    }, body: JSON.stringify({
                        cmt: $cmt
                    })
                })
                    .then(res => {
                        if (!res.ok) {
                            throw new Error("댓글 작성에 실패하였습니다.");
                        }
                        return res.json();
                    })
                    .then(newComment => {
                        // 새로운 댓글을 생성하여 HTML로 추가
                        const $newComment = document.createElement("li");
                        $newComment.innerHTML = `
                    <input type="hidden" class="comment-code" value="${escapeHTML(newComment.cmtCode)}" />
                    <img src="${newComment.profile != null ? escapeHTML(newComment.profile.profileImgPath) : ''}" />
                    <span>${escapeHTML(newComment.profile != null ? newComment.profile.profileNickname : '')}</span>
                    <span>${escapeHTML(newComment.cmtWriDate)}</span>
                    <br>
                    <span class="cmtContent">${escapeHTML(newComment.cmtText)}</span>
                    <input type="button" value="답글">
                    <input ${newComment.memberCode == authentication.principal.memberCode ? '' : 'style="display: none;"'} type="button" class="modifyComments" value="수정">
                    <input ${newComment.memberCode == authentication.principal.memberCode ? '' : 'style="display: none;"'} type="button" class="deleteComments" value="삭제">
                    <input ${newComment.memberCode != authentication.principal.memberCode ? '' : 'style="display: none;"'} type="button" value="차단">
                    <input ${newComment.memberCode != authentication.principal.memberCode ? '' : 'style="display: none;"'} type="button" value="신고">
                `;
                        document.getElementById("commentList").appendChild($newComment);
                        console.log("새로운 댓글이 작성되었습니다.", newComment);
                    })
                    .catch(error => {
                        console.error("오류 발생", error);
                        alert("댓글 작성에 실패하였습니다. 다시 시도해주세요.")
                    })
            }
        };
    }

    /* 댓글 수정, 삭제 */
    const modifyCommentsButtons = document.querySelectorAll(".modifyComments");
    const deleteCommentsButtons = document.querySelectorAll(".deleteComments");

    modifyCommentsButtons.forEach(function (button) {
        button.addEventListener("click", function () {
            // 수정 버튼이 속한 댓글 요소 찾기
            const $commentElement = button.closest('li')
            const $cmtCode = $commentElement.querySelector('.comment-code').value;
            const $cmtText = prompt("댓글을 수정하세요: ", "");
            console.log($cmtCode);

            if ($cmtText != null && $cmtText.trim() !== "") {
                fetch("/comments/update", {
                    method: "POST", headers: {
                        "Content-Type": "application/json"
                    }, body: JSON.stringify({
                        cmtCode: $cmtCode, cmtText: $cmtText
                    })
                })
                    .then(res => {
                        if (!res.ok) {
                            throw new Error("댓글 수정에 실패하였습니다.");
                        }
                        $commentElement.querySelector('.cmtContent').innerText = $cmtText;
                        console.log("댓글이 성공적으로 수정되었습니다.");
                    })
                    .catch(error => {
                        console.error("오류 발생", error);
                        alert("댓글 수정에 실패하였습니다. 다시 시도해주세요.")
                    })
            }
        });
    });

    deleteCommentsButtons.forEach(function (button) {
        button.addEventListener("click", function () {
            const $commentElement = button.closest('li')
            const $cmtCode = $commentElement.querySelector('.comment-code').value;

            fetch("/comments/delete", {
                method: "POST", headers: {
                    "Content-Type": "application/json"
                }, body: JSON.stringify({
                    cmtCode: $cmtCode,
                })
            })
                .then(res => {
                    if (!res.ok) {
                        throw new Error("댓글 삭제에 실패하였습니다.");
                    }
                    $commentElement.remove();
                    console.log("댓글이 성공적으로 삭제되었습니다.");
                })
                .catch(error => {
                    console.error("오류 발생", error);
                    alert("댓글 삭제에 실패하였습니다. 다시 시도해주세요.")
                })
        });
    });
}


// function sendComments() {
//     const $cmt = document.querySelector("#cmt").value;
//
//     if ($cmt != null && $cmt.trim() !== "") {
//         fetch("/comments/write", {
//             method: "POST",
//             headers: {
//                 "Content-Type": "application/json"
//             },
//             body: JSON.stringify({
//                 cmt: $cmt
//             })
//         })
//             .then(res => {
//                 if (!res.ok) {
//                     throw new Error("댓글 작성에 실패하였습니다.");
//                 }
//                 // $commentElement.querySelector('.cmtContent').innerText = $cmtText;
//                 console.log("댓글이 성공적으로 작성되었습니다.");
//             })
//             .catch(error => {
//                 console.error("오류 발생", error);
//                 alert("댓글 작성에 실패하였습니다. 다시 시도해주세요.")
//             })
//     }
// }