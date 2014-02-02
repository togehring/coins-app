package de.tgehring.coins.app.service;

import android.net.Uri;

import de.tgehring.coins.app.utils.NetworkUtility;

public class RemoteService {

	private static RemoteService instance = null;
	private final String BASE_URL = "http://www.tgehring.de:8080/Coins-Server/crud/%s";

	private RemoteService() {
		
	}

	public static RemoteService getInstance() {
		if (instance == null) {
			instance = new RemoteService();
		}
		return instance;
	}
	
	public String getCountries() {
		NetworkUtility nwu = new NetworkUtility();
		Uri source = Uri.parse(String.format(BASE_URL, "countries"));
		
		return nwu.get(source);
	}
	
	public String getCoins() {
		NetworkUtility nwu = new NetworkUtility();
		Uri source = Uri.parse(String.format(BASE_URL, "coins"));
		
		return nwu.get(source);
	}
}
