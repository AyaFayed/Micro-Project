package components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import components.CPU;
import javafx.scene.layout.Border;
import sun.net.www.content.image.jpeg;

public class ExecutionPanel extends JPanel implements ActionListener {

	JPanel mainPanel;
	TextArea code;
	JButton run;
	public TextArea log;
	public TextArea[][] registers;
	public TextArea[][] addReservationStations;
	public TextArea[][] mulReservationStations;
	public TextArea[][] loadBuffers;
	public TextArea[][] storeBuffers;
	public TextArea[][] instructionsQueue;
	public TextArea[][] memory;
	/*
	 * 
	 * leftColumn: code Buffers: L.D S.D
	 * 
	 * middleCol: registers memory
	 * 
	 * rightColumn: ReservationStations: add mul instructionQueue
	 * 
	 * 
	 * 
	 * 
	 */

	private JPanel leftColumn;
	private JPanel middleColumn;
	private JPanel rightColumn;

	public ExecutionPanel() {
		code = new TextArea();
		code.setFont(new Font("Calisto MT", 0, 22));

		instructionsQueue = new TextArea[1][6];
		initializeinstructionsQueue();

		run = new JButton("Run");
		run.addActionListener(this);
		// open the lecture for reference
		registers = new TextArea[35][2];

		for (int i = 0; i < 35; i++) {
			if (i < 3) {
				registers[i][0] = new TextArea("Register", 1, 20, TextArea.SCROLLBARS_NONE);
				registers[i][0].setEditable(false);
				registers[i][1] = new TextArea("Value", 1, 20, TextArea.SCROLLBARS_NONE);
				registers[i][1].setEditable(false);
			} else {
				registers[i][0] = new TextArea("F" + (i - 3), 1, 20, TextArea.SCROLLBARS_NONE);
				registers[i][0].setEditable(false);
				registers[i][1] = new TextArea("", 1, 20, TextArea.SCROLLBARS_NONE);
				registers[i][1].setEditable(false);
			}
		}

		mulReservationStations = new TextArea[3][7];
		mulReservationStations[0][0] = new TextArea("Tag", 1, 20, TextArea.SCROLLBARS_NONE);
		mulReservationStations[0][0].setEditable(false);
		mulReservationStations[0][1] = new TextArea("Busy", 1, 20, TextArea.SCROLLBARS_NONE);
		mulReservationStations[0][1].setEditable(false);
		mulReservationStations[0][2] = new TextArea("OP", 1, 20, TextArea.SCROLLBARS_NONE);
		mulReservationStations[0][2].setEditable(false);
		mulReservationStations[0][3] = new TextArea("Vj", 1, 20, TextArea.SCROLLBARS_NONE);
		mulReservationStations[0][3].setEditable(false);
		mulReservationStations[0][4] = new TextArea("Vk", 1, 20, TextArea.SCROLLBARS_NONE);
		mulReservationStations[0][4].setEditable(false);
		mulReservationStations[0][5] = new TextArea("Qj", 1, 20, TextArea.SCROLLBARS_NONE);
		mulReservationStations[0][5].setEditable(false);
		mulReservationStations[0][6] = new TextArea("Qk", 1, 20, TextArea.SCROLLBARS_NONE);
		mulReservationStations[0][6].setEditable(false);

		for (int i = 1; i < 3; i++) {
			mulReservationStations[i][0] = new TextArea("", 1, 20, TextArea.SCROLLBARS_NONE);
			mulReservationStations[i][0].setText("M" + i);
			mulReservationStations[i][0].setEditable(false);
			mulReservationStations[i][1] = new TextArea("0", 1, 20, TextArea.SCROLLBARS_NONE);
			mulReservationStations[i][1].setEditable(false);
			mulReservationStations[i][2] = new TextArea("", 1, 20, TextArea.SCROLLBARS_NONE);
			mulReservationStations[i][2].setEditable(false);
			mulReservationStations[i][3] = new TextArea("", 1, 20, TextArea.SCROLLBARS_NONE);
			mulReservationStations[i][3].setEditable(false);
			mulReservationStations[i][4] = new TextArea("", 1, 20, TextArea.SCROLLBARS_NONE);
			mulReservationStations[i][4].setEditable(false);
			mulReservationStations[i][5] = new TextArea("", 1, 20, TextArea.SCROLLBARS_NONE);
			mulReservationStations[i][5].setEditable(false);
			mulReservationStations[i][6] = new TextArea("", 1, 20, TextArea.SCROLLBARS_NONE);
			mulReservationStations[i][6].setEditable(false);

		}

		addReservationStations = new TextArea[4][7];
		addReservationStations[0][0] = new TextArea("Tag", 1, 20, TextArea.SCROLLBARS_NONE);
		addReservationStations[0][0].setEditable(false);
		addReservationStations[0][1] = new TextArea("Busy", 1, 20, TextArea.SCROLLBARS_NONE);
		addReservationStations[0][1].setEditable(false);
		addReservationStations[0][2] = new TextArea("OP", 1, 20, TextArea.SCROLLBARS_NONE);
		addReservationStations[0][2].setEditable(false);
		addReservationStations[0][3] = new TextArea("Vj", 1, 20, TextArea.SCROLLBARS_NONE);
		addReservationStations[0][3].setEditable(false);
		addReservationStations[0][4] = new TextArea("Vk", 1, 20, TextArea.SCROLLBARS_NONE);
		addReservationStations[0][4].setEditable(false);
		addReservationStations[0][5] = new TextArea("Qj", 1, 20, TextArea.SCROLLBARS_NONE);
		addReservationStations[0][5].setEditable(false);
		addReservationStations[0][6] = new TextArea("Qk", 1, 20, TextArea.SCROLLBARS_NONE);
		addReservationStations[0][6].setEditable(false);

		loadBuffers = new TextArea[4][3];
		loadBuffers[0][0] = new TextArea("Tag", 1, 20, TextArea.SCROLLBARS_NONE);
		loadBuffers[0][0].setEditable(false);
		loadBuffers[0][1] = new TextArea("Busy", 1, 20, TextArea.SCROLLBARS_NONE);
		loadBuffers[0][1].setEditable(false);
		loadBuffers[0][2] = new TextArea("Address", 1, 20, TextArea.SCROLLBARS_NONE);
		loadBuffers[0][2].setEditable(false);

		storeBuffers = new TextArea[4][5];
		storeBuffers[0][0] = new TextArea("Tag", 1, 20, TextArea.SCROLLBARS_NONE);
		storeBuffers[0][0].setEditable(false);
		storeBuffers[0][1] = new TextArea("Busy", 1, 20, TextArea.SCROLLBARS_NONE);
		storeBuffers[0][1].setEditable(false);
		storeBuffers[0][2] = new TextArea("Address", 1, 20, TextArea.SCROLLBARS_NONE);
		storeBuffers[0][2].setEditable(false);
		storeBuffers[0][3] = new TextArea("V", 1, 20, TextArea.SCROLLBARS_NONE);
		storeBuffers[0][3].setEditable(false);
		storeBuffers[0][4] = new TextArea("Q", 1, 20, TextArea.SCROLLBARS_NONE);
		storeBuffers[0][4].setEditable(false);

		for (int i = 1; i < 4; i++) {
			addReservationStations[i][0] = new TextArea("A" + i, 1, 20, TextArea.SCROLLBARS_NONE);
			addReservationStations[i][0].setEditable(false);
			addReservationStations[i][1] = new TextArea("0", 1, 20, TextArea.SCROLLBARS_NONE);
			addReservationStations[i][1].setEditable(false);
			addReservationStations[i][2] = new TextArea("", 1, 20, TextArea.SCROLLBARS_NONE);
			addReservationStations[i][2].setEditable(false);
			addReservationStations[i][3] = new TextArea("", 1, 20, TextArea.SCROLLBARS_NONE);
			addReservationStations[i][3].setEditable(false);
			addReservationStations[i][4] = new TextArea("", 1, 20, TextArea.SCROLLBARS_NONE);
			addReservationStations[i][4].setEditable(false);
			addReservationStations[i][5] = new TextArea("", 1, 20, TextArea.SCROLLBARS_NONE);
			addReservationStations[i][5].setEditable(false);
			addReservationStations[i][6] = new TextArea("", 1, 20, TextArea.SCROLLBARS_NONE);
			addReservationStations[i][6].setEditable(false);

			loadBuffers[i][0] = new TextArea("L" + i, 1, 20, TextArea.SCROLLBARS_NONE);
			loadBuffers[i][0].setEditable(false);
			loadBuffers[i][1] = new TextArea("0", 1, 20, TextArea.SCROLLBARS_NONE);
			loadBuffers[i][1].setEditable(false);
			loadBuffers[i][2] = new TextArea("", 1, 20, TextArea.SCROLLBARS_NONE);
			loadBuffers[i][2].setEditable(false);

			storeBuffers[i][0] = new TextArea("S" + i, 1, 20, TextArea.SCROLLBARS_NONE);
			storeBuffers[i][0].setEditable(false);
			storeBuffers[i][1] = new TextArea("0", 1, 20, TextArea.SCROLLBARS_NONE);
			storeBuffers[i][1].setEditable(false);
			storeBuffers[i][2] = new TextArea("", 1, 20, TextArea.SCROLLBARS_NONE);
			storeBuffers[i][2].setEditable(false);
			storeBuffers[i][3] = new TextArea("", 1, 20, TextArea.SCROLLBARS_NONE);
			storeBuffers[i][3].setEditable(false);
			storeBuffers[i][4] = new TextArea("", 1, 20, TextArea.SCROLLBARS_NONE);
			storeBuffers[i][4].setEditable(false);
		}
		initializeMemory();
		paint();
	}

