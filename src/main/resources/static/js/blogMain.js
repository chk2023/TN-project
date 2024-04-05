const $tabMenu = document.querySelector(".tabMenu");
const $blogList = document.querySelector(".blogList");

let tabMenu = "고정글"; //탭메뉴 기본값
let index = 0; //로드할 게시물 시작점
let range = 10; //로드할 게시물 수
let loadedPostIds = []; //중복 게시물 id 체크 배열
let isLoading = false; //현재 로딩 중 여부

$tabMenu.addEventListener('click', tabMenuClick);

function tabMenuClick(e) {
    let thisEl = e.target; //현재 선택된 요소
    if (thisEl.tagName === 'BUTTON' && !thisEl.parentElement.classList.contains("viewBtn")) {
        const $li = $tabMenu.querySelectorAll("li:not(.viewBtn)");
        $li.forEach((li) => {
            li.classList.remove("active"); //모든 탭에서 active 제거
        })
        thisEl.parentElement.classList.add("active"); //현재 클릭된 탭에만 active 클래스 추가
        tabMenu = thisEl.textContent; //탭메뉴 값 설정

        loadedPostIds = []; //중복 체크 배열 초기화
        $blogList.innerHTML = ''; // 컨테이너 초기화
        index=0; //로드할 게시물 시작점 초기화
        update(); //비동기 조회 내용 로드
    }
}

document.addEventListener('click', function (e) {
    const $optionBtn = document.querySelectorAll(".optionBtn"); //모든 .optionBtn 요소를 선택
    const targetOptionBtn = e.target.closest(".optionBtn"); //클릭된 요소 또는 그 조상 중 .optionBtn을 찾음
    const targetViewTypeBtn = e.target.closest(".viewBtn"); //클릭된 요소 또는 그 조상 중 .viewBtn을 찾음
    const $viewBtn = document.querySelector('.viewBtn > button'); //.viewBtn 눌렀을때 active 걸릴 버튼

    if (targetOptionBtn) {
        $optionBtn.forEach((option) => {
            option.classList.remove("active"); //먼저 모든 .optionBtn에서 active 클래스 제거
        });
        targetOptionBtn.classList.add("active"); //클릭된 .optionBtn에만 active 클래스 추가
    } else if (targetViewTypeBtn) {
        $viewBtn.classList.toggle("active"); //클릭된 .viewBtn 바로 아래 버튼에 토글 active 클래스
        if(e.target.textContent === "블로그식 보기") {
            $blogList.classList.remove("snsList"); //블로그식 보기 클릭시 snsList 클래스 제거
        } else if(e.target.textContent === "SNS식 보기") {
            $blogList.classList.add("snsList"); //sns식 보기 클릭시 snsList 클래스 추가
        }
    } else {
        $optionBtn.forEach((option) => {
            option.classList.remove("active"); //모든 .optionBtn에서 active 클래스 제거
        });
        $viewBtn.classList.remove("active"); //.viewBtn 바로 아래 버튼 active 클래스 제거
    }
});

function formatCount(count) {
    if (count > 1000) {
        return (count / 1000).toFixed(1) + 'k';
    } else {
        return count.toString();
    }
}

function folderAllList() {
    const urlParams = new URLSearchParams(window.location.search);
    const memberCode = urlParams.get('memberCode');

    location.href = "/post/list?memberCode="+memberCode;
}

async function update() {
    if (isLoading) return; //중복 로드 방지
    isLoading = true;

    const urlParams = new URLSearchParams(window.location.search);
    const memberCode = urlParams.get('memberCode');
    const response = await fetch('/post/load?memberCode='+memberCode+'&tabMenu='+tabMenu+'&index='+index+'&range='+range);
    const result = await response.json();

    if (result.length) {
        result.forEach(post => {
            if(!loadedPostIds.includes(post.postCode)) { //중복제거 로직 -> 게시물 고유 id가 loadedPostIds 배열에 없다면, 화면에 표시하고 배열에 추가
                const $li = document.createElement('li');
                const $fragment = document.createDocumentFragment();

                // 'post.postWriDate'를 Date 객체로 변환
                const postWriDate = new Date(post.postWriDate);
                const formattedDate = `${(postWriDate.getMonth() + 1).toString().padStart(2, '0')}월 ${postWriDate.getDate().toString().padStart(2, '0')}일`; // 간단한 날짜 형식 MM월 dd일 변경
                const likeBtnStr = post.liked ? "likeBtn active" : "likeBtn";

                const formattedLikeCount = formatCount(post.likeCount);
                const formattedCmtCount = formatCount(post.cmtCount);
                const postElement = `
                                                <div class="list_head">
                                                    <div class="prof_photo">
                                                        <img src="${escapeHTML(post.thumbnailPath ? post.thumbnailPath : '/images/icon_user.png')}" alt="프로필 사진">
                                                    </div>
                                                    <div>
                                                        <div>
                                                            <strong class="blog_user_name" onclick="location.href='/post/detail?postCode=${escapeHTML(post.postCode)}'">${escapeHTML(post.profile.profileNickname)}</strong>
                                                            <span class="blog_user_birth" onclick="location.href='/post/detail?postCode=${escapeHTML(post.postCode)}'">${escapeHTML(formattedDate)}</span>
                                                        </div>
                                                        <span class="status_msg">${escapeHTML(post.profile.profileStatmsg)}</span>
                                                    </div>
                                                    <button class="optionBtn"><img src="/images/icon_option.png" alt="글 옵션 버튼"></button>
                                                    <ul class="optionBox">
                                                        <li><a>글 신고하기</a></li>
                                                        <li><a>글 관심없음</a></li>
                                                    </ul>
                                                </div>
                                                <div class="list_body">
                                                <div>
                                                    <strong class="contentTit">${escapeHTML(post.postTitle)}</strong>
                                                    <div class="contentsTxt">
                                                        <p>${escapeHTML(post.postText)}</p>
                                                    </div>
                                                    <div class="showLikeAndComments">
                                                        <button class="commentsBtn">
                                                            <img src="/images/icon_comment.png" alt="댓글 버튼 아이콘">
                                                            <span>${escapeHTML(formattedCmtCount)}</span>
                                                        </button>
                                                        <button class="${escapeHTML(likeBtnStr)}">
                                                            <img src="/images/icon_like.png" alt="좋아요 버튼 아이콘">
                                                            <span>${escapeHTML(formattedLikeCount)}</span>
                                                        </button>
                                                    </div>
                                                </div>
                                        `;
                $li.insertAdjacentHTML('beforeend', postElement);
                $fragment.appendChild($li);
                $blogList.appendChild($fragment);
                loadedPostIds.push(post.postCode); //로드된 게시물 id 저장
            }
        });

    } else {
        alert("글이 없습니다.");
    }
    index += range; //다음 위한 로드할 게시물 시작점 업데이트
    isLoading = false; // 로딩 상태 해제
}

function escapeHTML(text) {
    var map = {
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": '&#039;'
    };
    return String(text).replace(/[&<>"']/g, function(m) { return map[m]; });
}

// 초기 로드
document.addEventListener('DOMContentLoaded', update);