//FUNCTION FOR SEARCHING USER firstName
const searchBtn = document.getElementById("searchBtn");
searchBtn.addEventListener("click", function () {
  let searchInput = document.getElementById("searchInput").value;
  searchCustName(searchInput);
}); //Search button is clicked, calls function to search Name, passing in the value of the text inputed

function searchCustName(firstName) {
  if (firstName == "") {  //Checks if string is empty prints message
    let custDetails = document.getElementById("custDetails");
    custDetails.innerHTML = "<p>Please type first name</p>";
    return;
  }
  const xhttp = new XMLHttpRequest(); //creating object to fetch data from our server-side
  xhttp.onload = function () {
    const response = JSON.parse(this.responseText); //Changes our json file into a javascript object
    if (response.message) {
      document.getElementById("custDetails").innerHTML = `<p>${response.message}</p>`; //prints message from send from server
    } else {
      // Creating a html table from data received in a json file to show user in our browser
      let html = "<table>";
      html += "<tr><th>Title</th><th>First Name</th><th>Surname</th><th>Mobile</th><th>Email Address</th></tr>";
      response.forEach((row) => {
        html += "<tr>";
        html += `<td>${row.Title}</td>`;
        html += `<td>${row.FirstName}</td>`;
        html += `<td>${row.Surname}</td>`;
        html += `<td>${row.Mobile}</td>`;
        html += `<td>${row.EmailAddress}</td>`;
        html += "</tr>";
        html += "<tr><th>Address Type</th><th>Address Line 1</th><th>Address Line 2</th><th>Town</th><th>County/City</th><th>Eircode</th></tr>";
        html += "<tr>";
        html += `<td>${row.HomeAddressType}</td>`;
        html += `<td>${row.HomeAddressLine1}</td>`;
        html += `<td>${row.HomeAddressLine2}</td>`;
        html += `<td>${row.HomeTown}</td>`;
        html += `<td>${row.HomeCountyCity}</td>`;
        html += `<td>${row.HomeEircode}</td>`;
        html += "</tr>";
        html += "<tr>";
        html += `<td>${row.ShippingAddressType}</td>`;
        html += `<td>${row.ShippingAddressLine1}</td>`;
        html += `<td>${row.ShippingAddressLine2}</td>`;
        html += `<td>${row.ShippingTown}</td>`;
        html += `<td>${row.ShippingCountyCity}</td>`;
        html += `<td>${row.ShippingEircode}</td>`;
        html += "</tr>";
      });
      html += "</table>";
      document.getElementById("custDetails").innerHTML = html; //displays results in our browser
    }
  };
  xhttp.open("GET", `/?name=${firstName}`); // getting the value of name
  xhttp.send(); //our request to the server
}






//FUNCTION FOR CREATING USER
//Variables for buttons, adding user, submitting info and displaying info in form
const addCustBtn = document.getElementById("addCustBtn");
const custForm = document.getElementById("customerForm");
const submitBtn = document.getElementById("submitCust");

addCustBtn.addEventListener("click", function () {
  custForm.style.display = "block"; //when btn clicked it shows form details to create a new customer
  submitBtn.disabled = false;       //allows us to submit the form once again
});
submitBtn.addEventListener("click", function () {
  submitBtn.disabled = true; //once form is submitted it prevents to many submissions sent to our server-side
  custForm.style.display = "none"; //hides our form details
  createCustomer();
});
function createCustomer() {
  //Retrieving values from customer details within the browser
  const title = document.getElementById("title").value;
  const firstName = document.getElementById("firstName").value;
  const surname = document.getElementById("surname").value;
  const mobile = document.getElementById("mobile").value;
  const emailAddress = document.getElementById("emailAddress").value;

  //Retrieving values from home address details
  const homeAddressLine1 = document.getElementById("homeAddressLine1").value;
  const homeAddressLine2 = document.getElementById("homeAddressLine2").value;
  const homeTown = document.getElementById("homeTown").value;
  const homeCountyCity = document.getElementById("homeCountyCity").value;
  const homeEircode = document.getElementById("homeEircode").value;

  // Retrieving values from shippind address details
  const shippingAddressLine1 = document.getElementById("shippingAddressLine1").value;
  const shippingAddressLine2 = document.getElementById("shippingAddressLine2").value;
  const shippingTown = document.getElementById("shippingTown").value;
  const shippingCountyCity = document.getElementById("shippingCountyCity").value;
  const shippingEircode = document.getElementById("shippingEircode").value;

  //Checking if the required information is inputted by the user
  if (!firstName || !surname || !mobile || !emailAddress || !shippingAddressLine1 ||
    !shippingTown || !shippingCountyCity || !homeAddressLine1 || !homeTown || !homeCountyCity) {
    alert("Please Fill all required data"); //alerts in browser to input data
    return;
  }

  //Checking information inputted in our Title matches our desired inputs.
  const valTitle = ["Mx", "Ms", "Mr", "Mrs", "Miss", "Dr"];
  if (!valTitle.includes(title.trim())) {
    alert("Please provide a valid title (Mx, Ms, Mr, Mrs, Miss, Dr)"); //alerts in browser to input data
    return;
  }

  //Building our data so it can be send to our JSON file
  const formData = {
    title: title, firstName: firstName, surname: surname, mobile: mobile, emailAddress: emailAddress,

    shippingAddressLine1: shippingAddressLine1, shippingAddressLine2: shippingAddressLine2,
    shippingTown: shippingTown, shippingCountyCity: shippingCountyCity, shippingEircode: shippingEircode,

    homeAddressLine1: homeAddressLine1, homeAddressLine2: homeAddressLine2,
    homeTown: homeTown, homeCountyCity: homeCountyCity, homeEircode: homeEircode
  };

  const xhttp = new XMLHttpRequest(); //creating an object instance to perform requests from browser
  xhttp.onload = function () { //executes our request and send response text received from server if created succesfully
    document.getElementById("addNewCustDetails").innerHTML = this.responseText;
    submitBtn.disabled = false;
  };
  xhttp.open("POST", "/create", true); //initializes the request 
  xhttp.setRequestHeader("Content-Type", "application/json"); //sets a request Header indicating our content is in Json format
  xhttp.send(JSON.stringify(formData)); //sends the request to our server
}


