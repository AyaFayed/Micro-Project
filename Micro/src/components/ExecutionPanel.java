package components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import components.CPU;

public class ExecutionPanel extends JPanel implements ActionListener  {
	
	JPanel mainPanel;
	TextArea code;
	JButton runAll;
	JButton nextCycle;
	boolean codeRead;
	JButton reset;
	public TextArea log;
	public TextArea[][] registers;
	public TextArea[][] addReservationStations;
	public TextArea[][] mulReservationStations;
	public TextArea[][] loadBuffers;
	public TextArea[][] storeBuffers;
	public TextArea[][]  instructionsTable;
	
	public ExecutionPanel() {
		codeRead = false;
		code = new TextArea();
		code.setFont(new Font("Calisto MT", 0, 22));
		nextCycle = new JButton("Next Cycle");
		runAll = new JButton("Run ALL");
		reset = new JButton("Reset");
		nextCycle.addActionListener(this);
		runAll.addActionListener(this);
		reset.addActionListener(this);
		//open the lecture for reference
		registers = new TextArea[33][2];
		
		registers[0][0] = new TextArea("Register", 1, 20, TextArea.SCROLLBARS_NONE);
		registers[0][0].setEditable(false);
		registers[0][1] = new TextArea("Value", 1, 20, TextArea.SCROLLBARS_NONE);
		registers[0][1].setEditable(false);
		
		for(int i=1;i<33;i++) {
			registers[i][0] = new TextArea("F"+(i-1), 1, 20, TextArea.SCROLLBARS_NONE);
			registers[i][0].setEditable(false);
			registers[i][1] = new TextArea("", 1, 20, TextArea.SCROLLBARS_NONE);
			registers[i][1].setEditable(false);
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
		
		for(int i=1;i<3;i++) {
			mulReservationStations[i][0] = new TextArea("", 1, 20, TextArea.SCROLLBARS_NONE);
			mulReservationStations[i][0].setText("M"+i);
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
		
		for(int i=1;i<4;i++) {
			addReservationStations[i][0] = new TextArea("A"+i, 1, 20, TextArea.SCROLLBARS_NONE);
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
			
			loadBuffers[i][0] = new TextArea("L"+i, 1, 20, TextArea.SCROLLBARS_NONE);
			loadBuffers[i][0].setEditable(false);
			loadBuffers[i][1] = new TextArea("0", 1, 20, TextArea.SCROLLBARS_NONE);
			loadBuffers[i][1].setEditable(false);
			loadBuffers[i][2] = new TextArea("", 1, 20, TextArea.SCROLLBARS_NONE);
			loadBuffers[i][2].setEditable(false);
			
			storeBuffers[i][0] = new TextArea("S"+i, 1, 20, TextArea.SCROLLBARS_NONE);
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
		
		instructionsTable = new TextArea[code.getRows()][6];
		instructionsTable[0][0] = new TextArea("Instruction", 1, 20, TextArea.SCROLLBARS_NONE);
		instructionsTable[0][0].setEditable(false);
		instructionsTable[0][1] = new TextArea("j", 1, 20, TextArea.SCROLLBARS_NONE);
		instructionsTable[0][1].setEditable(false);
		instructionsTable[0][2] = new TextArea("k", 1, 20, TextArea.SCROLLBARS_NONE);
		instructionsTable[0][2].setEditable(false);
		instructionsTable[0][3] = new TextArea("Issue", 1, 20, TextArea.SCROLLBARS_NONE);
		instructionsTable[0][3].setEditable(false);
		instructionsTable[0][4] = new TextArea("Execution", 1, 20, TextArea.SCROLLBARS_NONE);
		instructionsTable[0][4].setEditable(false);
		instructionsTable[0][5] = new TextArea("Write Result", 1, 20, TextArea.SCROLLBARS_NONE);
		instructionsTable[0][5].setEditable(false);
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
