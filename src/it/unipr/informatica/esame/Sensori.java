package it.unipr.informatica.esame;

import java.util.Random;

public class Sensori {
	public static double leggiTemperatura() {
		double tempMax = 100;
		double tempMin = 1;
		
		Random r = new Random();
	    return (r.nextInt((int)((tempMax-tempMin)*10+1))+tempMin*10) / 10.0;
	}
	public static int leggiPurezza() {
		int purMax = 250;
		int purMin = 0;
		
		Random r = new Random();
	    return (r.nextInt(purMax - purMin + 1) + purMin);
	}
}
