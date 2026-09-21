package mquinn.whackamole;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
 * Author: Matthew Quinn
 * 5/4/17
 *
 * Class for the end of the game, used to display some information on the game end such as score
 * and reason for end. Also has the text field to submit your score under your name.
 *
 */

public class EndActivity extends AppCompatActivity {

    private String defaultName = String.valueOf(R.string.default_name);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        TextView ScoreCheck = (TextView) findViewById(R.id.mScoreValue);
        TextView ReasonCheck = (TextView) findViewById(R.id.mReasonDisplay);

        Button mSubmit = (Button) findViewById(R.id.button_Submit);
        Button mMenu = (Button) findViewById(R.id.button_Menu);

        final EditText nameBox = (EditText)findViewById(R.id.mNameEntry);
        final Player newPlayer = new Player();

        // Get the extras our Intent sent through
        Intent intent = getIntent();
        final int ScoreValue = intent.getExtras().getInt("score");
        String ReasonValue = intent.getExtras().getString("reason");

        final Dbhelper db = new Dbhelper(this);

        // Populate our new Player object's Name and Score
        newPlayer.setVarName(defaultName);
        newPlayer.setVarScore(ScoreValue);

        if (ScoreCheck != null){
            ScoreCheck.setText(String.valueOf(ScoreValue));
        }

        if (ReasonCheck != null){
            ReasonCheck.setText(ReasonValue);
        }

        // Submit button saves the Player score
        if (mSubmit != null){
            mSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Validate text box - shouldn't be empty
                    if (nameBox != null) {
                        if ((nameBox.getText().toString()).equals("")) {
                            Toast.makeText(EndActivity.this, R.string.str_empty_name, Toast.LENGTH_SHORT).show();
                        } else {

                            // Upon submit, add player to the database and take us to the scoreboard
                            String playerName = nameBox.getText().toString();

                            newPlayer.setVarName(playerName);
                            newPlayer.setVarScore(ScoreValue);

                            db.addPlayer(newPlayer);

                            Intent intent = new Intent(getApplicationContext(), ScoresActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            });
        }

        // Menu button goes back to the main menu
        if (mMenu != null){
            mMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
