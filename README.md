# OIBSIP
A collection of internship tasks and projects completed under the Oasis Infobyte Internship Program, showcasing practical coding, problem-solving, and project implementation skills.


# Task 1 — Basic Network Scanning with Nmap

## What is Nmap?
Nmap (Network Mapper) is a free, open-source tool used to discover hosts and
services on a network by sending crafted packets and analyzing the responses.
It's one of the most widely used tools in network security for reconnaissance,
inventory, and vulnerability assessment.

## Why Network Scanning Matters
Before you can secure a system, you need to know what's actually running on it.
Scanning reveals open ports, active services, and software versions — the same
information an attacker would gather during reconnaissance. Security teams use
this to find and close unnecessary exposure before someone else finds it first.

## Ethical Use Guidelines
- Only scan machines you own or have explicit written permission to scan.
- This task was performed exclusively against a local virtual machine /
  localhost — never against external or production systems.
- Unauthorized scanning of systems you don't own can be illegal in many
  jurisdictions, even if no damage is done.

## Installation
```bash
# On Kali Linux / Ubuntu / Debian
sudo apt update
sudo apt install nmap -y

# Verify installation
nmap --version
```
Kali Linux ships with Nmap pre-installed — download it free from kali.org/get-kali.

## Scans Performed

### 1. Basic scan
```bash
nmap 127.0.0.1
```
Records which ports are open using Nmap's default port list (top 1000 ports).

### 2. Service version scan
```bash
nmap -sV 127.0.0.1
```
Identifies the specific software and version running behind each open port
(e.g., "OpenSSH 8.9p1" instead of just "port 22 open").

### 3. OS detection scan
```bash
sudo nmap -O 127.0.0.1
```
Attempts to fingerprint the operating system based on how the target's TCP/IP
stack responds to specific probes. Requires root/sudo privileges.

## Findings — Open Ports & Security Analysis

| Port | Service | What it does | Security risk? |
|---|---|---|---|
| 22 | SSH | Encrypted remote shell access | Low risk if key-based auth + fail2ban are used; high risk if password auth is open to the internet (brute-force target) |
| 80 | HTTP | Unencrypted web traffic | Medium — data sent in plaintext; should redirect to HTTPS in production |
| 443 | HTTPS | Encrypted web traffic | Low — encrypted, but still needs up-to-date TLS config |
| 3306 | MySQL | Database service | High if exposed beyond localhost — databases should never be internet-facing |

*(Replace this table with your actual scan results — ports found will vary by machine/VM.)*

## Files in this Repository
| File | Description |
|---|---|
| `nmap_scan_results.txt` | Raw output of all three scans |
| `README.md` | This file |
| `screenshots/` | Terminal screenshots of each scan running |

## Skills Demonstrated
Network reconnaissance · Port/service identification · OS fingerprinting · Risk analysis
