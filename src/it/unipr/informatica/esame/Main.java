package it.unipr.informatica.esame;

import java.util.ArrayList;
import java.util.List;

import it.unipr.informatica.esame.gestori.*;

public class Main {
	public static void main(String[] args) {
		
		
		List<Manager> lg = new ArrayList<>();
		lg.add(new ManagerPurezza());
		lg.add(new ManagerTemperatura());
		
		
		for(int i = 0; i < 4; i++)
			for(Manager g : lg)
				g.insert();
		
	}
}