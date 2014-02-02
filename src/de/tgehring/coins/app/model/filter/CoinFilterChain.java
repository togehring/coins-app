package de.tgehring.coins.app.model.filter;

import java.util.LinkedList;
import java.util.List;

import de.tgehring.coins.app.model.CoinDto;

public class CoinFilterChain implements CoinFilterInterface {
	
	private List<CoinFilterInterface> filters;
	
	public CoinFilterChain() {
		this.filters = new LinkedList<CoinFilterInterface>();
	}
	
	public void addFilter(CoinFilterInterface filter) {
		this.filters.add(filter);
	}

	@Override
	public List<CoinDto> applyFilter(List<CoinDto> coins) {
		List<CoinDto> filteredCoins = coins;
		for (CoinFilterInterface filter: filters) {
			filteredCoins = filter.applyFilter(filteredCoins);
		}
		
		return filteredCoins;
	}

}
