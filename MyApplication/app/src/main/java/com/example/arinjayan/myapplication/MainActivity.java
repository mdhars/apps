package com.example.arinjayan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.company.presale.calc.BoardWeightCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    private BoardWeightCalculator boardWeightCalculator = new BoardWeightCalculator();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean calculateBoardSize(View arg0){
        EditText nmHeight = (EditText) findViewById(R.id.nmHeight);
        EditText nmLength = (EditText) findViewById(R.id.nmLength);
        EditText nmBreadth = (EditText) findViewById(R.id.nmBreadth);
        TextView wdReelSize = (TextView) findViewById(R.id.txReelSize);
        TextView wdBrdSize = (TextView) findViewById(R.id.txBrdSize);
        TextView nmAdjust = (TextView) findViewById(R.id.nmAdjust);
        CheckBox chkInchCM = (CheckBox) findViewById(R.id.chkInchCM);
        String strLen = nmLength.getText().toString();
        String strBreadth = nmBreadth.getText().toString();
        String strHeight = nmHeight.getText().toString();
        if(strLen.length()>0 & strBreadth.length()>0 && strHeight.length()>0){
            int length = Integer.parseInt(strLen);
            int breadth = Integer.parseInt(strBreadth);
            int height = Integer.parseInt(strHeight);
            String adjust =nmAdjust.getText().toString();


            double boardSize = length+breadth + (25.4*((adjust.length()==0?2:Integer.parseInt(adjust))));
            double reelSize = (height+breadth)*2+ (25.4*((adjust.length()==0?2:Integer.parseInt(adjust)) ));

            if(chkInchCM.isChecked()){
                boardSize = boardSize/25.4;
                BigDecimal bd = new BigDecimal(Double.toString(boardSize));
                bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
                boardSize = bd.doubleValue();
                reelSize = reelSize/25.4;
                bd = new BigDecimal(Double.toString(reelSize));
                bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
                reelSize = bd.doubleValue();
            }
            wdReelSize.setText(String.valueOf(reelSize));
            wdBrdSize.setText(String.valueOf(boardSize));
            return true;
        }
        return false;
    }

    public boolean calcGramPerBoard(View arg0){

        Spinner spPly = (Spinner) findViewById(R.id.spPly);
        Spinner spGsm = (Spinner) findViewById(R.id.spGSM);

        String plyOption = spPly.getSelectedItem().toString();
        String gsmOption = spGsm.getSelectedItem().toString();
        if(plyOption.equals("Ply") || gsmOption.equals("GSM"))
            return false;


        int totalPly =Integer.parseInt(plyOption);
        int gsm = Integer.parseInt(gsmOption);


        TextView wdBrdLength = (TextView) findViewById(R.id.txReelSize);
        TextView wdBrdWidth = (TextView) findViewById(R.id.txBrdSize);

        TextView wtBoard = (TextView) findViewById(R.id.txBoardWt);

        String strLen = wdBrdLength.getText().toString();
        String strBreadth = wdBrdWidth.getText().toString();
        if(strLen.length()>0 & strBreadth.length()>0){
            double length = Double.parseDouble(strLen);
            double breadth = Double.parseDouble(strBreadth);

            BigDecimal bd = new BigDecimal(boardWeightCalculator.unitBoardWt(totalPly, gsm, length, breadth));
            bd=bd.setScale(2, RoundingMode.CEILING);

            wtBoard.setText(String.valueOf(bd.doubleValue()));
            return true;
        }


        return false;
    }


}
