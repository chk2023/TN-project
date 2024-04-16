//------------------------------------------------------------------------------------tabMenu관련코드
function trendBtnClicked() {
    contentsType = 1;
    btnProcess();
}

function latestBtnClicked() {
    contentsType = 2;
    btnProcess();
}

function recomendedBtnClicked() {
    contentsType = 3;
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
            $tabMenu.querySelector("#contentsType1").classList.add("active");
            break;
        case 2:
            $tabMenu.querySelector("#contentsType2").classList.add("active");
            break;
        case 3:
            $tabMenu.querySelector("#contentsType3").classList.add("active");
    }
}