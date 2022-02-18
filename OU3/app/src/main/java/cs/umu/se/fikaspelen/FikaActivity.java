package cs.umu.se.fikaspelen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The class representing the activity for FikaActivity
 *
 * @since 2021-03-22
 * @author Jesper Byström
 */
public class FikaActivity extends AppCompatActivity {

    private ViewPager pager;
    private FikaFragmentSlideAdapter pageAdapter;
    private ArrayList<FikaItem> fikaItems = new ArrayList<>();
    private FikaTimer timer;
    public boolean johane = false;
    private int itemsFound;
    private int currentItem = 0;

    /**
     * Initilizes the activity
     */
    private void initilize() {
        fikaItems.add(new FikaItem(R.drawable.pringles, "Pringles", "Pringles? Är det fredagsmys eller fika? Fika såklart! Pringles fungerar när som helst", "5053990138753"));
        fikaItems.add(new FikaItem(R.drawable.chokladbollar, "Chokladbollar", "Inget fika är komplett utan hederligt traditionella chokladbollar!", "7318690076933"));
        fikaItems.add(new FikaItem(R.drawable.crunchy_peanut_butter, "Crunchy Jordnötssmör", "Jordnötssmör går perfekt till kex under fikat!", "7350021421869"));
        fikaItems.add(new FikaItem(R.drawable.cheese, "Den skrattande kon - ost!", "Är inte klassikern att lägga ost på kexet? Jo såklart! Fika!", "3073780774130"));
        fikaItems.add(new FikaItem(R.drawable.majskakor, "Majskakor", "Majskakor är något alla älskar!", "7350028546640"));
        fikaItems.add(new FikaItem(R.drawable.kex, "Göteborgs utvalda kex till ost", "Jajamen, lägg lite ost på kexet så blir det fint ska du se", "7310523061017"));
        fikaItems.add(new FikaItem(R.drawable.minibullar, "Minibullar", "Fungerar även perfekt till utflykter!", "7318690132349"));
        fikaItems.add(new FikaItem(R.drawable.dammsugare, "Punchrullar / dammsugare", "Dammsugare? Punchrullar? Vad du än kallar dem, är de fantastiskt goda!", "7318690076926"));
        fikaItems.add(new FikaItem(R.drawable.ballerina, "Ballerina - Peanut butter", "Ballerina kommer i många olika smaker, men jordnötssmören blir perfekt till fikat", "7310520009531"));
        fikaItems.add(new FikaItem(R.drawable.oatdrink, "Havredryck", "Havredryck, perfekt att smälta ner kaffet med!", "6408430102204"));

        Collections.shuffle(fikaItems);

        pageAdapter = new FikaFragmentSlideAdapter(getSupportFragmentManager(), fikaItems);
        pageAdapter.setFoundItemListener(new FoundItemListener() {
            @Override
            public void invoke() {
                foundItem();
            }
        });
        pager.setAdapter(pageAdapter);
    }

    /**
     * Restores fika items from a saved instance state
     *
     * @param savedInstanceState
     */
    private void restoreFikaItems(Bundle savedInstanceState) {
        fikaItems = savedInstanceState.getParcelableArrayList("fikaItems");

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            FikaFragment f = (FikaFragment)fragment;
            f.setFoundItemListener(new FoundItemListener() {
                @Override
                public void invoke() {
                    foundItem();
                }
            });
        }

        pageAdapter = new FikaFragmentSlideAdapter(getSupportFragmentManager(), fikaItems);
        pageAdapter.setFoundItemListener(new FoundItemListener() {
            @Override
            public void invoke() {
                foundItem();
            }
        });
        pager.setAdapter(pageAdapter);
    }

    /**
     * Restores fika timer from a saved instance state
     * @param savedInstanceState
     */
    private void restoreFikaTimer(Bundle savedInstanceState) {
        long timeLeft = savedInstanceState.getLong("timeLeft");

        timer = new FikaTimer(findViewById(R.id.txt_timer), timeLeft, 1000);
    }

    /**
     * Finishes the game
     *
     */
    private void finishGame() {
        Intent intent = new Intent(FikaActivity.this, ResultActivity.class);
        intent.putExtra("timeLeft", timer.getTimeLeft());
        intent.putExtra("itemsFound", itemsFound);
        intent.putExtra("johane", johane);
        timer.cancel();
        startActivity(intent);
        finish();
    }

    /**
     * This will go back to the previous page
     */
    private void goBack() {
        Intent intent = new Intent(FikaActivity.this, MainActivity.class);
        intent.putExtra("johane", johane);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fika);

        johane = getIntent().getBooleanExtra("johane", false);

        pager = findViewById(R.id.vp_pager);

        if(savedInstanceState != null) {
            johane = savedInstanceState.getBoolean("johane");
            itemsFound = savedInstanceState.getInt("itemsFound");
            currentItem = savedInstanceState.getInt("currentItem");
            restoreFikaItems(savedInstanceState);
            restoreFikaTimer(savedInstanceState);
        }
        else {
            initilize();
            timer = new FikaTimer(findViewById(R.id.txt_timer), (1000 * 60) * 30, 1000);
            johane = getIntent().getBooleanExtra("johane", johane);
        }

        timer.setFikaTimerFinishedListener(new FikaTimerFinishedListener() {
            @Override
            public void finished() {
                finishGame();
            }
        });

        timer.start();
    }

    public void foundItem() {
        itemsFound++;
        checkForWin();

    }

    public void checkForWin() {
        if(itemsFound >= 10) {
            finishGame();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("johane", johane);
        outState.putInt("itemsFound", itemsFound);
        outState.putInt("currentItem", currentItem);
        outState.putParcelableArrayList("fikaItems", fikaItems);
        outState.putLong("timeLeft", timer.getTimeLeft());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        timer.cancel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goBack();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

    @Override
    public void onBackPressed() {
        goBack();
    }
}