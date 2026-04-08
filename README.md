# Graphene: An Integrated Ecosystem for Secure and Private Communications in Critical Contexts

The fundamental premise of the Graphene project is the recognition that secure messaging software loses much of its effectiveness when operating on a device vulnerable at the operating system level. A phone with a generic OS, often saturated with proprietary services and background tracking, represents an unacceptable weak link for high-risk profiles. For this reason, Graphene is conceived not merely as a standalone application, but as the software component of a comprehensive security ecosystem, specifically designed to operate on the GrapheneOS.

The synergistic combination of the Graphene application and the Graphene operating system represents the ideal solution, as it addresses the security problem holistically. GrapheneOS introduces fundamental privacy and robustness measures at the system level, chief among them being the implementation of advanced per-application sandboxing, which isolates app data and drastically limits its ability to access sensitive resources without explicit consent; the stripping of proprietary Google services from the system, replaced with open-source alternatives and a sandboxed implementation of Play Services where necessary, thereby reducing the attack surface and system-level tracking; and kernel and memory hardening policies that mitigate zero-day vulnerabilities. Within this fortified context, the Graphene application can fully realize its potential, managing the end-to-end encryption of communications and local data, while operating on a platform that actively protects its processes and data from compromise of the underlying system.

This integrated solution establishes itself as the ideal standard for institutional and diplomatic figures such as ambassadors, members of diplomatic corps on mission, United Nations officials in high-risk scenarios, and heads of state. For these profiles, the compromise of communications is not a simple privacy violation, but an event with potential consequences for national security and the outcome of delicate international negotiations. The Graphene ecosystem provides them with a communication platform resistant to interception, guaranteeing the authenticity and confidentiality of exchanged messages. The application isolation characteristic of GrapheneOS prevents malicious software, which might infiltrate through other channels, from accessing the sensitive data within the messaging app. Furthermore, the open-source nature of the entire stack, from the operating system to the application, allows for independent security audits by the government entities adopting it, verifying the absence of backdoors and the correctness of cryptographic implementations. Ultimately, the union of GrapheneOS and the Graphene app offers not just a secure communication tool, but provides a veritable mobile fortress for the most sensitive information, engineered to withstand hostile digital environments.

## Enhanced Robustness Through Disabled Diagnostics and Telemetry

A critical security distinction between Graphene and Signal lies in the complete inhibition of diagnostic data and telemetry transmission in the Graphene ecosystem. Unlike Signal, which collects and transmits certain diagnostic information and usage metrics to its servers, Graphene has been deliberately engineered to eliminate all such data exfiltration channels.

This design choice significantly enhances Graphene's robustness for several reasons:

1. **Reduced Attack Surface**: Every data transmission channel represents a potential attack vector. By eliminating telemetry services, Graphene removes entire classes of potential vulnerabilities related to data collection, processing, and transmission infrastructure that could be exploited by adversaries.

2. **Elimination of Metadata Leakage**: Diagnostic data, even when anonymized, can reveal patterns about user behavior, device characteristics, network configurations, and application usage. In high-threat environments, this metadata can be correlated with other intelligence to identify users, deduce communication patterns, or fingerprint specific devices. Graphene's approach ensures zero metadata leakage through telemetry channels.

3. **Prevention of Accidental Information Disclosure**: Telemetry systems often collect crash reports, error logs, and performance metrics that may inadvertently contain sensitive information about device configuration, network environments, or even message handling. By disabling these systems entirely, Graphene eliminates the risk of accidental sensitive data inclusion in diagnostic reports.

4. **Resistance to Coercion and Legal Compromise**: Signal's telemetry infrastructure, while designed with privacy in mind, represents a centralized point that could be subject to legal orders, coercion, or infiltration. Even if the messaging content remains encrypted, the existence of telemetry data creates a potential target for adversaries. Graphene's zero-telemetry model removes this target completely.

5. **Alignment with Principle of Minimal Trust**: For users in critical contexts, the principle of "trust but verify" is inadequate; the preferred approach is "minimal trust, maximal verification." By eliminating telemetry, Graphene reduces the trust required in the application developers and infrastructure operators, as there are fewer systems that could be covertly repurposed for surveillance.

