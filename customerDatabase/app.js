//Allowing our Server to Require, mysql, express, and body-parser
const mysql = require("mysql");
const express = require("express");
const bodyParser = require("body-parser");
const app = express();

//Credentials/database to create a connection to mySQl
const config = {
  host: "localhost",
  user: "sqluse",
  password: "password",
  database: "users",
};

//Creating a connection to mysql database
const connection = mysql.createConnection(config);
connection.connect((err) => {
  if (err) {
    console.error("Error connecting to database:", err);
    return;
  }
  console.log("Connected to MySQL database");
});

//Using our main files for Assignment from our folder
app.use(express.static("public"));
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());



//Using CRUD for Retrieving Name and UserId
app.get('/', (req, res) => {
  const firstName = req.query.name; //requesting name from browser
  const userID = req.query.UserID;  //requesting userId from browser

  if (firstName) {
    //The Sql query to search for name and address
    const sql = `SELECT u.Title, u.FirstName, u.Surname, u.Mobile,u.EmailAddress,
    home.AddressType AS HomeAddressType, home.AddressLine1 AS HomeAddressLine1,
    home.AddressLine2 AS HomeAddressLine2,home.Town AS HomeTown,
    home.CountyCity AS HomeCountyCity,home.Eircode AS HomeEircode,
    shipping.AddressType AS ShippingAddressType, shipping.AddressLine1 AS ShippingAddressLine1,
    shipping.AddressLine2 AS ShippingAddressLine2, shipping.Town AS ShippingTown,
    shipping.CountyCity AS ShippingCountyCity,shipping.Eircode AS ShippingEircode
    FROM USERS u LEFT JOIN ADDRESSES home ON u.UserID = home.UserID AND home.AddressType = 'Home'
    LEFT JOIN ADDRESSES shipping ON u.UserID = shipping.UserID AND shipping.AddressType = 'Shipping'
    WHERE u.FirstName = ?`;

    connection.query(sql, [firstName], (err, results) => { //sending our query to mysql 
      if (err) {
        console.error("Error searching for customer:", err);
        return res.status(500).send("Server Error");
      }
      if (results.length === 0) {
        return res.status(404).json({ message: "Customer not found" }); // Return JSON with a message
      }

      res.json(results); // Return JSON data with results if name found
    });
  }
  else if (userID) {
    //The Sql query to search for UserId and address and so we can send it to front-end as a Json file
    const sql = `SELECT u.Title, u.FirstName, u.Surname, u.Mobile, u.EmailAddress,
    home.AddressType AS HomeAddress, home.AddressLine1 AS HomeAddressLine1, home.AddressLine2 AS HomeAddressLine2,
    home.Town AS HomeTown, home.CountyCity AS HomeCountyCity,home.Eircode AS HomeEircode,
    shipping.AddressType AS ShippingAddress, shipping.AddressLine1 AS ShippingAddressLine1,
    shipping.AddressLine2 AS ShippingAddressLine2, shipping.Town AS ShippingTown,
    shipping.CountyCity AS ShippingCountyCity, shipping.Eircode AS ShippingEircode
    FROM USERS u LEFT JOIN ADDRESSES home ON u.UserID = home.UserID AND home.AddressType = 'Home'
    LEFT JOIN ADDRESSES shipping ON u.UserID = shipping.UserID AND shipping.AddressType = 'Shipping'
    WHERE u.UserID = ?`;
    connection.query(sql, [userID], (err, results) => { //sending our query to mysql
      if (err) {
        console.error("Error searching for customer:", err);
        return res.status(500).send("Internal Server Error");
      }
      res.json(results); // Return JSON data with results if userID found
    });
  }
  else {
    res.sendFile(__dirname + "/public/assignment-04.html"); //load our main files from folder
  }
});




