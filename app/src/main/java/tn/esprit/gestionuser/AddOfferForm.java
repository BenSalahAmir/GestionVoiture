package tn.esprit.gestionuser;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddOfferForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_hotel_form);

        Button buttonSubmit = findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(view -> {
            // Retrieve the EditText fields and CheckBoxes
            EditText editTextName = findViewById(R.id.edittext_name);
            EditText editTextDetails = findViewById(R.id.edittext_details);
            EditText editTextPrice = findViewById(R.id.edittext_price);
            CheckBox checkboxOption1 = findViewById(R.id.checkbox_option1);
            CheckBox checkboxOption2 = findViewById(R.id.checkbox_option2);
            CheckBox checkboxOption3 = findViewById(R.id.checkbox_option3);
            CheckBox checkboxOption4 = findViewById(R.id.checkbox_option4);

            // Validate and collect the data entered
            String name = editTextName.getText().toString().trim();
            String details = editTextDetails.getText().toString().trim();
            float price;
            try {
                price = Float.parseFloat(editTextPrice.getText().toString().trim());
            } catch (NumberFormatException e) {
                Toast.makeText(AddOfferForm.this, "Please enter a valid price", Toast.LENGTH_SHORT).show();
                return;
            }

            // Add offer logic here, use UserDatabaseHelper to add to the database
            DatabaseHelper dbHelper = new DatabaseHelper(AddOfferForm.this);
            boolean success = dbHelper.addOffer2(name, details, price);

            // Check if add was successful and return accordingly
            if (success) {
                Toast.makeText(AddOfferForm.this, "Service added successfully!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(AddOfferForm.this, "Failed to add Service. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
