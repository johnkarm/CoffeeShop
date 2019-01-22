package com.example.android.coffeeshop;



import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.coffeeshop.R;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */


    public void increment(View view) {
        if (quantity == 100)
        {
            Toast.makeText(this,"You cannot order more than 100 coffees",
                    Toast.LENGTH_SHORT).show();

        }
        if ((quantity + 1) <= 100) {
            quantity = quantity + 1;
        }
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if ((quantity - 1) > 0) {
            quantity = quantity - 1;
        }
        displayQuantity(quantity);
    }

    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCream.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = chocolate.isChecked();
        EditText nameOfDrinker = (EditText) findViewById(R.id.name_of_coffee_drinker);
        String name = nameOfDrinker.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);


        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);
        //displayMessage(priceMessage);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, "johnkarm@umich.edu");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        startActivity(Intent.createChooser(intent, "Send Email"));

    }

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }



    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */


    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int totalPrice = quantity * 5;
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        hasWhippedCream = whippedCream.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate);
        hasChocolate = chocolate.isChecked();

        if (hasWhippedCream == true) {
            totalPrice += 1;
        }
        if (hasChocolate == true) {
            totalPrice += 2;
        }

        return totalPrice;
    }

    private String createOrderSummary(String name, int price, boolean whippedCream, boolean chocolate) {

        String Summary = "Name: " + name;
        Summary += "\nAdd Whipped Cream? " +whippedCream;
        Summary += "\nAdd Chocolate? " + chocolate;
        Summary += "\nQuantity:" + quantity;
        Summary += "\nTotal: $" + price;
        Summary += "\nThank you!";

        return Summary;

    }

}