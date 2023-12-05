package tn.esprit.gestionuser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsHotel extends AppCompatActivity {
    private String offerName;
    private String offerLocation;
    private float offerPrice;
    private String offerDetails;
    private int offerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_hotel);

        offerId = getIntent().getIntExtra("offer_id", -1); // Get the offer ID passed from the previous activity
        offerName = getIntent().getStringExtra("offer_name");
        offerLocation = getIntent().getStringExtra("offer_location");
        offerPrice = getIntent().getFloatExtra("offer_price", 0); // Provide a default value
        offerDetails = getIntent().getStringExtra("offer_details");

        // Populate the TextViews
        TextView nameTextView = findViewById(R.id.text_view_1);
        TextView locationTextView = findViewById(R.id.text_view_2);
        TextView priceTextView = findViewById(R.id.text_view_7);
        TextView detailsTextView = findViewById(R.id.text_view_4);

        nameTextView.setText(offerName);
        locationTextView.setText(offerLocation);
        priceTextView.setText(String.format("$%.2f", offerPrice)); // Format the price to a string
        detailsTextView.setText(offerDetails);

        // Set up the update button
        Button updateButton = findViewById(R.id.button_update_offer);
        updateButton.setOnClickListener(view -> {
            Intent intent = new Intent(DetailsHotel.this, UpdateOfferActivity.class);
            intent.putExtra("offer_id", offerId); // Pass the ID to UpdateOfferActivity
            intent.putExtra("offer_name", offerName);
            intent.putExtra("offer_location", offerLocation);
            intent.putExtra("offer_price", offerPrice);
            intent.putExtra("offer_details", offerDetails);
            startActivity(intent);
        });
    }
}