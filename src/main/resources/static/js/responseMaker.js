function MakeResponse() {
    if (memberCode) {
        return baseURL + "?memberCode=" + memberCode + "&contentsType=" + contentsType + "&index=" + index + "&range=" + range;
    } else {
        return baseURL + "?contentsType=" + contentsType + "&index=" + index + "&range=" + range;
    }
}