# Graphene: An Integrated Ecosystem for Secure and Private Communications in Critical Contexts

The fundamental premise of the Graphene project is the recognition that secure messaging software loses much of its effectiveness when operating on a device vulnerable at the operating system level. A phone with a generic OS, often saturated with proprietary services and background tracking, represents an unacceptable weak link for high-risk profiles. For this reason, Graphene is conceived not merely as a standalone application, but as the software component of a comprehensive security ecosystem, specifically designed to operate on the GrapheneOS.

The synergistic combination of the Graphene application and the Graphene operating system represents the ideal solution, as it addresses the security problem holistically. GrapheneOS introduces fundamental privacy and robustness measures at the system level, chief among them being the implementation of advanced per-application sandboxing, which isolates app data and drastically limits its ability to access sensitive resources without explicit consent; the stripping of proprietary Google services from the system, replaced with open-source alternatives and a sandboxed implementation of Play Services where necessary, thereby reducing the attack surface and system-level tracking; and kernel and memory hardening policies that mitigate zero-day vulnerabilities. Within this fortified context, the Graphene application can fully realize its potential, managing the end-to-end encryption of communications and local data, while operating on a platform that actively protects its processes and data from compromise of the underlying system.

This integrated solution establishes itself as the ideal standard for institutional and diplomatic figures such as ambassadors, members of diplomatic corps on mission, United Nations officials in high-risk scenarios, and heads of state. For these profiles, the compromise of communications is not a simple privacy violation, but an event with potential consequences for national security and the outcome of delicate international negotiations. The Graphene ecosystem provides them with a communication platform resistant to interception, guaranteeing the authenticity and confidentiality of exchanged messages. The application isolation characteristic of GrapheneOS prevents malicious software, which might infiltrate through other channels, from accessing the sensitive data within the messaging app. Furthermore, the open-source nature of the entire stack, from the operating system to the application, allows for independent security audits by the government entities adopting it, verifying the absence of backdoors and the correctness of cryptographic implementations. Ultimately, the union of GrapheneOS and the Graphene app offers not just a secure communication tool, but provides a veritable mobile fortress for the most sensitive information, engineered to withstand hostile digital environments.

![Graphene](app/src/main/res/drawable-mdpi/welcome.webp)

## Graphene: Secure Messaging Reimagined

Graphene is an Android messaging application that originated as a fork of the renowned Signal app, aiming to provide a more secure, privacy-respecting, and fully open-source alternative. The project stands out for reintroducing security features that Signal had abandoned, such as the ability to encrypt the local database with a passphrase, thereby ensuring added protection for data stored on the device.

It is important to emphasize that this project has its roots in Criptogram, the celebrated messaging software that acted as a precursor to more modern messaging applications. Criptogram was one of the first systems inspired by trustless technology, with core concepts derived directly from the Bitcoin protocol. The fundamental cybersecurity principles introduced by Criptogram were later adopted and reused by software such as Signal, Threema, Wire, and many others.

Our goal is to create a significantly enhanced version of these technologies, leveraging the open-source philosophy and providing – as experts and the original authors of Criptogram and similar systems – a comprehensive cybersecurity audit concerning the algorithms and technical solutions implemented in this project.

Graphene connects to Signal's servers, allowing users to communicate with their contacts seamlessly, but it integrates a series of improvements that make it particularly suited for those seeking a more controlled experience, independent of Google's proprietary services. Among its main features are support for the UnifiedPush protocol, which enables receiving push notifications without relying on Firebase Cloud Messaging, and compatibility with Tor and SOCKS proxies, allowing traffic to be routed through anonymous networks.

Additionally, Graphene offers functionalities such as secure RAM wiping, automatic app locking based on user-defined conditions, advanced backup management, and the ability to link multiple devices to the same Signal account, including Android tablets. The project is available in two versions: Graphene, which includes proprietary components to ensure full compatibility with Signal, and Graphene-FOSS, completely free from non-open-source software, designed for those who desire a fully auditable application. Both versions can be installed and switched without data loss or the need to re-register.

Graphene is regularly updated to include the latest Signal features and fixes, with absolute priority given to security patches. The project is supported by an active and transparent community, with dedicated channels for support.

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

## Open Source License Compliance

We are committed to meticulously respecting all terms of the open source licenses for the components we use. If you notice any discrepancies or issues regarding license attributions, we encourage you to report them through the project's issues.

## Acknowledgments

We are grateful to all developers and projects that have made Graphene possible through their work. Our commitment to open source includes not only developing free software but also properly acknowledging all contributions that make our project possible.