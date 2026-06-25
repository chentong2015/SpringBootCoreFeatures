const singleUploadForm = document.querySelector('#singleUploadForm');
const singleFileUploadInput = document.querySelector('#singleFileUploadInput');

const multipleUploadForm = document.querySelector('#multipleUploadForm');
const multipleFileUploadInput = document.querySelector('#multipleFileUploadInput');

const fileUploadError = document.querySelector('#fileUploadError');
const fileUploadSuccess = document.querySelector('#fileUploadSuccess');

singleUploadForm.addEventListener('submit', function(event){
    const files = singleFileUploadInput.files;
    if(files.length === 0) {
        fileUploadError.innerHTML = "Please select a file";
        fileUploadSuccess.style.display = "block";
    }
    uploadSingleFile(files[0]);
    event.preventDefault();
}, true);

function uploadSingleFile(file) {
    const formData = new FormData();
    formData.append("file", file);

    const xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.open("POST", "/v1/file/upload/single");
    xmlHttpRequest.onload = function () {
        handleUploadResponse(xmlHttpRequest);
    };
    xmlHttpRequest.send(formData);
}

multipleUploadForm.addEventListener('submit', function(event){
    const files = multipleFileUploadInput.files;
    if(files.length === 0) {
        fileUploadError.innerHTML = "Please select at least one file";
        fileUploadSuccess.style.display = "block";
    }
    uploadMultipleFiles(files);
    event.preventDefault();
}, true);

function uploadMultipleFiles(files) {
    const formData = new FormData();
    for(let index = 0; index < files.length; index++) {
        formData.append("files", files[index]);
    }
    const xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.open("POST", "/v1/file/upload/multiple");

    xmlHttpRequest.onload = function () {
        handleUploadResponse(xmlHttpRequest);
    };
    xmlHttpRequest.send(formData);
}

function handleUploadResponse(xmlHttpRequest) {
    console.log(xmlHttpRequest.responseText);
    const response = JSON.parse(xmlHttpRequest.responseText);

    if (xmlHttpRequest.status === 200) {
        fileUploadError.style.display = "none";
        fileUploadSuccess.innerHTML = "<p>All Files Uploaded Successfully</p>";
        fileUploadSuccess.style.display = "block";
    } else {
        fileUploadSuccess.style.display = "none";
        fileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
    }
}