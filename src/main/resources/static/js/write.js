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

quill.on('text-change', function () {
    document.getElementById("editorTxtCopy").value = quill.root.innerHTML;
})


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
    console.log("HTML 내용" + content); // 콘솔에 내용 출력
}

//태그 설정
function extractTags(inputString) {
    const regex = /#([\wㄱ-ㅎㅏ-ㅣ가-힣_＆＊＠§※☆★○●◎◇◆□■△▲▽▼→←↑↓↔〓◁◀▷▶♤♠♡♥♧♣⊙◈♨☏☎☜☞↗↙↖↘♭♩♪♬㈜]+)/g;
    let tags = [];
    let match;
    while ((match = regex.exec(inputString)) !== null) {
        tags.push(match[1]);
    }
    return tags;
}

// 유료글 설정
document.querySelectorAll('input[name="postDTO.postPrice"]').forEach(input => {
    input.addEventListener('change', function () {
        const priceInput = document.getElementById('priceInput');
        const priceValue = document.getElementById('priceValue');
        const postPaid = document.getElementById('postPaid');

        if (input.id === 'postPaid' && input.checked) {
            priceInput.style.display = 'block';
            priceValue.addEventListener('change', function () {
                postPaid.value = priceValue.value; // 입력 값이 변경될 때 유료글 포스팅의 value를 업데이트
            });
        } else if (input.id === 'postFree' && input.checked) {
            priceInput.style.display = 'none';
            priceValue.value = "";
            postPaid.value = 'paid'; // 유료글 포스팅을 다시 기본 값으로 설정
        }
    });
});

document.querySelector('#작성완료버튼').addEventListener('click', function (e) {
    //e.preventDefault();
    const postTitle = document.querySelector("input[name='postDTO.postTitle']").value;
    const postText = document.querySelector("textarea[name='postDTO.postText']").value;
    const folderCode = document.querySelector("select[name='postDTO.folderCode']").value;
    const tagInput = document.querySelector("textarea[name='postTagDTO.tagDTO.tagName']").value;
    const postState = document.querySelector("input[name='postDTO.postState']:checked").value;
    const postPrice = document.querySelector("input[name='postDTO.postPrice']:checked").value;
    const postIsFixed = document.querySelector("input[name='postDTO.postIsFixed']:checked").value;

    // 타이틀이 비어있는 경우 경고 표시
    if (!postTitle.trim()) {
        alert('제목을 입력해주세요.');
        return;
    }

    // 내용이 비어있는 경우 경고 표시
    if (!postText.trim()) {
        alert('내용을 입력해주세요.');
        return;
    }

    const tags = extractTags(tagInput);

    console.log("타이틀: " + postTitle);
    console.log("내용: " + postText);
    console.log("폴더코드 : " + folderCode);
    console.log("태그 : ", tags);
    console.log("공개설정 : " + postState);
    console.log("유료설정 : " + postPrice);
    console.log("고정설정 : " + postIsFixed);

    getUserInput();

    document.getElementById("writeForm").submit();
});


quill.getModule('toolbar').addHandler('image', function () {
    selectLocalImage();
})

function selectLocalImage() {
    const input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.accept = "image/*";
    input.click();
    input.onchange = () => {
        const file = input.files[0];
        if (file) {
            const formData = new FormData();
            formData.append('uploadFile', file); // 서버에 파일 데이터를 'uploadFile'로 전송

            fetch('/post/upload', {
                method: 'POST',
                enctype: 'multipart/form-data',
                body: formData,
                headers: {
                    'Accept': 'application/json'
                }
            }).then(response => response.json())
                .then(data => {
                    if (data.error) {
                        console.error('Upload failed:', data.error);
                    } else {
                        insertToEditor(data.newName, data.originalName);
                    }
                }).catch(error => {
                console.error('Error:', error);
            });
        }
    };
}

function insertToEditor(newFileName, originalFileName) {
    const path = `/post/display?fileName=${encodeURIComponent(newFileName)}`;
    const range = quill.getSelection();
    quill.insertEmbed(range.index, 'image', path);

    // 새 파일명과 기존 파일명을 hidden input에 저장하여 서버에 전송 준비
    const newFileNameInput = document.createElement('input');
    newFileNameInput.setAttribute('type', 'hidden');
    newFileNameInput.setAttribute('name', 'attachmentDTO.safeName');
    newFileNameInput.value = newFileName;
    document.getElementById('writeForm').appendChild(newFileNameInput);

    const originalFileNameInput = document.createElement('input');
    originalFileNameInput.setAttribute('type', 'hidden');
    originalFileNameInput.setAttribute('name', 'attachmentDTO.originName');
    originalFileNameInput.value = originalFileName;
    document.getElementById('writeForm').appendChild(originalFileNameInput);
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

            const fetchPromise = fetch('/post/uploadBase64', {
                method: 'POST',
                enctype: 'multipart/form-data',
                body: formData
            })
                .then(response => response.text())
                .then(data => {
                    img.setAttribute('src', `/userUploadFiles/post/${data}`);
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

document.getElementById('writeForm').addEventListener('submit', function (event) {
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


