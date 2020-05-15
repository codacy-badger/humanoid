package de.cloudbug.humanoid.profile;

import de.cloudbug.humanoid.profile.client.Project;
import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@MongoEntity
public class Profile extends PanacheMongoEntity {

    public ObjectId id;

    @Size(min = 3, max = 50)
    public String firstName;

    @Size(min = 3, max = 50)
    public String lastName;

    @NotNull
    public String employeeId;

    public List<Project> projects;

    public static Profile findByEmployeeId(String employeeId) {
        return find("employeeId", employeeId).firstResult();
    }


}
