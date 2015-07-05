package br.com.resource;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class PlayerSystemProperties {

	private static String propertiesFile = "playersystem.properties";
	private Properties properties;
	private InputStream inputStream;
	
	public PlayerSystemProperties(){
		properties = new Properties();
	}
	
	public String getProperty(String property) throws IOException{		
		inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFile);
		if(inputStream != null){
			properties.load(inputStream);
		}else{
			throw new FileNotFoundException("Arquivo de configuração '"+ propertiesFile + "' não encontrado");
		}	
		return properties.getProperty(property);
	}	
}