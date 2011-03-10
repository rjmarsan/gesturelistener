package com.sigmusic.tacchi.tuio;

import TUIO.TuioClient;
import TUIO.TuioContainer;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

public class TuioGestureClient implements TuioListener {
	
	public TuioGestureClient(TuioClient client) {
		client.addTuioListener(this);
	}

	@Override
	public void addTuioCursor(TuioCursor tcur) {
		// TODO Auto-generated method stub
		System.out.println("cursor added! "+tcur);
	}

	@Override
	public void addTuioObject(TuioObject tobj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(TuioTime ftime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTuioCursor(TuioCursor tcur) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTuioObject(TuioObject tobj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTuioCursor(TuioCursor tcur) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTuioObject(TuioObject tobj) {
		// TODO Auto-generated method stub
		
	}
	


}
