//리스트 엔드는 무한 스크롤할 마지막 위치에 있어야 합니다
const target = document.querySelector(`#listEnd`);
const options ={
    root: null,
    rootMargin: '0px 0px 0px 0px',
    threshold: 0.5
};
const observer = new IntersectionObserver((entries, observer) => {
    entries.forEach(entry => {
        if (entry.isIntersecting && !isLoading) {
            // 타겟 요소가 뷰포트에 들어왔을 때 호출되는 로직을 여기에 작성합니다.
            // 무한 스크롤을 구현하는 로직을 호출합니다.
            update();
            // 한 번 로드되었으므로 관찰을 중단합니다.
            //observer.unobserve(entry.target);
        }
    });
}, options);
// const target = document.querySelector(`main > section`);
observer.observe(target);