# Number Guessing Game

A console-based Java game where the computer picks a random number and the
player tries to guess it, with "Too High!" / "Too Low!" hints, a limited
number of attempts, difficulty levels, and score tracking across rounds.

## Features
- Random number generation each round
- Console input via `Scanner`
- Feedback: "Too High!", "Too Low!", "Correct!"
- Visible attempt counter
- Max attempts limit -> "You Lost!" reveal
- Play Again prompt
- Round score summary: `Round X - guessed in Y attempts`
- Difficulty levels: Easy (1-50, 10 attempts), Medium (1-100, 7 attempts), Hard (1-200, 5 attempts)

## How to Run
```bash
cd src
javac NumberGuessingGame.java
java NumberGuessingGame
```

No external dependencies required — pure Java standard library
(`java.util.Random`, `java.util.Scanner`).
