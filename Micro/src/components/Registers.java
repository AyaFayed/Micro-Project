package components;

import java.util.*;
import java.util.Map.Entry;

import helper.Pair;

public class Registers {
	private HashMap<String, String[]> registers;
	private HashMap<String, HashSet<String>> waitingForResult;

	private static final Registers instance = new Registers();

	private Registers() {
		this.registers = new HashMap<>();
		this.waitingForResult = new HashMap<>();
	}

	public static Registers getInstance() {
		return instance;
	}

	public boolean hasValidValue(String register) {
		register = register.toLowerCase();
		if (registers.containsKey(register)) {
			String[] entry = registers.get(register);
			if (entry[0].equals("0"))
				return true;
			else
				return false;
		} else {
			return true;
		}
	}

	public void addToWaitingForResult(String reservationStationTag, String register) {
		if (waitingForResult.containsKey(reservationStationTag)) {
			waitingForResult.get(reservationStationTag).add(register);
		} else {
			HashSet<String> tmp = new HashSet<>();
			tmp.add(register);
			waitingForResult.put(reservationStationTag, tmp);
		}
	}

	public void waitForTheResult(String reservationStationTag, String register) {
		register = register.toLowerCase();
		if (registers.containsKey(register)) {
			if (!hasValidValue(register)) {
				String oldResStationTag = registers.get(register)[0];
				waitingForResult.get(oldResStationTag).remove(register);
			}
			registers.get(register)[0] = reservationStationTag;
			addToWaitingForResult(reservationStationTag, register);
		} else {
			registers.put(register, new String[] { reservationStationTag, "" });
			addToWaitingForResult(reservationStationTag, register);
		}
	}

	public void write(String register, double value) {
		register = register.toLowerCase();
		if (registers.containsKey(register)) {
			String[] entry = registers.get(register);
			entry[0] = "0"; // fresh value
			entry[1] = "" + value;
		} else {
			registers.put(register, new String[] { "0", "" + value });
		}
	}

	public String read(String register) {
		register = register.toLowerCase();
		if (registers.containsKey(register)) {
			String[] entry = registers.get(register);
			if (hasValidValue(register)) {
				return entry[1];
			} else
				return entry[0];
		} else {
			return "0";
		}
	}

	public void checkValueOnBus(String reservationStationTag, double value) {
		if (waitingForResult.containsKey(reservationStationTag)) {
			HashSet<String> regs = waitingForResult.get(reservationStationTag);
			for (String register : regs) {
				this.write(register, value);
			}
			waitingForResult.put(reservationStationTag, new HashSet<>());
		}
	}

	public void display() {
		System.out.println("Registers :");
		for (Entry<String, String[]> reg : registers.entrySet()) {
			System.out.println(
					reg.getKey().toUpperCase() + " " + reg.getValue()[0].toUpperCase() + " " + reg.getValue()[1]);
		}
		System.out.println("---------------------------------------------------");
	}
	
	public Pair [] getUI() {
		Pair [] tableUI= new Pair[registers.size()];
		int i=0;
		for (Entry<String, String[]> reg : registers.entrySet()) {
			tableUI[i++]= new Pair(Integer.parseInt(reg.getKey().substring(1)),reg.getValue()[0].equals("0")?reg.getValue()[1]:reg.getValue()[0].toUpperCase());
		}
		return tableUI;
	}

}
