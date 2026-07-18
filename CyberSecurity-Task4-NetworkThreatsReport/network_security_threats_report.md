# Research Report: Common Network Security Threats

**Author:** Pawan Kumar V
**Program:** Oasis Infobyte Cyber Security Externship
**Task:** Task 4 – Research Report on Common Network Security Threats

---

## Introduction

Modern organisations run almost every business function over networks that are, by design, reachable from the outside world — email, customer portals, remote access for employees, cloud services, and IoT devices all widen the attack surface. Attackers no longer need physical access to a building to cause damage; a single exposed service, weak protocol, or unpatched router can let them disrupt operations, steal data, or hijack traffic from anywhere on the internet. Understanding how the most common network-layer attacks work — and how to defend against them — is foundational to any security role, because these attack classes recur constantly in real incidents even as specific tools and exploits change year to year.

---

## 1. Denial-of-Service / Distributed Denial-of-Service (DoS/DDoS) Attacks

**How it works:** A DoS attack floods a target system or network with more traffic or requests than it can handle, exhausting bandwidth, CPU, memory, or connection tables until legitimate users can no longer get through. A DDoS attack is the same idea carried out from many machines at once — often a botnet of compromised devices — which makes the traffic harder to block by simply denying a single source IP.

**Real-world example:** In October 2016, attackers used the Mirai botnet — built from hundreds of thousands of poorly secured IoT devices such as routers and IP cameras still running factory-default passwords — to flood Dyn, a major DNS provider, with traffic. Because so many popular sites depended on Dyn for DNS resolution, the attack knocked services such as Twitter, Netflix, PayPal, Reddit, and GitHub offline for users across the US and Europe for several hours.

**Impact:** Service outages, lost revenue during downtime, reputational damage, SLA penalties, and — as the Dyn case showed — cascading effects on any third party that depends on the targeted infrastructure.

**Mitigation strategies:**

1. Deploy DDoS-specific protection (e.g., cloud scrubbing services, CDN-based traffic absorption) in front of public-facing infrastructure.
2. Implement rate limiting and connection throttling at the network edge, and maintain redundant DNS/hosting providers so a single point of failure can't take everything down.
3. Harden IoT and edge devices by changing default credentials and keeping firmware patched, since botnets like Mirai specifically hunt for such devices.

---

## 2. Man-in-the-Middle (MITM) Attacks

**How it works:** An attacker secretly positions themselves between two communicating parties — for example, on a shared Wi-Fi network or by spoofing ARP entries — and intercepts, reads, or alters traffic in transit. Victims believe they are talking directly to each other while the attacker relays (and often modifies) the conversation.

**Real-world example:** Rogue Wi-Fi access points set up in public places (airports, cafés) that mimic legitimate network names are a widely documented MITM technique; once a device connects, the attacker can intercept unencrypted HTTP traffic, session cookies, and login credentials passing through the fake access point. Public advisories from bodies such as CISA and multiple documented penetration-testing case studies have repeatedly identified public Wi-Fi impersonation as one of the most practical, low-cost MITM techniques still seen today.

**Impact:** Credential theft, session hijacking, exposure of sensitive communications, and unauthorised transaction tampering (e.g., altering bank transfer details in transit).

**Mitigation strategies:**

1. Enforce HTTPS/TLS everywhere so intercepted traffic is encrypted and unreadable even if captured.
2. Use VPNs on untrusted networks and disable auto-connect to open Wi-Fi networks.
3. Deploy ARP-spoofing detection tools and enable dynamic ARP inspection on managed switches in corporate environments.

---

## 3. IP Spoofing

**How it works:** An attacker forges the source IP address in packet headers to impersonate a trusted host, bypass IP-based access controls, or hide the true origin of an attack. It is frequently used as a building block for other attacks — for instance, to launch reflection/amplification DDoS attacks or to defeat simple IP allow-listing.

**Real-world example:** IP spoofing underpins reflection-based DDoS techniques such as DNS and NTP amplification attacks, where forged source addresses direct flood traffic at a victim while making the responding servers believe they are answering the victim's own requests. Security advisories from CISA on UDP-based amplification attacks describe this pattern as a recurring technique behind some of the largest volumetric DDoS attacks on record.

