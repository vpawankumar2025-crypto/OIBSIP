# Task 1 — Basic Network Scanning with Nmap

**Track:** Cyber Security | **Level:** Beginner | **Type:** Practical

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
- This task was performed exclusively against **localhost (127.0.0.1)** on a
  personal/sandboxed Linux machine — never against external or production systems.
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
Identifies the specific software and version running behind each open port.

### 3. OS detection scan
```bash
sudo nmap -O 127.0.0.1
```
Attempts to fingerprint the operating system based on how the target's TCP/IP
stack responds to specific probes. Requires root/sudo privileges.

Full raw output of all three scans is in [`nmap_scan_results.txt`](./nmap_scan_results.txt).
Screenshots of each scan running are in [`screenshots/`](./screenshots).

## Findings — Open Ports & Security Analysis

| Port | Service | What it does | Security risk? |
|---|---|---|---|
| 22 | SSH | Encrypted remote shell/login access | **Low risk** when key-based auth + tools like fail2ban are used. **High risk** if password authentication is left open to the public internet — SSH is one of the most brute-forced services on the internet. |

**OS Detection note:** Nmap's `-O` scan reported *"Too many fingerprints match this host"* rather than a confident single OS guess. This is expected behavior on a minimal/sandboxed host with only one open port — OS fingerprinting relies on subtle differences across multiple services and TCP/IP stack quirks, so a single-service host gives Nmap very little signal to work with. This limitation itself is a useful finding: attackers doing recon on a locked-down host face the same uncertainty.

## Files in this Repository
| File | Description |
|---|---|
| `nmap_scan_results.txt` | Raw terminal output of all three scans |
| `screenshots/` | Terminal screenshots of each scan running |
| `README.md` | This file |

## Skills Demonstrated
Network reconnaissance · Port/service identification · OS fingerprinting · Risk analysis · Ethical scanning practice

---
*Note: this scan was run against a minimal single-service host (only SSH exposed) as a controlled, safe demonstration environment. For a fuller demonstration with more services to analyze (e.g. a local web server, database), re-run these same three commands against a personal VM with more services installed — the methodology and analysis approach stay identical.*
