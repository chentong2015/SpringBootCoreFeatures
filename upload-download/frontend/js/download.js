async function loadFiles() {
    try {
        // response data format is List<String>
        const response = await fetch('http://localhost:8080/v1/file/all', {method: 'GET'});
        if (response.ok) {
            const data = await response.json();
            renderList(data);
        }
    } catch (error) {
        console.error(error);
    }
}

function renderList(files) {
    const ul = document.getElementById('fileList');
    ul.innerHTML = '';
    if (!files || files.length === 0) {
        ul.innerHTML = '-- No files --';
        return;
    }

    files.forEach(file => {
        const li = document.createElement('li');
        li.textContent = file;
        li.style.cursor = "pointer";
        li.style.color = "#128ff2";
        li.onclick = function () {
            downloadFile(file);
        };
        ul.appendChild(li);
    });
}

// TODO. 标准资源文件下载的JS前端实现
function downloadFile(fileName) {
    fetch(`http://localhost:8080/v1/file/download/${encodeURIComponent(fileName)}`, {method: 'GET',})
        .then(response => {
            if (!response.ok) {
                throw new Error("Download failed: " + response.status);
            }
            return response.blob(); // 转成二进制
        }).then(blob => {
            const objectUrl = window.URL.createObjectURL(blob);
            const anchor = document.createElement('a');
            anchor.href = objectUrl;
            anchor.download = fileName;
            document.body.appendChild(anchor);
            anchor.click();

            anchor.remove();
            window.URL.revokeObjectURL(objectUrl);
        }).catch(err => {
            console.error(err);
        });
}