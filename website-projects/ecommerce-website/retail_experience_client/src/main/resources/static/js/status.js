let oldPercentage = 0;
let oldIndexStatusList = 0;
const baseUrlPath = "http://localhost:8081/status/refresh/";

const progressBarNotFull = document.getElementById("progress_bar_not_full")
const progressBarAnimated = document.getElementById("progress-bar-animated");
const progressBarText = document.getElementById("progress_span_text_id");
const progressBarFull = document.getElementById("progress_bar_full")

setInterval(refreshStatusCallback, 500);

function refreshStatusCallback() {
    let orderId = getOderIdFromUrl();
    if (orderId !== "search") {
        $.ajax({
            url: baseUrlPath + orderId
        }).then(function (data) {
            if (data.percentage > oldPercentage) {
                oldPercentage = data.percentage;
                refreshProgressBar(data.percentage)
                refreshStatusContent(data.statusList)
            }
        });
    }
}

function getOderIdFromUrl() {
    let oderIdFound;
    let searchParams = new URLSearchParams(window.location.search)
    if (searchParams.has('searchOrderId')) {
        oderIdFound = searchParams.get('searchOrderId')
    } else {
        let pathName = window.location.pathname
        const paths = pathName.split('/');
        oderIdFound = paths[2];
    }
    return oderIdFound;
}

function refreshProgressBar(percentage) {
    if (percentage >= 100) {
        progressBarNotFull.style.display = "none";
        progressBarFull.style.display = "block";
    } else {
        progressBarNotFull.style.display = "block";
        progressBarAnimated.style.width = percentage.toString() + "%";
        progressBarText.innerText = percentage.toString() + "%";
        progressBarFull.style.display = "none";
    }
}

function refreshStatusContent(statusList) {
    for (let i = oldIndexStatusList; i < statusList.length; i++) {
        const node = document.createElement("p");
        node.style.margin = "0";
        const text = document.createTextNode(statusList[i]);
        node.appendChild(text);
        document.getElementById("result_content").appendChild(node);
        oldIndexStatusList++;
    }
}
