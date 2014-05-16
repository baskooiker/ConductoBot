package skeletontracker;

import java.awt.Container;
import java.util.ArrayList;

import processing.core.PApplet;

public class SkeletonTracker extends PApplet {

	private YIN yin;
	private int counter = 0;
	private AlphaFilter interval;
	private int currentInterval = 40;
	private HistogramBuffer hb;
	private boolean wasSync = false;
	private ArrayList<ArrayList<Float>> buffer;
	private AlphaFilter size;
	private float sizeDecay = .9f;

	public void setup() {
		size(640, 480);
		frameRate(30);
		yin = new YIN(2);
		interval = new AlphaFilter(1f, 40f);
		hb = new HistogramBuffer(50);
		buffer = new ArrayList<ArrayList<Float>>();
		size = new AlphaFilter(.005f);
	}

	public void draw() {
		fill(200, 50);
		stroke(0,0);
		rect(0, 0, width, height);

		text(frameRate, 30, 30);
		ArrayList<Float> v = new ArrayList<Float>();
		v.add((float) mouseX / width);
		v.add((float) mouseY / height);
		yin.process(v);

		buffer.add(v);
		if(buffer.size() > 100)
			buffer.remove(0);
		
		stroke(255);
		translate(width / 2, height / 2);
		textAlign(CENTER);

		if (yin.isSync()) {
			if (!wasSync) {
				wasSync = true;
			}
			
			// modify size to intensity 
			size.put(boundingBox(buffer));

			// clamp between 60 and 240 bpm;
			int val = yin.getLength();
			while (val > frameRate * 2)
				val /= 2;
			while (val < frameRate * .5)
				val *= 2;

			// put value in HistogramBuffer to filter out YIN mistakes
			val = hb.put(val);

			// If histogramBuffer is at least half full, change detected tempo
			if (hb.size() > 25) {
				interval.put((float) val);
				 println(hb.get());
			}
		} else if (wasSync) { // if no repeated movement was detected, clear
								// HistogramBuffer
			wasSync = false;
			hb.clear();
		}

		text(120f / (interval.get() / frameRate), 0, 0);

		// show/send beat and set new interval
		if (++counter % currentInterval == 0) {
			fill(255);
			counter = 0;
			ellipse(0, 0, size.get()*height, size.get()*height);
			size.set(size.get() * sizeDecay );
			currentInterval = (int) interval.get();
		}
	}

	private float boundingBox(ArrayList<ArrayList<Float>> buffer2) {
		ArrayList<Float> min = new ArrayList<Float>();
		ArrayList<Float> max = new ArrayList<Float>();
		for(int i = 0; i < buffer2.get(0).size(); i++){
			min.add(Float.MAX_VALUE);
			max.add(Float.MIN_VALUE);
		}
		for(ArrayList<Float> l : buffer2){
			for(int i = 0; i < l.size(); i++){
				float val = l.get(i);
				min.set(i, val < min.get(i) ? val : min.get(i));
				max.set(i, val > max.get(i) ? val : max.get(i));
			}
		}
		float sum = 0f;
		for(int i = 0; i < min.size(); i++){
			sum += Math.pow(max.get(i) - min.get(i), 2);
		}
		return (float) Math.sqrt(sum);
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { skeletontracker.SkeletonTracker.class
				.getName() });
	}
}