//CRUD FOR CREATE
function createUser(user, shippingAddress, homeAddress, callback) {
  //Inserting our our user details 
  const userSql = "INSERT INTO users (Title, FirstName, Surname, Mobile, EmailAddress) VALUES (?, ?, ?, ?, ?)";
  const values = [user.Title, user.FirstName, user.Surname, user.Mobile, user.EmailAddress,];

  connection.query(userSql, values, (err, result) => { //sending our query to mysql
    if (err) {
      console.error("Error creating user:", err);
      return callback(err);
    }
    console.log("User created successfully");

    const userId = result.insertId; // Get the our generated user ID

    //Insert data to our shipping address
    const shippingSql = "INSERT INTO addresses (UserID, AddressType, AddressLine1, AddressLine2, Town, CountyCity, Eircode) VALUES (?, ?, ?, ?, ?, ?, ?)";
    const shippingValues = [userId, "Shipping", shippingAddress.AddressLine1, shippingAddress.AddressLine2, shippingAddress.Town,
      shippingAddress.CountyCity, shippingAddress.Eircode,];

    connection.query(shippingSql, shippingValues, (err) => { //sending our query to mysql
      if (err) {
        console.error("Error creating shipping address:", err);
        return callback(err);
      }
      console.log("Shipping address created successfully");

      //Inserting data to our home address
      const homeSql = "INSERT INTO addresses (UserID, AddressType, AddressLine1, AddressLine2, Town, CountyCity, Eircode) VALUES (?, ?, ?, ?, ?, ?, ?)";
      const homeValues = [userId, "Home", homeAddress.AddressLine1, homeAddress.AddressLine2, homeAddress.Town, homeAddress.CountyCity, homeAddress.Eircode,];

      connection.query(homeSql, homeValues, (err) => { //sending our query to mysql
        if (err) {
          console.error("Error creating home address:", err);
          return callback(err);
        }
        console.log("Home address created successfully");
        callback(null);
      });
    });
  });
}

//Handle POST requests to create a new customer using express
app.post("/create", (req, res) => {
  //Requesting values form browser for our user
  const newUser = {
    Title: req.body.title,
    FirstName: req.body.firstName,
    Surname: req.body.surname,
    Mobile: req.body.mobile,
    EmailAddress: req.body.emailAddress,
  };
  //Requesting values form browser for our shipping address
  const shippingAddress = {
    AddressLine1: req.body.shippingAddressLine1,
    AddressLine2: req.body.shippingAddressLine2,
    Town: req.body.shippingTown,
    CountyCity: req.body.shippingCountyCity,
    Eircode: req.body.shippingEircode,
  };
  //Requesting values form browser for our home address
  const homeAddress = {
    AddressLine1: req.body.homeAddressLine1,
    AddressLine2: req.body.homeAddressLine2,
    Town: req.body.homeTown,
    CountyCity: req.body.homeCountyCity,
    Eircode: req.body.homeEircode,
  };

  //Calling our function to create user, shipping / home address 
  createUser(newUser, shippingAddress, homeAddress, (err) => {
    if (err) {
      console.error("Error creating customer:", err);
      return res.status(500).send("Internal Server Error");
    }
    res.json("Customer created successfully");
  });
});



