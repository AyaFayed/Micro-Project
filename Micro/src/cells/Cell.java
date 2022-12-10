package cells;

public abstract class Cell {
	private int latency;
	private String tag;
	private int busy;
	private int order;
	private int executedCycles;
	private Double result;

	public Cell(String tag, int latency) {
		this.tag = tag;
		this.busy = 0;
		this.latency = latency;
		this.executedCycles = 0;
	}

	public void incExecutedCycles() {
		this.executedCycles++;
	}

	public boolean finishedExecution() {
		return executedCycles == latency;
	}

	public void free() {
		this.busy = 0;
		this.executedCycles = 0;
	}

	public void setBusy() {
		this.busy = 1;
	}

	public boolean isAvailable() {
		return busy == 0;
	}

	public String getTag() {
		return tag;
	}

	public int getExecutedCycles() {
		return executedCycles;
	}

	public int getOrder() {
		return order;
	}

	public int getBusy() {
		return busy;
	}

	public void setLatency(int latency) {
		this.latency = latency;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}

	public void occupy(int order) {
		this.setOrder(order);
		this.setBusy();
	}

	public abstract void execute();

	public abstract void display();
}
