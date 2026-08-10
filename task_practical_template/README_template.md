# Task 1 — Basic Network Scanning with Nmap

> This is a fill-in template. Replace every `[ ... ]` placeholder with your own real output
> after running the commands in your own VM. Do not submit this template unfilled.

## What is Nmap and Why Network Scanning Matters

[Write 2–3 sentences in your own words: Nmap is a network scanning tool used to discover
hosts, open ports, and running services on a network. Network scanning matters because
it's how both defenders and attackers first map out what's reachable and what might be
vulnerable — you can't secure what you don't know exists.]

## Ethical Use Guidelines

- Only scan machines/networks you own or have explicit written permission to test.
- Always use an isolated local VM (e.g., a Kali Linux or Ubuntu VM in VirtualBox) as the target — never scan external or production systems.
- [Add one more sentence in your own words about scope and permission.]

## Installation Steps

```bash
[Paste the exact commands you ran to install Nmap, e.g.:]
sudo apt update
sudo apt install nmap -y
nmap --version
```

## 1. Basic Scan

Command:
```bash
nmap [target IP]
```

Result: *(paste real output here, or reference `nmap_scan_results.txt`)*
```
[paste output]
```

## 2. Service Version Scan

Command:
```bash
nmap -sV [target IP]
```

Result:
```
[paste output]
```

## 3. OS Detection Scan

Command:
```bash
nmap -O [target IP]
```

Result:
```
[paste output]
```

## Open Ports Found — Analysis

| Port | Service | What It Does | Security Risk? |
|---|---|---|---|
| [22] | [SSH] | [Remote command-line access] | [Risk if weak passwords / outdated version — brute-force target] |
| [80] | [HTTP] | [Unencrypted web traffic] | [Risk — data sent in plaintext, vulnerable to MITM] |
| [ ] | [ ] | [ ] | [ ] |

*(Add a row for every open port your scan actually found.)*

## Screenshots

Place your terminal screenshots in the `screenshots/` folder and reference them here, e.g.:
`![Basic scan](screenshots/basic_scan.png)`

## Files in This Submission

- `README.md` — this file
- `nmap_scan_results.txt` — raw scan output
- `screenshots/` — terminal screenshots for each scan type
