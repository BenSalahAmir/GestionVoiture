package tn.esprit.gestionuser;

// UpdateOfferActivity.java

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateOfferActivity extends AppCompatActivity {

    private EditText editName, editLocation, editPrice, editDetails;
    private Button submitUpdate;
    private DatabaseHelper dbHelper;
    private int offerId; // The ID of the offer to update

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_hotel_form);

        dbHelper = new DatabaseHelper(this);

        // Bind the EditTexts and Button
        editName = findViewById(R.id.edittext_name);
        editDetails = findViewById(R.id.edittext_details);
        editPrice = findViewById(R.id.edittext_price);
        // Initialize other EditTexts: editLocation, editPrice, editDetails

        submitUpdate = findViewById(R.id.button_submit);

        // Retrieve and display the current offer details
        offerId = getIntent().getIntExtra("offer_id", -1);
        if(offerId != -1) {
            // Query the offer details from the database and display them
            // Offer currentOffer = dbHelper.getOffer(offerId);
            // editName.setText(currentOffer.getName());
            // ... set other fields ...
        }

        submitUpdate.setOnClickListener(v -> {
            String name = editName.getText().toString();
            String location = editLocation.getText().toString();
            String details = editDetails.getText().toString();
            float price = 0f;

            try {
                price = Float.parseFloat(editPrice.getText().toString().trim());
            } catch (NumberFormatException e) {
                Toast.makeText(UpdateOfferActivity.this, "Please enter a valid price", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d("UpdateOffer", "Attempting to update offer ID: " + offerId); // Logging

            boolean updated = dbHelper.updateOffer(offerId, name, location, price, details);
            if (updated) {
                Log.d("UpdateOffer", "Offer update successful."); // Logging
                Toast.makeText(UpdateOfferActivity.this, "Offer updated successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Log.d("UpdateOffer", "Offer update failed."); // Logging
                Toast.makeText(UpdateOfferActivity.this, "Update failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
