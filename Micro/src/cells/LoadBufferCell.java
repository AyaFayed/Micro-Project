package cells;

import components.CPU;
import components.Memory;

public class LoadBufferCell extends BufferCell {

	public LoadBufferCell(String tag, int latency) {
		super(tag, latency);
	}

	public void occupy(int index, int order, String address) {
		this.setIndex(index);
		this.occupy(order);
		this.setAddress(address);
		CPU.getInstance().addExecuting(this);
		this.execute();
	}

	public void execute() {
		CPU.getInstance().startExecutingInstruction(this.getIndex());
		this.setResult(Memory.getInstance().load(this.getAddress()));
	}

	public void display() {
		System.out.println(getTag().toUpperCase() + " " + getBusy() + " " + getAddress());
	}
	
	public String [] getUI() {
		return new String[] {""+ getBusy(),getAddress()==null?"":""+getAddress()};
	}

}