//Function to update our user similar how we created a user but now updating the user
function updateUser(user, callback) {
  const userSql = `UPDATE users SET Title = ?, FirstName = ?, Surname = ?, Mobile = ?, EmailAddress = ? WHERE UserID = ?;`;
  const userValues = [user.Title, user.FirstName, user.Surname, user.Mobile, user.EmailAddress, user.UserID];

  const shippingSql = `UPDATE addresses SET AddressLine1 = ?, AddressLine2 = ?, Town = ?, CountyCity = ?, Eircode = ? WHERE UserID = ? AND AddressType = 'Shipping';`;
  const shippingValues = [user.ShippingAddressLine1, user.ShippingAddressLine2, user.ShippingTown, user.ShippingCountyCity, user.ShippingEircode, user.UserID];

  const homeSql = `UPDATE addresses SET AddressLine1 = ?, AddressLine2 = ?, Town = ?, CountyCity = ?, Eircode = ? WHERE UserID = ? AND AddressType = 'Home';`;
  const homeValues = [user.HomeAddressLine1, user.HomeAddressLine2, user.HomeTown, user.HomeCountyCity, user.HomeEircode, user.UserID];

  connection.query(userSql, userValues, (err) => {
    if (err) {
      console.error("Error updating user:", err);
      return callback(err);
    }

    connection.query(shippingSql, shippingValues, (err) => {
      if (err) {
        console.error("Error updating shipping address:", err);
        return callback(err);
      }

      connection.query(homeSql, homeValues, (err) => {
        if (err) {
          console.error("Error updating home address:", err);
          return callback(err);
        }

        console.log("User and addresses updated successfully");
        callback(null);
      });
    });
  });
}

//Handle POST requests to update customer using express 
app.post("/update", (req, res) => {
  const data = {
    Title: req.body.Title,
    FirstName: req.body.FirstName,
    Surname: req.body.Surname,
    Mobile: req.body.Mobile,
    EmailAddress: req.body.EmailAddress,
    UserID: req.body.UserID,
    ShippingAddressLine1: req.body.ShippingAddressLine1,
    ShippingAddressLine2: req.body.ShippingAddressLine2,
    ShippingTown: req.body.ShippingTown,
    ShippingCountyCity: req.body.ShippingCountyCity,
    ShippingEircode: req.body.ShippingEircode,
    HomeAddressLine1: req.body.HomeAddressLine1,
    HomeAddressLine2: req.body.HomeAddressLine2,
    HomeTown: req.body.HomeTown,
    HomeCountyCity: req.body.HomeCountyCity,
    HomeEircode: req.body.HomeEircode
  };
  //calling our function and passing in our data
  updateUser(data, (err) => {
    if (err) {
      console.error("Error updating user and addresses:", err);
      return res.status(500).json({ error: "Error updating user and addresses", message: err.message });
    }
    console.log("User and addresses updated successfully");
    res.json("User and addresses updated successfully");
  });
});





function deleteUser(email, phone, name, callback) {
  //Selecting a userId that matches all 3 inputs email, phone, name so we can delete all records
  const sqlId = `SELECT UserID FROM users WHERE EmailAddress = ? AND Mobile = ? AND FirstName = ?`;

  connection.query(sqlId, [email, phone, name], (err, rows) => {
    if (err) {
      console.error("Error getting user ID:", err);
      callback(err);
      return;
    }

    if (rows.length === 0) {
      callback(new Error("User not found"));
      return;
    }

    const userID = rows[0].UserID;
    const deleteAddressesSql = `DELETE FROM addresses WHERE UserID = ?`;

    connection.query(deleteAddressesSql, [userID], (err, result) => {
      if (err) {
        console.error("Error deleting addresses:", err);
        callback(err);
        return;
      }

      const deleteUserSql = `DELETE FROM users WHERE UserID = ?`;
      connection.query(deleteUserSql, [userID], (err, result) => {
        if (err) {
          console.error("Error deleting user:", err);
          callback(err);
          return;
        }
        console.log("User deleted successfully");
        callback(null, result);
      });
    });
  });
}


//Our Requests for deleting users
app.delete("/delete", (req, res) => {
  const email = req.body.email;
  const phone = req.body.phone;
  const name = req.body.name;

  if (!email || !phone || !name) {
    return res.status(400).json({ error: "Please provide all 3 fields: email, phone, and name" });
  }
  deleteUser(email, phone, name, (err) => {
    if (err) {
      return res.status(500).json({ error: "Internal Server Error" });
    }
    res.json("Deleted successfully");
  });
});


// Starting the server
app.listen(3000, () => {
  console.log("Server is running on port 3000");
});






