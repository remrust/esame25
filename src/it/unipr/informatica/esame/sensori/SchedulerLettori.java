package it.unipr.informatica.esame.sensori;

import java.util.LinkedList;

public abstract class SchedulerLettori {
	private LinkedList<ReaderTemperatura> listTem;
	private LinkedList<ReaderPurezza> listPur;
	
	protected SchedulerLettori() {
		this.listTem = new LinkedList<>();
		this.listPur = new LinkedList<>();
		
		new Thread() {
			@Override
			public void run() {
				try {
					loopTem();
				}
				catch (InterruptedException exception) {
					// Volutamente in bianco
				}
			}
		}.start();
		
		new Thread() {
			@Override
			public void run() {
				try {
					loopPur();
				}
				catch (InterruptedException exception) {
					// Volutamente in bianco
				}
			}
		}.start();
	}
	
	protected void accodaTemperatura(ReaderTemperatura reader) {
		synchronized (listTem) {
			listTem.add(reader);
			listTem.notifyAll();		
		}
	}
	
	protected void accodaPurezza(ReaderPurezza reader) {
		synchronized (listPur) {
			listPur.add(reader);
			listPur.notifyAll();
		}
	}
	
	private void loopTem() throws InterruptedException {
		for(;;) {
			ReaderTemperatura reader;
			
			synchronized(listTem) {
				while(listTem.isEmpty())
					listTem.wait();
			
				reader = listTem.removeFirst();
			}
			
			try {
				reader.read();
			}
			catch(Throwable e) {
				e.printStackTrace();
			}
		}
	}
	
	private void loopPur() throws InterruptedException {
		for(;;) {
			ReaderPurezza reader;
			
			synchronized(listPur) {
				while(listPur.isEmpty())
					listPur.wait();
			
				reader = listPur.removeFirst();
			}
			
			try {
				reader.read();
			}
			catch(Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
