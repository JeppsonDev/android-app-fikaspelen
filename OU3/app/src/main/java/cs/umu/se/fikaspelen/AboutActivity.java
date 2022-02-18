package cs.umu.se.fikaspelen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

/**
 * A class for the about activity
 *
 * @since 2021-03-22
 * @author Jesper Byström
 */
public class AboutActivity extends AppCompatActivity {

    private boolean johane = false;

    private void end() {
        Intent intent = new Intent();
        intent.putExtra("johane", johane);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Switch johaneSwitch = findViewById(R.id.switch_johane);
        johaneSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                johane = isChecked;
            }
        });

        if(savedInstanceState != null) {
            johane = savedInstanceState.getBoolean("johane");
        } else {
            johane = getIntent().getBooleanExtra("johane", false);
        }

        johaneSwitch.setChecked(johane);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("johane", johane);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                end();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

    @Override
    public void onBackPressed() {
        end();
    }
}