document.addEventListener("DOMContentLoaded", function () {
  checkEachCell(); //function to check valid data
  const finalGradeBtn = document.querySelector(".finalGradeBtn"); //Once final Grade column is clicked it changes grade System
  finalGradeBtn.addEventListener("click", showFinalGrade); //calls function on click

  const addRow = document.getElementById("addRow");
  addRow.addEventListener("click", function () {
    //Calls function to add a Row and calculate average
    addRowToTable(); //add row Function
    calculateAverage(); //calculate average
  });

  const addColumn = document.getElementById("addColumn");
  addColumn.addEventListener("click", function () {
    //Calls function to add a column
    addColumnToTable(); //add Column
    calculateAverage();
  });

  const save = document.getElementById("save");
  save.addEventListener("click", function () {
    saveResults();
  });
  const restore = document.getElementById("restore");
  restore.addEventListener("click", function () {
    restoreLastSave();
  });
});

function calculateAverage() {
  let unsubmitted = 0; //keeps track of unsubmitted assignments
  const assignments = document.querySelectorAll(".mainTh.Assignment").length; //Get total count of columns that are assignments
  const rows = document.querySelectorAll(".studentTableResults tbody tr"); // gets all rows in our table

  rows.forEach((row) => {
    let sum = 0;
    let countRows = 0;
    row.querySelectorAll(".grade").forEach((cell) => {
      //checks every row with a grade result
      const content = cell.textContent.trim();
      const input = parseFloat(content);
      if (!isNaN(input) && isFinite(input)) {
        sum += input;
        countRows++; // Increment countRows for each submitted assignment
      } else if (content === "-") {
        unsubmitted++;
        cell.style.backgroundColor = "yellow";
      } else {
        cell.textContent = "-"; // If cell content is not a valid number, set to '-'
        cell.style.backgroundColor = "yellow";
      }
    });
    // Calculate the average grade for each row
    const avg = row.querySelector(".average");
    if (countRows > 0) {
      const average = Math.round(sum / assignments); // Divide sum by total number of assignments and round it
      avg.textContent = average;
      if (average < 60) {
        //Check result of average and Style it
        avg.style.color = "white";
        avg.style.backgroundColor = "red";
      } else {
        avg.style.color = ""; // Resets the font color
        avg.style.backgroundColor = ""; // Resets the background color
      }
    } else {
      avg.textContent = "-";
      avg.style.color = ""; //Resets the font color
      avg.style.backgroundColor = ""; // Resets the background color
    }
  });

  const unsubmittedA = document.getElementById("unsubmittedAssignments");
  unsubmittedA.textContent = `Unsubmitted Assignments: ${unsubmitted}`;
}

//Validating input so that its a valid number
function checkInputData(cell) {
  const whiteSpace = cell.textContent.trim(); //removes whitespace and get content for each cell
  const regex = /^[0-9]*\.?[0-9]+$/; //expression in order to match our input
  if (
    !regex.test(whiteSpace) ||
    parseFloat(whiteSpace) < 0 ||
    parseFloat(whiteSpace) > 100
  ) {
    cell.textContent = "-"; //if invalid input replace with -
    cell.style.backgroundColor = "yellow"; // change background to yellow
  }
}
//making sure every cell is checked, and average is always calculated when data is inputed
function checkEachCell() {
  const grades = document.querySelectorAll(".mainTd.grade");
  grades.forEach((cell) => {
    cell.addEventListener("input", function () {
      checkInputData(cell); //verifies if data is valid number between 0-100
      calculateAverage();
    });
  });
}

//Array to keep track of our Final Grades
const finalGradeArray = [
  [
    { num: 93, grade: "A" },
    { num: 90, grade: "A-" },
    { num: 87, grade: "B+" },
    { num: 83, grade: "B" },
    { num: 80, grade: "B-" },
    { num: 77, grade: "C+" },
    { num: 73, grade: "C" },
    { num: 70, grade: "C-" },
    { num: 67, grade: "D+" },
    { num: 63, grade: "D" },
    { num: 60, grade: "D-" },
    { num: 0, grade: "F" },
  ],
  [
    { num: 93, grade: "4.0" },
    { num: 90, grade: "3.7" },
    { num: 87, grade: "3.3" },
    { num: 83, grade: "3.0" },
    { num: 80, grade: "2.7" },
    { num: 77, grade: "2.3" },
    { num: 73, grade: "2.0" },
    { num: 70, grade: "1.7" },
    { num: 67, grade: "1.3" },
    { num: 63, grade: "1.0" },
    { num: 60, grade: "0.7" },
    { num: 0, grade: "0.0" },
  ],
  [
    { num: 93, grade: "93-100%" },
    { num: 90, grade: "90-92%" },
    { num: 87, grade: "87-89%" },
    { num: 83, grade: "83-86%" },
    { num: 80, grade: "80-82%" },
    { num: 77, grade: "77-79%" },
    { num: 73, grade: "73-76%" },
    { num: 70, grade: "70-72%" },
    { num: 67, grade: "67-69%" },
    { num: 63, grade: "63-66%" },
    { num: 60, grade: "60-62%" },
    { num: 0, grade: "<60%" },
  ],
];
let index = 0; //keep track of grade System index, able to change the gradeSystem without going out of bounds
function showFinalGrade() {
  index = (index + 1) % finalGradeArray.length; //increment our grade system
  const system = finalGradeArray[index];
  const rows = document.querySelectorAll(".studentTableResults tbody tr"); // Get all rows except the header

  rows.forEach((row) => {
    const avg = parseInt(row.querySelector(".average").textContent); //get the rows that have average
    const finalGrade = row.querySelector(".finalGrade"); //get rows in finalGrade Column

    for (let i = 0; i < system.length; i++) {
      const grade = system[i];
      if (avg >= grade.num) {
        finalGrade.textContent = grade.grade;
        break; // if matching grade it will exit the loop.
      }
    }
  });
}

