package com.ufo.core.config.environment.bean;

import java.util.Map;
import java.util.Properties;

/**
 * Created by FOG on 20.02.2018.
 *
 * Entity that represents environment configurations holder.
 */
public class EnvConfig {

    private String environmentName;
    private Properties configProperties;

    public EnvConfig(String envName){
        environmentName = envName;
        configProperties = new Properties();
    }

    public EnvConfig(String envName, Properties defaultProps){
        environmentName = envName;
        configProperties = defaultProps;
    }

    public void setEnvironmentName(String environmentName){
        this.environmentName = environmentName;
    }

    public EnvConfig addProperties(Properties properties){
        for (Map.Entry<Object, Object> entry : properties.entrySet()){
            configProperties.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public EnvConfig addProperty(String key, String value){
        configProperties.put(key, value);
        return this;
    }

    public EnvConfig addProperty(String key, Object value){
        configProperties.put(key, value);
        return this;
    }

    public void setProperties(Properties properties){
        configProperties = properties;
    }

    public String get(String name){
        return getValue(name);
    }

    public int getInt(String name){
        try {
            return Integer.parseInt(getValue(name));
        } catch (NumberFormatException e){
            return 0;
        }
    }

    public float getFloat(String name){
        try {
            return Float.valueOf(getValue(name));
        } catch (NumberFormatException e){
            return 0;
        }
    }

    public double getDouble(String name){
        try {
            return Double.valueOf(getValue(name));
        } catch (NumberFormatException e){
            return 0;
        }
    }

    public boolean getBool(String name){
        return Boolean.valueOf(getValue(name));
    }

    private String getValue(String key){
        String argValue = System.getProperty(key);
        if (argValue != null && !argValue.isEmpty()){
            return argValue;
        }
        return configProperties.getProperty(key.toLowerCase());
    }

    public String getPropertyByValue(String value){
        for (Map.Entry<Object, Object> entry : System.getProperties().entrySet()) {
            if (entry.getValue() != null && String.valueOf(entry.getValue()).equalsIgnoreCase(value)){
                return String.valueOf(entry.getKey());
            }
        }

        for (Map.Entry<Object, Object> entry : configProperties.entrySet()) {
            if (entry.getValue() != null && String.valueOf(entry.getValue()).equalsIgnoreCase(value)){
                return String.valueOf(entry.getKey());
            }
        }
        return "";
    }

    //TODO: think about merge strategies
    //TODO: rethink interface solution
    public EnvConfig mergeAsDefault(Properties defaults){
        for (Map.Entry<Object, Object> entry : defaults.entrySet()) {
            if (configProperties.get(entry.getKey()) == null) {
                addProperty(String.valueOf(entry.getKey()), entry.getValue());
            }
        }
        return this;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public Properties getConfigProperties() {
        return configProperties;
    }

    @Override
    public String toString() {
        return "EnvConfig{" +
                "environmentName='" + environmentName + '\'' +
                ", configProperties=" + configProperties +
                '}';
    }
}
