package cs.umu.se.fikaspelen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * The result activity, the activity that gets visited when the application is finished
 *
 * @since 2021-03-22
 * @author Jesper Byström
 */
public class ResultActivity extends AppCompatActivity {

    private boolean johane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        long timeLeft = getIntent().getLongExtra("timeLeft", 0);
        int itemsFound = getIntent().getIntExtra("itemsFound", 0);
        johane = getIntent().getBooleanExtra("johane", false);

        TextView timeLeftText = (TextView)findViewById(R.id.txt_timeleft);
        timeLeftText.setText("Tid Kvar: " + FikaTimerString.generate(timeLeft));

        TextView itemsFoundText = (TextView)findViewById(R.id.txt_itemsfound);
        itemsFoundText.setText("Föremål hittade: " + itemsFound + " / 10");

        Button playAgain = (Button)findViewById(R.id.btn_play_again);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ResultActivity.this, FikaActivity.class);
                i.putExtra("johane", johane);
                finish();
                startActivity(i);
            }
        });

        Button mainMenu = (Button)findViewById(R.id.btn_to_main_menu);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ResultActivity.this, MainActivity.class);
                i.putExtra("johane", johane);
                finish();
                startActivity(i);
            }
        });
    }
}