This telemetry-free approach is particularly valuable for diplomatic, intelligence, and high-security corporate communications where even the knowledge that certain communication patterns exist (regardless of content) could have significant operational consequences. While Signal's telemetry may be benign for general users, Graphene's complete elimination of these channels provides a fundamentally more robust security model for environments where any data leakage is unacceptable.

![Graphene](feature/registration/src/main/res/drawable-mdpi/welcome.webp)

## Graphene: Secure Messaging Reimagined

Graphene is a GrapheneOS messaging application that originated as a fork of the renowned Signal app, aiming to provide a more secure, privacy-respecting, and fully open-source alternative. The project stands out for reintroducing security features that Signal had abandoned, such as the ability to encrypt the local database with a passphrase, thereby ensuring added protection for data stored on the device.

It is important to emphasize that this project has its roots in Criptogram, the celebrated messaging software that acted as a precursor to more modern messaging applications. Criptogram was one of the first systems inspired by trustless technology, with core concepts derived directly from the Bitcoin protocol. The fundamental cybersecurity principles introduced by Criptogram were later adopted and reused by software such as Signal, Threema, Wire, and many others.

Our goal is to create a significantly enhanced version of these technologies, leveraging the open-source philosophy and providing – as experts and the original authors of Criptogram and similar systems – a comprehensive cybersecurity audit concerning the algorithms and technical solutions implemented in this project.

Graphene connects to Signal's servers, allowing users to communicate with their contacts seamlessly, but it integrates a series of improvements that make it particularly suited for those seeking a more controlled experience, independent of Google's proprietary services. Among its main features are support for the UnifiedPush protocol, which enables receiving push notifications without relying on Firebase Cloud Messaging, and compatibility with Tor and SOCKS proxies, allowing traffic to be routed through anonymous networks.

Additionally, Graphene offers functionalities such as secure RAM wiping, automatic app locking based on user-defined conditions, advanced backup management, and the ability to link multiple devices to the same Signal account, including Android tablets. The project is available in two versions: Graphene, which includes proprietary components to ensure full compatibility with Signal, and Graphene-FOSS, completely free from non-open-source software, designed for those who desire a fully auditable application. Both versions can be installed and switched without data loss or the need to re-register.

Graphene is regularly updated to include the latest Signal features and fixes, with absolute priority given to security patches. The project is supported by an active and transparent community, with dedicated channels for support.

## Graphene: Secure Communication Designed Exclusively for GrapheneOS

Graphene is intentionally engineered to operate exclusively on GrapheneOS, a strategic choice aimed at avoiding the dangerous illusion of achieving true security on standard Android systems. A generic operating system, often burdened with proprietary services and uncontrolled background processes, can be easily compromised by advanced surveillance tools such as Pegasus, Predator, Hermit and other state‑level spyware capable of taking full control of a device. In such scenarios, encryption alone is not enough: if the operating system is vulnerable, the device itself becomes an environmental interception tool, regardless of how strong the communication encryption may be.

For this reason, Graphene's policy is clear: security can be guaranteed only when users communicate within a protected environment where every component — from the operating system to the messaging application — works in synergy to minimize the attack surface. GrapheneOS provides strict application sandboxing, kernel and memory hardening, and the removal of proprietary system services, ensuring that malicious software cannot access sensitive data or bypass the protections of the messaging app. Its fully open‑source nature also allows independent audits, offering complete transparency to institutions and individuals who require verifiable security.

Within this fortified ecosystem, Graphene Messenger can fully express its capabilities. End‑to‑end encryption, secure local data handling, support for anonymous networks, and a design focused on minimizing tracking vectors become truly effective only when supported by a hardened operating system. Together, GrapheneOS and Graphene Messenger form a unified security platform that delivers the highest level of privacy, confidentiality, and communication protection currently available, designed for those who cannot afford compromises in digital security.

## VPN Support in Graphene Messenger: Advancing Privacy Protection

