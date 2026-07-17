# Research Report: The Importance of Patch Management

**Author:** [Your Name]
**Program:** Oasis Infobyte Cyber Security Externship
**Task:** Task 6 – Research Report on Patch Management

---

## Introduction

Patch management is the structured process of identifying, testing, and deploying software updates that fix known security vulnerabilities, bugs, and performance issues across an organisation's systems. It sits at the centre of the vulnerability lifecycle: a flaw is discovered, a fix is released, and the window between release and deployment is exactly the period during which attackers race to exploit organisations that haven't yet applied it. Unpatched systems remain one of the largest and most avoidable attack surfaces in cybersecurity, not because defences don't exist, but because known fixes simply aren't applied in time.

---

## Why Patches Matter

When a researcher or vendor discovers a security flaw, it is typically catalogued as a **CVE (Common Vulnerabilities and Exposures)** entry with a corresponding **CVSS (Common Vulnerability Scoring System)** severity score. Once a CVE and patch are public, the vulnerability details are available to attackers as much as defenders — meaning disclosure itself accelerates exploitation attempts against anyone slow to patch.

**Real-world breach 1 — WannaCry/EternalBlue (2017):** Microsoft released patch MS17-010 in March 2017, fixing a critical SMB vulnerability (CVE-2017-0144 and related CVEs) that had been part of a leaked NSA exploit toolkit. Despite the patch being available for roughly two months, the WannaCry ransomware worm spread using the unpatched flaw and infected more than 200,000 computers across over 150 countries within days in May 2017, crippling systems including the UK's National Health Service, Renault and Nissan factories, and FedEx. Total global damages from WannaCry alone have been estimated at several billion dollars, and the related NotPetya attack using the same exploit is estimated to have caused over $10 billion in damages. The NHS impact alone was later estimated at thousands of cancelled appointments and several million pounds in direct costs.

**Real-world breach 2 — Equifax (2017):** Apache disclosed a critical remote-code-execution vulnerability in the Struts framework (CVE-2017-5638) on March 7, 2017, with a patch released the same day. Equifax failed to apply it. Attackers exploited the still-open flaw starting in mid-May 2017, moved laterally through Equifax's network, and exfiltrated personal data on approximately 147 million people over roughly 78 days before the breach was discovered — one of the largest data breaches in history, later confirmed by both Equifax and the Apache Struts project itself as a failure to install an available security update in a timely manner.

Both cases involve the same underlying failure mode: a patch existed, was publicly known, and simply wasn't deployed before attackers took advantage of the gap.

---

## Consequences of Not Patching

- **Data breaches:** As seen with Equifax, unpatched application-layer vulnerabilities can lead directly to mass exfiltration of sensitive personal data.
- **Ransomware attacks:** WannaCry and NotPetya demonstrate how a single unpatched vulnerability can enable self-propagating malware that disrupts operations across an entire organisation or industry within hours.
- **Compliance violations:** Regulations such as GDPR, HIPAA, and PCI-DSS explicitly require timely patching as part of "reasonable security measures"; breaches traced to unpatched, publicly known CVEs are treated as an aggravating factor in regulatory penalties.
- **Financial penalties and costs:** Beyond regulatory fines, organisations face incident response costs, legal settlements, credit-monitoring obligations (as Equifax did for affected consumers), and stock price impact following public disclosure of a preventable breach.

---

## The Patch Management Lifecycle

1. **Discovery:** Identify new vulnerabilities affecting the organisation's software inventory, typically via vendor advisories, CVE/NVD feeds, or internal vulnerability scanning tools.
2. **Assessment:** Evaluate the severity (using CVSS scoring) and relevance of each vulnerability to the organisation's specific systems — not every disclosed CVE is exploitable or applicable everywhere.
3. **Testing:** Apply the patch in a staging or test environment first to confirm it does not break existing functionality or introduce regressions, especially for critical production systems.
4. **Deployment:** Roll the patch out to production systems, ideally in a staged or phased manner, prioritising the most critical or internet-facing assets first.
5. **Verification:** Confirm the patch was applied successfully across all target systems and that the vulnerability is actually closed (e.g., via a follow-up scan), since partial or failed deployments leave organisations with a false sense of security.

---

## Best Practices: A 7-Step Patch Management Checklist

1. Maintain an accurate, up-to-date inventory of all software and hardware assets — you cannot patch what you don't know you have.
2. Subscribe to vendor and CVE/NVD advisories relevant to your technology stack for early warning of new vulnerabilities.
3. Triage new vulnerabilities by CVSS severity and actual exposure (e.g., internet-facing vs. internal-only) rather than patching everything in the order it's announced.
4. Establish defined SLAs for patching based on severity (e.g., critical/internet-facing within days, low-severity internal systems on a longer cycle).
5. Test patches in a non-production environment before wide deployment to avoid breaking critical services.
6. Automate patch deployment where possible to reduce the delay between availability and installation.
7. Verify and audit patch compliance regularly, and track exceptions (systems that can't yet be patched) with compensating controls until they can be.

---

## Challenges and How to Overcome Them

| Challenge | Why It Happens | How to Overcome It |
|---|---|---|
| Legacy systems | Older systems may not support current patches or the vendor no longer issues them | Isolate legacy systems on segmented networks, apply compensating controls (e.g., strict firewall rules), and plan a migration timeline |
| Downtime concerns | Patching production systems may require reboots or service interruptions | Use staged/rolling deployments, maintain redundant systems, and schedule patch windows during low-traffic periods |
| Testing requirements | Untested patches risk breaking critical business functionality | Maintain a staging environment that mirrors production, and automate regression testing where feasible |
| Resource/staffing limits | Many organisations lack dedicated patch management staff or tooling | Adopt automated patch management platforms and prioritise ruthlessly by risk rather than trying to patch everything manually |

---

## References

1. NIST Special Publication 800-40 – Guide to Enterprise Patch Management: https://nvlpubs.nist.gov
2. CVE Database (MITRE) and CVSS Scoring System: https://cve.mitre.org, https://www.first.org/cvss
3. Apache Software Foundation – Statement on the Equifax Data Breach: https://news.apache.org/foundation/entry/media-alert-the-apache-software
4. Virus Bulletin – EternalBlue: A Prominent Threat Actor of 2017–2018: https://www.virusbulletin.com/virusbulletin/2018/06/eternalblue-prominent-threat-actor-20172018/
5. CISA – guidance on vulnerability management and patching best practices: https://www.cisa.gov
