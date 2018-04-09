package it.unipr.informatica.esame.gestori;

import it.unipr.informatica.esame.Configuration;
import it.unipr.informatica.esame.model.ModelManager;
import it.unipr.informatica.esame.sensori.MyFuture;
import it.unipr.informatica.esame.sensori.SensoriManager;

public class ManagerPurezza implements Manager{
	private Thread t;
	@Override
	public void insert(){
		t = new Thread() {
			@Override
			public void run() {
				actualInsert();
			}
		};
		t.start();
	}
	
	private void actualInsert() {
		Configuration conf = Configuration.getConfiguration();
		SensoriManager sens =  conf.getSensoriManager();
		
		MyFuture<Integer> f = sens.getPurezza();
		
		try {
			ModelManager model = conf.getModelManager();
			model.insertPurezza(f.get());
			model.insertStatoPurezza(f.get());
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public boolean isWorking() {
		return t.isAlive();
	}
}