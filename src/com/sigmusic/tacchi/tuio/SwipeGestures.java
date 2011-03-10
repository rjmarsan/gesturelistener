package com.sigmusic.tacchi.tuio;

import java.util.ArrayList;

import TUIO.TuioClient;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioPoint;
import TUIO.TuioTime;

public class SwipeGestures implements TuioListener {
	int MAX_CURSORS = 5;
	float MIN_BLOB_DISTANCE = 0.1f;
	float MAX_BLOB_DISTANCE = 0.3f;
	float HORIZ_MIN_X = 0.3f;
	float HORIZ_MAX_Y = 0.1f;
	float VERT_MIN_Y = 0.3f;
	float VERT_MAX_X = 0.1f;
	
	ArrayList<TuioCursor> pastCursors = new ArrayList<TuioCursor>();
	
	public SwipeGestures(TuioClient client) {
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


		if (pastCursors.size() >= MAX_CURSORS) {
			boolean success = false;
			
			float startBlobSize = minCircle(pastCursors, 0);
			float endBlobSize = lastMinCircle(pastCursors);
			
			if (startBlobSize > MIN_BLOB_DISTANCE && startBlobSize < MAX_BLOB_DISTANCE
					&& endBlobSize > MIN_BLOB_DISTANCE && endBlobSize < MAX_BLOB_DISTANCE) {
				//ok. it passed that test.
				if (isLeftSwipe(pastCursors)) {
					success = true;
					System.out.println("Left Swipe!!!");
					//call whatever
				} else if (isUpSwipe(pastCursors)) {
					success = true;
					System.out.println("Up Swipe!!!");
					//call whatever
				}
			}
				
			
			
			
			
			if (success) {
				pastCursors.clear();
			}
		}
		
		
		
		
	}
	
	boolean isLeftSwipe(ArrayList<TuioCursor> cursors) {
		if (allPointsWithinRect(cursors, findMaxIndex(cursors), 0, HORIZ_MIN_X, 0, 1))
			return true;
		return false;
	}
	
	boolean isUpSwipe(ArrayList<TuioCursor> cursors) {
		if (allPointsWithinRect(cursors, findMaxIndex(cursors), 0, 1, 0, VERT_MIN_Y))
			return true;
		return false;
	}

	
	
	int findMaxIndex(ArrayList<TuioCursor> cursors) {
		int maxIndex = 0;
		for (TuioCursor cursor : cursors) {
			int len = cursor.getPath().size();
			if (len > maxIndex) 
				maxIndex = len;
		}
		
		return maxIndex;
	}
	boolean allPointsWithinRect(ArrayList<TuioCursor> cursors, int index, float left, float right, float top, float bottom) {
		for (TuioCursor cursor : cursors) {
			TuioPoint p;
			if (index > cursor.getPath().size())
				p = cursor.getPath().lastElement();
			else
				p = cursor.getPath().get(index);
			
			if (p.getX() < left || p.getX() > right || p.getY() < top || p.getY() > bottom) {
				return false;
			}
		}
		return true;
	}
	
	float lastMinCircle(ArrayList<TuioCursor> cursors) {
		int maxIndex = 0;
		for (TuioCursor cursor : cursors) {
			int len = cursor.getPath().size();
			if (len > maxIndex) 
				maxIndex = len;
		}
		
		return minCircle(cursors, maxIndex);
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
			TuioPoint point;
			if (cursor.getPath().size() < index)
				point = cursor.getPath().lastElement();
			else
				point = cursor.getPath().get(index);
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
