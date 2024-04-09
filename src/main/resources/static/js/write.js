// 글자 크기 속성을 가져오고, 사용자 정의 글자 크기를 whitelist에 설정
var Size = Quill.import('attributors/style/size');
Size.whitelist = ['8px', '9px', '10px', '12px', '14px', '16px', '20px', '24px', '32px', '42px', '54px', '68px', '84px', '98px'];
Quill.register(Size, true);


// 툴바 옵션을 설정합니다.
var toolbarOptions = [
    ['bold', 'italic', 'underline', 'strike'],
    ['blockquote', 'code-block'],
    ['link', 'image', 'video'],
    [{'list': 'ordered'}, {'list': 'bullet'}, {'list': 'check'}],
    [{'align': []}],
    [{'color': []}, {'background': []}],
    [{'font': []}],
    [{'size': Size.whitelist}],
    ['clean']
];

// Quill 에디터 인스턴스를 생성합니다.
var quill = new Quill('#editorTxt', {
    modules: {
        toolbar: toolbarOptions
    },
    theme: 'snow',
    placeholder: '내용을 입력해주세요.'
});

//글씨 크키 폰트로 지정하게 커스텀함 데헷-
//TODO 차후 본문 클릭시 label에 active 되는 부분도 수정할거임!!
//TODO clean 버튼  눌렀을때 태그 전체 삭제 되게 해야하나바 ㅠㅠㅠㅠ 글씨 설정했더니 잘 안먹음 ㅠㅠㅠ 추후 수정예정
let spanEl = document.querySelectorAll(".ql-size .ql-picker-label,.ql-size .ql-picker-item");
let label = document.querySelector(".ql-size .ql-picker-label")
label.setAttribute("data-value", "16px");
spanEl.forEach(el => {
    let dataValue = el.getAttribute("data-value");
    let txtNode = document.createTextNode(dataValue);
    if (dataValue) {
        el.classList.remove("ql-selected");
        document.querySelector(".ql-size .ql-picker-item[data-value='16px']").classList.add("ql-selected");
        el.insertBefore(txtNode, el.firstChild);
        el.addEventListener("click", function () {
            removeLabelTextAddNewLabelText();
        })
    }
})
document.querySelector(".ql-clean").addEventListener("click", function () {
    label.setAttribute("data-value", "16px");
    removeLabelTextAddNewLabelText();
    label.classList.remove("ql-active");
})

function removeLabelTextAddNewLabelText() {
    Array.from(label.childNodes).forEach(node => {
        if (node.nodeType === node.TEXT_NODE) {
            label.removeChild(node);
        }
    });
    label.insertBefore(document.createTextNode(label.getAttribute("data-value")), label.firstChild);
}

// 사용자 입력 내용 가져오기 예시
function getUserInput() {
    var content = quill.root.innerHTML; // 에디터 내용을 HTML 형태로 가져옴
    console.log(content); // 콘솔에 내용 출력
}

// 예를 들어, 내용을 가져오는 버튼을 클릭할 때 getUserInput 함수를 호출
document.querySelector('#작성완료버튼').addEventListener('click', getUserInput);