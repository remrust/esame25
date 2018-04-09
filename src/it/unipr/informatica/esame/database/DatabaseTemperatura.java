package it.unipr.informatica.esame.database;

import it.unipr.informatica.esame.model.ModelTemperatura;;

public class DatabaseTemperatura implements ModelTemperatura{
	private double temperatura;
	private int ID;
	
	public DatabaseTemperatura(int ID, double temperatura) {
		if(ID < 0)
			throw new IllegalArgumentException();
		
		this.ID = ID;
		this.temperatura = temperatura;
	}
	
	@Override
	public double getTemperatura() {
		return temperatura;
	}

	@Override
	public int getID() {
		return ID;
	}
}
