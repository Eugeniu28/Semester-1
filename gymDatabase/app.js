const { MongoClient, ServerApiVersion } = require("mongodb");
const express = require("express");
const bodyParser = require("body-parser");

const uri = "insertAPI";
const app = express();

//Creating a connection our MongoDB
const client = new MongoClient(uri, {
    serverApi: {
        version: ServerApiVersion.v1,
        strict: true,
        deprecationErrors: true,
    },
});

async function run() {
    try {
        await client.connect(); //Start our MongoDB database
        console.log("Connected to MongoDB!");
    } catch (err) {
        console.error("Error connecting to MongoDB:", err);
    }
}
run().catch(console.dir);

//Using our main files for Assignment from our folder
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
const { ObjectId } = require("mongodb");

//Route for Finding Member
app.get("/member", async (req, res) => {
    try {
        const db = client.db("gym"); // Using the database called gym
        const collection = db.collection("gymMember"); // Collection name gymMember to store member details

        // const memberID = req.body._id; //retrieves our ID from Mongo Database Gym/gymMember
        // const memberIdOBJ = new ObjectId(memberID); //It converts the id into ObjectId

        const userId = parseInt(req.body.MemberID);
        const result = await collection.findOne({ MemberID: userId }); //Retrieves the member matching our ID

        if (!result) {
            //If no Member found / error
            return res.status(404).json({ error: "Member not found" });
        }

        //Prints the Member Details in our Terminal
        console.log("Member Details:");
        console.log("ObjectId:", result._id);
        console.log("Customer Id:", result.MemberID);
        console.log("Tile: ", result.Title);
        console.log("Name:", result.FirstName, result.LastName);
        console.log("Email:", result.EmailAddress);
        console.log("PremiumMemberShip:", result.PremiumMemberShip);
        //console.log(result); //print our details as Json file
        res.json(result); //return details as a JSON response
    } catch (err) {
        //Handle any errors during code execution and logs / returns errors
        console.error("Error retrieving random customer:", err);
        res.status(500).send("Server Error");
    }
});

//Route for Finding Gym Classes
app.get("/class", async (req, res) => {
    try {
        const db = client.db("gym"); //Database Name
        const collection = db.collection("gymClass"); // Collection name

        //Returns only 10 classes in an array
        const result = await collection.find().limit(10).toArray();
        console.log("Classes successfully retrieved");
        res.json(result); //10 classes send back as JSON response
    } catch (err) {
        console.error("Error retrieving Classes:", err);
        res.status(500).send("Server Error");
    }
});

//Route for Finding Member-Class Information
app.get("/memberClassInfo", async (req, res) => {
    try {
        const db = client.db("gym"); //database name
        const collection = db.collection("memberClassInfo"); //collection name

        const count = await collection.countDocuments(); //counting all our Json objects
        const randomIndex = Math.floor(Math.random() * count); //Pick a random class-Info to return

        const result = await collection.find().skip(randomIndex).limit(1).next();


        console.log("Member-Class Information successfully retrieved");
        console.log(result);
        res.json(result); //Returns it a Json Response
    } catch (err) {
        console.error("Error retrieving Member-Class Information:", err);
        res.status(500).send("Server Error");
    }
});

let memberID = 1;
// Route for Creating a Gym Member
app.post("/create/member", async (req, res) => {
    try {
        const db = client.db("gym"); //database name
        const collection = db.collection("gymMember"); //collection name

        let newMemberID = memberID++; //Everytime a member is created an id is also created for them.

        //Validate MemberShip as a boolean Value
        const isPremium =
            req.body.PremiumMemberShip === true ||
            req.body.PremiumMemberShip === false;

        // Creating a new member
        const newMember = {
            MemberID: newMemberID,
            Title: req.body.Title,
            FirstName: req.body.FirstName,
            LastName: req.body.LastName,
            EmailAddress: req.body.EmailAddress,
            PremiumMemberShip: isPremium,
        };

        // Inserting the new Member into the collection
        const result = await collection.insertOne(newMember);
        console.log("Member created succesfully:", result.insertedId);
        res.json(result);
    } catch (err) {
        console.error("Error creating Member:", err);
        res.status(500).json({ error: "Internal Server Error" });
    }
});

