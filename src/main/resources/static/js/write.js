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


quill.getModule('toolbar').addHandler('image', function () {
    selectLocalImage();
})

function selectLocalImage() {
    const fileInput = document.createElement('input');
    fileInput.setAttribute('type', 'file');
    fileInput.accept = "image/*";
    fileInput.click();

    fileInput.addEventListener("change", function () {
        if (this.files.length > 0) {  // 파일이 선택되었는지 확인
            const file = this.files[0];
            const ext = file.name.split(".").pop().toLowerCase();

            // 지원되는 확장자 목록
            if (!["gif", "jpg", "jpeg", "png", "bmp"].includes(ext)) {
                alert("jpg, jpeg, png, bmp, gif 파일만 업로드 가능합니다.");
                return;
            }

            // 파일 크기 확인
            const maxSize = 20 * 1024 * 1024; // 20MB
            if (file.size > maxSize) {
                alert("업로드 가능한 최대 이미지 용량은 20MB입니다.");
                return;
            }

            const formData = new FormData();
            formData.append('uploadFile', file);

            fetch('/post/upload', {
                method: 'POST',
                body: formData
                // Fetch API를 사용할 때는 processData와 contentType을 설정하지 않아도 됩니다.
                // FormData 객체를 사용하면 fetch 자체가 적절한 headers를 설정합니다.
            })
                .then(response => response.text())  // 서버 응답을 텍스트로 변환
                .then(data => {
                    // 성공적으로 파일을 업로드하고 URL을 받았다면, 에디터에 이미지 삽입
                    const range = quill.getSelection(true);
                    quill.insertEmbed(range.index, 'image', "/file/display?fileName=" + data);
                })
                .catch(error => {
                    console.error('ERROR!! ::', error);
                });
        }
    });
}

const imgTags = document.querySelectorAll('img');

const fetchPromises = [];

imgTags.forEach(img => {
    const currentSrc = img.getAttribute('src');

    // 이미지가 base64로 인코딩된 데이터인지 확인
    if (currentSrc.startsWith('data:image')) {
        const splitDataURI = currentSrc.split(',');

        if (splitDataURI[0].includes('base64')) {
            const base64Data = splitDataURI[1];
            const formData = new FormData();
            formData.append('base64Image', base64Data);

            const fetchPromise = fetch('/file/uploadBase64', {
                method: 'POST',
                body: formData
            })
                .then(response => response.text())
                .then(data => {
                    img.setAttribute('src', `/file/display?fileName=${data}`);
                })
                .catch(err => {
                    console.error('ERROR!! ::', err);
                });

            fetchPromises.push(fetchPromise);
        }
    }
});

Promise.all(fetchPromises).then(() => {
    // 여기서 폼 제출 로직을 넣거나, 다른 검증을 수행합니다.
    console.log("모든 이미지가 서버에 저장되었습니다.");
    // 폼 제출 등의 로직을 실행
}).catch(error => {
    console.error("이미지 저장 중 에러 발생:", error);
    // 에러 처리 로직
});

document.getElementById('submit-form').addEventListener('submit', function (event) {
    event.preventDefault(); // 폼의 기본 제출을 막습니다.

    // 위에서 정의한 이미지 처리 로직을 여기에 포함
    Promise.all(fetchPromises).then(() => {
        // 이미지 처리가 성공적으로 완료되면, 폼을 제출
        this.submit();
    }).catch(error => {
        console.error("이미지 저장 실패:", error);
        // 에러가 발생했을 때의 처리 로직
    });
});


