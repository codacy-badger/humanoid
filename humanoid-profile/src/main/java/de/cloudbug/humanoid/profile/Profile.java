package de.cloudbug.humanoid.profile;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MongoEntity
public class Profile extends PanacheMongoEntity {

    @Size(min = 3, max = 50)
    public String firstName;

    @Size(min = 3, max = 50)
    public String lastName;

    @NotNull
    public String employeeId;

    public static Profile findByEmployeeId(String employeeId) {
        return find("employeeId", employeeId).firstResult();
    }

}
