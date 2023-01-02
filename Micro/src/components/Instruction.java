package components;

public class Instruction {

	private Operation operation;
	private String operand1;
	private String operand2;
	private String destination;
	private String instruction;

	public Instruction(String instruction) {
		this.instruction = instruction;
		String[] inst = instruction.split(" ");
		String op = inst[0].toLowerCase();
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
		String[] operands = inst[1].split(",");
		this.destination = operands[0].toLowerCase().trim();
		this.operand1 = operands[1].toLowerCase().trim();
		if (operands.length == 3) {
			this.operand2 = operands[2].toLowerCase().trim();
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

	public void display() {
		System.out.println(this.instruction);
	}
}
