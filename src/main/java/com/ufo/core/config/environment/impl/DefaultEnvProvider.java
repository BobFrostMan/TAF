package com.ufo.core.config.environment.impl;

import com.ufo.core.common.SearchStrategy;
import com.ufo.core.config.environment.IEnvProvider;
import com.ufo.core.config.environment.bean.EnvConfig;
import com.ufo.core.exception.EnvironmentNotProvidedException;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by FOG on 20.02.2018.
 */
public class DefaultEnvProvider implements IEnvProvider {

    private static final String DEFAULT_ENV_NAME = "local";
    private static final String DEFAULT_ENV_CONFIG_PATH = "src/main/resource/env/";

    private Map<String, EnvConfig> envConfigs;

    public DefaultEnvProvider() {
        envConfigs = new HashMap<>();
    }

    @Override
    public EnvConfig getConfig(String envName) {
        EnvConfig config = envConfigs.get(envName);
        if (config == null) {
            throw new EnvironmentNotProvidedException("Environment '" + envName + "' properties are not loaded!");
        }
        return config;
    }

    @Override
    public EnvConfig loadEnvironment(String envName) {
        return loadEnvironment(envName, DEFAULT_ENV_CONFIG_PATH);
    }

    @Override
    public EnvConfig loadEnvironment(String envName, String path, Properties defaultProps) {
        //TODO: rethink interface
        return null;
    }

    @Override
    public EnvConfig loadEnvironment(String envName, String path) {
        //TODO: rethink interface
        //TODO: load properties
        return null;
    }

    @Override
    public void loadEnvironments() {
        //in basic common case you have a simple single instance of environment
        String envName = System.getProperty("env", DEFAULT_ENV_NAME);
        String configPath = System.getProperty("config", DEFAULT_ENV_CONFIG_PATH);
        loadEnvironment(envName, configPath);
    }

    @Override
    public Map<String, EnvConfig> getEnvironmentConfigs() {
        return envConfigs;
    }

    public String getEnvNameByPropertyValue(String key, String valueToSearch) {
        return getEnvNameByPropertyValue(key, valueToSearch, SearchStrategy.EXACT_MATCH);
    }

    public String getEnvNameByPropertyValue(String key, String valueToSearch, SearchStrategy strategy) {
        for (Map.Entry<String, EnvConfig> envConfigEntry : envConfigs.entrySet()) {
            if (strategy.search(envConfigEntry.getValue().get(key), valueToSearch)) {
                return envConfigEntry.getKey();
            }
        }
        throw new EnvironmentNotProvidedException("Environment with existing key=value pair was not found by '" + strategy.name() +"' search!");
    }
}
