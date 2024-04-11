const $homeBtn = document.getElementById("homeBtn");
const $memberInfoBtn = document.getElementById("memberInfoBtn");
const $paymentBtn = document.getElementById("paymentBtn");
const $blockListBtn = document.getElementById("blockListBtn");
const $adminListBtn = document.getElementById("adminListBtn");
const $reportListBtn = document.getElementById("reportListBtn");
const $memberListBtn = document.getElementById('memberListBtn');
const $logoutBtn = document.getElementById("logoutBtn");
const $testHubBtn = document.getElementById("testHubBtn");
const $backBtn = document.querySelector(".backBtn");

if ($homeBtn) {
    $homeBtn.onclick = function () {
        location.href = "/timeline/list";
    };
}
if ($memberInfoBtn) {
    $memberInfoBtn.onclick = function () {
        location.href = "/member/update";
    };
}
if ($paymentBtn) {
    $paymentBtn.onclick = function () {
        location.href = "/payment/payment";
    };
}
if ($blockListBtn) {
    $blockListBtn.onclick = function () {
        location.href = "/member/blockList";
    };
}
if ($adminListBtn) {
    $adminListBtn.onclick = function () {
        location.href = "/manager/admin/list";
    };
}
if ($reportListBtn) {
    $reportListBtn.onclick = function () {
        location.href = "/manager/report/list";
    };
}
if ($memberListBtn) {
    $memberListBtn.onclick = function () {
        location.href = "/manager/member/list";
    };
}
if ($logoutBtn) {
    $logoutBtn.onclick = function () {
        location.href = "/member/logout";
    };
}
if ($testHubBtn) {
    $testHubBtn.onclick = function () {
        location.href = "/common/testhub";
    };
}
if ($backBtn) {
    $backBtn.onclick = function () {
        window.history.back();
    };
}