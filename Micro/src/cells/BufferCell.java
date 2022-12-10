package cells;

public abstract class BufferCell extends Cell {
	private String address;

	public BufferCell(String tag, int latency) {
		super(tag, latency);
	}

	public void occupy(int order, String address) {
		this.occupy(order);
		this.setAddress(address);
	}

	public void occupy(int order, String address, String register) {

	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void checkValueOnBus(String reservationStationTag, double value) {

	}

}
