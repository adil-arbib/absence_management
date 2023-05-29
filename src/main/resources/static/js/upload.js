document.addEventListener("DOMContentLoaded", function() {
    const fileInput = document.getElementById("file");
    const previewImage = document.getElementById("preview");
    const defaultImage = document.getElementById("defaultImg");

    fileInput.addEventListener("change", function() {
        const file = fileInput.files[0];

        if (file && (file.type === "image/png" || file.type === "image/jpeg")) {
            const reader = new FileReader();

            reader.onload = function(e) {
                previewImage.src = e.target.result;
            };

            reader.readAsDataURL(file);
        } else {
            previewImage.src = "";
        }
    });
});