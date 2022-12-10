package reservationStations;

public class AddReservationStation extends ReservationStation {

	private final static int addRSSize = 3;

	public AddReservationStation(int latency1, int latency2) {
		super(latency1, latency2, addRSSize, 'a');
	}

	public void display() {
		display("ADD");
	}

}
