const $blogList = document.querySelector(".blogList");
const $main = document.querySelector("main");

// update(); //최초 1회 실행
viewTypeChange();
function update() {
    fetch(`/timeline/updateList?index=${index}&range=${range}&contentsType=${contentsType}`)
        .then(res => res.json())
        .then(data => {
            if (!data.length) {
                alert("이런! 오늘은 글이 더 없네요.");
                return;
            }
            source = document.querySelector("#contentsTemplate").innerHTML;
            var fragment = document.createDocumentFragment();
            var template = Handlebars.compile(source);

            data.forEach(item => {
                item.cmtCount = formatCount(item.cmtCount);
                item.likeCount = formatCount(item.likeCount);
                item.postWriDate = formatWriDate(item.postWriDate);
                if (!isTitlePhotoChanged && item.thumbnailPath !== `/images/icon_default_photo.png`) {
                    //이미지가 있는 가장 첫번째 사진으로 타이틀사진을 변경
                    setTitlePhoto(item.thumbnailPath);
                    isTitlePhotoChanged = true;
                }
            });
            var element = document.createElement("li");
            element.innerHTML = template(data, {data: {handlebars: Handlebars.create()}});
            fragment.appendChild(element); // DocumentFragment에 요소 추가
            // DocumentFragment를 한 번에 추가하여 DOM 조작 효율성 향상
            $blogList.appendChild(fragment);

            index = index + range;
        }).catch(error => console.error('Error fetching profile data:', error))
        .finally(function () {
            isLoading = false
        });
}

//------------------------------------------------------------------------------------format관련코드
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

//------------------------------------------------------------------------------------tabMenu관련코드
function trendBtnClicked() {
    contentsType = 1;
    btnProcess();
}

function latestBtnClicked() {
    contentsType = 2;
    btnProcess();
}
function btnProcess() {
    $blogList.innerHTML = "";
    index = 0;
    isTitlePhotoChanged = false;
    childList = $tabMenu.querySelectorAll("*");
    console.log(childList);
    childList.forEach(element => element.classList.remove("active"));
    switch (contentsType) {
        case 1:
            $tabMenu.querySelector("#trend").classList.add("active");
            break;
        case 2:
            $tabMenu.querySelector("#latest").classList.add("active");
            break;
        case 3:
            $tabMenu.querySelector("#recommended").classList.add("active");
    }
}
//------------------------------------------------------------------------------------viewType관련코드
function viewBtnClicked() {
    $viewBtn.classList.add(`active`);
}

function blogViewClicked() {
    $viewBtn.classList.remove('active');
    viewType = "blog";
    viewTypeChange();
}

function snsViewClicked() {
    $viewBtn.classList.remove('active');
    viewType = "sns";
    viewTypeChange();
}

function viewTypeChange() {
    console.log(viewType);
    switch (viewType) {
        case 'blog':
            $blogList.classList.remove(`snsList`);
            break;
        case `sns`:
            $blogList.classList.add(`snsList`);
            break;
    }
}