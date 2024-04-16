document.addEventListener('click', function (e) {
    const $optionBtn = document.querySelectorAll(".optionBtn"); //모든 .optionBtn 요소를 선택
    const targetOptionBtn = e.target.closest(".optionBtn"); //클릭된 요소 또는 그 조상 중 .optionBtn을 찾음
    const targetViewTypeBtn = e.target.closest(".viewBtn"); //클릭된 요소 또는 그 조상 중 .viewBtn을 찾음
    const $viewBtn = document.querySelector('.viewBtn > button'); //.viewBtn 눌렀을때 active 걸릴 버튼

    if (targetOptionBtn) {
        $optionBtn.forEach((option) => {
            option.classList.remove("active"); //먼저 모든 .optionBtn에서 active 클래스 제거
        });
        targetOptionBtn.classList.add("active"); //클릭된 .optionBtn에만 active 클래스 추가
    } else if (targetViewTypeBtn) {
        $viewBtn.classList.toggle("active"); //클릭된 .viewBtn 바로 아래 버튼에 토글 active 클래스
        if (e.target.textContent === "블로그식 보기") {
            $blogList.classList.remove("snsList"); //블로그식 보기 클릭시 snsList 클래스 제거
        } else if (e.target.textContent === "SNS식 보기") {
            $blogList.classList.add("snsList"); //sns식 보기 클릭시 snsList 클래스 추가
        }
    } else {
        $optionBtn.forEach((option) => {
            option.classList.remove("active"); //모든 .optionBtn에서 active 클래스 제거
        });
        $viewBtn.classList.remove("active"); //.viewBtn 바로 아래 버튼 active 클래스 제거
    }
});