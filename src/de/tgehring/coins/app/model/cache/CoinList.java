package de.tgehring.coins.app.model.cache;

import java.util.LinkedList;
import java.util.List;

import de.tgehring.coins.app.model.CoinDto;

public class CoinList {
	private static CoinList instance = null;
	private List<CoinDto> coins;

	private CoinList() {
		coins = new LinkedList<CoinDto>();
	}

	public static CoinList getInstance() {
		if (instance == null) {
			instance = new CoinList();
		}
		return instance;
	}
	
	public void setCoinList(List<CoinDto> coins) {
		this.coins = coins;
	}
	
	public int getSize() {
		return this.coins.size();
	}
	
	public List<CoinDto> getCoinList() {
		return this.coins;
	}
}
