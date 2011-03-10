package com.sigmusic.tacchi.tuio;

import java.util.ArrayList;

import TUIO.TuioClient;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioPoint;
import TUIO.TuioTime;

public class TuioGestureClient implements TuioListener {
	int MAX_CURSORS = 5;
	
	ArrayList<TuioCursor> pastCursors = new ArrayList<TuioCursor>();
	
	public TuioGestureClient(TuioClient client) {
		client.addTuioListener(this);
	}

	@Override
	public void addTuioCursor(TuioCursor tcur) {
//		System.out.println("cursor added! "+tcur);
	}

	@Override
	public void addTuioObject(TuioObject tobj) {
		
	}

	@Override
	public void refresh(TuioTime ftime) {
		
	}

	@Override
	public void removeTuioCursor(TuioCursor tcur) {
		//trim history to previous 5
		pastCursors.add(tcur);
		if (pastCursors.size() > MAX_CURSORS+1) {
			pastCursors.remove(0);
			while (pastCursors.size() > MAX_CURSORS) {
				pastCursors.remove(0);
			}
		}

		/**
		 * check:
		 * were the last 5 cursors to be removed from the screen started at the same point?
		 */
		
		if (pastCursors.size() >= MAX_CURSORS) {
			boolean success = false;
			//check to see if all 5 are within same time-bounds of touch down and touch up
			//but we're not going to do that now.
			
			
			//check to see if points started within certain min-radius and ended within min-radius
			
			
			//find pivot
			TuioCursor pivot = findLeastActive(pastCursors);
			
			//iterate over each moving point, calculating arc
			//compare all arcs together.
			//test for relative and absolute bounds
			// as in: all arcs are within 10 degrees, and all over 45 degrees.
			for (TuioCursor cursor : pastCursors) {
				if (pivot != cursor ) {
					//pi
				}
			}
			if (success) {
				pastCursors.clear();
			}
		}
		
		
		
		
	}
	
	
	float minCircle(ArrayList<TuioCursor> cursors, int index) {
		float totalMinimum = 100f;
		for (TuioCursor cursor : cursors) {
			float max = maxDistance(cursors, index, cursor);
			if (max < totalMinimum) 
				totalMinimum = max;
		}
		return totalMinimum;
	}
	
	float maxDistance(ArrayList<TuioCursor> cursors, int index, TuioCursor targetCursor) {
		TuioPoint pivotPoint = targetCursor.getPath().get(index);
		float maxDistance = 0;
		for (TuioCursor cursor : cursors) {
			TuioPoint point = cursor.getPath().get(index);
			float distance = point.getDistance(pivotPoint);
			if (distance > maxDistance) {
				maxDistance = distance;
			}
		}
		
		return maxDistance;
	}
	
	
	TuioCursor findLeastActive(ArrayList<TuioCursor> cursors) {
		TuioCursor result = null;
		float minDistance = 100;
		for (TuioCursor cursor : cursors ) {
			float maxHistory = 0;
			TuioPoint first = cursor.getPath().get(0);
			for (TuioPoint point : cursor.getPath()) {
				float distance = first.getDistance(point);
				if (distance > maxHistory) {
					maxHistory = distance;
				}
			}
			if (maxHistory < minDistance)  {
				result = cursor;
				minDistance = maxHistory;
			}
		}
		return result;
	}
	
	
	float calcArc(TuioCursor pivot, TuioCursor movingcursor) {
		TuioPoint pivotPoint = pivot.getPosition();
		
		TuioPoint firstPoint = movingcursor.getPath().get(0);
		TuioPoint lastPoint = movingcursor.getPosition();
		
		return calcArc(pivotPoint, firstPoint, lastPoint);
	}
	
	float calcArc(TuioPoint pivot, TuioPoint startpoint, TuioPoint endpoint) {
		float angle1 = pivot.getAngle(startpoint);
		float angle2 = pivot.getAngle(endpoint);
		
		float result = Math.abs(angle1 - angle2);
		if (result > 3.1415f) {
			result = 6.2832f - result;
		}
		return result;
	}

	
	
	
	

	@Override
	public void removeTuioObject(TuioObject tobj) {
		
	}

	@Override
	public void updateTuioCursor(TuioCursor tcur) {
		
	}

	@Override
	public void updateTuioObject(TuioObject tobj) {
		
	}
	


}
