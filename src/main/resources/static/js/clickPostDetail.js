// function handleClickPost() {
//     document.querySelectorAll(".clickPost").forEach(btn => {
//         btn.addEventListener('click', (e) => {
//
//             // var postCode = parseInt(e.currentTarget.getAttribute('data-post-code'));
//             // console.log("postCode:",postCode);
//             // clickPost(postCode);
//
//             var postCodeAttr = e.currentTarget.getAttribute('data-post-code');
//             var postCode = model.postCode;
//             console.log("글 코드 확인 :", postCodeAttr);
//             console.log("글 코드 확인 :", postCode);
//             if (!isNaN(postCodeAttr)) {
//                 clickPost(postCodeAttr);
//             } else {
//                 alert("유효하지 않은 postCode입니다.");
//             }
//         })
//     })
// }
//
// function clickPost(postCode) {
//     if (postCode) {
//         fetch('/post/detail', { // postCode를 body에 포함하여 요청
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json'
//             },
//             body: JSON.stringify({ postCode: postCode })
//         })
//             .then(response => {
//                 if (!response.ok) {
//                     throw new Error('서버 응답이 실패했습니다.');
//                 }
//             })
//             .catch(error => {
//                 console.error('오류 발생:', error);
//             });
//     } else {
//         alert("글의 postCode가 없습니다.");
//     }
// }