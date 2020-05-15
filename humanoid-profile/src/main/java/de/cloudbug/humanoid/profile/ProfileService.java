package de.cloudbug.humanoid.profile;

import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

@ApplicationScoped
@Transactional(REQUIRED)
public class ProfileService {

    @Transactional(SUPPORTS)
    public List<Profile> findAllProfiles() {
        return Profile.listAll();
    }

    @Transactional(SUPPORTS)
    public Profile findProfileById(ObjectId id) {
        return Profile.findById(id);
    }

    @Transactional(SUPPORTS)
    public Profile findProfileByEmployeeId(String employeeId) {
        return Profile.find("employeeId", employeeId).firstResult();
    }

    public Profile persistProfile(@Valid Profile profile) {
        Profile.persist(profile);
        return profile;
    }

    public Profile updateProfile(@Valid Profile profile) {
        Profile.persistOrUpdate(profile);
        return profile;
    }

    public void deleteProfile(ObjectId id) {
        Profile profile = Profile.findById(id);
        profile.delete();
    }
}
