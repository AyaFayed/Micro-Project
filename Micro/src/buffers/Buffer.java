package buffers;

import cells.BufferCell;

public class Buffer {
	private int availableBuffers;
	private BufferCell[] buffer;
	private int size;

	public Buffer(int size) {
		this.size = size;
		availableBuffers = size;
	}

	public boolean hasAvailableBuffers() {
		return availableBuffers > 0;
	}

	public void freeCell(String tag) {
		for (BufferCell cell : buffer) {
			if (tag.equals(cell.getTag())) {
				availableBuffers++;
				cell.free();
			}
		}
	}

	public void decAvailableBuffers() {
		availableBuffers--;
	}

	public String add(int index, int order, String address) {
		for (BufferCell cell : buffer) {
			if (cell.isAvailable()) {
				cell.occupy(index, order, address);
				availableBuffers--;
				return cell.getTag();
			}
		}
		return "";
	}

	public boolean hasAddress(String address) {
		for (BufferCell cell : buffer) {
			if (address.equals(cell.getAddress())) {
				return true;
			}
		}
		return false;
	}

	public void display(String type) {
		System.out.println(type + " Buffers :");
		for (BufferCell cell : buffer) {
			cell.display();
		}
		System.out.println("---------------------------------------------------");
	}
	
	public String [][] getUI(){
		String [][] tableUI = new String[size][6];
		for (int i=0; i<buffer.length;i++) {
			tableUI[i]= buffer[i].getUI();
		}
		return tableUI;
	}

	public BufferCell[] getBuffer() {
		return buffer;
	}

	public boolean isEmpty() {
		return availableBuffers == size;
	}

	public void setBuffer(BufferCell[] buffer) {
		this.buffer = buffer;
	}

}
