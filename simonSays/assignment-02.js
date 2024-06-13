// Variables for all 4 Colours
const topLeft = document.querySelector('.greenCircle');
const topRight = document.querySelector('.redCircle');
const bottomLeft = document.querySelector('.yellowCircle');
const bottomRight = document.querySelector('.blueCircle');
const colorsArray = [topLeft, topRight, bottomLeft, bottomRight]; //Array to store all Colours

//Variables for the Score & HighScore
const highScoreA = document.querySelector('#highScore');
const currentScore = document.querySelector('#currentScore');
const flashRedGreen = document.querySelector('#flashRedGreen');

//Arrays to keep track store randomColors Picked and store when User Clicks buttons.
const randomArray = [];
const userArray = [];
let score = 0; //Keep track and show score as game progresses
let round = 0; //Keep track of the round
let highScore = 0;
let playingGame = false;
let flashSpeed;//Variable for to keep the timing of flashing speed

//Clicking button Start & game starts after 3 seconds
const start = document.querySelector('#start');
start.addEventListener('click', function () {
    flashRedGreen.style.backgroundColor = 'green'; //Change color from red to Green to let user game has started
    setTimeout(startGame, 3000); //3000 is micro-second, calling the startGame function
    //If we call function startGame() with (), timeout won't apply it will start immediately
});

//Function to start Game
function startGame() {
    start.disabled = true; //Disabling the start button while user is playing
    playingGame = true;    //Keep track if game is still playing
    currentScore.textContent = score < 10 ? '0' + score : score; //update the score as the game progresses.
    randomArray.length = 0;  // Start with an Empty random color array 
    userArray.length = 0;   //Start with an Empty the user array
    flashSpeed = 600;       //Reset the flashing speed back to normal;
    score = 0;              //reset score
    round = 0;              // reset the round
    chooseRandomColor(); //Call the function to choose the 1st random color 
}

//Function to Restart Game
function restartGame() {
    start.disabled = false; //Letting the user click start again so they can restart Game
    playingGame = false;    //Variable to keep track if game is on/off
    flashRedGreen.style.backgroundColor = 'red'; //let user know game ended
    currentScore.textContent = score < 10 ? '0' + score : score; //Display the Score from the last Game  

    //Update if user beat highScore, if statement to re-assign value for HighScore
    if (score > highScore) {
        highScore = score;
        highScoreA.textContent = highScore < 10 ? '0' + highScore : highScore;
    }
    //Flash 5 Times at the End of the Game
    for (let i = 0; i < colorsArray.length; i++) { //Loops through the Color Array
        let count = 0; //Keep track of the interval
        let interval = setInterval(function () {
            count++;
            // when count equals to 5, stop the function
            if (count === 5) {
                clearInterval(interval);
            }
            flashWhite(colorsArray[i]); //Calling the function to flashWhite and passing in all colors, using a for loop
        }, 800); //How fast it's flashing
    }
}
//Increasing the Speed as the game, Rounds Progresses
function increaseFlashSpeed() {
    if (round >= 5 && round < 8) {
        flashSpeed = 550;
    }
    else if (round >= 9 && round < 12) {
        flashSpeed = 500;
    }
    else if (round >= 13) {
        flashSpeed = 400;
    }
    else {
        return flashSpeed;
    }
}


//Chooses a Random color and stores it in an array
function chooseRandomColor() {
    let random = Math.floor(Math.random() * 4); //Picking a random number 
    let flashOneColor = colorsArray[random];    //Choose random color from the Color Array
    randomArray.push(flashOneColor);            //Store random color in a new array    
    flashRandomArray();                         //Calls the function to print the color in the array stored
}

//Whatever Random Color is picked, it flashes white per color, function to add white background and then return to default color
function flashWhite(color) {
    color.classList.add('active');
    setTimeout(() => {
        color.classList.remove('active');
    }, flashSpeed); // Uses the flashSpeed timing so it gives it enough time to change back to default color
}

let isFlashing = false; //Keep track of the flashing round
let secondsUserTakeToClick; //Keep track of seconds user takes to click one sequence

// As each round progresses, it prints the previous white flash 
function flashRandomArray() {
    let i = 0;
    let consecutiveCount = 0; // Keep track of consecutive occurrences of the same color to delay time so color doesn't stay white for too long
    isFlashing = true; // Sets flashing to true during the round
    const flashNextColor = () => {
        if (i < randomArray.length) {
            const color = randomArray[i];
            flashWhite(color);
            const prevColor = randomArray[i - 1];
            let delay = 800 - (consecutiveCount * 200); // If there same random color picked it reduces delay otherwise it will keep at SAME SPEED.
            if (prevColor === color && i > 0) {
                consecutiveCount++;
            } else {
                consecutiveCount = 0; // Reset consecutive count if the color changes
            }
            delay = Math.max(delay, 100); // Ensure delay is not less than 100 milliseconds,
            i++;
            setTimeout(flashNextColor, delay); // Delay between flashes, 
            //I decided to use this logic as by the time my functions were being called it was affecting my flashWhite Function in miliseconds and skiping white color or pausing
            // it was affecting my how it would print, in future I would use delay and await as it looks simpler
        } else {
            isFlashing = false; // Enable clicking after flashing is done
            clearTimeout(secondsUserTakeToClick);
            secondsUserTakeToClick = setTimeout(() => {
                restartGame(); // Restart the game if the user takes more than 5 seconds to click a button
            }, 5000);
        }
    };
    flashNextColor(); // Start flashing the colors
}

// Allow user to click buttons during the game
colorsArray.forEach(color => {
    color.addEventListener('click', function () {
        if (!playingGame || isFlashing) {
            return; // Ignore clicks if the game is not active or flashing, so it doesn't push an answer to array
        }
        const clickedColorIndex = colorsArray.indexOf(color); // Get the color clicked 
        userArray.push(clickedColorIndex); // Store the User Color clicked into an array
        checkAnswer(); // Check the answer while the user is playing the game
    });
});

// Check Answer while the user is playing game
function checkAnswer() {
    clearTimeout(secondsUserTakeToClick);
    let isCorrect = true;

    for (let i = 0; i < userArray.length; i++) { //Loop to check if user color index clicked, doesn't match color index flashed
        if (colorsArray.indexOf(randomArray[i]) !== userArray[i]) {
            isCorrect = false;
            break;
        }
    }

    // If user sequence is incorrect
    if (!isCorrect) {
        restartGame();
        return; //Ignore clicks 
    }
    // If the user has correctly entered the entire sequence it continues playing
    if (userArray.length === randomArray.length && isCorrect) {
        score++;   //Incrementing Score
        round++;   //Incrementing the Round
        currentScore.textContent = score < 10 ? '0' + score : score; // Update the score for user in Game
        userArray.length = 0; // Reset user sequence for the next round
        increaseFlashSpeed(); //Function Check level of user to increase how fast it flashes
        setTimeout(() => {
            chooseRandomColor(); //Function to pick and add another color to the random array
        }, 1000); // Wait a bit before starting the next round
    }
}

