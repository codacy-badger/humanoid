package de.cloudbug.humanoid.profile.client;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Project {

    @NotNull
    public String title;

    @Size(max = 250, min = 2)
    public String description;

}
