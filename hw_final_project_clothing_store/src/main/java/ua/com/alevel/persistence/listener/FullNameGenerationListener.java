package ua.com.alevel.persistence.listener;

import ua.com.alevel.persistence.entity.users.Personal;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

public class FullNameGenerationListener {

    @PostLoad
    @PostPersist
    @PostUpdate
    public void generateFullName(Personal personal) {
        if (StringUtils.isBlank(personal.getFirstName()) || StringUtils.isBlank(personal.getLastName())) {
            personal.setFullName("");
            return;
        }
        personal.setFullName(personal.getFirstName() + " " + personal.getLastName());
    }
}
