package skeletontracker;

import java.util.ArrayList;
import java.util.Collections;

public class HistogramBuffer {

	private int bufferSize;
	ArrayList<Integer> buffer;

	public HistogramBuffer(int i) {
		bufferSize = i;
		buffer = new ArrayList<Integer>();
	}

	public int put(int length) {
		buffer.add(length);
		if (buffer.size() > bufferSize)
			buffer.remove(0);

		
		return get();
	}

	public int get() {

		int maxFreq = 0;
		int maxVal = 0;
		for (Integer i : buffer) {
			int freq = Collections.frequency(buffer, i);
			if(freq > maxFreq){
				maxFreq = freq;
				maxVal = i;
			}
		}
		return maxVal;
	}

	public void clear() {
		buffer.clear();
	}

	public int size() {
		return buffer.size();
	}

}
