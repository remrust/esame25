package it.unipr.informatica.esame.sensori;

public class MyFuture<T> {
	private T dato;
	private Throwable errore;
	
	public MyFuture() {
		this.dato = null;
	}

	public synchronized void set(T valore) {
		if(valore == null)
			throw new IllegalArgumentException();
		if(dato != null)
			throw new IllegalStateException();
			
		dato = valore;
		
		notifyAll();
	}
	
	public synchronized T get() throws InterruptedException {
		while(dato == null && errore == null)
			wait();
		
		return dato;
	}
	
	public synchronized void setErrore(Throwable e) {
		if(e == null)
			throw new IllegalArgumentException();
		if(errore != null)
			throw new IllegalStateException();
			
		errore = e;
		
		notifyAll();
	}
	
	public synchronized Throwable getErrore() throws InterruptedException {
		while(errore == null)
			wait();
		
		return errore;
	}
}
