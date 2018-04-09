package it.unipr.informatica.esame.database;

import it.unipr.informatica.esame.model.ModelPurezza;

public class DatabasePurezza implements ModelPurezza{
	private int purezza;
	private int ID;
	
	public DatabasePurezza(int ID, int purezza) {
		if(ID < 0)
			throw new IllegalArgumentException();
		if(purezza > 250 || purezza < 0)
			throw new IllegalArgumentException();
		
		this.ID = ID;
		this.purezza = purezza;
	}
	
	@Override
	public int getPurezza() {
		return purezza;
	}

	@Override
	public int getID() {
		return ID;
	}
}
