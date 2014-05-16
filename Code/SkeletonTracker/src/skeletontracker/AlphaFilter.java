package skeletontracker;

public class AlphaFilter {

	private float alpha;
	private float value;

	public AlphaFilter(float alpha, float initialValue) {
		this.alpha=alpha;
		this.value = initialValue;
	}

	public AlphaFilter(float f) {
		this(f, 0f);
	}

	public float get() {
		return value;
	}

	public float put(float newVal) {
		return value = value * (1 - alpha) + newVal * alpha;
	}

	public void set(float f) {
		value = f;
	}

}
