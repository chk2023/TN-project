$searchBtn = $(`#searchBtn`);
$searchInput = $(`#search_input`);
$closeBtn = $(`#closeBtn`);
$title = $(`#title`);
$backBtn = $(`#backBtn`);

// 검색 버튼에 클릭 이벤트 핸들러를 할당
$searchBtn.on('click', function() {
    $title.addClass('search');
    console.log($title.attr('class'));
});

// 닫기 버튼에 클릭 이벤트 핸들러를 할당
$closeBtn.on('click', function() {
    $title.removeClass('search');
    $searchInput.val("");
});