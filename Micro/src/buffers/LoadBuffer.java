package buffers;

import cells.BufferCell;
import cells.LoadBufferCell;

public class LoadBuffer extends Buffer {
	private final static int loadBufferSize = 3;

	public LoadBuffer(int latency) {
		super(loadBufferSize);
		setBuffer(new LoadBufferCell[loadBufferSize]);
		BufferCell[] b = getBuffer();
		for (int i = 0; i < loadBufferSize; i++) {
			b[i] = new LoadBufferCell("l" + i, latency);
		}
	}

	public void display() {
		display("Load");
	}

}
