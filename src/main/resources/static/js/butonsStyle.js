function format(cmd, value) {
    document.execCommand(cmd, false, value);
}

function saveContent() {
    const inputContent = document.querySelector('#content');
    const editor = document.querySelector('#editor');
    inputContent.value = editor.innerHTML;
}