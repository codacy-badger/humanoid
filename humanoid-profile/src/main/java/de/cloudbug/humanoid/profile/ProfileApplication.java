package de.cloudbug.humanoid.profile;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
@OpenAPIDefinition(
        info = @Info(title = "Profile API",
                description = "This API allows CRUD operations on a profile",
                version = "1.0",
                contact = @Contact(name = "cloudbug", url = "https://github.com/cloudbug2020")),
        servers = {
                @Server(url = "http://localhost:8080")
        },
        tags = {
                @Tag(name = "api", description = "Public that can be used by anybody"),
                @Tag(name = "profile", description = "profiles of employees in company")
        }
)
public class ProfileApplication extends Application {
}

