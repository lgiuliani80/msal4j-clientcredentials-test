package it.lucagiuliani;

import com.microsoft.aad.msal4j.*;
import org.apache.commons.cli.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;


public class MsalClientCertDemo {
    public static void main(String[] args) throws Exception {
        Options options = new Options();

        options.addOption("h", "help", false, "Show help");
        options.addOption("c", "clientId", true, "Application (client) ID");
        options.addOption("t", "tenantId", true, "Directory (tenant) ID");
        options.addOption("f", "pfxFile", true, "PFX file path");
        options.addOption("p", "pfxPassword", true, "PFX file password");
        options.addOption("s", "scope", true, "Scope");

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("h")) {
                formatter.printHelp("CommandLineDemo", options);
                return;
            }

            if (!cmd.hasOption("c") || !cmd.hasOption("t") || !cmd.hasOption("f") || !cmd.hasOption("s")) {
                throw new ParseException("Client Id, Tenant Id, PFX Fle and Scope are all mandatory!");
            }

            String clientId = cmd.getOptionValue("c");
            String tenantId = cmd.getOptionValue("t");
            String certPath = cmd.getOptionValue("f");
            String certPassword = cmd.getOptionValue("p");

            String authority = "https://login.microsoftonline.com/" + tenantId;
            Set<String> scopes = new HashSet<>(Arrays.asList(cmd.getOptionValue("s").split(" ")));

            try (InputStream pfxIs = new FileInputStream(certPath)) {
                IClientCredential credential = ClientCredentialFactory.createFromCertificate(pfxIs, certPassword);

                ConfidentialClientApplication app = ConfidentialClientApplication.builder(clientId, credential)
                        .authority(authority)
                        .build();

                ClientCredentialParameters parameters = ClientCredentialParameters.builder(scopes).build();

                CompletableFuture<IAuthenticationResult> future = app.acquireToken(parameters);
                IAuthenticationResult result = future.get();

                System.out.println("Access Token: ");
                System.out.println(result.accessToken());
            }

        } catch (ParseException e) {
            System.err.println("Parse Error: " + e.getMessage());
            formatter.printHelp("MsalClientCertDemo", options);
        }
    }
}
