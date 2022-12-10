package cells;

import components.CPU;
import components.Memory;
import components.Registers;

public class StoreBufferCell extends BufferCell {
	private String q;
	private Double v;

	public StoreBufferCell(String tag, int latency) {
		super(tag, latency);
	}

	public void execute() {
		Memory.getInstance().store(this.getAddress(), v);
	}

	public void occupy(int order, String address, String register) {
		this.occupy(order, address);
		if (Registers.getInstance().hasValidValue(register)) {
			this.v = Double.parseDouble(Registers.getInstance().read(register));
			CPU.getInstance().addExecuting(this);
			this.execute();
		} else {
			this.q = Registers.getInstance().read(register);
		}

	}

	public void checkValueOnBus(String reservationStationTag, double value) {
		if (q != null && q.equals(reservationStationTag)) {
			q = null;
			v = value;
			CPU.getInstance().addExecuting(this);
			this.execute();
		}
	}

	public void display() {
		System.out.println(getTag().toUpperCase() + " " + getBusy() + " " + getAddress() + " " + v + " " + q);
	}

}