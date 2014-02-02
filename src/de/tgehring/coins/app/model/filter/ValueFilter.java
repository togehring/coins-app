package de.tgehring.coins.app.model.filter;

import java.util.LinkedList;
import java.util.List;

import de.tgehring.coins.app.model.CoinDto;

public class ValueFilter implements CoinFilterInterface {
	
	private double value;
	
	public ValueFilter(double value) {
		this.value = value;
	}

	@Override
	public List<CoinDto> applyFilter(List<CoinDto> coins) {
		List<CoinDto> filteredCoins = new LinkedList<CoinDto>();
		for(CoinDto coin: coins) {
			if (coin.value == value) {
				filteredCoins.add(coin);
			}
		}
		
		return filteredCoins;
	}
}