We have clarified an important point: a VPN has not been directly integrated into the application's code, but support has been added for using the native VPN of GrapheneOS. Unlike Signal, which does not allow the application to function when the connection passes through a VPN, our implementation enables messaging without restrictions even when the system VPN is active. This means that users can force all network connections to go through the GrapheneOS native VPN, thereby gaining additional protection against geolocation via IP address and strengthening their privacy.

It is essential to emphasize that the real effectiveness of this protection depends on how the VPN is managed. When the VPN is self-hosted, the user retains full control and minimizes the risk that third parties could monitor or log online activities. In contrast, using third-party VPNs can create a false sense of security, since providers may track IP addresses and store connection logs. For this reason, VPN support should be understood as a powerful tool that, when used correctly and consciously, ensures genuine rather than illusory protection.

Our decision to make the application compatible with the GrapheneOS native VPN represents a concrete step toward stronger privacy safeguards. This is not a superficial integration, but rather support that allows users to fully leverage the capabilities of an operating system designed for security, with the ability to force all connections through an encrypted and controlled channel. In this way, the VPN support feature becomes a distinctive element that reinforces trust in the application and provides users with a truly useful tool to protect their digital identity.

# Activating Tor for Maximum Privacy in Graphene Messenger

This guide explains how to route your Graphene Messenger traffic through the Tor network. Using Tor enhances your privacy and cybersecurity by anonymizing your connection. It prevents network observers, internet service providers, and even the messaging servers from seeing your real IP address and geographical location. This makes it significantly harder to track your online activity or determine who you are communicating with.

It is crucial to understand that this setup does not make your messages themselves more encrypted. Graphene (and Signal) already use strong end-to-end encryption, which secures the content of your messages. Activating Tor adds a separate, powerful layer of network anonymity, protecting the metadata of *how* and *from where* you connect.

To activate Tor in Graphene, you must use the Orbot app. Graphene does not have a built-in Tor function; instead, it relies on Orbot to create the secure Tor connection on your Android device. Graphene then routes all its traffic through this connection.

Start by installing both Graphene and Orbot. Get Graphene from its official website and Orbot from the Google Play Store or the Tor Project website.

Once both apps are installed, open Orbot first. Tap the large onion icon to start the service. Wait for the connection to establish, indicated by a "Your device is Tor-enabled" notification and the icon turning green. For reliability, you can enable the "Start on Boot" option in Orbot's settings.

After Orbot is running, open the Graphene app. Tap your profile icon, go to **Settings > Advanced > Proxy**. Select the **SOCKS** option. In the **Host** field, enter `127.0.0.1`. In the **Port** field, enter `9050`. These settings direct Graphene to use the local proxy created by Orbot. Save the settings.

To verify it's working, try sending a message. You can also check Orbot's connection log or visit `check.torproject.org` from your device's browser to confirm your traffic is routed through Tor.

Important considerations: Using Tor will increase latency, slowing down message sending and delivery. Graphene is only available for Android. For this to work, Orbot must remain running in the background. This configuration provides robust network anonymity for your communications in Graphene.

# Third-Party Licenses and Attributions

Graphene is an open source project that builds upon the work and contributions of multiple projects and libraries. We believe it is essential to acknowledge and respect the intellectual property rights of all contributors.

## Third-Party Component Licenses

This project includes software and libraries developed by third parties, each distributed under its specific license. All these licenses are compatible with Graphene's main license and have been selected to ensure maximum transparency and compliance.

## Complete Licenses File

To view the complete list of all used third-party libraries along with their full license texts, please refer to:

**[Credits and License](../main/app/src/main/res/raw/third_party_licenses)**

This file contains:
- Complete list of all third-party dependencies
- Full texts of all applicable licenses
- Attribution details for each component
- Links to original project repositories

We are committed to meticulously respecting all terms of the open source licenses for the components we use. If you notice any discrepancies or issues regarding license attributions, we encourage you to report them through the project's issues.

## Acknowledgments

We are grateful to all developers and projects that have made Graphene possible through their work. Our commitment to open source includes not only developing free software but also properly acknowledging all contributions that make our project possible.