	void paint() {
		// main panel layout
		this.setLayout(new GridLayout(1, 3));

		// setup left column
		leftColumn = new JPanel();
		leftColumn.setLayout(new GridLayout(2, 1));

		// code
		leftColumn.add(code);

		// buffers
		JPanel buffers = new JPanel(new GridLayout(2, 1));

		// Load Buffers
		JPanel loadBuffersContainer = new JPanel(new GridLayout(loadBuffers.length, loadBuffers[0].length));
		for (int i = 0; i < loadBuffers.length; i++) {
			for (int j = 0; j < loadBuffers[0].length; j++) {
				loadBuffersContainer.add(loadBuffers[i][j]);
			}
		}
		JPanel loadBuffersLabled = new JPanel(new BorderLayout());
		loadBuffersLabled.add(new JLabel("Load Buffers"), BorderLayout.NORTH);
		loadBuffersLabled.add(loadBuffersContainer, BorderLayout.CENTER);
		buffers.add(loadBuffersLabled);

		// Store Buffers
		JPanel storeBuffersContainer = new JPanel(new GridLayout(storeBuffers.length, storeBuffers[0].length));
		for (int i = 0; i < storeBuffers.length; i++) {
			for (int j = 0; j < storeBuffers[0].length; j++) {
				storeBuffersContainer.add(storeBuffers[i][j]);
			}
		}

		JPanel storeBuffersLabled = new JPanel(new BorderLayout());
		storeBuffersLabled.add(new JLabel("store Buffers"), BorderLayout.NORTH);
		storeBuffersLabled.add(storeBuffersContainer, BorderLayout.CENTER);
		buffers.add(storeBuffersLabled);

		leftColumn.add(buffers);

		this.add(leftColumn);

		// middle layer
		middleColumn = new JPanel(new GridLayout(2, 1));

		// registers
		JPanel registersContainer = new JPanel(new BorderLayout());

		JLabel registersLable = new JLabel("Register File");
		registersContainer.add(registersLable, BorderLayout.NORTH);

		JPanel registersTable = new JPanel(new GridLayout(12, 6));
		for (int i = 0; i < registers.length; i++) {
			for (int j = 0; j < registers[i].length; j++) {
				registersTable.add(registers[i][j]);
			}
		}
		registersContainer.add(registersTable, BorderLayout.CENTER);
		middleColumn.add(registersContainer);

		// memory
		JPanel memoryContainer = new JPanel(new BorderLayout());

		JLabel memoryLable = new JLabel("Memory");
		memoryContainer.add(memoryLable, BorderLayout.NORTH);

		JPanel memoryTable = new JPanel(new GridLayout(12, 6));
		for (int i = 0; i < memory.length; i++) {
			for (int j = 0; j < memory[i].length; j++) {
				memoryTable.add(memory[i][j]);
			}
		}
		memoryContainer.add(memoryTable, BorderLayout.CENTER);
		middleColumn.add(memoryContainer);

		this.add(middleColumn);

		// right column
		rightColumn = new JPanel(new GridLayout(2, 1));

		// instruction queue

		JPanel instructionsQueueContainer = new JPanel(new BorderLayout());

		instructionsQueueContainer.add(new Label("Instruction Queue"), BorderLayout.NORTH);
		JPanel instructionsQueueTable = new JPanel(
				new GridLayout(instructionsQueue.length, instructionsQueue[0].length));
		instructionsQueueTable.setPreferredSize(new Dimension(100, 100));
		for (int i = 0; i < instructionsQueue.length; i++) {
			for (int j = 0; j < instructionsQueue[0].length; j++) {
				instructionsQueueTable.add(instructionsQueue[i][j]);
			}
		}
		JScrollPane instructionsQueueScroll = new JScrollPane(instructionsQueueTable);
		instructionsQueueScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		instructionsQueueScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		instructionsQueueContainer.add(instructionsQueueScroll, BorderLayout.CENTER);

		rightColumn.add(instructionsQueueContainer);

		// reservation station
		JPanel reservationStations = new JPanel(new GridLayout(2, 1));

		// add resrvation station
		JPanel addReservationStationsTable = new JPanel(
				new GridLayout(addReservationStations.length, addReservationStations[0].length));
		for (int i = 0; i < addReservationStations.length; i++) {
			for (int j = 0; j < addReservationStations[0].length; j++) {
				addReservationStationsTable.add(addReservationStations[i][j]);
			}
		}

		JPanel addReservationStationsContainer = new JPanel(new BorderLayout());
		addReservationStationsContainer.add(new JLabel("Add Reservation Stations"), BorderLayout.NORTH);
		addReservationStationsContainer.add(addReservationStationsTable, BorderLayout.CENTER);
		reservationStations.add(addReservationStationsContainer);

		JPanel mulReservationStationsTable = new JPanel(
				new GridLayout(mulReservationStations.length, mulReservationStations[0].length));
		for (int i = 0; i < mulReservationStations.length; i++) {
			for (int j = 0; j < mulReservationStations[0].length; j++) {
				mulReservationStationsTable.add(mulReservationStations[i][j]);
			}
		}
		JPanel mulReservationStationsContainer = new JPanel(new BorderLayout());
		mulReservationStationsContainer.add(new JLabel("mul Reservation Stations"), BorderLayout.NORTH);
		mulReservationStationsContainer.add(mulReservationStationsTable, BorderLayout.CENTER);
		reservationStations.add(mulReservationStationsContainer);

		rightColumn.add(reservationStations);

		this.add(rightColumn);
	}

