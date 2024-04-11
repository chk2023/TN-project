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
    e.preventDefault();
    const postTitle = document.querySelector("input[name='postDTO.postTitle']").value;
    const postText = document.querySelector("textarea[name='postDTO.postText']").value;
    const folderCode = document.querySelector("select[name='postDTO.folderCode']").value;
    const tagInput = document.querySelector("textarea[name='tagDTOList.tagName']").value;
    const tags = extractTags(tagInput);
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

    // 폼 데이터 준비
    const formData = new FormData(document.getElementById('writeForm'));
    // 기존 폼 데이터 제거 (옵셔널)
    formData.delete('postDTO.postTitle');
    formData.delete('postDTO.postText');
    formData.delete('postDTO.folderCode');
    formData.delete('postDTO.postState');
    formData.delete('postDTO.postPrice');
    formData.delete('postDTO.postIsFixed');
    formData.getAll('tagDTOList.tagName').forEach(() => formData.delete('tagDTOList.tagName'));

    formData.append('postDTO.postTitle', postTitle);
    formData.append('postDTO.postText', postText);
    formData.append('postDTO.folderCode', folderCode);
    formData.append('postDTO.postState', postState);
    formData.append('postDTO.postPrice', postPrice);
    formData.append('postDTO.postIsFixed', postIsFixed);

    tags.forEach((tag, index) => {
        formData.append(`tagDTOList[${index}].tagName`, tag);
    })
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
        fetch('/post/write', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (!response.ok && !response.redirected) {
                    throw new Error('Network response was not ok');
                }
                if (response.redirected) {
                    window.location.href = response.url; // 리다이렉트 응답을 받은 경우 새 위치로 이동
                    return; //추가 처리를 방지
                }
                //return response.json(); //JSON 응답을 받은 경우 처리
            })
            .then(data => {
                if (data) { //데이터가 있는 경우만 처리
                    console.log('Post submission succeeded', data);
                    window.location.href = '/post/list'; //성공후 페이지 이동
                }
            })
            .catch(error => {
                console.error('Error during form submission:', error);
            });
    }).catch(error => {
        console.error("이미지 저장 중 에러 발생:", error);
    });
    // 기존 파일명과 바뀐 파일명을 폼 데이터에 추가
    uploadedFileNames.originalNames.forEach((originalName, index) => {
        formData.append(`attachmentDTOList[${index}].originName`, originalName);
        formData.append(`attachmentDTOList[${index}].safeName`, uploadedFileNames.newNames[index]);
    });
});

quill.getModule('toolbar').addHandler('image', function () {
    selectImagesAndSend();
})

function selectImagesAndSend() {
    const input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.setAttribute('multiple', '');
    input.accept = "image/*";
    input.onchange = () => {
        const files = input.files;
        const formData = new FormData();

        for (const file of files) {
            formData.append('uploadFiles', file);
        }

        fetch('/post/upload', {
            method: 'POST',
            body: formData
        }).then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok.');
            }
            return response.json();
        })
            .then(handleUploadResponse)
            .catch(error => {
                console.error('Error:', error);
            });
    };
    input.click();
}

// 업로드 응답 처리 및 파일명 저장
function handleUploadResponse(data) {
    data.forEach(fileInfo => {
        insertToEditor(fileInfo.newName, fileInfo.originalName);
        // 파일명 정보 저장
        uploadedFileNames.originalNames.push(fileInfo.originalName);
        uploadedFileNames.newNames.push(fileInfo.newName);
    });
}

// 이미지 업로드와 관련된 파일명 정보를 저장할 배열
let uploadedFileNames = {
    originalNames: [],
    newNames: []
};

// 이미지를 Quill 에디터에 삽입하는 함수
function insertToEditor(newFileName, originalFileName) {
    const path = `/post/display?fileName=${encodeURIComponent(newFileName)}`;
    const range = quill.getSelection(true);
    quill.insertEmbed(range.index, 'image', path, Quill.sources.USER);
    quill.setSelection(range.index + 1, Quill.sources.SILENT);
}

