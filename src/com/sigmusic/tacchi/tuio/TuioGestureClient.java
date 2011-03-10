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
		if (pastCursors.size() > MAX_CURSORS) {
			pastCursors.remove(0);
		}
		pastCursors.add(tcur);
		
		/**
		 * check:
		 * were the last 5 cursors to be removed from the screen started at the same point?
		 */
		
		if (pastCursors.size() == MAX_CURSORS) {
			float minstartdistance = minCircle(pastCursors, 0);
			System.out.println("Min start distance: "+minstartdistance);
			pastCursors.clear();
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
