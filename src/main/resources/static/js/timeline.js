const $blogList = document.querySelector(".blogList");
const $main = document.querySelector("main");

// update(); //최초 1회 실행
function update() {
    fetch(`/timeline/updateList?index=${index}&range=${range}&contentsType=${contentsType}`)
        .then(res => res.json())
        .then(data => {
            console.log("업데이트 실행됨")
            if (!data.length) {
                alert("이런! 오늘은 글이 더 없네요.");
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
                if (!isTitlePhotoChanged && item.thumbnailPath !== `/image/icon_default_photo.png`) {
                    //이미지가 있는 가장 첫번째 사진으로 타이틀사진을 변경
                    setTitlePhoto(item.thumbnailPath);
                    isTitlePhotoChanged = true;
                }
            });
            var html = document.createElement("li");
            html.innerHTML = template(data, {data: {handlebars: Handlebars.create()}});
            fragment.appendChild(html); // DocumentFragment에 요소 추가
            // DocumentFragment를 한 번에 추가하여 DOM 조작 효율성 향상
            $blogList.appendChild(fragment);

            index = index + range;
        }).catch(error => console.error('Error fetching profile data:', error))
        .finally(function () {
            isLoading = false
        });
}

function setTitlePhoto(path) {
    $img = document.querySelector(".mainProfile img");
    $img.src = path;
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

function trendBtnClicked() {
    contentsType = 1;
    btnProcess();
    $tabMenu.querySelector("#trend").classList.add("active");
}

function latestBtnClicked() {
    contentsType = 2;
    btnProcess();
    $tabMenu.querySelector("#latest").classList.add("active");
}

function btnProcess() {
    $blogList.innerHTML = "";
    index = 0;
    isTitlePhotoChanged = false;
    update();
    childList = $tabMenu.querySelectorAll("*");
    console.log(childList);
    childList.forEach(element => element.classList.remove("active"));
}
//무한 스크롤 이벤트
$main.addEventListener("scroll", function () {
    let scrollTop = $main.scrollTop;
    let viewportHeight = $main.clientHeight;
    let containerHeight = $main.scrollHeight;
    console.log("scrollTop : " + scrollTop);
    console.log("vHeight : " + viewportHeight);
    console.log("cHeight :" + containerHeight);
    if (scrollTop + viewportHeight >= containerHeight && !isLoading) {
        isLoading = true;
        update();
    }
});