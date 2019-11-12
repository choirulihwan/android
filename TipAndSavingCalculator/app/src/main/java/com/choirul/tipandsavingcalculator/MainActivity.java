package com.choirul.tipandsavingcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private static final NumberFormat currencyFormatValue = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormatValue = NumberFormat.getPercentInstance();

    private double billAmount = 0.0;
    private double tipPercent = 0.25;
    private TextView txtBillAmount, txtTipPercent, txtTip, txtTotalBillAmount;

    private double totalSalary = 0.0;
    private double savingPercent = 0.25;
    private TextView txtSavePercent, txtMoneySaved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtBillAmount = (TextView) findViewById(R.id.txtBillAmount);
        txtTipPercent = (TextView) findViewById(R.id.txtTipPercentage);
        txtTip = (TextView) findViewById(R.id.txtTip);
        txtTotalBillAmount = (TextView) findViewById(R.id.txtTotalBillAmount);

        txtTip.setText(currencyFormatValue.format(0));
        txtTotalBillAmount.setText(currencyFormatValue.format(0));

        txtSavePercent = (TextView) findViewById(R.id.txtSavePercent);
        txtMoneySaved = (TextView) findViewById(R.id.txtMoneySaved);

        txtMoneySaved.setText(currencyFormatValue.format(0));

        EditText edtMoneyAmount = (EditText) findViewById(R.id.edtMoneyAmount);
        edtMoneyAmount.addTextChangedListener(tipEdtMoneyAmountTextWatcher);

        SeekBar seekBarPercent = (SeekBar) findViewById(R.id.seekbarPercent);
        seekBarPercent.setOnSeekBarChangeListener(tipSeekBarChangeListener);

        EditText edtSalary = (EditText) findViewById(R.id.edtSalary);
        edtSalary.addTextChangedListener(edtSalaryChangedTextWatcher);

        SeekBar seekbarSavePercent = (SeekBar) findViewById(R.id.seekbarSavePercent);
        seekbarSavePercent.setOnSeekBarChangeListener(seekbarSavePercentChangeListener);
    }

    private final TextWatcher edtSalaryChangedTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try {
                totalSalary = Double.parseDouble(charSequence.toString());
                calculateSaving();
            } catch (NumberFormatException e) {
                totalSalary = 0.0;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private final SeekBar.OnSeekBarChangeListener seekbarSavePercentChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            savingPercent = i/100.0;
            calculateSaving();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void calculateSaving(){
        txtSavePercent.setText(percentFormatValue.format(savingPercent));

        double savedMoney = (totalSalary * savingPercent);

        txtMoneySaved.setText(currencyFormatValue.format(savedMoney));

    }

    private final TextWatcher tipEdtMoneyAmountTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try {
                billAmount = Double.parseDouble(charSequence.toString()) / 100.0;
                txtBillAmount.setText(currencyFormatValue.format(billAmount));
            } catch (NumberFormatException e){
                txtBillAmount.setText("");
                billAmount = 0.0;
            }

            calculateTip();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private final SeekBar.OnSeekBarChangeListener tipSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            tipPercent = i/100.0;
            calculateTip();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void calculateTip(){
        txtTipPercent.setText(percentFormatValue.format(tipPercent));

        double tipValue = billAmount * tipPercent;
        double totalValue = billAmount + tipValue;

        txtTip.setText(currencyFormatValue.format(tipValue));
        txtTotalBillAmount.setText(currencyFormatValue.format(totalValue));
    }
}
