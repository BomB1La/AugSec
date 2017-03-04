package augsec.augsec.non_ar_files;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;

import augsec.augsec.MainActivity;
import augsec.augsec.R;

public class NonArView extends Activity {
	/** Hold a reference to our GLSurfaceView */
	private NonArGLSurfaceView mGLSurfaceView;
	private NonArRenderer mRenderer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.non_ar_view);

		mGLSurfaceView = (NonArGLSurfaceView) findViewById(R.id.gl_surface_view);

		// Check if the system supports OpenGL ES 2.0.
		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
		final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

		if (supportsEs2) {
			// Request an OpenGL ES 2.0 compatible context.
			mGLSurfaceView.setEGLContextClientVersion(2);

			final DisplayMetrics displayMetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

			// Set the renderer to our demo renderer, defined below.
			mRenderer = new NonArRenderer(this, mGLSurfaceView);
			mGLSurfaceView.setRenderer(mRenderer, displayMetrics.density);
		} else {
			// This is where you could create an OpenGL ES 1.x compatible
			// renderer if you wanted to support both ES 1 and ES 2.
			return;
		}

		findViewById(R.id.button_decrease).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				decreaseCount();
			}
		});

		findViewById(R.id.button_increase).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				increaseCount();
			}
		});

	}
	@Override
	public void onBackPressed()
	{
		Intent intent = new Intent(this,MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		super.onBackPressed();  // optional depending on your needs
	}
	@Override
	protected void onResume() {
		// The activity must call the GL surface view's onResume() on activity
		// onResume().
		super.onResume();
		mGLSurfaceView.onResume();
	}

	@Override
	protected void onPause() {
		// The activity must call the GL surface view's onPause() on activity
		// onPause().
		super.onPause();
		mGLSurfaceView.onPause();
	}

	private void decreaseCount() {
		mGLSurfaceView.queueEvent(new Runnable() {
			@Override
			public void run() {
				mRenderer.decreaseCubeCount();
			}
		});
	}

	private void increaseCount() {
		mGLSurfaceView.queueEvent(new Runnable() {
			@Override
			public void run() {
				mRenderer.increaseCubeCount();
			}
		});
	}

}