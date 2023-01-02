package components;

import java.util.*;

import buffers.LoadBuffer;
import buffers.StoreBuffer;
import cells.Cell;
import reservationStations.AddReservationStation;
import reservationStations.MulReservationStation;

public class CPU {
	private int addLatency;
	private int mulLatency;
	private int subLatency;
	private int divLatency;
	private int loadLatency = 2;
	private int storeLatency = 2;
	private int issueOrder;
	private int cycle = 0;
	private Queue<Instruction> instructions;
	private String[][] instructionsTable;
	private ArrayList<Cell> executing;
	private HashSet<Integer> willStartExecutionInTheNextCycle;
	private PriorityQueue<Cell> writeBack;
	private LoadBuffer loadBuffer;
	private StoreBuffer storeBuffer;
	private AddReservationStation addReservationStation;
	private MulReservationStation mulReservationStation;

	private static final CPU instance = new CPU();

	private CPU() {
		this.instructions = new LinkedList<>();
		this.executing = new ArrayList<>();
		this.willStartExecutionInTheNextCycle = new HashSet<>();
		this.writeBack = new PriorityQueue<>((cell1, cell2) -> cell1.getOrder() - cell2.getOrder());
		this.issueOrder = 0;
		this.loadBuffer = new LoadBuffer(loadLatency);
		this.storeBuffer = new StoreBuffer(storeLatency);
	}

	public static CPU getInstance() {
		return instance;
	}

	public void run() {
		while (!instructions.isEmpty() || !executing.isEmpty() || !writeBack.isEmpty()) {
			if (cycle == 0) {
				displayCycle0();
				initializeInstructionsTable();
				cycle++;
				continue;
			}
			Cell writeBackCell = writeBack.poll();
			execute();
			issue();
			if (writeBackCell != null) {
				writeBack(writeBackCell);
			}
			display();
			cycle++;
		}
	}

	public void initializeInstructionsTable() {
		int n = instructions.size();
		this.instructionsTable = new String[n][6];
		for (int i = 0; i < n; i++) {
			Instruction instruction = instructions.poll();
			instruction.setIndex(i);
			instructions.add(instruction);
			this.instructionsTable[i][0] = instruction.getInstruction();
			this.instructionsTable[i][1] = instruction.getOperand1().toUpperCase();
			this.instructionsTable[i][2] = instruction.getOperand2() == null ? ""
					: instruction.getOperand2().toUpperCase();
			this.instructionsTable[i][3] = "";
			this.instructionsTable[i][4] = "";
			this.instructionsTable[i][5] = "";

		}
	}

	public boolean canIssue() {
		if (!instructions.isEmpty()) {
			Instruction instruction = instructions.peek();
			switch (instruction.getOperation()) {
			case ADD:
			case SUB:
				return addReservationStation.hasAvailableReservationStations();
			case DIV:
			case MUL:
				return mulReservationStation.hasAvailableReservationStations();
			case L:
				return loadBuffer.hasAvailableBuffers() && !storeBuffer.hasAddress(instruction.getOperand1());
			case S:
				return storeBuffer.hasAvailableBuffers() && !storeBuffer.hasAddress(instruction.getOperand1())
						&& !loadBuffer.hasAddress(instruction.getOperand1());

			}
			return false;
		}
		return false;
	}

	public void issue() {
		if (canIssue()) {
			Instruction instruction = instructions.poll();
			instructionsTable[instruction.getIndex()][3] = "" + cycle;
			String tag;
			switch (instruction.getOperation()) {
			case ADD:
			case SUB:
				tag = addReservationStation.add(instruction.getIndex(), issueOrder, instruction.getOperation(),
						instruction.getOperand1(), instruction.getOperand2());
				Registers.getInstance().waitForTheResult(tag, instruction.getDestination());
				break;
			case DIV:
			case MUL:
				tag = mulReservationStation.add(instruction.getIndex(), issueOrder, instruction.getOperation(),
						instruction.getOperand1(), instruction.getOperand2());
				Registers.getInstance().waitForTheResult(tag, instruction.getDestination());
				break;
			case L:
				tag = loadBuffer.add(instruction.getIndex(), issueOrder, instruction.getOperand1());
				Registers.getInstance().waitForTheResult(tag, instruction.getDestination());
				break;
			case S:
				storeBuffer.add(instruction.getIndex(), issueOrder, instruction.getOperand1(),
						instruction.getDestination());
				break;

			}
			this.incIssueOrder();
		}
	}

