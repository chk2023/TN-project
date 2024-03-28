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

    if(document.getElementById("emailCheck")) {

        const $duplication = document.getElementById("emailCheck");
        const $signupButton = document.getElementById("signupButton");
        const $duplicationMessage = document.getElementById("duplicationMessage");
        const $optionalId = document.getElementById("optionalId");

        $duplication.onclick = function() {
            let memberId = document.getElementById("memberId").value.trim();
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

            // 도메인과 아이디 조합해서 fetch로 요청 보내기
            memberId += domain;

            fetch("/member/idDupCheck", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                },
                body: JSON.stringify({memberId: memberId})
            })
                .then(result => result.text())
                .then(result => {
                    if (result === '중복 된 아이디가 존재합니다.') {
                        $duplicationMessage.textContent = result;
                    } else {
                        $duplicationMessage.textContent = result;
                    }
                })
                .catch((error) => error.text().then((res) => alert(res)));

            sendNumber(memberId);
        }
    }
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

    // 폼 submit 이벤트에 대한 리스너 등록
    const $signupForm = document.querySelector("form");
    $signupForm.addEventListener("submit", function(event) {
        if (!checkPasswordMatch()) {
            event.preventDefault(); // submit 중지
        }
    });

    /* 이메일 인증 코드 전송 */
    function sendNumber(memberId){
        // const ver_code = document.getElementById("ver_code");
        const confirm = document.getElementById("confirm");
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

    let generatedCode;

    if(document.getElementById("confirmBtn")) {
        const $confirmBtn = document.getElementById("confirmBtn");
        const $signupButton = document.getElementById("signupButton");

        $confirmBtn.onclick = function() {
            const userInput = document.getElementById("code").value.trim();
            console.log("userinput : " +userInput);
            console.log("generatedCode :" +generatedCode);

            if (userInput === generatedCode) {
                alert("인증되었습니다.");
                $signupButton.disabled = false;
            } else {
                alert("인증번호가 일치하지 않습니다.");
                $signupButton.disabled = true;
            }
        }
    }
}