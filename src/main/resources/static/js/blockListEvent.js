function unblockMemberByCode(code) {
    window.location.href = "/member/unblockMember?targetMemberCode=" + code;
}

function blockMemberByCode(event,code, contentsType) {
    event.preventDefault();
    //컨트롤러에서는 기본값( 0 )인 변수는 처리 하지않도록 구성함
    let postCode = 0;
    let cmtCode = 0;
    //콘텐츠 타입에 따라 postCode 혹은 cmtCode에 값을 넣어줌
    if (contentsType === "post") {
        postCode = code;
    } else if (contentsType === "cmt") {
        cmtCode = code;
    }
    fetch("/member/blockMember", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        }, body: JSON.stringify({postCode: postCode, cmtCode: cmtCode})
    }).then(response => {
        if (!response.ok) {
            throw new Error("응답오류");
        }
        return response.text();
    }).then(data => {
        // 반환하는 값은 message이므로 alert로 출력
        alert(data);
        console.log(data);
    }).catch(error => {
        alert(error.body);
        console.error('오류:', error);
    });
}