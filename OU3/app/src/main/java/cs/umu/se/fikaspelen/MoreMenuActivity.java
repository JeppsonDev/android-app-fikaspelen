package cs.umu.se.fikaspelen;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * An activity that adds a more menu to the toolbar with "about" and "rules" as items
 *
 * @since 2021-03-22
 * @author Jesper Byström
 */
public class MoreMenuActivity extends AppCompatActivity {

    protected Intent getAboutActivityIntent() {
        return new Intent(MoreMenuActivity.this, AboutActivity.class);
    }

    protected Intent getInstructionsActivityIntent() {
        return new Intent(MoreMenuActivity.this, InstructionsActivity.class);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.item_about:
                startActivityForResult(getAboutActivityIntent(), 1);
                break;
            case R.id.item_rules:
                startActivityForResult(getInstructionsActivityIntent(), 1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.more_menu, menu);
        return true;
    }
}