let classID = 1;
// Route for Creating a Gym Class
app.post("/create/class", async (req, res) => {
    try {
        const db = client.db("gym"); //database name
        const collection = db.collection("gymClass"); // Collection name

        // Validating our values to integers
        const sessionLength = parseInt(req.body.SessionLength);
        const price = parseInt(req.body.Price);
        const currentMembers = parseInt(req.body.CurrentMembers);

        let newClassID = classID++; //Creating an id everytime a class is added

        // Creating a new Gym Class
        const newClass = {
            ClassID: newClassID,
            ClassName: req.body.ClassName,
            ClassDay: req.body.ClassDay,
            SessionLength: sessionLength,
            Price: price,
            CurrentMembers: currentMembers,
        };

        // Inserting the new gymClass into the collection
        const result = await collection.insertOne(newClass);
        console.log("GymClass created succesfully:", result.insertedId);
        res.json(result);
    } catch (err) {
        console.error("Error GymClass phone:", err);
        res.status(500).json({ error: "Internal Server Error" });
    }
});

//Route for Creating Member-Class Information
app.post("/create/memberClassInfo", async (req, res) => {
    try {
        const db = client.db("gym"); //database name
        const collection = db.collection("memberClassInfo"); //collection name

        const ClassesTaken = req.body.ClassesTaken;

        if (!ClassesTaken || ClassesTaken.length < 3) {
            console.log("Gym members must take 3 classes minimum");
            return res.status(400).json({ error: "Gym members must take 3 classes minimum" });
        }

        const CurrentClasses = ClassesTaken.map((name) => ({
            ClassID: parseInt(name.ClassID),
        }));

        const newClassInfo = {
            MemberID: parseInt(req.body.MemberID),
            CurrentClasses: CurrentClasses
        };
        const result = await collection.insertOne(newClassInfo);
        console.log("Member-Class Information Created:", result.insertedId);
        res.json(result);
    } catch (err) {
        console.error("Error creating Member-Class Information:", err);
        res.status(500).json({ error: "Internal Server Error" });
    }
});

//Route for Updating a Member
app.post("/update/member", async (req, res) => {
    try {
        const db = client.db("gym"); //Database Name
        const collection = db.collection("gymMember"); // Collection name

        const memberID = parseInt(req.body.MemberID);
        //const memberIdOBJ = new ObjectId(memberID);

        // Validate PremiumMembership
        const isPremium =
            req.body.PremiumMemberShip === true ||
            req.body.PremiumMemberShip === false;

        const updateMember = {
            Title: req.body.Title,
            FirstName: req.body.FirstName,
            LastName: req.body.LastName,
            EmailAddress: req.body.EmailAddress,
            PremiumMemberShip: isPremium,
        };

        const result = await collection.updateOne(
            { MemberID: memberID },
            { $set: updateMember }
        );
        console.log("Member Updated Succesfully");
        res.json(result);
    } catch (err) {
        console.error("Error Updating Member:", err);
        res.status(500).json({ error: "Internal Server Error" });
    }
});

//Route for Updating a Gym Class
app.post("/update/class", async (req, res) => {
    try {
        const db = client.db("gym"); //database name
        const collection = db.collection("gymClass"); // Collection name

        // Parse values to integers
        const sessionLength = parseInt(req.body.SessionLength);
        const price = parseInt(req.body.Price);
        const currentMembers = parseInt(req.body.CurrentMembers);

        const classID = parseInt(req.body.ClassID);
        //const classIdObj = new ObjectId(classID);

        const updateClass = {
            ClassName: req.body.ClassName,
            ClassDay: req.body.ClassDay,
            SessionLength: sessionLength,
            Price: price,
            CurrentMembers: currentMembers,
        };

        const result = await collection.updateOne(
            { ClassID: classID },
            { $set: updateClass }
        );
        console.log("Class Updated Succesfully");
        res.json(result);
    } catch (err) {
        console.error("Error updating Class:", err);
        res.status(500).json({ error: "Internal Server Error" });
    }
});

