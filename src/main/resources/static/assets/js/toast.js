document.addEventListener('DOMContentLoaded', function () {
    const toast = document.getElementById('toast');

    setTimeout(function () {
        toast.classList.remove('show');
    }, 5000);
});