**Impact:** Enables large-scale DDoS amplification, undermines trust-based network access controls, and complicates attribution/incident response since logs show a falsified source.

**Mitigation strategies:**

1. Implement ingress/egress filtering (BCP 38) at network borders so packets with implausible source addresses are dropped.
2. Avoid relying on IP address alone for authentication; pair it with cryptographic authentication (e.g., IPsec, mutual TLS).
3. Deploy anti-spoofing features on routers/firewalls (unicast reverse path forwarding checks).

---

## 4. DNS Poisoning / Spoofing (Bonus)

**How it works:** An attacker corrupts a DNS resolver's cache or forges DNS responses so that a domain name resolves to an attacker-controlled IP address instead of the legitimate one. Victims typing a normal, trusted URL are silently redirected to a malicious server.

**Real-world example:** DNS cache-poisoning techniques have been used in numerous phishing and credential-harvesting campaigns where victims are redirected from legitimate banking or login pages to look-alike sites without any visible change in the URL bar (when combined with other spoofing techniques). It remains a documented category in CISA and NIST guidance on DNS infrastructure security as a persistent, high-impact weakness in unsecured resolvers.

**Impact:** Credential theft via convincing fake login pages, malware distribution, traffic redirection for surveillance or censorship, and erosion of trust in a domain's legitimate infrastructure.

**Mitigation strategies:**

1. Deploy DNSSEC to cryptographically validate DNS responses and prevent forged records from being accepted.
2. Use DNS resolvers that randomise query IDs and source ports to make cache-poisoning attempts far harder to pull off.
3. Monitor DNS logs for anomalous resolution changes and restrict recursive DNS queries to trusted internal clients only.

---

## Comparison Table

| Threat        | Attack Vector                                             | Who Is At Risk                                     | Difficulty to Execute               | Ease of Mitigation                                      |
| ------------- | --------------------------------------------------------- | -------------------------------------------------- | ----------------------------------- | ------------------------------------------------------- |
| DoS/DDoS      | Traffic flooding, often via botnets                       | Any internet-facing service                        | Low–Medium (botnets for hire exist) | Medium (needs dedicated DDoS protection)                |
| MITM          | Network position interception (rogue Wi-Fi, ARP spoofing) | Users on shared/untrusted networks                 | Medium                              | Medium–High (TLS + VPN largely neutralise it)           |
| IP Spoofing   | Forged packet source addresses                            | ISPs, DNS/NTP servers, any IP-trust-based system   | Medium                              | Medium (needs border filtering, not just local fixes)   |
| DNS Poisoning | Corrupting DNS cache/responses                            | Anyone resolving domains via the affected resolver | Medium–High                         | Medium (DNSSEC adoption still incomplete industry-wide) |

---

## Conclusion

Three key takeaways for a network administrator:

1. **Encryption and validation beat trust-by-default.** MITM, IP spoofing, and DNS poisoning all exploit the assumption that a packet or response is legitimate just because it looks right; TLS, DNSSEC, and cryptographic authentication remove that assumption.
2. **Edge hardening prevents your network from becoming someone else's weapon.** The Dyn/Mirai incident shows that DDoS defence isn't only about protecting your own servers — poorly secured devices on your network can be conscripted into attacks against others.
3. **No single control is sufficient.** Effective defence against this whole class of threats requires layering border filtering, encryption, monitoring, and device hardening together, since each threat exploits a different weak link in the chain.

---

## References

1. CISA – Heightened DDoS Threat Posed by Mirai and Other Botnets: https://www.cisa.gov/news-events/alerts/2016/10/14/heightened-ddos-threat-posed-mirai-and-other-botnets
2. ThousandEyes – The DDoS Attack on Dyn's DNS Infrastructure: https://www.thousandeyes.com/blog/dyn-dns-ddos-attack
3. NIST – Guide to General Server Security (SP 800-123) and DNS security guidance: https://www.nist.gov
4. CISA – DDoS Quick Guide (UDP-Based Amplification Attacks): https://www.cisa.gov
5. MITRE ATT&CK – Network Denial of Service, Adversary-in-the-Middle techniques: https://attack.mitre.org
