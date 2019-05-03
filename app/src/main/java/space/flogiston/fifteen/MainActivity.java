package space.flogiston.fifteen;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button B[] = new Button[16];
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        B[0] = findViewById(R.id.btn0);
        B[1] = findViewById(R.id.btn1);
        B[2] = findViewById(R.id.btn2);
        B[3] = findViewById(R.id.btn3);
        B[4] = findViewById(R.id.btn4);
        B[5] = findViewById(R.id.btn5);
        B[6] = findViewById(R.id.btn6);
        B[7] = findViewById(R.id.btn7);
        B[8] = findViewById(R.id.btn8);
        B[9] = findViewById(R.id.btn9);
        B[10] = findViewById(R.id.btn10);
        B[11] = findViewById(R.id.btn11);
        B[12]= findViewById(R.id.btn12);
        B[13] = findViewById(R.id.btn13);
        B[14] = findViewById(R.id.btn14);
        B[15] = findViewById(R.id.btn15);

        loadState();
    }
    public void startNewGame (View view) {
        ArrayList<Integer> usedNumbers = new ArrayList<Integer>();
        for (int i = 0; i < 15; i++) {
            Random random = new Random();
            int num = random.nextInt(15);;
            while (usedNumbers.contains(num)) {
                num = random.nextInt(15);
            }
            usedNumbers.add(num);
            B[i].setText("" + (num + 1));
            B[i].setVisibility(View.VISIBLE);
        }
        B[15].setVisibility(View.INVISIBLE);
        saveState();
    }
    public void buttonClick(View view) {
        Button clickedButton = (Button) view;
        // B[0].setText(view.getTag().toString());
        moveTile(Integer.parseInt(view.getTag().toString()));
    }
    private void moveTile (int a) {
        if (a - 4 >= 0) {
            if (B[a - 4].getVisibility() == View.INVISIBLE) {
                swap(a, a - 4);
                return;
            }
        }
        if (a + 4 <= 15) {
            if (B[a + 4].getVisibility() == View.INVISIBLE) {
                swap(a, a + 4);
                return;
            }
        }
        if (a - 1 >= 0 && a % 4 != 0) {
            if (B[a - 1].getVisibility() == View.INVISIBLE) {
                swap(a, a - 1);
                return;
            }
        }
        if (a + 1 <= 15 && a % 4 != 3) {
            if (B[a + 1].getVisibility() == View.INVISIBLE) {
                swap(a, a + 1);
                return;
            }
        }
    }
    private void swap (int a, int b) {
        B[b].setText(B[a].getText());
        B[a].setVisibility(View.INVISIBLE);
        B[b].setVisibility(View.VISIBLE);
        saveState();
    }
    private void saveState () {
        String memory = "";
        int invisibleButtonId = 0;
        for (int i = 0; i < 16; i++) {
            memory += B[i].getText() + "/";
            if (B[i].getVisibility() == View.INVISIBLE) {
                invisibleButtonId = i;
            }
        }
        memory += invisibleButtonId;

        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("memory", memory);
        ed.commit();
    }
    private void loadState () {
        sPref = getPreferences(MODE_PRIVATE);
        String memory = sPref.getString("memory", "");
        if (memory.length() > 0) {
            String[] state = memory.split("/");
            for (int i = 0; i < 16; i++) {
                B[i].setText(state[i]);
            }
            B[Integer.parseInt(state[16])].setVisibility(View.INVISIBLE);
        } else {
            startNewGame(null);
        }
    }
}
