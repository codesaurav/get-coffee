package com.subtlyrude.justjava;

import android.content.Intent;
import android.icu.util.Currency;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

/**

 This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int numberOfCoffees=1;
    int price=5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        /**
         username
         */
        EditText customerName  = findViewById(R.id.name);
        String name = customerName.getText().toString();

        /**
         checkboxes
         */
        CheckBox whippedCream = findViewById(R.id.checkBoxWhippedCream);
        boolean hasWhippedCream = whippedCream.isChecked();

        CheckBox chocolate = findViewById(R.id.checkBoxChocolate);
        boolean hasChocolate = chocolate.isChecked();

        /**
         if else for changing the price according to the toppings
         */
        if (hasWhippedCream){
            price+=1;
        }
        else if (hasChocolate){
            price+=2;
        }
        else if(hasChocolate && hasWhippedCream){
            price+=3;
        }

        /**
         code for local currency symbol
         */
        Currency currency = Currency.getInstance(Locale.getDefault());
        String symbol = currency.getSymbol();

        /**
         order summary
         */
        String message = getString(R.string.name1)+"\t"+name+
                "\n"+getString(R.string.quantity1)+"\t"+numberOfCoffees+
                "\n"+getString(R.string.add_whipped_cream)+"\t"+hasWhippedCream+
                "\n"+getString(R.string.add_chocolate)+"\t"+hasChocolate+
                "\n"+getString(R.string.Total)+"\t"+symbol+numberOfCoffees*price;

        /**
         This is an intent for mailing the coffee order
         */
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order for "+name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        /**
         toast for thanks
         */
        Toast.makeText(this, "Thanks for Ordering!",Toast.LENGTH_SHORT).show();


    }

    /**
     this method increments the numberOfCoffees
     */

    public void increment(View view) {
        if(numberOfCoffees<=20)
            numberOfCoffees+=1;

        else Toast.makeText(this, "Quantity should not be more than 100",Toast.LENGTH_SHORT).show();

        display(numberOfCoffees);

    }

    /**
      this method decrements the numberOfCoffees
     */
    public void decrement(View view) {
        if(numberOfCoffees>1)
            numberOfCoffees-=1;

        else Toast.makeText(this, "Quantity should not be less than 1",Toast.LENGTH_SHORT).show();
        display(numberOfCoffees);

    }
    /**
     this method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}


