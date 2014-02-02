package de.tgehring.coins.app.model.filter;

import java.util.LinkedList;
import java.util.List;

import de.tgehring.coins.app.model.CoinDto;

public class YearFilter implements CoinFilterInterface {
	
	private int year;
	
	public YearFilter(int year) {
		this.year = year;
	}

	@Override
	public List<CoinDto> applyFilter(List<CoinDto> coins) {
		List<CoinDto> filteredCoins = new LinkedList<CoinDto>();
		for(CoinDto coin: coins) {
			if (coin.year == year) {
				filteredCoins.add(coin);
			}
		}
		
		return filteredCoins;
	}

}
