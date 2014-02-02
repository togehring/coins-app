package de.tgehring.coins.app.model.filter;

import java.util.LinkedList;
import java.util.List;

import de.tgehring.coins.app.model.CoinDto;

public class CountryFilter implements CoinFilterInterface {
	
	private String countryName;
	
	public CountryFilter(String countryName) {
		this.countryName = countryName;
	}

	@Override
	public List<CoinDto> applyFilter(List<CoinDto> coins) {
		List<CoinDto> filteredCoins = new LinkedList<CoinDto>();
		for(CoinDto coin: coins) {
			if (coin.country.name.equals(this.countryName)) {
				filteredCoins.add(coin);
			}
		}
		
		return filteredCoins;
	}

}
