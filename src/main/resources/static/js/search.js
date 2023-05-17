const searchInput = document.getElementById('searchInput');
const contacts = document.querySelectorAll('.items-div');

searchInput.addEventListener('input', (event) => {
    const searchTerm = event.target.value.toLowerCase();

    contacts.forEach((contact) => {
        const label = contact.querySelector('.form-check-label');
        const name = label.textContent.toLowerCase();
        const checkbox = contact.querySelector('.form-check-input');

        if (name.includes(searchTerm)) {
            contact.style.display = 'block';
            checkbox.disabled = false;
        } else {
            contact.style.display = 'none';
            checkbox.disabled = true;
        }
    });
});