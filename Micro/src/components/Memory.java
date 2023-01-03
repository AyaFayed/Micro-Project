package components;

import java.util.*;
import java.util.Map.Entry;

import helper.Pair;

public class Memory {
	private HashMap<String, Double> mem;

	private static final Memory instance = new Memory();

	private Memory() {
		mem = new HashMap<>();
	}

	public static Memory getInstance() {
		return instance;
	}

	public void store(String address, double value) {
		mem.put(address, value);
	}

	public double load(String address) {
		return mem.getOrDefault(address, 0.0);
	}

	public void display() {
		System.out.println("Memory :");
		for (Entry<String, Double> item : mem.entrySet()) {
			System.out.println(item.getKey().toUpperCase() + " " + item.getValue());
		}
		System.out.println("---------------------------------------------------");
	}
	
	public Pair [] getUI() {
		Pair [] tableUI = new Pair[mem.size()];
		int i=0;
		for (Entry<String, Double> item : mem.entrySet()) {
			tableUI[i++]= new Pair(Integer.parseInt(item.getKey()), ""+item.getValue() );
		}
		return tableUI;
	}

}
