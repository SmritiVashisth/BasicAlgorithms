package co.in.sdslabs.basicalgorithms;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Heapify extends SurfaceView implements SurfaceHolder.Callback {

	private MainThread thread;
	private HeapAnimated heap;

	public Heapify(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		// create heap and load bitmap
		heap = new HeapAnimated(BitmapFactory.decodeResource(getResources(),
				R.drawable.heapanim), 10, 10 // initial position
												// x and y
				, 10, 20 // width and height of sprite
				, 5, 18); // FPS and number of frames in the animation

		// create the game loop thread
		thread = new MainThread(getHolder(), this);

		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// at this point the surface is created
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d("key", "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		Log.d("key", "Thread was shut down cleanly");
	}

	public void render(Canvas canvas) {
		heap.draw(canvas);
	}

	public void update() {
		heap.update(System.currentTimeMillis());
	}

}
