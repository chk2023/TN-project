document.addEventListener('click', function (e) {
    const $optionBtn = document.querySelectorAll(".optionBtn"); //모든 .optionBtn 요소를 선택
    const targetOptionBtn = e.target.closest(".optionBtn"); //클릭된 요소 또는 그 조상 중 .optionBtn을 찾음
    const targetViewBtn = e.target.closest(".viewBtn");
    if (targetOptionBtn) {
        $optionBtn.forEach((option) => {
            option.classList.remove("active"); //먼저 모든 .optionBtn에서 active 클래스 제거
        });
        targetOptionBtn.classList.add("active"); //클릭된 .optionBtn에만 active 클래스 추가
    }else if (targetViewBtn) {
        $viewBtn.forEach((option) => {
            option.classList.remove("active");
        });
        $viewBtn.classList.add("active");
    } else {
        $optionBtn.forEach((option) => {
            option.classList.remove("active"); //모든 .optionBtn에서 active 클래스 제거
        });
        if (targetViewBtn) {
            $viewBtn.classList.remove("active"); //.viewBtn 바로 아래 버튼 active 클래스 제거
        }
    }
});