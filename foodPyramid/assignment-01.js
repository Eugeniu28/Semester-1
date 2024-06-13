document.querySelectorAll(".wrap-button").forEach((wrap) => {
  const minus = wrap.querySelector(".minus");
  const plus = wrap.querySelector(".plus");
  const display = wrap.querySelector(".display");

  // Function to add Value 
  function add() {
    let currentValue = parseInt(display.value, 10); 
    display.value = currentValue + 1;
  }
  //Function to subtract value
  function subtract() {
    let currentValue = parseInt(display.value, 10); 
    if (currentValue > 0) {
      display.value = currentValue - 1;
    }
  }
  plus.addEventListener('click', add);
  minus.addEventListener('click', subtract);
});


//Function to retrieve the values from each input and display it
document.querySelector('.saveButton').addEventListener('click', function() {
  const treatsFats = document.querySelector(".treats-fats").value;
  const fatsOils = document.querySelector(".fats-oils").value;
  const meatFish = document.querySelector(".meat-fish").value;
  const diaryMilk = document.querySelector(".diary-milk").value;
  const breadPotatoe = document.querySelector(".bread-potatoe").value;
  const fruitVeg = document.querySelector(".fruit-veg").value;
  
  const resultsDisplay = document.getElementById('resultsDisplay');

  resultsDisplay.innerHTML += `<p>Treats, Fats, Sugar:  ${treatsFats}</p>`;
  resultsDisplay.innerHTML += `<p>Fats, Oils:  ${fatsOils}</p>`;
  resultsDisplay.innerHTML += `<p>Meat, Fish: ${meatFish}</p>`;
  resultsDisplay.innerHTML += `<p>Diary, Milk, Cheese: ${diaryMilk}</p>`;
  resultsDisplay.innerHTML += `<p>Bread, Cereal, Potatoes: ${breadPotatoe}</p>`;
  resultsDisplay.innerHTML += `<p>Fruit, Vegetables: ${fruitVeg}</p>`;
}); 

//Function to show today's date
function addDate(element) {
  const now = new Date();
  const day = now.getDate();
  const month = now.getMonth() + 1; 
  const year = now.getFullYear();

  element.addEventListener("click", () => {
    const userInput = prompt("Enter your date  in format DD/MM/YYYY");
    let date;
    if (userInput) {
      date = userInput;
    } else {
      date = `[Today's Date: ${day}/${month}/${year}]`;
    }
    element.innerHTML = date;
  });
  
  element.innerHTML = `[ Enter Prefered Date / Today's Date: ${day} / ${month} / ${year} ]`;
}
const showDate = document.getElementById("showDate");
addDate(showDate);

// function input date and display it 
// const dateInput = document.getElementById("dateInput");
// dateInput.addEventListener('change', (event) => {
//   const selectDate = event.target.value;
//   document.getElementById('displayDate').innerHTML = `Selected Date : ${selectDate}`;
// });