//Route for Updating Member-Class Information
app.post("/update/memberClassInfo", async (req, res) => {
    try {
        const db = client.db("gym"); //database name
        const collection = db.collection("memberClassInfo"); //collection name

        const infoID = parseInt(req.body.MemberID);
        const updateClassID = req.body.CurrentClasses;

        const updateClassInfo = {
            $set: {
                CurrentClasses: updateClassID.map(obj =>
                    ({ ClassID: parseInt(obj.ClassID), }))
            }
        };

        const result = await collection.updateOne(
            { MemberID: infoID },
            updateClassInfo
        );
        console.log("Member-Class Information Updated Successfully");
        res.json(result);
    } catch (err) {
        console.error("Error Updating Member-Class Information:", err);
        res.status(500).json({ error: "Internal Server Error" });
    }
});

//Route for Deleting a Member and Member-Class-Info
app.delete("/delete/member", async (req, res) => {
    const db = client.db("gym"); // Using the database
    const collection = db.collection("gymMember"); // Collection name for members
    const collection2 = db.collection("memberClassInfo"); // Collection name for Member-Class Info

    const memberId = parseInt(req.body.MemberID);
    const firstName = req.body.FirstName;
    const lastName = req.body.LastName;
    const email = req.body.EmailAddress;

    // Check if all fields are provided
    if (!memberId || !firstName || !lastName || !email) {
        return res.status(400).json({
            error: "Please provide all 4 fields: memberId, firstName, lastName, email",
        });
    }

    try {
        // Check if member exists, and all details are correct
        const existingMember = await collection.findOne({
            MemberID: memberId,
            FirstName: firstName,
            LastName: lastName,
            EmailAddress: email
        });

        // If member does not exist or details do not match
        if (!existingMember) {
            return res.status(404).json({ error: "Member not found or details do not match" });
        }

        const delMember = {
            MemberID: memberId
        };

        const result = await collection.deleteOne(delMember);

        if (result.deletedCount === 0) {
            return res.status(404).json({ error: "Member not found" });
        }

        // If member was deleted successfully, then delete their associated member-class info
        const delMClassInfo = {
            MemberID: memberId
        };
        const result2 = await collection2.deleteOne(delMClassInfo);

        if (result2.deletedCount === 0) {
            console.log("Member-Class Info not found for the deleted member");
        } else {
            console.log("Member-Class Info deleted successfully");
        }

        console.log("Member deleted successfully");
        return res.json({ message: "Member and Member-Class Info deleted successfully" });
    } catch (err) {
        console.error("Error deleting Member:", err);
        return res.status(500).json({ error: "Error deleting Member", message: err.message });
    }
});


//Route for Deleting a Class
app.delete("/delete/class", async (req, res) => {
    const db = client.db("gym"); //database name
    const collection = db.collection("gymClass"); // Collection name
    const collection2 = db.collection("memberClassInfo"); // Collection name for Member-Class Info

    const ClassID = parseInt(req.body.ClassID);
    const ClassName = req.body.ClassName;

    // Check if all fields are provided
    if (!ClassID || !ClassName) {
        return res.status(400).json({
            error: "Please provide all 3 fields: ClassID and ClassName",
        });
    }

    try {
        const existingClass = await collection.findOne({
            ClassID: ClassID,
            ClassName: ClassName,
        })

        // If Class does not exist or details do not match
        if (!existingClass) {
            return res.status(404).json({ error: "Class not found or details do not match" });
        }

        const delClass = {
            ClassID: ClassID,
        };
        const result = await collection.deleteOne(delClass);
        if (result.deletedCount === 0) {
            return res.status(404).json({ error: "Class not found" });
        }
        // If class was deleted successfully, then delete their associated member-class info
        const delMClassInfo = {
            ClassID: ClassID,
        };
        const result2 = await collection2.deleteOne(delMClassInfo);

        if (result2.deletedCount === 0) {
            console.log("Member-Class Info not found for the deleted member");
        } else {
            console.log("Member-Class Info deleted successfully");
        }

        console.log("Class deleted successfully");
        return res.json("Class deleted successfully");
    } catch (err) {
        console.error("Error deleting Class:", err);
        return res
            .status(500)
            .json({ error: "Error deleting Class", message: err.message });
    }
});

// Starting the server
app.listen(3000, () => {
    console.log("Server is running on port 3000");
});
