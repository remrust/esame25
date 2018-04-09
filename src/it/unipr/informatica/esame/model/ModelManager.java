package it.unipr.informatica.esame.model;

import java.util.List;

public interface ModelManager {
	public ModelTemperatura insertTemperatura(double temperatura) throws ModelException;
	
	public ModelPurezza insertPurezza(int purezza) throws ModelException;
	
	public ModelStato insertStatoTemperatura(double temperatura) throws ModelException;
	
	public ModelStato insertStatoPurezza(int purezza) throws ModelException;
	
	public ModelStato getStato() throws ModelException;
	
	public List<ModelPurezza> getAllPurezza() throws ModelException;
	
	public List<ModelTemperatura> getAllTemperatura() throws ModelException;
}