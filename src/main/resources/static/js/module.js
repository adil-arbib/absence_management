const submitButton = document.getElementById("module-add")


const myList = /*[[${listElements}]]*/ [];
console.log(myList)

// const input = document.getElementById("value-input")
// const button = document.getElementById("addBtn")
// const divContainer = document.getElementById("div-container")
// const saveBtn = document.getElementById("save-btn")
// const valList = document.getElementById("valList")
// const valList2 = document.getElementById("valList2")
// var myList = [[${myList}]] ;
//

//
// const db = ["spring boot", "spring", "react js ", "python", "C++"]
//
// // saveBtn.addEventListener("click", () => {
// //     valList2.innerHTML = ""
// //     valList.innerHTML = ""
// //     valArr.forEach((val) => {
// //         const listItem = document.createElement("li")
// //         listItem.innerText = val
// //         valList2.appendChild(listItem)
// //     })
// // })
//
// input.addEventListener("input", () => {
//     valList.innerHTML = ""
//
//     if (input.value === "") {
//         valList.innerHTML = ""
//         filteredDb = ""
//     }
//
//     const inputValue = input.value.trim().toLowerCase()
//     let filteredDb = db.filter((item) => item.toLowerCase().includes(inputValue))
//
//     //console.log(filteredDb)
//
//     filteredDb.forEach((item) => {
//         const listItem = document.createElement("li")
//         listItem.innerText = item
//
//         listItem.addEventListener("click", () => {
//             const childDiv = document.createElement("div")
//             childDiv.innerText = item
//             valArr.push(item)
//             //console.log(valArr)
//
//             const deleteButton = document.createElement("button")
//             deleteButton.innerText = "x"
//             deleteButton.classList.add("delete-button")
//
//             deleteButton.addEventListener("click", () => {
//                 const index = valArr.indexOf(item)
//
//                 valArr.splice(index, 1)
//                 divContainer.removeChild(childDiv)
//             })
//
//             childDiv.appendChild(deleteButton)
//             divContainer.appendChild(childDiv)
//
//             input.value = ""
//         })
//         valList.appendChild(listItem)
//         filteredDb = []
//     })
// })
//
//
// submitButton.addEventListener("click", function() {
//     let selectedItemsData = JSON.stringify(selectedItems);


// $.ajax({
//         type: "POST",
//         url: "/modules/create",
//         data: selectedItemsData,
//         contentType: "application/json",
//         success: function() {
//             alert("Items submitted successfully");
//         },
//         error: function() {
//             alert("Error submitting items");
//         }
//     });
// })