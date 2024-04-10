window.onload = function () {

    if (document.getElementById("writeComments")) {
        /* 댓글 작성 */
        const $writeComments = document.getElementById("writeComments");
        const $comments = document.querySelector("#cmt");

        $writeComments.onclick = function () {
            const $postCode = '[[${comments.postCode}]]';
            const $cmt = $comments.value;

            if ($cmt === "" || $cmt.trim() === "") {
                alert('내용을 작성해주세요.')
                return;
            }
            fetch("/comments/write", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8"
                }, body: JSON.stringify({
                    cmt: $cmt,
                    postCode: $postCode
                })
            })
                .then(res => {
                    $cmt.value = '';
                    loadReply();
                })
                .catch(error => {
                    console.error("오류 발생", error);
                    alert("댓글 작성에 실패하였습니다. 다시 시도해주세요.")
                })
        };
    }

    function loadReply() {
        const $postCode = `[[${comments.postCode}]]`;

        fetch('/comments/loadComments?postCode=' + $postCode)
            .then(result => result.json())
            .then(data => makeCommentsTable(data))
            .catch(error => console.log(error))
    }

    function makeCommentsTable(commentsList) {
        const $list = document.querySelector('#commentList');
        $list.innerHTML = '';

        commentsList.forEach(comments => {
            const $li = document.createElement("li");
            const $hiddenInput = document.createElement("input");
            const $img = document.createElement("img");
            const $nicknameSpan = document.createElement("span");
            const $createdDateSpan = document.createElement("span");
            const $cmtContentSpan = document.createElement("span");
            const $replyButton = document.createElement("input");
            const $modifyButton = document.createElement("input");
            const $deleteButton = document.createElement("input");
            const $blockButton = document.createElement("input");
            const $reportButton = document.createElement("input");

            $hiddenInput.setAttribute("type", "hidden");
            $hiddenInput.setAttribute("class", "comment-code");
            $hiddenInput.setAttribute("value", comments.cmtCode);

            if (comments.profile != null) {
                $img.setAttribute("src", comments.profile.profileImgPath);
            }

            $nicknameSpan.textContent = comments.profile.profileNickname;
            $createdDateSpan.textContent = comments.cmtWriDate;
            $cmtContentSpan.setAttribute("class", "cmtContent");
            $cmtContentSpan.textContent = comments.cmtText;

            $replyButton.setAttribute("type", "button");
            $replyButton.setAttribute("value", "답글");
            $modifyButton.setAttribute("type", "button");
            $modifyButton.setAttribute("value", "수정");
            $modifyButton.setAttribute("class", "modifyComments");
            $deleteButton.setAttribute("type", "button");
            $deleteButton.setAttribute("value", "삭제");
            $deleteButton.setAttribute("class", "deleteComments");
            $blockButton.setAttribute("type", "button");
            $blockButton.setAttribute("value", "차단");
            $reportButton.setAttribute("type", "button");
            $reportButton.setAttribute("value", "신고");

            if (comments.memberCode == `[[${#authentication.principal.memberCode}]]`) {
                $li.append($hiddenInput, $img, $nicknameSpan, $createdDateSpan, $cmtContentSpan, $replyButton, $modifyButton, $deleteButton);
            } else {
                $li.append($hiddenInput, $img, $nicknameSpan, $createdDateSpan, $cmtContentSpan, $replyButton, $blockButton, $reportButton);
            }
            $list.appendChild($li);

        })

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








