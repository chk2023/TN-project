window.onload = function () {

    if (document.getElementById("writeComments")) {
        /* 댓글 작성 */
        const $writeComments = document.getElementById("writeComments");
        const $cmt = document.querySelector("#cmtText");

        $writeComments.onclick = function () {
            if (!$cmt.value.trim()) {
                alert('내용을 작성해주세요.')
                return;
            }

            const postCode = document.querySelector(".postCode").value;
            const cmtText = $cmt.value;

            console.log(postCode);
            console.log(cmtText);

            fetch("/comments/write", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8"
                }, body: JSON.stringify({postCode, cmtText})
            })
                .then(res => {
                    $cmt.value = '';
                    loadReply(postCode);
                })
                .catch(error => console.log(error));
        };
    }

    function loadReply(postCode) {
        fetch('/comments/load?postCode=' + postCode)
            .then(result => result.json())
            .then(data => makeCommentsTable(data))
            .catch(error => console.log(error))
    }

    function makeCommentsTable(commentsList) {
        const $list = document.querySelector('#commentsList');
        const $memberCode = document.querySelector('#memberCode').value;
        $list.innerHTML = '';

        commentsList.forEach(comments => {
            const $li = document.createElement("li");
            const $hiddenInput = document.createElement("input");
            const $img = document.createElement("img");
            const $nicknameSpan = document.createElement("span");
            const $createdDateSpan = document.createElement("span");
            const $cmtContentSpan = document.createElement("span");
            // const $replyButton = document.createElement("input");
            const $modifyButton = document.createElement("input");
            const $deleteButton = document.createElement("input");
            const $blockButton = document.createElement("input");
            // const $reportButton = document.createElement("input");

            $hiddenInput.setAttribute("type", "hidden");
            $hiddenInput.setAttribute("class", "comment-code");
            $hiddenInput.setAttribute("value", comments.cmtCode);

            if (comments.profile != null) {
                $img.setAttribute("src", comments.profile.profileImgPath);
            }

            $nicknameSpan.textContent = comments.profile.profileNickname;
            const date = new Date(comments.cmtWriDate);
            const formattedDate = `${date.getMonth() + 1}월 ${date.getDate()}일`;
            $createdDateSpan.textContent = formattedDate;
            $cmtContentSpan.setAttribute("class", "cmtContent");
            $cmtContentSpan.textContent = comments.cmtText;

            // $replyButton.setAttribute("type", "button");
            // $replyButton.setAttribute("value", "답글");
            $modifyButton.setAttribute("type", "button");
            $modifyButton.setAttribute("value", "수정");
            $modifyButton.setAttribute("class", "modifyComments");
            $deleteButton.setAttribute("type", "button");
            $deleteButton.setAttribute("value", "삭제");
            $deleteButton.classList.add("deleteComments");
            $deleteButton.setAttribute("id", "blackBtn");
            $blockButton.setAttribute("type", "button");
            $blockButton.setAttribute("value", "차단");
            $blockButton.classList.add("blockBtn");
            // $reportButton.setAttribute("type", "button");
            // $reportButton.setAttribute("value", "신고");

            const $contentGroupDiv = document.createElement("div");
            $contentGroupDiv.classList.add("content-group");
            const $profileDiv = document.createElement("div");
            $profileDiv.appendChild($img);
            const $nicknameDiv = document.createElement("div");
            $nicknameDiv.appendChild($nicknameSpan);
            $contentGroupDiv.appendChild($profileDiv);
            $contentGroupDiv.appendChild($nicknameDiv);
            $contentGroupDiv.appendChild($createdDateSpan);

            const $buttonContainerDiv = document.createElement("div");
            $buttonContainerDiv.classList.add("button-container");

            if (comments.memberCode == $memberCode) {
                $buttonContainerDiv.appendChild($modifyButton);
                $buttonContainerDiv.appendChild($deleteButton);
            } else {
                $buttonContainerDiv.appendChild($blockButton);
            }

            $li.appendChild($hiddenInput);
            $li.appendChild($contentGroupDiv);
            $li.appendChild($cmtContentSpan);
            $li.appendChild($buttonContainerDiv);
            $list.appendChild($li);
        })
    }

    document.addEventListener('click', e => {
        const button = e.target;

        if (button.classList.contains('modifyComments')) {
            /* 댓글 수정 */
            const $commentElement = button.closest('li')
            const $cmtCode = $commentElement.querySelector('.comment-code').value;
            const $cmtText = prompt("댓글을 수정하세요: ", "");

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

        }

        /* 댓글 삭제 */

        if (button.classList.contains("deleteComments")) {
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
        }
    });
}







