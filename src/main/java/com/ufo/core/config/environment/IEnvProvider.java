package com.ufo.core.config.environment;

import com.ufo.core.config.environment.bean.EnvConfig;

import java.util.Map;
import java.util.Properties;

/**
 * Created by FOG on 20.02.2018.
 */
public interface IEnvProvider {

    public void loadEnvironments();

    public EnvConfig loadEnvironment(String envName);

    public EnvConfig loadEnvironment(String envName, String path, Properties defaultProps);

    public EnvConfig loadEnvironment(String envName, String path);

    public EnvConfig getConfig(String name);

    public Map<String, EnvConfig> getEnvironmentConfigs();
}
