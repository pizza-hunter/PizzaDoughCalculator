package com.huntergreen.pizzadoughcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private double breadFlour;
    private double plainFlour;
    private double salt;
    private double yeast;
    private double oil;
    private double water;

    private int pizzaRadius;
    private int totalPizzas;

    private static final double BREADFLOUR = 75;
    private static final double PLAINFLOUR = 75;
    private static final double SALT = 4;
    private static final double YEAST = 1;
    private static final double OIL = 2;
    private static final double WATER = 100;
    private static final double NINEINCHPIZZA = 0.556;
    private static final double FIFTEENINCHPIZZA = 1.546;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSpinner();

        findViewById(R.id.calcbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText pizzas = findViewById(R.id.editPizzaTotal);

                if(totalPizzasSelected() && pizzaRadiusSelected()) {
                    calculate();
                    updateResults();
                    pizzas.onEditorAction(EditorInfo.IME_ACTION_DONE);
                }
                else{
                    CharSequence errorText = "Select number of Pizza's";
                    makeToast(errorText);
                    pizzas.onEditorAction(EditorInfo.IME_ACTION_DONE);
                }
            }
        });

    }

    private void makeToast(CharSequence text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context,text, duration);
        toast.show();
    }

    private boolean pizzaRadiusSelected() {
        Spinner spinner = findViewById(R.id.pizzaSizeSpinner);
        if(spinner.getSelectedItem() != null){
            return true;
        }
        //todo: notify user to select size
        return false;
    }

    private boolean totalPizzasSelected() {
        EditText pizzaTotalText = findViewById(R.id.editPizzaTotal);
        if(pizzaTotalText.getText().length() > 0) {
            totalPizzas = Integer.parseInt(pizzaTotalText.getText().toString());
            return true;
        } else {
            return false;
        }
    }

    private void updateResults() {
        TextView bfNumber = findViewById(R.id.breadflourNumber);
        TextView pfNumber = findViewById(R.id.plainflourNumber);
        TextView sNumber = findViewById(R.id.seaSaltNumber);
        TextView yNumber = findViewById(R.id.yeastNumber);
        TextView oNumber = findViewById(R.id.oilNumber);
        TextView wNumber = findViewById(R.id.waterNumber);
        
        bfNumber.setText(String.valueOf((int) breadFlour) + " g");
        pfNumber.setText(String.valueOf((int) plainFlour) + " g");
        sNumber.setText(String.valueOf((int) salt) + " g");
        yNumber.setText(String.format("%.2f", yeast));
        yNumber.setText(yNumber.getText()  + " g");
        oNumber.setText(String.valueOf((int) oil) + " ml");
        wNumber.setText(String.valueOf((int) water) + " ml");

    }

    private void calculate() {
        switch (pizzaRadius){
            case 9: {
                breadFlour = BREADFLOUR * NINEINCHPIZZA * totalPizzas;
                plainFlour = PLAINFLOUR * NINEINCHPIZZA * totalPizzas;
                salt = SALT * NINEINCHPIZZA * totalPizzas;
                yeast = YEAST * NINEINCHPIZZA * totalPizzas;
                oil = OIL * NINEINCHPIZZA * totalPizzas;
                water = WATER * NINEINCHPIZZA * totalPizzas;
                break;
            }
            case 12: {
                breadFlour = BREADFLOUR * totalPizzas;
                plainFlour = PLAINFLOUR *  totalPizzas;
                salt = SALT * totalPizzas;
                yeast = YEAST * totalPizzas;
                oil = OIL * totalPizzas;
                water = WATER * totalPizzas;
                break;
            }
            case 15: {
                breadFlour = BREADFLOUR * FIFTEENINCHPIZZA * totalPizzas;
                plainFlour = PLAINFLOUR * FIFTEENINCHPIZZA * totalPizzas;
                salt = SALT * FIFTEENINCHPIZZA * totalPizzas;
                yeast = YEAST * FIFTEENINCHPIZZA * totalPizzas;
                oil = OIL * FIFTEENINCHPIZZA * totalPizzas;
                water = WATER * FIFTEENINCHPIZZA * totalPizzas;
                break;
            }
        }
    }

    private void setSpinner(){
        Spinner spinner = findViewById(R.id.pizzaSizeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.pizzaSizes,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        pizzaRadius = Integer.parseInt(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

       /*
    Area of a circle = pi R ^2
    9 inch = 254.47
    12 inch = 457.2
    15 inch = 706.86

    Ratios
    9"/12" = 0.556
    15"/12" = 1.546

     */

    /*
    Ingredients for one 12 inch pizza
    Bread flour (or 00)      75g
    Plain flour              75g
    Sea salt                  4g, 1/2 teaspoon
    Active dry yeast          1g
    Olive oil                 2g, 1/2 teaspoon
    Lukewarm tap water      100g
     */

    /*
    App will make 9, 12 and 15" pizza doughs

    Inputs: pizza size - Inches, number of pizzas - #

    Outputs: ingredient proportions

     */
}
