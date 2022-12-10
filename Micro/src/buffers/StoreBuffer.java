package buffers;

import cells.BufferCell;
import cells.StoreBufferCell;

public class StoreBuffer extends Buffer {
	private final static int storeBufferSize = 3;

	public StoreBuffer(int latency) {
		super(storeBufferSize);
		setBuffer(new StoreBufferCell[storeBufferSize]);
		BufferCell[] b = getBuffer();
		for (int i = 0; i < storeBufferSize; i++) {
			b[i] = new StoreBufferCell("s" + i, latency);
		}
	}

	public void add(int order, String address, String register) {
		BufferCell[] b = getBuffer();
		for (BufferCell cell : b) {
			if (cell.isAvailable()) {
				cell.occupy(order, address, register);
				decAvailableBuffers();
				break;
			}
		}
	}

	public void checkValueOnBus(String reservationStationTag, double value) {
		BufferCell[] b = getBuffer();
		for (BufferCell cell : b)
			cell.checkValueOnBus(reservationStationTag, value);
	}

	public void display() {
		display("Store");
	}
}
