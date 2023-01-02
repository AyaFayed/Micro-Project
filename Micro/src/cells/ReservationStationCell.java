package cells;

import components.CPU;
import components.Operation;
import components.Registers;

public class ReservationStationCell extends Cell {

	private Operation op;
	private Double vj;
	private Double vk;
	private String qj;
	private String qk;
	private int subOrDivLatency;

	public ReservationStationCell(String tag, int latency1, int latency2) {
		super(tag, latency1);
		this.subOrDivLatency = latency2;
	}

	public boolean isReady() {
		return vj != null && vk != null;
	}

	public void occupy(int index, int order, Operation op, String reg1, String reg2) {
		this.setIndex(index);
		this.occupy(order);
		this.vj=null;
		this.vk=null;
		this.qj=null;
		this.qk=null;
		this.op = op;
		if (op == Operation.DIV || op == Operation.SUB)
			setLatency(subOrDivLatency);
		if (Registers.getInstance().hasValidValue(reg1)) {
			this.vj = Double.parseDouble(Registers.getInstance().read(reg1));
		} else {
			this.qj = Registers.getInstance().read(reg1);
		}
		if (Registers.getInstance().hasValidValue(reg2)) {
			this.vk = Double.parseDouble(Registers.getInstance().read(reg2));
		} else {
			this.qk = Registers.getInstance().read(reg2);
		}
		System.out.println();
		if (isReady()) {
			CPU.getInstance().addExecuting(this);
			this.execute();
		}

	}

	public void execute() {
		CPU.getInstance().startExecutingInstruction(this.getIndex());
		switch (op) {
		case ADD:
			setResult(vj + vk);
			break;
		case SUB:
			setResult(vj - vk);
			break;
		case DIV:
			setResult(vj / vk);
			break;
		case MUL:
			setResult(vj * vk);
			break;
		default:
			break;
		}

	}

	public void checkValueOnBus(String reservationStationTag, double value) {
		if (qj != null && qj.equals(reservationStationTag)) {
			qj = null;
			vj = value;
			if (isReady()) {
				CPU.getInstance().addExecuting(this);
				this.execute();
			}
		}
		if (qk != null && qk.equals(reservationStationTag)) {
			qk = null;
			vk = value;
			if (isReady()) {
				CPU.getInstance().addExecuting(this);
				this.execute();
			}
		}

	}

	public void display() {
		System.out.println(
				getTag().toUpperCase() + " " + getBusy() + " " + op + " " + vj + " " + vk + " " + qj + " " + qk);
	}

}
