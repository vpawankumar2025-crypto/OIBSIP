# Research Report: Social Engineering Attacks

**Author:** Pawan Kumar V

**Program:** Oasis Infobyte Cyber Security Externship

**Task:** Task 5 – Research Report on Social Engineering Attacks

---

## Introduction

Social engineering is the practice of manipulating people — rather than exploiting software flaws — into breaking normal security procedures, revealing confidential information, or granting unauthorised access. It is considered one of the most effective attack vectors precisely because it targets human judgement, trust, and urgency instead of a technical control that can be patched. Regulatory investigations such as the New York Department of Financial Services' review of the 2020 Twitter breach found that a substantial share of significant cybersecurity incident notices filed in that period involved phishing or vishing, underlining how central social engineering has become to real-world breaches.

---

## 1. Phishing

**Types:** Spear phishing (targeted at a specific individual using personal details), whaling (targeted at senior executives), vishing (voice/phone-based phishing), and smishing (SMS-based phishing).

**How it works:** The attacker impersonates a trusted entity — a colleague, vendor, or internal support desk — through email, phone, or text, and pressures the victim into revealing credentials, clicking a malicious link, or performing an action (like a wire transfer) under a false pretext, often using urgency or authority to short-circuit normal caution.

**Real-world case study — the July 2020 Twitter hack:** Attackers ran a phone-based spear-phishing (vishing) campaign against Twitter employees. They first tricked lower-level staff without administrative access into giving up credentials, then used the information gathered in those calls to sound more credible in follow-up calls to employees who *did* have access to internal account-support tools. Twitter's own investigation confirmed the intrusion required both employee credentials and access to the internal network gained this way. Once inside, the attackers took over roughly 130 high-profile accounts (including Barack Obama, Elon Musk, and Apple) to run a Bitcoin scam, collecting over $118,000 from victims within a very short window. The incident showed how a single successful vishing call can cascade into a full-scale platform compromise.

**Prevention recommendations:**
1. Train employees to independently verify identity through a separate, known channel before acting on unusual requests, especially ones involving access changes or money.
2. Restrict and closely monitor access to sensitive internal admin tools, applying least-privilege so a single compromised low-level account cannot escalate broadly.
3. Deploy phishing-resistant multi-factor authentication (e.g., hardware security keys) rather than SMS/OTP alone, since attackers who obtain credentials via vishing often still need to defeat MFA.
4. Run regular simulated phishing/vishing exercises so staff recognise real-time pressure tactics rather than encountering them for the first time during an actual attack.

---

## 2. Pretexting

**Definition:** Pretexting involves an attacker fabricating a believable scenario or false identity (e.g., posing as IT support, an auditor, or a new employee) to gain a victim's trust and extract information or access over an extended interaction, rather than a single deceptive message.

**How the false scenario is built:** The attacker typically researches the organisation in advance — job titles, internal jargon, org charts, recent events — so the fabricated identity holds up under questioning, then builds rapport before making the actual request, which reduces the victim's suspicion.

**Case study:** The 2020 Twitter attackers' vishing calls doubled as pretexting: callers impersonated Twitter's internal IT department and used details about the company and its staff gathered from public sources and earlier calls to sound legitimate to each subsequent target, illustrating how pretexting and phishing/vishing are often combined in a single campaign rather than used in isolation.

**Prevention measures:**
1. Establish and train staff on strict identity-verification procedures for any request involving credentials or system access, regardless of how convincing the caller sounds.
2. Limit publicly available organisational details (staff directories, internal terminology, org charts) that attackers can use to build a convincing pretext.
3. Encourage a "verify, then trust" culture where employees are explicitly supported for pausing or escalating suspicious requests rather than penalised for slowing things down.

---

## 3. Baiting

**Definition:** Baiting lures victims with a tempting offer or object — a free download, a "found" USB drive, or a fake prize — that delivers malware or captures credentials once the victim takes the bait.

**Physical baiting:** Leaving malware-infected USB drives in a parking lot or lobby, relying on curiosity to get someone to plug the device into a work computer.

**Digital baiting:** Fake software downloads, cracked-software sites, or too-good-to-be-true online offers that bundle malware, or fraudulent giveaway links — a pattern echoed in the 2020 Twitter incident, where compromised celebrity accounts posted fake "double your Bitcoin" giveaway links that hundreds of people fell for within minutes.

**Prevention measures:**
1. Enforce endpoint controls that block or restrict autorun and unauthorised USB devices on corporate machines.
2. Train employees never to plug in unknown removable media and to report anything found on-site to security rather than testing it themselves.
3. Use web filtering and browser protections to flag known malicious download sites and typosquatted domains used in digital baiting campaigns.

---

## 4. Quid Pro Quo (Bonus)

**Explanation:** In a quid pro quo attack, the attacker offers a service or benefit (commonly posing as IT support offering to "fix" a problem) in exchange for information or access — for example, calling random extensions in a company claiming to be from the help desk and offering to resolve an issue if the employee just provides their login details.

**Prevention:** Route all IT support requests through a verified, single official channel (ticketing system or known help-desk number) so employees learn to distrust unsolicited "helpful" outreach, and make staff aware that legitimate IT support will never ask for a password outright.

---

## Comparison Table

| Attack Type | Primary Target | Psychological Lever Exploited | Best Countermeasure |
|---|---|---|---|
| Phishing (incl. vishing/smishing) | Any employee with system/account access | Urgency, authority, fear | Independent verification + phishing-resistant MFA |
| Pretexting | Employees with access to sensitive processes | Trust, perceived legitimacy | Strict identity-verification procedures |
| Baiting | Curious or careless individuals | Curiosity, greed | Endpoint controls + awareness training |
| Quid Pro Quo | Employees needing IT help | Reciprocity, desire for a quick fix | Single verified support channel |

---

## Organisational Recommendations: 5-Point Employee Security Awareness Checklist

1. Conduct recurring, scenario-based phishing and vishing simulations rather than one-off annual training.
2. Establish a clear, blame-free reporting channel so employees flag suspicious contact immediately.
3. Mandate independent, out-of-band verification for any request involving credentials, access changes, or financial transactions.
4. Apply least-privilege access so a single compromised employee cannot cascade into full administrative compromise, as nearly happened at Twitter.
5. Limit the organisational and personal details exposed publicly (LinkedIn, staff pages) that attackers use to construct convincing pretexts.

---

## References

1. New York State Department of Financial Services – Twitter Investigation Report: https://www.dfs.ny.gov/Twitter_Report
2. Social-Engineer.com – Analyzing the 2020 Twitter Attack: https://www.social-engineer.com/analyzing-the-2020-twitter-attack/
3. CISA – Social Engineering guidance: https://www.cisa.gov
4. SANS Institute Reading Room – Social Engineering research papers: https://www.sans.org/reading-room
