package helper;

public class Instruction {

	private Operation operation;
	private String operand1;
	private String operand2;
	private String destination;
	private String instruction;
	private int index;

	public Instruction(String instruction) {
		this.instruction = instruction;
		String[] inst = instruction.split(",");
		String [] opAndDestination = inst[0].split(" ");
		String op = opAndDestination[0].toLowerCase();
		switch (op) {
		case "add.d":
		case "add.s":
		case "add":
			this.operation = Operation.ADD;
			break;
		case "sub.d":
		case "sub.s":
		case "sub":
			this.operation = Operation.SUB;
			break;
		case "mul.d":
		case "mul.s":
		case "mul":
			this.operation = Operation.MUL;
			break;
		case "div.d":
		case "div.s":
		case "div":
			this.operation = Operation.DIV;
			break;
		case "l.d":
			this.operation = Operation.L;
			break;
		case "s.d":
			this.operation = Operation.S;
			break;
		default:
			this.operation = Operation.ADD;
			break;
		}
		this.destination = opAndDestination[1].toLowerCase().trim();
		this.operand1 = inst[1].toLowerCase().trim();
		if (inst.length == 3) {
			this.operand2 = inst[2].toLowerCase().trim();
		} else {
			this.operand2 = null;
		}
	}

	public Operation getOperation() {
		return operation;
	}

	public String getOperand1() {
		return operand1;
	}

	public String getOperand2() {
		return operand2;
	}

	public String getDestination() {
		return destination;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void display() {
		System.out.println(this.instruction);
	}
}
