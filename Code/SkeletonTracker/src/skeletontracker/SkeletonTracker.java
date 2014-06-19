package skeletontracker;

import java.awt.Container;
import java.util.ArrayList;

import SimpleOpenNI.ContextWrapper;
import SimpleOpenNI.SimpleOpenNI;

import processing.core.PApplet;
import processing.core.PVector;

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
	private AlphaFilter rightXFilter, rightYFilter;

	private SimpleOpenNI context;
	int[] userClr = new int[] { color(255, 0, 0), color(0, 255, 0),
			color(0, 0, 255), color(255, 255, 0), color(255, 0, 255),
			color(0, 255, 255) };

	PVector bodyCenter = new PVector();
	PVector bodyDir = new PVector();
	PVector com = new PVector();
	float zoomF = 0.5f;
	float rotX = radians(180);
	float rotY = radians(0);

	public void setup() {
		size(640, 480, P3D);
		frameRate(30);
		yin = new YIN(2);
		interval = new AlphaFilter(1f, 40f);
		hb = new HistogramBuffer(50);
		buffer = new ArrayList<ArrayList<Float>>();
		size = new AlphaFilter(.005f);

		rightXFilter = new AlphaFilter(.15f);
		rightYFilter = new AlphaFilter(.15f);

		context = new SimpleOpenNI(this);
		if (context.isInit() == false) {
			println("Can't init SimpleOpenNI, maybe the camera is not connected!");
			exit();
			return;
		}

		// disable mirror
		context.setMirror(false);
		// enable depthMap generation
		context.enableDepth();
		// enable skeleton generation for all joints
		context.enableUser();
		perspective(radians(45), (float) (width) / (float) (height), 10, 150000);
	}

	void drawLimb(int userId, int jointType1, int jointType2) {
		PVector jointPos1 = new PVector();
		PVector jointPos2 = new PVector();
		float confidence;

		// draw the joint position
		confidence = context.getJointPositionSkeleton(userId, jointType1,
				jointPos1);
		confidence = context.getJointPositionSkeleton(userId, jointType2,
				jointPos2);

		stroke(255, 0, 0, confidence * 200 + 55);
		line(jointPos1.x, jointPos1.y, jointPos1.z, jointPos2.x, jointPos2.y,
				jointPos2.z);
	}

	void getBodyDirection(int userId, PVector centerPoint, PVector dir) {
		PVector jointL = new PVector();
		PVector jointH = new PVector();
		PVector jointR = new PVector();
		float confidence;

		// draw the joint position
		confidence = context.getJointPositionSkeleton(userId,
				SimpleOpenNI.SKEL_LEFT_SHOULDER, jointL);
		confidence = context.getJointPositionSkeleton(userId,
				SimpleOpenNI.SKEL_HEAD, jointH);
		confidence = context.getJointPositionSkeleton(userId,
				SimpleOpenNI.SKEL_RIGHT_SHOULDER, jointR);

		// take the neck as the center point
		confidence = context.getJointPositionSkeleton(userId,
				SimpleOpenNI.SKEL_NECK, centerPoint);

		/*
		 * // manually calc the centerPoint PVector shoulderDist =
		 * PVector.sub(jointL,jointR);
		 * centerPoint.set(PVector.mult(shoulderDist,.5));
		 * centerPoint.add(jointR);
		 */

		PVector up = PVector.sub(jointH, centerPoint);
		PVector left = PVector.sub(jointR, centerPoint);

		dir.set(up.cross(left));
		dir.normalize();
	}

	// draw the skeleton with the selected joints
	void drawSkeleton(int userId) {
		strokeWeight(3);

		// to get the 3d joint data
		drawLimb(userId, SimpleOpenNI.SKEL_HEAD, SimpleOpenNI.SKEL_NECK);

		drawLimb(userId, SimpleOpenNI.SKEL_NECK,
				SimpleOpenNI.SKEL_LEFT_SHOULDER);
		drawLimb(userId, SimpleOpenNI.SKEL_LEFT_SHOULDER,
				SimpleOpenNI.SKEL_LEFT_ELBOW);
		drawLimb(userId, SimpleOpenNI.SKEL_LEFT_ELBOW,
				SimpleOpenNI.SKEL_LEFT_HAND);

		drawLimb(userId, SimpleOpenNI.SKEL_NECK,
				SimpleOpenNI.SKEL_RIGHT_SHOULDER);
		drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_SHOULDER,
				SimpleOpenNI.SKEL_RIGHT_ELBOW);
		drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_ELBOW,
				SimpleOpenNI.SKEL_RIGHT_HAND);

		drawLimb(userId, SimpleOpenNI.SKEL_LEFT_SHOULDER,
				SimpleOpenNI.SKEL_TORSO);
		drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_SHOULDER,
				SimpleOpenNI.SKEL_TORSO);

		drawLimb(userId, SimpleOpenNI.SKEL_TORSO, SimpleOpenNI.SKEL_LEFT_HIP);
		drawLimb(userId, SimpleOpenNI.SKEL_LEFT_HIP,
				SimpleOpenNI.SKEL_LEFT_KNEE);
		drawLimb(userId, SimpleOpenNI.SKEL_LEFT_KNEE,
				SimpleOpenNI.SKEL_LEFT_FOOT);

		drawLimb(userId, SimpleOpenNI.SKEL_TORSO, SimpleOpenNI.SKEL_RIGHT_HIP);
		drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_HIP,
				SimpleOpenNI.SKEL_RIGHT_KNEE);
		drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_KNEE,
				SimpleOpenNI.SKEL_RIGHT_FOOT);

		// draw body direction
		getBodyDirection(userId, bodyCenter, bodyDir);

		bodyDir.mult(200); // 200mm length
		bodyDir.add(bodyCenter);

		stroke(255, 200, 200);
		line(bodyCenter.x, bodyCenter.y, bodyCenter.z, bodyDir.x, bodyDir.y,
				bodyDir.z);

		strokeWeight(1);

	}

	public void draw() {
		context.update();

		PVector lefthand = new PVector();
		float confidence;

		// draw the joint position
		context.getJointPositionSkeleton(1, SimpleOpenNI.SKEL_RIGHT_HAND,
				lefthand);
		PVector righthand = new PVector();
		context.getJointPositionSkeleton(1, SimpleOpenNI.SKEL_LEFT_HAND,
				righthand);

		// println("right = " + lefthand.x + " " + lefthand.y);
		// println("left = " + righthand.x + " " + righthand.y);

		fill(200, 50);
		stroke(0, 0);
		rect(0, 0, width, height);

		text(frameRate, 30, 30);

		translate(width / 2, height / 2, 0);
		rotateX(rotX);
		rotateY(rotY);
		scale(zoomF);

		ArrayList<Float> v = new ArrayList<Float>();
		v.add(rightXFilter.put((float) righthand.x / 500.f));
		v.add(rightYFilter.put((float) righthand.y / 500.f));
		println(v.get(0) + " " + v.get(1));
		yin.process(v);

		buffer.add(v);
		if (buffer.size() > 100)
			buffer.remove(0);

		stroke(255);
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
			ellipse(0, 0, size.get() * height, size.get() * height);
			size.set(size.get() * sizeDecay);
			currentInterval = (int) interval.get();
		}

		// //////////////////////////////////////////////////////
		// set the scene pos
		int[] depthMap = context.depthMap();
		int[] userMap = context.userMap();
		int steps = 3; // to speed up the drawing, draw every third point
		int index;
		PVector realWorldPoint;

		 translate(0, 0, -1000); // set the rotation center of the scene 1000
		// infront of the camera

		// draw the pointcloud
		beginShape(POINTS);
		for (int y = 0; y < context.depthHeight(); y += steps) {
			for (int x = 0; x < context.depthWidth(); x += steps) {
				index = x + y * context.depthWidth();
				if (depthMap[index] > 0) {
					// draw the projected point
					realWorldPoint = context.depthMapRealWorld()[index];
					if (userMap[index] == 0)
						stroke(100);
					else
						stroke(userClr[(userMap[index] - 1) % userClr.length]);

					point(realWorldPoint.x, realWorldPoint.y, realWorldPoint.z);
				}
			}
		}
		endShape();

		// draw the skeleton if it's available
		int[] userList = context.getUsers();
		for (int i = 0; i < userList.length; i++) {
			if (context.isTrackingSkeleton(userList[i]))
				drawSkeleton(userList[i]);

			// draw the center of mass
			if (context.getCoM(userList[i], com)) {
				stroke(100, 255, 0);
				strokeWeight(1);
				beginShape(LINES);
				vertex(com.x - 15, com.y, com.z);
				vertex(com.x + 15, com.y, com.z);

				vertex(com.x, com.y - 15, com.z);
				vertex(com.x, com.y + 15, com.z);

				vertex(com.x, com.y, com.z - 15);
				vertex(com.x, com.y, com.z + 15);
				endShape();

				fill(0, 255, 100);
				text(Integer.toString(userList[i]), com.x, com.y, com.z);
			}
		}

		// draw the kinect cam
		context.drawCamFrustum();
	}

	private float boundingBox(ArrayList<ArrayList<Float>> buffer2) {
		ArrayList<Float> min = new ArrayList<Float>();
		ArrayList<Float> max = new ArrayList<Float>();
		for (int i = 0; i < buffer2.get(0).size(); i++) {
			min.add(Float.MAX_VALUE);
			max.add(Float.MIN_VALUE);
		}
		for (ArrayList<Float> l : buffer2) {
			for (int i = 0; i < l.size(); i++) {
				float val = l.get(i);
				min.set(i, val < min.get(i) ? val : min.get(i));
				max.set(i, val > max.get(i) ? val : max.get(i));
			}
		}
		float sum = 0f;
		for (int i = 0; i < min.size(); i++) {
			sum += Math.pow(max.get(i) - min.get(i), 2);
		}
		return (float) Math.sqrt(sum);
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { skeletontracker.SkeletonTracker.class
				.getName() });
	}

	public void onNewUser(SimpleOpenNI curContext, int userId) {
		println("onNewUser - userId: " + userId);
		println("\tstart tracking skeleton");

		context.startTrackingSkeleton(userId);
	}

	public void onLostUser(SimpleOpenNI curContext, int userId) {
		println("onLostUser - userId: " + userId);
	}

	public void onVisibleUser(SimpleOpenNI curContext, int userId) {
		// println("onVisibleUser - userId: " + userId);
	}

}
