package mquinn.whackamole;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 *
 * Author: Matthew Quinn
 * 2/4/17
 *
 * Options class - handles some preferences and settings.
 * Have added option to clear out the database and a difficulty setting spinner
 *
 * TODO: Use an onSelect listener for the spinner to negate need for save button
 *
 */

public class OptionsActivity extends AppCompatActivity{

    private Spinner mSpinner;
    private Dbhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        mSpinner = (Spinner) findViewById(R.id.diff_spinner);

        db = new Dbhelper(this);
        Button mSave = (Button) findViewById(R.id.btn_save);
        Button mClear = (Button) findViewById(R.id.button_clear);
        Button mMain = (Button) findViewById(R.id.btn_main);

        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        // Loads current difficulty setting if there is one, defaults to Medium
        String currentDiff = sharedPref.getString("saved_difficulty", "Medium");

        // ArrayAdapter handles our array of difficulties (Easy, Medium, Hard)
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.difficulties, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (mSpinner != null) {
            mSpinner.setAdapter(adapter);
            mSpinner.setSelection(adapter.getPosition(currentDiff));
        } else {
            Log.i("OptionsActivity", "mSpinner failed - null pointer");
        }

        // Button to clear database, shows alert and offers yes or no
        if (mClear != null) {
            mClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    db.deleteAll();
                                    Toast.makeText(OptionsActivity.this, R.string.str_clearscore, Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    dialog.dismiss();
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage(R.string.str_clear_scores_alert).setPositiveButton(R.string.str_yes, dialogClickListener)
                            .setNegativeButton(R.string.str_no, dialogClickListener).show();
                }
            });
        }

        // Button saves changes
        if (mSave != null) {
            mSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String diffString = mSpinner.getSelectedItem().toString();

                    // Store difficulty string to prefs, show success toast
                    editor.putString("saved_difficulty", diffString);
                    editor.apply();
                    Toast.makeText(OptionsActivity.this, R.string.str_savedmsg, Toast.LENGTH_SHORT).show();

                }
            });
        }

        // Main menu button
        if (mMain != null) {
            mMain.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick (View v){

                    Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                    startActivity(intent);

                }
            });
        }

    }
}

