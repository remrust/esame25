package it.unipr.informatica.esame.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.unipr.informatica.esame.Configuration;
import it.unipr.informatica.esame.model.ModelManager;
import it.unipr.informatica.esame.model.ModelPurezza;
import it.unipr.informatica.esame.model.ModelStato;
import it.unipr.informatica.esame.model.ModelTemperatura;
import it.unipr.informatica.esame.model.ModelException;

public class DatabaseModelManager implements ModelManager{
	private String url;
	
	public DatabaseModelManager() {
		try {
			Configuration conf = Configuration.getConfiguration();
			
			this.url = conf.getStringFromProperties("it.unipr.informatica.esame.database.url");
			
			String classNameString = conf.getStringFromProperties("it.unipr.informatica.esame.database.driver");
			Class.forName(classNameString);
		}
		catch (Throwable e) {
			throw new IllegalStateException(e);
		}
	}
	
	@Override
	public List<ModelPurezza> getAllPurezza() throws ModelException {
		String queryString = "SELECT * FROM PUREZZA ORDER BY ID DESC";
		try (
			Connection conn = DriverManager.getConnection(url);
			Statement st = conn.createStatement();
			ResultSet queryResult = st.executeQuery(queryString);
		) {
			List<ModelPurezza> listPurezza = new ArrayList<>();
			
			while(queryResult.next()) {
				int id = queryResult.getInt("ID");
				int purezza = queryResult.getInt("LIVELLO");
				ModelPurezza pur = new DatabasePurezza(id, purezza);
				
				listPurezza.add(pur);
			}
			
			return listPurezza;
		}
		catch (Throwable e) {
			throw new ModelException(e);
		}
	}
	
	@Override
	public List<ModelTemperatura> getAllTemperatura() throws ModelException {
		String queryString = "SELECT * FROM TEMPERATURA ORDER BY ID DESC";
		try (
			Connection conn = DriverManager.getConnection(url);
			Statement st = conn.createStatement();
			ResultSet queryResult = st.executeQuery(queryString);
		) {
			List<ModelTemperatura> listTemperatura = new ArrayList<>();
			
			while(queryResult.next()) {
				int id = queryResult.getInt("ID");
				double temperatura = queryResult.getDouble("GRADI");
				ModelTemperatura tem = new DatabaseTemperatura(id, temperatura);
				
				listTemperatura.add(tem);
			}
			
			return listTemperatura;
		}
		catch (Throwable e) {
			throw new ModelException(e);
		}
	}
	
	@Override
	public ModelTemperatura insertTemperatura(double temperatura) throws ModelException{
		String queryString = "INSERT INTO TEMPERATURA(GRADI) VALUES(?)";
		try (
				Connection conn = DriverManager.getConnection(url);
				PreparedStatement st = conn.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);
		){		
			st.setDouble(1, temperatura);
				
			if(st.execute())
				throw new IllegalStateException();
				
			ResultSet queryResult = st.getGeneratedKeys();
			if(!queryResult.next()) {
				queryResult.close();
				throw new IllegalArgumentException();
			}
			int id = queryResult.getInt(1);
			queryResult.close();
				
				
			return new DatabaseTemperatura(id, temperatura);
			
		}
		catch (Throwable e) {
			throw new ModelException(e);
		}
	}
	
	@Override
	public ModelPurezza insertPurezza(int purezza) throws ModelException{
		String queryString = "INSERT INTO PUREZZA(LIVELLO) VALUES(?)";
		try (
				Connection conn = DriverManager.getConnection(url);
				PreparedStatement st = conn.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);
			){		
				st.setInt(1, purezza);
				
				if(st.execute())
					throw new IllegalStateException();
				
				ResultSet queryResult = st.getGeneratedKeys();
				if(!queryResult.next()) {
					queryResult.close();
					throw new IllegalArgumentException();
				}
				int id = queryResult.getInt(1);
				queryResult.close();
				
				
				return new DatabasePurezza(id, purezza);
			}
			catch (Throwable e) {
				throw new ModelException(e);
			}
	}
	
	@Override
	public ModelStato insertStatoPurezza(int purezza) throws ModelException{
		String queryString = "INSERT INTO STATO(TEMPERATURA, PUREZZA) VALUES(?, ?)";
		try (
			Connection conn = DriverManager.getConnection(url);
			PreparedStatement st = conn.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);
		){
			ModelStato stato = this.getStato();
			Double temperatura = stato.getTemperatura();
			st.setDouble(1, temperatura);
			st.setInt(2, purezza);
			
			if(st.execute())
				throw new IllegalStateException();
			
			ResultSet queryResult = st.getGeneratedKeys();
			if(!queryResult.next()) {
				queryResult.close();
				throw new IllegalArgumentException();
			}
			queryResult.close();
			
			
			return new DatabaseStato(temperatura, purezza);
		}
		catch (Throwable e) {
			throw new ModelException(e);
		}
	}
	
	@Override
	public ModelStato insertStatoTemperatura(double temperatura) throws ModelException{
			String queryString = "INSERT INTO STATO(TEMPERATURA, PUREZZA) VALUES(?, ?)";
			try (
				Connection conn = DriverManager.getConnection(url);
				PreparedStatement st = conn.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);
			){
				ModelStato stato = this.getStato();
				int purezza = stato.getPurezza();
				st.setDouble(1, temperatura);
				st.setInt(2, purezza);
				
				if(st.execute())
					throw new IllegalStateException();
				
				ResultSet queryResult = st.getGeneratedKeys();
				if(!queryResult.next()) {
					queryResult.close();
					throw new IllegalArgumentException();
				}
				queryResult.close();
				
				
				return new DatabaseStato(temperatura, purezza);
			}
			catch (Throwable e) {
				throw new ModelException(e);
			}
	}
	
	@Override
	public ModelStato getStato() throws ModelException{
		String queryString = "SELECT * FROM STATO";
		try(
			Connection conn = DriverManager.getConnection(url);
			Statement st = conn.createStatement();
			ResultSet queryResult = st.executeQuery(queryString);
		){
			List<ModelStato> listStati = new ArrayList<>();
			
			while(queryResult.next()) {
				double temperatura = queryResult.getDouble("TEMPERATURA");
				int purezza = queryResult.getInt("PUREZZA");
				listStati.add(new DatabaseStato(temperatura, purezza));
			}
			return listStati.get(listStati.size()-1);
		}
		catch (Throwable e) {
			throw new ModelException(e);
		}
	}
}

/*
	DB STUCTURE:
	
	connect 'jdbc:derby://localhost/Esame25; create = true';
	create table TEMPERATURA(ID integer generated by default as identity, GRADI double);
	create table PUREZZA(ID integer generated by default as identity, LIVELLO int);
	create table STATO(TEMPERATURA double, PUREZZA int);
*/
