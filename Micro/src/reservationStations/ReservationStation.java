package reservationStations;

import cells.ReservationStationCell;
import components.Operation;

public class ReservationStation {
	private ReservationStationCell[] reservationStation;
	private int availableReservationStations;
	private int size;

	public ReservationStation(int latency1, int latency2, int size, char c) {
		reservationStation = new ReservationStationCell[size];
		availableReservationStations = size;
		this.size = size;
		for (int i = 0; i < size; i++) {
			reservationStation[i] = new ReservationStationCell(c + "" + i, latency1, latency2);
		}
	}

	public boolean hasAvailableReservationStations() {
		return availableReservationStations > 0;
	}

	public void freeCell(String tag) {
		for (ReservationStationCell cell : reservationStation) {
			if (tag.equals(cell.getTag())) {
				availableReservationStations++;
				cell.free();
			}
		}
	}

	public String add(int index, int order, Operation op, String reg1, String reg2) {
		for (ReservationStationCell cell : reservationStation) {
			if (cell.isAvailable()) {
				cell.occupy(index, order, op, reg1, reg2);
				availableReservationStations--;
				return cell.getTag();
			}
		}
		return "";
	}

	public void checkValueOnBus(String reservationStationTag, double value) {
		for (ReservationStationCell cell : reservationStation)
			cell.checkValueOnBus(reservationStationTag, value);
	}

	public boolean isEmpty() {
		return availableReservationStations == size;
	}

	public void display(String type) {
		System.out.println(type + " Reservation Stations :");
		for (ReservationStationCell cell : reservationStation) {
			cell.display();
		}
		System.out.println("---------------------------------------------------");
	}

}