//FUNCTION FOR UPDATING USER
const searchID = document.getElementById("searchCustID");
searchID.addEventListener('click', function () {
  let userID = document.getElementById("updateInput").value;
  saveChanges.style.display = "block";
  searchCustID(userID);
});
//Searching userID from server-side so it can send back information to be edited/updated
function searchCustID(userID) {
  const xhttp = new XMLHttpRequest();
  xhttp.onload = function () { //when the information receiveds as a Json it gets formated in formatData function so it looks like a form
    const responseData = JSON.parse(this.responseText);
    const formattedData = formatData(responseData);
    document.getElementById("updDetails").innerHTML = formattedData;
  };
  xhttp.open("GET", `/?UserID=${userID}`); //the server route 
  xhttp.send(); //sending our request
}

//Receiving our data from server-side and formating it as a form for each value
function formatData(data) {
  let formattedData = '<form id="updateForm">';
  data.forEach(obj => {
    for (const value in obj) {
      formattedData += `<div><label for="${value}">${value}</label>: <input type="text" id="${value}" name="${value}" value="${obj[value]}"></div>`;
    }
  });
  formattedData += '</form>';
  return formattedData;
}




//Calling function to update our Customer and send back our changes as a json format
const saveChanges = document.getElementById('saveChanges');
saveChanges.addEventListener('click', function () {
  let userID = document.getElementById("updateInput").value;
  updateCustomer(userID);
  saveChanges.style.display = "none";
});

//Updating the customer and getting each value from our form
function updateCustomer(userID) {
  const updateForm = document.getElementById("updateForm");
  const formData = new FormData(updateForm);

  const updated = {};
  formData.forEach((value, key) => {
    updated[key] = value;
  });

  updated.UserID = userID;
  const xhttp = new XMLHttpRequest();
  xhttp.onload = function () {
    document.getElementById("updDetails").innerHTML = this.responseText;
  };
  xhttp.open("POST", "/update");
  xhttp.setRequestHeader("Content-Type", "application/json");
  xhttp.send(JSON.stringify(updated));
  console.log(updated);
}





//CRUD ------ FUNCTION FOR DELETING
const deleteBtn = document.getElementById("deleteBtn"); //button to show form so user can be deleted
const deleteCust = document.getElementById('deleteCust'); //delete user 

searchToDelete.addEventListener('click', function () {
  deleteCust.style.display = "block"; //shows our 3 inputs once clicked
});

deleteBtn.addEventListener('click', function () {
  const deleteEmail = document.getElementById("deleteEmail").value; //all of our 3 values 
  const deletePhone = document.getElementById("deletePhone").value;
  const deleteName = document.getElementById("deleteName").value;
  deleteCustomer(deleteEmail, deletePhone, deleteName);
});

function deleteCustomer(deleteEmail, deletePhone, deleteName) {
  if (!deleteEmail || !deletePhone || !deleteName) { //checking if they all have been inputted
    const deleteDetails = document.getElementById("deleteDetails");
    deleteDetails.innerHTML = "<p>Please enter all 3 fields</p>";
    return;
  }
  const xhttp = new XMLHttpRequest();
  xhttp.onload = function () {
    document.getElementById("deleteDetails").innerHTML = this.responseText; //showing in browser our message from server if user has been deleted or error
  };
  xhttp.open("DELETE", "/delete");
  xhttp.setRequestHeader("Content-Type", "application/json");
  xhttp.send(JSON.stringify({ email: deleteEmail, phone: deletePhone, name: deleteName })); //sending data as a Json
}


