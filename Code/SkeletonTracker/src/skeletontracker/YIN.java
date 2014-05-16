package skeletontracker;

import java.util.ArrayList;

public class YIN {

	private int dimensions = 3;
	private int length = 60;
	private ArrayList<ArrayList<Float>> buffers;
	private int buffersize;
	private int maxdelay = 200;
	private float[][] dimvalues;
	private float[] values;
	private int minDips = 2;
	private float avg;
	private float[] avgs;
	private float dipThreshold = .1f;
	private float avgThreshold = .04f;
	private boolean sync = false;
	private int dip = -1;

	public YIN() {
		this(1);
	}

	public YIN(int dimensions) {
		this.dimensions = dimensions;
		buffersize = maxdelay + length + 1;

		buffers = new ArrayList<>();
		for (int i = 0; i < dimensions; i++){
			buffers.add(new ArrayList<Float>());
			for(int j = 0; j < buffersize; j++)
				buffers.get(i).add(0f);
		}

		dimvalues = new float[dimensions][maxdelay];
		values = new float[maxdelay];
		avgs = new float[maxdelay];
	}

	float r(int t, int delta, ArrayList<Float> buffer) {
		float sum = 0.0f;
		int i;
		for (i = 0; i < length; i++)
			sum += buffer.get(i + t) * buffer.get(i + t + delta);
		return sum / length;
	}

	float average(float[] array, int begin, int end) {
		float sum = 0f;
		for (int i = begin; i <= end; i++) {
			sum += array[i];
		}
		return sum / (float) (end - begin + 1);
	}

	void cmndf(float[] array, int length) {
		float cmndf[] = new float[length];
		cmndf[0] = 1.f;
		for (int i = 1; i < length; i++)
			cmndf[i] = array[i] / average(cmndf, 0, i - 1);
		for (int i = 0; i < length; i++)
			array[i] = cmndf[i];
	}

	void process(ArrayList<Float> v) {
		if (v.size() != dimensions)
			return;

		// shift and add to buffer
		for (int i = 0; i < dimensions; i++) {
			buffers.get(i).add(0, v.get(i));
			if (buffers.get(i).size() > buffersize)
				buffers.get(i).remove(buffers.get(i).size() - 1);
		}

		// compute values
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < maxdelay; j++) {
				dimvalues[i][j] = r(0, 0, buffers.get(i))
						+ r(j, 0, buffers.get(i)) - 2 * r(0, j, buffers.get(i));
			}
		}

		// cmndf
		for (int i = 0; i < dimensions; i++)
			cmndf(dimvalues[i], maxdelay);

		values[0] = 0.0f;
		for (int j = 1; j < maxdelay; j++) {
			float sum = 0;
			for (int i = 0; i < dimensions; i++)
				sum += (float) Math.pow(dimvalues[i][j], 2);
			values[j] = (float) Math.sqrt(sum);
		}

		int minimumIndex = -1;
		float minimumValue = 1f;

		int[] dips = new int[minDips];
		for (int i = 0; i < minDips; i++)
			dips[i] = -1;
		int detectedDips = 0;

		for (int i = 0; i < maxdelay && detectedDips < minDips; i++) {
			avg = average(values, 0, Math.min(maxdelay - 1, (i + 1) * 3));
			avgs[i] = avg;

			float val = values[i];
			if (dips[detectedDips] < 0.) {
				if (val > avg) {
					if (minimumValue < dipThreshold) {
						dips[detectedDips] = minimumIndex;
					}
				} else if (val < dipThreshold || i == 0) {
					if (val < minimumValue) {
						minimumValue = val;
						minimumIndex = i;
					}
				}
			} else {
				if (val > avg) {
					detectedDips++;
					minimumValue = Float.MAX_VALUE;
				}
			}
		}

		sync = detectedDips >= minDips
				&& average(values, 0, maxdelay - 1) > avgThreshold;
		if (sync)
			dip = dips[1];
		else
			dip = 0;

	}

	float[] getYIN() {
		return values;
	}

	int getLength() {
		return dip;
	}

	int getDimensions() {
		return dimensions;
	}

	boolean isSync() {
		return sync;
	}

	public void setLength(int length) {
		this.length = length;
		buffersize = maxdelay + length + 1;
	}

	public void setMaxdelay(int maxdelay) {
		this.maxdelay = maxdelay;
		buffersize = maxdelay + length + 1;
	}

}
