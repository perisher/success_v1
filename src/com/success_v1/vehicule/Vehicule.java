package com.success_v1.vehicule;

public class Vehicule {
	String id;
	String marque;
	String modele;
	String motorisation;
	String prix;
	
	public Vehicule(String identif, String mark, String mod, String motor, String price)
	{
		id = identif;
		marque = mark;
		modele=mod;
		motorisation=motor;
		prix = price;
	}
	public Vehicule()
	{
		
	}
}
