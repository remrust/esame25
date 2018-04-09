package it.unipr.informatica.esame.database;

import it.unipr.informatica.esame.model.ModelStato;

public class DatabaseStato implements ModelStato{
	private double temperatura;
	private int purezza;
	
	public DatabaseStato(double temperatura, int purezza) {
		//if()
		//	throw new IllegalArgumentException();
		
		this.temperatura = temperatura;
		this.purezza = purezza;
	}
	
	@Override
	public int getPurezza() {
		return purezza;
	}
	@Override
	public double getTemperatura() {
		return temperatura;
	}
}