function addRowToTable() {
  const tBody = document.querySelector(".studentTableResults tbody");
  const numOfAssignments = document.querySelectorAll(".mainTh").length - 4; //Exclude Student Name, Student ID, Average, Final Grade

  // Create a new row while keeping track of Assigments
  const newRow = document.createElement("tr");
  newRow.innerHTML = `
        <td contenteditable="true" class="mainTd sName">-</td>
        <td contenteditable="true" class="mainTd sID">-</td>
        ${'<td contenteditable="true" class="mainTd grade">-</td>'.repeat(
          numOfAssignments
        )}
        <td class="mainTd average">-</td>
        <td class="mainTd finalGrade"></td>
    `;
  tBody.appendChild(newRow);

  //Find the new cells inputed and calculate avg, check valid data
  newRow.querySelectorAll(".grade").forEach((cell) => {
    cell.addEventListener("input", function () {
      calculateAverage();
      checkInputData(cell);
    });
  });
}

function addColumnToTable() {
  const tableHead = document.querySelector(".studentTableResults thead tr");
  const avgIndex = Array.from(tableHead.children).findIndex((cell) =>
    cell.classList.contains("average")
  ); // Find the index of the "Average" column

  // Find the index of the last assignment column
  let lastAssignmentIndex = -1;
  for (let i = 0; i < tableHead.children.length; i++) {
    if (tableHead.children[i].classList.contains("Assignment")) {
      lastAssignmentIndex = i;
    }
  }
  if (lastAssignmentIndex === -1) {
    // If no assignment column found, insert the new column before the "Average" column
    lastAssignmentIndex = avgIndex;
  }
  // Create a new column header
  const newCol = document.createElement("th");
  newCol.classList.add("mainTh", "Assignment");
  newCol.textContent = `Assignment ${
    document.querySelectorAll(".mainTh.Assignment").length + 1
  }`; // Increment assignment number

  // Insert the new column header after the last assignment column and before the "Average" column
  tableHead.insertBefore(newCol, tableHead.children[lastAssignmentIndex + 1]);

  // Add a new cell for the new column in each row after the last assignment cell and before the "Average" cell
  const rows = document.querySelectorAll(".studentTableResults tbody tr");
  rows.forEach((row) => {
    const newCells = document.createElement("td");
    newCells.classList.add("mainTd", "grade");
    newCells.contentEditable = true;
    newCells.textContent = "-";
    row.insertBefore(newCells, row.children[lastAssignmentIndex + 1]); // Insert after the last assignment inputed

    // Attach event listener to the new cell
    newCells.addEventListener("input", function () {
      calculateAverage();
      checkInputData(cell);
    });
  });
}

function saveResults() {
  const tRows = document.querySelectorAll(".studentTableResults tbody tr"); //get rows
  const tCols = document.querySelectorAll(".studentTableResults thead th"); //get cols
  const saveRows = []; // arrays to store our values
  const saveCols = [];

  tRows.forEach((row) => {
    const rowData = []; //saving data for rows
    row.querySelectorAll(".mainTd").forEach((cell) => {
      rowData.push(cell.textContent);
    });
    saveRows.push(rowData);
  });
  tCols.forEach((column) => {
    //saving data for cols
    saveCols.push(column.textContent);
  });

  localStorage.setItem("rows", JSON.stringify(saveRows));
  localStorage.setItem("cols", JSON.stringify(saveCols));
  alert("Saved");
}
function restoreLastSave() {
  const savedRows = localStorage.getItem("rows");
  const savedCols = localStorage.getItem("cols");

  if (savedRows && savedCols) {
    const rows = JSON.parse(savedRows);
    const cols = JSON.parse(savedCols);

    // Restore rows
    const tableRows = document.querySelectorAll(
      ".studentTableResults tbody tr"
    );
    tableRows.forEach((row, index) => {
      const rowData = rows[index];
      row.querySelectorAll(".mainTd").forEach((cell, cellIndex) => {
        cell.textContent = rowData[cellIndex];
      });
    });
    // Restore columns
    const tableCols = document.querySelectorAll(
      ".studentTableResults thead th"
    );
    tableCols.forEach((col, index) => {
      col.textContent = cols[index];
    });
  }
  alert("Restored");
}

function selectStudent() {
  const tableBody = document.querySelector(".studentTableResults tbody");

  tableBody.addEventListener("click", function (event) {
    const clickedCell = event.target;
    const row = clickedCell.closest("tr");
    if (row && !row.classList.contains("titleRow")) {
      const nameCell = row.querySelector(".sName");
      if (clickedCell === nameCell) {
        // If the name cell is clicked
        if (row.classList.contains("selected")) {
          row.classList.remove("selected"); // If the row is already selected, removes colour
          row.querySelectorAll(".mainTd").forEach((cell) => {
            if (
              !cell.classList.contains("sName") &&
              !cell.classList.contains("sID") &&
              !cell.classList.contains("average") &&
              !cell.classList.contains("finalGrade")
            ) {
              cell.style.backgroundColor = "yellow"; // Applying yellow background only to assignment rows
            } else {
              cell.style.backgroundColor = ""; // Resets background color for other cells
            }
          });
        } else {
          row.classList.add("selected"); // If the row is not selected, select it
          row.querySelectorAll(".mainTd").forEach((cell) => {
            cell.style.backgroundColor = "lightgreen"; // Apply lightgreen background for all cells
          });
        }
      }
    }
  });
}
selectStudent();
