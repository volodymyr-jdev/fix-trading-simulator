# Native Image Stub Module

This module serves as a **technical workaround** required exclusively for building the application in **GraalVM Native Image** mode.

## 🛑 The Problem

The **QuickFIX/J** library contains legacy integration code for the **Proxool** connection pool (`org.logicalcobwebs.proxool`).

Although this project does not use Proxool, GraalVM performs a static analysis of all reachable code during native compilation. When it detects references to missing Proxool classes within QuickFIX/J, the build fails with linking errors:

```text
Error: Discovered unresolved type during parsing: org.logicalcobwebs.proxool.ProxoolDataSource
Caused by: java.lang.NoClassDefFoundError: org/logicalcobwebs/proxool/ProxoolDataSource