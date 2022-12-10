package reservationStations;

public class MulReservationStation extends ReservationStation {

	private final static int mulRSSize = 2;

	public MulReservationStation(int latency1, int latency2) {
		super(latency1, latency2, mulRSSize, 'm');
	}

	public void display() {
		display("MUL");
	}

}
