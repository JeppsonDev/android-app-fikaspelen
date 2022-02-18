package cs.umu.se.fikaspelen;

import android.content.Intent;
import android.view.KeyEvent;

/**
 * A class for the capture activity
 *
 * @since 2021-03-22
 * @author Jesper Byström
 */
public class CaptureActivity extends com.journeyapps.barcodescanner.CaptureActivity
{
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
            Intent i = new Intent(CaptureActivity.this, FikaActivity.class);
            startActivity(i);
            finish();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
