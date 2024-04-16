//------------------------------------------------------------------------------------tabMenu관련코드
const $tabMenu = document.querySelector(`.tabMenu`);
function contentsType1Clicked() {
    contentsType = 1;
    btnProcess();
}

function contentsType2Clicked() {
    contentsType = 2;
    btnProcess();
}

function contentsType3Clicked() {
    contentsType = 3;
    btnProcess();
}
function btnProcess() {
    $blogList.innerHTML = "";
    index = 0;
    isTitlePhotoChanged = false;
    update();
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