const $blogList = document.querySelector(".blogList");
update(); //최초 1회 실행
function update() {
    fetch(`/timeline/updateList?index=${index}&range=${range}&contentsType=${contentsType}`)
        .then(res => res.json())
        .then(data => {
            if (!data.length) {
                alert("더이상 보여줄 글이 없습니다.");
                return;
            }
            // 템플릿 소스 설정
            var source = '';
            if (viewType == 'blog') {
                source = document.querySelector("#blogTemplate").innerHTML;
            } else {
                // 다른 viewType에 대한 템플릿 소스 설정
            }

            var fragment = document.createDocumentFragment();
            var template = Handlebars.compile(source);

            data.forEach(item => {
                item.cmtCount = formatCount(item.cmtCount);
                item.likeCount = formatCount(item.likeCount);
                item.postWriDate = formatWriDate(item.postWriDate);
            });
            var html = document.createElement("li");
            html.innerHTML = template(data, {data: {handlebars: Handlebars.create()}});
            fragment.appendChild(html); // DocumentFragment에 요소 추가
            // DocumentFragment를 한 번에 추가하여 DOM 조작 효율성 향상
            $blogList.appendChild(fragment);

            index = index + range;
        }).catch(error => console.error('Error fetching profile data:', error));
}

function formatWriDate(postWriDate) {
    let time = new Date(postWriDate);
    return time.toLocaleDateString("ko-KR", {
        month: 'long',
        day: 'numeric',
    });
}

function formatCount(count) {
    if (count > 1000) {
        return count = (count / 1000).toFixed(1) + 'k';
    } else {
        return count.toString();
    }
}

function latestBtnClicked() {
    contentsType = 2;
    btnProcess();
    $tabMenu.querySelector("#latest").classList.add("active");
}

function trendBtnClicked() {
    contentsType = 1;
    btnProcess();
    $tabMenu.querySelector("#trend").classList.add("active");
}

function btnProcess() {
    $blogList.innerHTML = "";
    index = 0;
    update();
    childList = $blogList.querySelectorAll("*");
    childList.forEach(element => element.classList.remove("active"))
}