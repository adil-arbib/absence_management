const searchInput = document.getElementById('searchInput');
const searchInput2 = document.getElementById('searchInput2');

const elementsDivs = document.querySelectorAll('.elements-div');
const enseignantsDivs = document.querySelectorAll('.enseignants-div');


searchInput.addEventListener('input', (event) => {
    const searchTerm = event.target.value.toLowerCase();

    elementsDivs.forEach((element) => {
        const label = element.querySelector('.form-check-label');
        const name = label.textContent.toLowerCase();
        const checkbox = element.querySelector('.form-check-input');

        if (name.includes(searchTerm)) {
            element.style.display = 'block';
            checkbox.disabled = false;
        } else {
            element.style.display = 'none';
            checkbox.disabled = true;
        }
    });
});

searchInput2.addEventListener('input', (event) => {
    const searchTerm = event.target.value.toLowerCase();

    enseignantsDivs.forEach((enseignant) => {
        const label = enseignant.querySelector('.form-check-label');
        const name = label.textContent.toLowerCase();
        const checkbox = enseignant.querySelector('.form-check-input');

        if (name.includes(searchTerm)) {
            enseignant.style.display = 'block';
            checkbox.disabled = false;
        } else {
            enseignant.style.display = 'none';
            checkbox.disabled = true;
        }
    });
});
