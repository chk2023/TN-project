//------------------------------------------------------------------------------------format관련코드
function setTitlePhoto(path) {
    $img = document.querySelector(".mainProfile img");
    $img.src = path;
}

function formatWriDate(postWriDate) {
    let time = new Date(postWriDate);
    return time.toLocaleDateString("ko-KR", {
        month: 'long',
        day: 'numeric',
    });
}

function formatCount(count) {
    if (count > 1000) {
        return count = (count / 1000).toFixed(1) + 'k';
    } else {
        return count.toString();
    }
}
