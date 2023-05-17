function clearFields(ids) {
    ids.forEach(id => document.getElementById(id).value = "")
}

function confirmDelete() {
    return confirm("Êtes-vous sûr ??");
}

