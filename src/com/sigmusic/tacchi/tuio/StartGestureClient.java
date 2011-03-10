package com.sigmusic.tacchi.tuio;

import TUIO.TuioClient;

public class StartGestureClient {
	public static void main(String args[]){
		TuioClient client = new TuioClient();
		
		TuioGestureClient gesture = new TuioGestureClient(client);
		client.connect();
	}
}
