# Online Examination System

A Swing GUI application where students log in, optionally update their
profile, take a timed multiple-choice exam, and view their results.

## Classes
- `ExamApp` — main frame, `CardLayout` screen switching, window-close handling
- `LoginPanel` — username + password login
- `ProfilePanel` — optional display name / password update before the exam
- `ExamPanel` — one MCQ at a time, Next/Previous nav, countdown timer, submit
- `ResultPanel` — score, time taken, correct/incorrect breakdown, logout
- `Question` — MCQ model (text, 4 options, correct index)
- `UserStore` — small in-memory user directory

## Features
- Login screen with access denied on bad credentials
- Profile update screen (display name / password) before starting
- One question at a time with 4 radio-button options (`ButtonGroup` + `JRadioButton`)
- Next / Previous navigation that preserves selected answers
- Live countdown timer (`javax.swing.Timer`), 30 minutes by default,
  auto-submits when it hits zero
- Manual "Submit Exam" button with a confirmation dialog (warns about
  unanswered questions)
- Result screen: score (X/Y), time taken, full correct/incorrect breakdown
- Closing the window mid-exam prompts "Are you sure you want to quit?"
- Logout button on the result screen returns to login

## Demo Login
- Username: `student` / Password: `pass123`
- Username: `alice` / Password: `alice123`

## How to Run
```bash
cd src
javac *.java
java ExamApp
```

No external dependencies — pure Java Swing.
