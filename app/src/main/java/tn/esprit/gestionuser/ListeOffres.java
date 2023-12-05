package tn.esprit.gestionuser;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
public class ListeOffres extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_OFFER = 1; // Request code for starting AddOfferForm activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offre);
        displayOffers();

        final Button buttonOpenForm = findViewById(R.id.button_open_form);

        buttonOpenForm.setOnClickListener(v -> {
            Intent intent = new Intent(ListeOffres.this, AddOfferForm.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_OFFER); // Start AddOfferForm Activity for result
        });
    }

    private void displayOffers() {
        DatabaseHelper dbHelper = new DatabaseHelper(ListeOffres.this);
        ArrayList<Service> services = dbHelper.getAllOffers2();

        LinearLayout offerContainer = findViewById(R.id.offerContainer);
        offerContainer.removeAllViews(); // Clear existing views

        // Inflate the layout for the static elements (rectangle and button)
        View headerView = getLayoutInflater().inflate(R.layout.activity_offre, null);
        offerContainer.addView(headerView);

        for (Service service : services) {
            // Inflate the offer card layout for each offer
            View offerCardView = getLayoutInflater().inflate(R.layout.offer_layout, null);

            // Set offer details
            //ImageView offerImageView = offerCardView.findViewById(R.id.imageView);
            TextView offerName = offerCardView.findViewById(R.id.offerName);
            TextView offerLocation = offerCardView.findViewById(R.id.offerLocation);
            TextView offerPrice = offerCardView.findViewById(R.id.offerPrice);

            // Set the text data
            offerName.setText(service.getName());
            offerLocation.setText(service.getLocation());
            offerPrice.setText(String.format("$%s/night", String.valueOf(service.getPrice())));
            offerPrice.setTextColor(Color.BLACK);

            // Add the inflated offer card view to the container
            offerContainer.addView(offerCardView);

            // Set a click listener for each offer card
            offerCardView.setOnClickListener(v -> {
                // Handle offer card click, if needed
                // You can access offer details using the offer object
                Toast.makeText(ListeOffres.this, "Clicked on: " + service.getName(), Toast.LENGTH_SHORT).show();
            });
            Button deleteButton = offerCardView.findViewById(R.id.deletebtn);
            deleteButton.setOnClickListener(v -> {
                // Call the delete offer method
                dbHelper.deleteOffer(service.getId());
                displayOffers(); // Refresh the offer list
            });

            // Now set the click listener for the card view, separate from the delete button
            View offerCard = offerCardView.findViewById(R.id.card_view); // Make sure you have a card view with this ID in your offer_layout
            offerCard.setOnClickListener(v -> {
                Intent intent = new Intent(ListeOffres.this, DetailsHotel.class);
                intent.putExtra("offer_id", service.getId()); // Make sure you're getting the correct ID here
                intent.putExtra("offer_name", service.getName());
                intent.putExtra("offer_location", service.getLocation());
                intent.putExtra("offer_price", service.getPrice());
                intent.putExtra("offer_details", service.getDetails());
                startActivity(intent);
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_OFFER && resultCode == RESULT_OK) {
            displayOffers(); // Refresh the list when we return from adding a new offer
        }
    }
}
