package it.unipr.informatica.esame.sensori;

import java.util.Timer;
import java.util.TimerTask;

import it.unipr.informatica.esame.Sensori;

public class SensoriManager extends SchedulerLettori{
	private Double temperatura;
	private Integer purezza;
	
	public SensoriManager() {
		this.initializeAttributes();
		
		new Timer().scheduleAtFixedRate(
			new TimerTask() {
				@Override
				public void run() {
					try {
						timeout();
					}
					catch (Throwable e) {
						e.printStackTrace();
					}
				}
			},
		0, 1000);
	}
	
	private void timeout() {
		synchronized(temperatura) {
			this.temperatura = Sensori.leggiTemperatura();
		}
		synchronized(purezza) {
			this.purezza = Sensori.leggiPurezza();
		}
	}
	
	public MyFuture<Double> getTemperatura() {
		MyFuture<Double> ris = new MyFuture<>();
		
		accodaTemperatura(
			new ReaderTemperatura(){
				@Override
				public void read() {
					try {
						synchronized(temperatura) {
							ris.set(temperatura);
						}
					}
					catch (Throwable e) {
						ris.setErrore(e);
					}
				}
			}
		);
		
		return ris;
	}
	
	public MyFuture<Integer> getPurezza() {
		MyFuture<Integer> ris = new MyFuture<>();
		
		accodaPurezza(new ReaderPurezza(){
			@Override
			public void read() {
				try {
					synchronized(purezza) {
						ris.set(purezza);
					}
				}
				catch (Throwable e) {
					ris.setErrore(e);
				}
			}
		});
		
		return ris;
	}
	private void initializeAttributes() {
		this.temperatura = Sensori.leggiTemperatura();
		this.purezza = Sensori.leggiPurezza();
	}
}
