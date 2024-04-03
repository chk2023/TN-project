const $addBtn = document.querySelector(".addBtn");
const $delBtn = document.querySelector(".deleteBtn");
const $feList = document.querySelector(".fEditList");
const fName = document.getElementById("folderName");

// 이벤트 리스너 등록
$addBtn.addEventListener('click', addFolder);
$delBtn.addEventListener('click', deleteFolder);
$feList.addEventListener('click', handleClick);
fName.addEventListener("keydown", handleEnter);
fName.addEventListener("focus", handleFocus);
fName.addEventListener("blur", handleBlur);
fName.addEventListener('input', handleInput);
document.querySelector(".iconBox").addEventListener("click", handleIconClick);

function addFolder() {
    let li = $feList.querySelectorAll("li:not(.hidden)");
    let lastLi = li[li.length - 1];
    let hiddenLi = $feList.querySelectorAll(".hidden");
    let lastHiddenLi = hiddenLi[hiddenLi.length - 1];
    if($feList.querySelectorAll("li:not(.hidden)").length <= 10) {
        console.log("10개이하라 버튼은 먹은거 같은디??" + hiddenLi.item(0))
        lastHiddenLi.classList.remove("hidden");
        lastHiddenLi.querySelector("span").textContent = "폴더";
        lastHiddenLi.querySelector("img").src = "/image/icon_folder.png";
        lastLi.after(lastHiddenLi);
    } else {
        alert("폴더는 최대 10개 까지만 생성 가능합니다.");
    }
}

function deleteFolder() {
    const $feListActive = $feList.querySelector(".active");
    if ($feListActive) {
        //$feListActive.remove();
        $feListActive.querySelector("span").textContent = "NoName";
        $feListActive.querySelector("img").src = "/image/icon_folder.png";
        $feListActive.classList.add("hidden");
        $feListActive.classList.remove("active");

        $feList.appendChild($feListActive);
        inputBlur();
    } else {
        alert("삭제할 폴더를 선택해 주세요.");
    }
}

function handleClick(e) {
    let thisEl = e.target;
    if (thisEl.tagName === "LI" || thisEl.tagName === "SPAN" || thisEl.tagName === "IMG") {
        removeActive();
        if (thisEl.tagName === "LI" && !thisEl.classList.contains("fixed")) {
            thisEl.classList.add("active");
        } else if (thisEl.tagName === "SPAN") {
            thisEl.parentElement.classList.add("active");
        } else if (thisEl.tagName === "IMG") {
            thisEl.parentElement.classList.add("active");
            document.querySelector(".iconDt").classList.add("active");
        }
        if (thisEl.tagName === "LI" && !thisEl.classList.contains("fixed") || thisEl.tagName === "SPAN" && !thisEl.parentElement.classList.contains("fixed")) {
            inputFocus(thisEl);
        }
    }
}

function handleFocus() {
    $feList.querySelector(".active").classList.add("active_tit");
}

function handleBlur() {
    inputBlur();
}

function handleInput() {
    $feList.querySelector(".active span").textContent = fName.value;
}

function handleEnter(e) {
    if (e.key === "Enter") {
        removeActive();
        inputBlur();
    }
}

function handleIconClick(e) {
    let thisEl = e.target;
    if (thisEl.tagName === "IMG") {
        let thisSrc = thisEl.src.slice(thisEl.src.indexOf("/image"));
        $feList.querySelector(".active img").src = thisSrc;
        document.querySelector(".iconDt").classList.remove("active");
        removeActive();
    }
}

function inputBlur() {
    const $activeElement = $feList.querySelector(".active_tit");
    if ($activeElement) {
        $activeElement.classList.remove("active_tit");
    }
    fName.setAttribute("readonly", "readonly");
    fName.value = '';
    fName.blur();
}

function inputFocus(thisEl) {
    let thisfName = thisEl.textContent;
    fName.removeAttribute("readonly");
    fName.value = thisfName.trimStart();
    fName.focus();
}

function removeActive() {
    const $lis = $feList.querySelectorAll("li");
    $lis.forEach((li) => {
        li.classList.remove("active");
    })
}

$feList.addEventListener('dragstart', function (e) {
    e.dataTransfer.setData('text/plain', e.target.id);
    setTimeout(() => {
        e.target.classList.add('drag_active');
        const $activeElement = $feList.querySelector(".active");
        if ($activeElement) {
            $activeElement.classList.remove("active");
        }
    }, 0);
});

$feList.addEventListener('dragend', function (e) {
    e.target.classList.remove('drag_active');
});

$feList.addEventListener('dragover', function (e) {
    e.preventDefault();
    const afterElement = getDragAfterElement($feList, e.clientY);
    const draggable = document.querySelector('.drag_active');
    if (afterElement == null) {
        $feList.appendChild(draggable);
    } else {
        $feList.insertBefore(draggable, afterElement);
    }
});

function getDragAfterElement(container, y) {
    const draggableElements = [...container.querySelectorAll('li:not(.fixed):not(.drag_active)')];
    return draggableElements.reduce((closest, child) => {
        const box = child.getBoundingClientRect();
        const offset = y - box.top - box.height / 2;
        if (offset < 0 && offset > closest.offset) {
            return {offset: offset, element: child};
        } else {
            return closest;
        }
    }, {offset: Number.NEGATIVE_INFINITY}).element;
}

async function sendFolderData() {
    let folderInfoList = [];
    $feList.querySelectorAll("li:not(.fixed)").forEach(function (li, index){
        const folderCode = li.querySelector('input[type="hidden"]').value;
        const folderName = li.querySelector("span").textContent;
        const folderIconPath = li.querySelector("img").src.slice(li.querySelector("img").src.indexOf("/image"));
        const folderSequence = index;
        const fMemberCode = 0;
        const folderStatus = li.classList.contains("hidden") ? 'N' : 'Y';
        const folderInfo = {folderCode, folderName, folderIconPath, folderSequence, fMemberCode, folderStatus}
        folderInfoList.push(folderInfo);
        console.log(folderInfo);
    })
    const json = JSON.stringify(folderInfoList);
    const response = await fetch('/post/folder_edit', {
        method : 'POST',
        headers : {
            'Content-Type' : 'application/json; charset=UTF-8'
        },
        body : json
    });
    const result = await response.text();
    console.log("결과 찍어보깅 : " + result)
    if(result) {
        alert("폴더 목록이 저장되었습니다.");
        window.location.href = "http://localhost:8080/post/main";
    }
}