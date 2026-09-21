package mquinn.whackamole;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * Author: Matthew Quinn
 * 10/4/17
 *
 * Our scoreboard class, takes our player scores and lists them in a table made of
 * scoreitem and scoretable XMLs
 *
 */

public class ScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        Button mMenu = (Button) findViewById(R.id.button_Menu);

        final Dbhelper db = new Dbhelper(this);
        final List<Player> dataList;

        TableLayout mTableLayout = (TableLayout)findViewById(R.id.scoreTable);

        dataList = db.getAll();

        // Sort operation to sort our Players by high score descending
        Collections.sort(dataList, new Comparator<Player>() {
            @Override
            public int compare(Player playerOne, Player playerTwo) {
                return playerTwo.getVarScore() - playerOne.getVarScore();
            }

        });

        // Loop our rows and add to our table using LayoutInflater
        for (int i = 0; i < dataList.size(); i++) {

            View tableRow = LayoutInflater.from(this).inflate(R.layout.scoreitem,null,false);

            TextView playerName = (TextView) tableRow.findViewById(R.id.player_name);
            TextView playerScore = (TextView) tableRow.findViewById(R.id.player_score);

            String varName = dataList.get(i).getVarName();
            int varScore = dataList.get(i).getVarScore();

            playerName.setText(varName);
            playerScore.setText(String.valueOf(varScore));

            if (mTableLayout != null){
                mTableLayout.addView(tableRow);
            }

        }

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