	public void execute() {
		ArrayList<Cell> stillExecuting = new ArrayList<>();
		for (int i = 0; i < executing.size(); i++) {
			Cell cell = executing.get(i);
			if (willStartExecutionInTheNextCycle.contains(cell.getIndex())) {
				instructionsTable[cell.getIndex()][4] = "" + cycle + "..";
				willStartExecutionInTheNextCycle.remove(cell.getIndex());
			}
			cell.incExecutedCycles();
			if (cell.finishedExecution()) {
				instructionsTable[cell.getIndex()][4] += "" + cycle;
				if (cell.getTag().charAt(0) != 's')
					writeBack.add(cell);
			} else {
				stillExecuting.add(cell);
			}
		}

		executing = stillExecuting;
	}

	public void writeBack(Cell cell) {

		switch (cell.getTag().charAt(0)) {
		case 'l':
			loadBuffer.freeCell(cell.getTag());
			break;
		case 'a':
			addReservationStation.freeCell(cell.getTag());
			break;
		case 'm':
			mulReservationStation.freeCell(cell.getTag());
			break;
		}
		double result = cell.getResult();
		Registers.getInstance().checkValueOnBus(cell.getTag(), result);
		storeBuffer.checkValueOnBus(cell.getTag(), result);
		addReservationStation.checkValueOnBus(cell.getTag(), result);
		mulReservationStation.checkValueOnBus(cell.getTag(), result);
		instructionsTable[cell.getIndex()][5] = "" + cycle;

	}

	public void startExecutingInstruction(int index) {
		willStartExecutionInTheNextCycle.add(index);
	}

	public int getAddLatency() {
		return addLatency;
	}

	public void setAddLatency(int addLatency) {
		this.addLatency = addLatency;
	}

	public void setSubLatency(int subLatency) {
		this.subLatency = subLatency;
		addReservationStation = new AddReservationStation(addLatency, this.subLatency);
	}

	public void setDivLatency(int divLatency) {
		this.divLatency = divLatency;

		mulReservationStation = new MulReservationStation(mulLatency, this.divLatency);
	}

	public int getMulLatency() {
		return mulLatency;
	}

	public void setMulLatency(int mulLatency) {
		this.mulLatency = mulLatency;
	}

	public int getIssueOrder() {
		return issueOrder;
	}

	public void incIssueOrder() {
		this.issueOrder++;
	}

	public Queue<Instruction> getInstructions() {
		return instructions;
	}

	public void addInstruction(String instruction) {
		this.instructions.add(new Instruction(instruction));
	}

	public ArrayList<Cell> getExecuting() {
		return executing;
	}

	public void addExecuting(Cell executingCell) {
		this.executing.add(executingCell);
	}

	public PriorityQueue<Cell> getWriteBack() {
		return writeBack;
	}

	public void addWriteBack(Cell writingBackCell) {
		this.writeBack.add(writingBackCell);
	}

	public String[][] getInstructionsTable() {
		return instructionsTable;
	}

	public void displayInstructionQueue() {
		System.out.println("Instructions Queue :");
		for (Instruction inst : instructions) {
			inst.display();
		}
		System.out.println("---------------------------------------------------");
	}

	public void display() {
		System.out.println("Cycle " + cycle);
		System.out.println("---------------------------");
//		displayInstructionQueue();
		for (String[] arr : instructionsTable)
			System.out.println(Arrays.toString(arr));
		Registers.getInstance().display();
		addReservationStation.display();
		mulReservationStation.display();
		loadBuffer.display();
		storeBuffer.display();
		System.out.println("--------------------------------------------------------");
	}

	public void displayCycle0() {
		System.out.println("Cycle 0");
		System.out.println("---------------------------");
		System.out.println("Fetching...");
		System.out.println("--------------------------------------------------------");
	}

}
