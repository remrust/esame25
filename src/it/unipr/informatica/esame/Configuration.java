package it.unipr.informatica.esame;


import java.util.ResourceBundle;

import it.unipr.informatica.esame.model.ModelManager;
import it.unipr.informatica.esame.sensori.SensoriManager;

public class Configuration {
	private static Configuration instance = null;
	private ModelManager model = null;
	private SensoriManager sensori = null;
	
	private Configuration() {}
	
	public static Configuration getConfiguration() {
		if(instance == null)
			instance = new Configuration();
		
		return instance;
	}

	public ModelManager getModelManager() {
		try {
			if(model == null) {
				String classNameString = getStringFromProperties("it.unipr.informatica.esame.model");
				Class<?> toInstantiate = Class.forName(classNameString);
				model = (ModelManager)toInstantiate.newInstance();
			}
			return model;
		}
		catch (Throwable e) {
			throw new IllegalStateException(e);
		}
	}
	
	public SensoriManager getSensoriManager() {
		try {
			if(sensori == null) {
				String classNameString = getStringFromProperties("it.unipr.informatica.esame.sensori");
				Class<?> toInstantiate = Class.forName(classNameString);
				sensori = (SensoriManager)toInstantiate.newInstance();
			}
			return sensori;
		}
		catch (Throwable e) {
			throw new IllegalStateException(e);
		}
	}
	
	public String getStringFromProperties(String st) {
		if(st == null || "".equals(st))
			throw new IllegalArgumentException();

		ResourceBundle bundle = ResourceBundle.getBundle("configuration");
		
		return bundle.getString(st);
	}
}
