window.onload = function () {
    if (document.getElementById("regist")) {
        const $regist = document.getElementById("regist");
        $regist.onclick = function () {
            location.href = "/member/regist";
        }
    }


    if (document.getElementById("logout")) {
        const $logout = document.getElementById("logout");
        $logout.onclick = function () {
            location.href = "/member/logout";
        }
    }

    if (document.getElementById("updateMember")) {
        const $updateMember = document.getElementById("updateMember");

        $updateMember.onclick = function () {
            location.href = "/member/update";
        }
    }

    if (document.getElementById("deleteMember")) {
        const $deleteMember = document.getElementById("deleteMember");

        $deleteMember.onclick = function () {
            const $confirmDelete = confirm("정말로 탈퇴하시겠습니까?");

            if ($confirmDelete) {
                location.href = "/member/delete"
                alert("탈퇴되었습니다.");
            }
        }
    }

    /* 회원가입 이메일 중복확인 및 비밀번호 찾기 이메일 체크 */
    if(document.getElementById("emailCheck")) {

        const $duplication = document.getElementById("emailCheck");
        const $resultMessage = document.getElementById("resultMessage");
        const $optionalId = document.getElementById("optionalId");
        const $memberId = document.getElementById("memberId");
        const $verCode = document.getElementById("verCode");
        const $currentPageUrl = window.location.href;

        $duplication.onclick = function() {
            memberId = $memberId.value.trim();
            let domain = '';

            // 입력된 아이디가 없는 경우
            if (memberId === "") {
                alert("아이디를 입력해주세요.");
                return;
            }

            // 직접입력 했을 때 이메일 형식인지 검증
            if ($optionalId.value === "default" && !isValidEmail(memberId)) {
                alert("이메일 형식으로 작성해주세요.");
                return;
            }

            // 도메인 선택 시 아이디만 입력하는지 검증
            if ($optionalId.value !== "default" && !isValidId(memberId)) {
                alert("아이디를 확인해주세요.");
                return;
            }

            // 옵션 박스에서 선택한 도메인 확인
            if ($optionalId.value !== "default") {
                domain = $optionalId.value;
            }

            // 현재 페이지에 따라 다른 URL 선택
            let url = "/member/idDupCheck";
            if ($currentPageUrl.includes("/member/findPwd")) {
                url = "/member/idCheck"
            }

            // 도메인과 아이디 조합해서 fetch로 요청 보내기
            memberId += domain;

            fetch(url, {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                },
                body: JSON.stringify({memberId: memberId})
            })
                .then(result => result.text())
                .then(result => {
                    if ($currentPageUrl.includes("/member/regist")) {
                        if (result === '중복 된 아이디가 존재합니다.') {
                            $resultMessage.textContent = result;
                        } else {
                            $resultMessage.textContent = result;
                        }
                    } else if ($currentPageUrl.includes("/member/findPwd")){
                        console.log(result);
                        if (result === '인증 코드 전송을 눌러.') {
                            $resultMessage.textContent = result;
                            $verCode.disabled = false;
                        } else {
                            $resultMessage.textContent = result;
                        }
                    }
                })
                .catch((error) => error.text().then((res) => alert(res)));
        }
    }
    let memberId;

    // 이메일 형식인지 검증하는 함수
    function isValidEmail(email) {
        const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        return emailPattern.test(email);
    }

    // 도메인 선택 시 아이디를 정확히 입력했는지 검증
    function isValidId(Id) {
        const idPattern = /^[a-zA-Z0-9._%+-]+$/;
        return idPattern.test(Id);
    }

    // 비밀번호와 2차 비밀번호가 같은지 확인하는 함수
    function checkPasswordMatch() {
        const password = document.getElementById("memberPwd").value.trim();
        const confirmPassword = document.getElementById("memberPwd2").value.trim();

        if (password !== confirmPassword) {
            alert("비밀번호와 2차 비밀번호가 일치하지 않습니다.");
            return false;
        }

        return true;
    }

    const $registForm = document.querySelector("#registForm");
    if ($registForm) {
        $registForm.addEventListener("submit", function(event) {
            if (!checkPasswordMatch()) {
                event.preventDefault(); // submit 중지
            }
        });
    }

    const $pwdResetForm = document.querySelector("#pwdResetForm");
    if ($pwdResetForm) {
        $pwdResetForm.addEventListener("submit", function(event) {
            if (!checkPasswordMatch()) {
                event.preventDefault(); // submit 중지
            }
        });
    }

    /* 이메일 인증 코드 전송 */
    if (document.getElementById("verCode")) {

        const $verifyEmail = document.getElementById("verCode");
        const $optionalId = document.getElementById("optionalId");

        $verifyEmail.onclick = function () {
            let memberId = document.getElementById("memberId").value.trim();
            let domain = '';

            // 옵션 박스에서 선택한 도메인 확인
            if ($optionalId.value !== "default") {
                domain = $optionalId.value;
            }

            // 도메인과 아이디 조합해서 fetch로 요청 보내기
            memberId += domain;

            fetch("/mail", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                },
                body: JSON.stringify({memberId: memberId})
            })
                .then(res => res.text())
                .then(result =>{
                    console.log(result);
                    alert("인증번호 발송");
                    generatedCode = result;
                })
                .catch((error) => error.text().then((res) => alert(res)));
        }
    }

    let generatedCode;

    if(document.getElementById("confirmBtn")) {
        const $confirmBtn = document.getElementById("confirmBtn");
        const $processButton = document.getElementById("processButton");

        $confirmBtn.onclick = function() {
            const userInput = document.getElementById("code").value.trim();
            console.log("userinput : " +userInput);
            console.log("generatedCode :" +generatedCode);

            if (userInput === generatedCode) {
                alert("인증되었습니다.");
                $processButton.disabled = false;
            } else {
                alert("인증번호가 일치하지 않습니다.");
                $processButton.disabled = true;
            }
        }
    }


    /* 회원 정보 수정 비동기 처리 */
    if (document.getElementById("updateMemberBtn")) {
        const $updateMemberbtn = document.getElementById("updateMemberBtn");

        $updateMemberbtn.onclick = function () {
            const $updateMemberButton = document.getElementById("updateMemberBtn");
            const $submitUpdateMemberButton = document.getElementById("submitUpdateMember");
            const $genderParagraph = document.querySelector("#genderParagraph");
            const $ageParagraph = document.querySelector("#ageParagraph");
            const $birthParagraph = document.querySelector("#birthParagraph");
            const $genderInput = document.getElementById("genderInput");
            const $ageInput = document.getElementById("ageInput");
            const $birthInput = document.getElementById("birthInput");

            $genderParagraph.classList.add("hidden");
            $ageParagraph.classList.add("hidden");
            $birthParagraph.classList.add("hidden");
            $genderInput.classList.remove("hidden");
            $ageInput.classList.remove("hidden");
            $birthInput.classList.remove("hidden");

            $genderInput.value = $genderParagraph.textContent;
            $ageInput.value = $ageParagraph.textContent;
            $birthInput.value = $birthParagraph.textContent;

            if ($genderParagraph.textContent === '남자') {
                $genderInput.selectedIndex = 0;
            } else {
                $genderInput.selectedIndex = 1;
            }

            // 수정완료 버튼 보이기
            $submitUpdateMemberButton.classList.remove("hidden");
            $updateMemberButton.classList.add("hidden");
        }
    }

    if (document.getElementById("processButton")) {
        const $processButton = document.getElementById("processButton");
        const $verification = document.getElementById("verification");
        const $pwdReset = document.getElementById("pwdReset");

        $processButton.onclick = function (){
            $verification.style.display = "none";
            $pwdReset.style.display = "block";
        }
    }

    // /* 비밀번호 재설정 */
    // if (document.getElementById("resetPwdBtn")) {
    //     const $resetPwdBtn = document.getElementById("resetPwdBtn");
    //     const $currentPwd = document.getElementById("currentPwd");
    //     const $newPwd = document.getElementById("newPwd");
    //
    //     $resetPwdBtn.onclick = function (){
    //         let currentPwd = $currentPwd.value;
    //         let newPwd = $newPwd.value;
    //
    //         if (currentPwd === "" || newPwd === "") {
    //             alert("현재 비밀번호와 새 비밀번호를 입력해주세요.");
    //             return;
    //         }
    //
    //         if (currentPwd === newPwd) {
    //             alert("현재 비밀번호와 새 비밀번호가 같습니다.")
    //             return;
    //         }
    //
    //         fetch("/member/findPwd", {
    //             method: "POST",
    //             headers: {
    //                 'Content-Type': 'application/json;charset=UTF-8'
    //             },
    //             body: JSON.stringify({
    //                 memberId: memberId,
    //                 currentPwd: currentPwd,
    //                 newPwd: newPwd
    //             })
    //         })
    //             .then(result => result.text())
    //             .then(result => {
    //                 if (result === '중복 된 아이디가 존재합니다.') {
    //                     $duplicationMessage.textContent = result;
    //                 } else {
    //                     $duplicationMessage.textContent = result;
    //                 }
    //             })
    //             .catch((error) => error.text().then((res) => alert(res)));
    //     }
    // }


}

