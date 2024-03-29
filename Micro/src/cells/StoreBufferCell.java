package cells;

import components.CPU;
import components.Registers;

public class StoreBufferCell extends BufferCell {
	private String q;
	private Double v;

	public StoreBufferCell(String tag, int latency) {
		super(tag, latency);
	}

	public void execute() {
		CPU.getInstance().startExecutingInstruction(this.getIndex());
	}

	public void occupy(int index, int order, String address, String register) {
		this.occupy(index, order, address);
		this.q = null;
		this.v = null;
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
	
	public double getV() {
		return this.v;
	}

	public void display() {
		System.out.println(getTag().toUpperCase() + " " + getBusy() + " " + getAddress() + " " + v + " " + q);
	}
	
	public String [] getUI() {
		return new String[] {""+ getBusy(),getAddress()==null?"":""+getAddress(), v==null?"":""+v , q==null?"":""+q.toUpperCase() };
	}

}