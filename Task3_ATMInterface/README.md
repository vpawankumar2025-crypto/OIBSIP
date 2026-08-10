# ATM Interface

A console-based ATM simulation built with clean OOP design.

## Classes
- `Main` — entry point
- `ATM` — drives login + menu + transaction flow
- `Account` — encapsulates a user's balance, PIN, and transaction history
- `Transaction` — a single logged transaction (withdraw/deposit/transfer)
- `Bank` — holds all accounts, handles authentication & transfers

## Features
- User ID + PIN login, access denied after 3 incorrect attempts
- Main menu: Transaction History, Withdraw, Deposit, Transfer, Quit
- Balance validation before withdrawals/transfers ("Insufficient Funds")
- All transactions stored in an `ArrayList<Transaction>` per account
- Transfers update both sender and recipient accounts

## Demo Accounts (seeded in `Bank`)
| Account ID | PIN  | Starting Balance |
|------------|------|-------------------|
| ACC1001    | 1234 | $5000.00 |
| ACC1002    | 4321 | $2500.00 |

## How to Run
```bash
cd src
javac *.java
java Main
```
