# MsalClientCertDemo

## Description
This project is a Java application that uses the **Microsoft Authentication Library (MSAL)** to authenticate with Entra ID
using a client certificate. The application accepts command-line parameters to configure authentication and retrieve an access token.

## Requirements
- **Java 17+**
- **Maven**
- A valid PFX certificate with a password
- Registered application (client ID) and tenant ID in Azure AD
- Configured access scope in Azure AD

## Compilation
To compile the project, run the following command in the project's root directory:

```bash
mvn clean install
```

## Execution
To run the application, use the following command:

```bash
java -jar target/MsalClientCertDemo-1.0-SNAPSHOT-shaded.jar -c <clientId> -t <tenantId> -f <pfxFile> -p <pfxPassword> -s <scope>
```

## Parameters
* -c, --clientId: Application (client) ID (required)
* -t, --tenantId: Directory (tenant) ID (required)
* -f, --pfxFile: Path to the PFX file (required)
* -p, --pfxPassword: Password for the PFX file (optional if the certificate is not password-protected)
* -s, --scope: Access scope (required, space-separated if multiple)
* -h, --help: Displays help

## Example

```bash
java -jar target/MsalClientCertDemo-1.0-SNAPSHOT.jar -c "your-client-id" -t "your-tenant-id" -f "path/to/your/certificate.pfx" -p "your-pfx-password" -s "https://graph.microsoft.com/.default"
```

This command authenticates the application using the specified certificate and retrieves an access token for the provided scope.