	void initializeMemory() {
		memory = new TextArea[35][2];
		for (int i = 0; i < memory.length; i++) {
			if (i < 3) {
				memory[i][0] = new TextArea("Address", 1, 20, TextArea.SCROLLBARS_NONE);
				memory[i][0].setEditable(false);
				memory[i][1] = new TextArea("Value", 1, 20, TextArea.SCROLLBARS_NONE);
				memory[i][1].setEditable(false);
			} else {
				memory[i][0] = new TextArea("", 1, 20, TextArea.SCROLLBARS_NONE);
				memory[i][0].setEditable(false);
				memory[i][1] = new TextArea("", 1, 20, TextArea.SCROLLBARS_NONE);
				memory[i][1].setEditable(false);
			}
		}
	}

	void initializeinstructionsQueue() {
		instructionsQueue[0][0] = new TextArea("Instruction", 1, 20, TextArea.SCROLLBARS_NONE);
		instructionsQueue[0][0].setEditable(false);
		instructionsQueue[0][1] = new TextArea("j", 1, 20, TextArea.SCROLLBARS_NONE);
		instructionsQueue[0][1].setEditable(false);
		instructionsQueue[0][2] = new TextArea("k", 1, 20, TextArea.SCROLLBARS_NONE);
		instructionsQueue[0][2].setEditable(false);
		instructionsQueue[0][3] = new TextArea("Issue", 1, 20, TextArea.SCROLLBARS_NONE);
		instructionsQueue[0][3].setEditable(false);
		instructionsQueue[0][4] = new TextArea("Execution", 1, 20, TextArea.SCROLLBARS_NONE);
		instructionsQueue[0][4].setEditable(false);
		instructionsQueue[0][5] = new TextArea("Write Result", 1, 20, TextArea.SCROLLBARS_NONE);
		instructionsQueue[0][5].setEditable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == run) {
			code.setEditable(false);
			instructionsQueue = new TextArea[code.getRows()][6];
			initializeinstructionsQueue();
			try {
//				For each instruction (line of code)
//				CPU.getInstance().addInstruction(line of code);
				CPU.getInstance().run();
				run.setEnabled(false);
			} catch (Exception ex) {
				// System.out.println(ex.getMessage());
			}
		}
	}

}
