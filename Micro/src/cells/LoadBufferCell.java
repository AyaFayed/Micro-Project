package cells;

import components.CPU;
import components.Memory;

public class LoadBufferCell extends BufferCell {

	public LoadBufferCell(String tag, int latency) {
		super(tag, latency);
	}

	public void occupy(int order, String address) {
		this.occupy(order);
		this.setAddress(address);
		CPU.getInstance().addExecuting(this);
		this.execute();
	}

	public void execute() {
		CPU.getInstance().startExecutingInstruction(this.getOrder());
		this.setResult(Memory.getInstance().load(this.getAddress()));
	}

	public void display() {
		System.out.println(getTag().toUpperCase() + " " + getBusy() + " " + getAddress());
	}

}
