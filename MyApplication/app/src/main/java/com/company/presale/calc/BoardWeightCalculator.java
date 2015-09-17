package com.company.presale.calc;

/**
 * Created by Arinjayan on 22-08-2015.
 */
public class BoardWeightCalculator {
    public double unitBoardWt(int totalPly,int gsm,double length,double breadth){
        int corrugPlys = totalPly/2;
        double corrugSheetWt = gsm * 1.4;
        double unitBoardWt= (totalPly - corrugPlys)* 100 + corrugPlys*corrugSheetWt;
        return length*breadth*unitBoardWt/1550;
    }
}
