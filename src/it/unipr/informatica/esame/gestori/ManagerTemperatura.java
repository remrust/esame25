package it.unipr.informatica.esame.gestori;

import it.unipr.informatica.esame.Configuration;
import it.unipr.informatica.esame.model.ModelManager;
import it.unipr.informatica.esame.sensori.MyFuture;
import it.unipr.informatica.esame.sensori.SensoriManager;

public class ManagerTemperatura implements Manager{
	private Thread t;
	
	@Override
	public void insert() {
		t = new Thread() {
			@Override
			public void run() {
				actualInsert();
			}
		};
		t.start();
	}
	public void actualInsert() {
		Configuration conf = Configuration.getConfiguration();
		SensoriManager sens =  conf.getSensoriManager();
		
		MyFuture<Double> f = sens.getTemperatura();
		
		try {
			ModelManager model = conf.getModelManager();
			model.insertTemperatura(f.get());
			model.insertStatoTemperatura(f.get());
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public boolean isWorking() {
		return t.isAlive();
	}
}
