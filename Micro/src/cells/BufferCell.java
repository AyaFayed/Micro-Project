package cells;

public abstract class BufferCell extends Cell {
	private String address;

	public BufferCell(String tag, int latency) {
		super(tag, latency);
	}

	public void occupy(int index, int order, String address) {
		this.setIndex(index);
		this.occupy(order);
		this.setAddress(address);
	}
	
	public void occupy(int index, int order, String address, String register) {

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
