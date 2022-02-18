package cs.umu.se.fikaspelen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

/**
 * The main activity of the app
 *
 * @since 2021-03-22
 * @author Jesper Byström
 */
public class MainActivity extends MoreMenuActivity
{
    private boolean johane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPlayListener();
        initRulesListener();

        johane = getIntent().getBooleanExtra("johane", false);

        if(savedInstanceState != null) {
            johane = savedInstanceState.getBoolean("johane");
        }
    }

    /**
     * Initilizes the listener for the play button
     */
    private void initPlayListener() {
        Button b = (Button)findViewById(R.id.btn_play);

        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FikaActivity.class);
                intent.putExtra("johane", johane);
                startActivityForResult(intent, 1);
            }
        });
    }

    /**
     * Initilizes the listener for the rule button
     */
    private void initRulesListener() {
        Button rb = (Button)findViewById(R.id.btn_rules);

        rb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InstructionsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                johane = data.getBooleanExtra("johane", false);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("johane", johane);
    }

    @Override
    protected Intent getAboutActivityIntent() {
        Intent i = super.getAboutActivityIntent();
        i.putExtra("johane", johane);
        return i;
    }

    @Override
    protected Intent getInstructionsActivityIntent() {
        Intent i = super.getInstructionsActivityIntent();
        i.putExtra("johane", johane);
        return i;
    }
}