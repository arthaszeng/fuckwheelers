package com.trailblazers.freewheelers.configuration;

import org.togglz.core.Feature;
import org.togglz.core.manager.TogglzConfig;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.file.FileBasedStateRepository;
import org.togglz.core.user.FeatureUser;
import org.togglz.core.user.SimpleFeatureUser;
import org.togglz.core.user.UserProvider;

import java.io.File;

public class ToggledFeatureConfiguration implements TogglzConfig {
    public Class<? extends Feature> getFeatureClass() {
        return ToggledFeature.class;
    }

    public StateRepository getStateRepository() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("features.properties").getFile());
        return new FileBasedStateRepository(file);
    }

    @Override
    public UserProvider getUserProvider() {
        return new UserProvider() {
            @Override
            public FeatureUser getCurrentUser() {
                return new SimpleFeatureUser("admin", true);
            }
        };
    }
}
