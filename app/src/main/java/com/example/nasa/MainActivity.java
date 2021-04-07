package com.example.nasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    DatePickerDialog.OnDateSetListener mDateListener;

    StringBuilder datee ;
    NetworkUtils networkUtils;
    Call<photoNasa> populartApiCall;
    ImageView imageView;
    private ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       imageView = findViewById(R.id.image_nasa);
        networkUtils = new NetworkUtils(getApplicationContext());

      //  dialog = new ProgressDialog(this);
        progressBar =findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);

        mDateListener =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                Log.d( "onDateSet" , month + "/" + day + "/" + year );

                datee =new StringBuilder().append( year ).append( "-" )
                        .append( month ).append( "-" ).append( day );
                Log.e("tt",datee.toString());
                LoadingData(String.valueOf(datee));

            }
        };

         CardView linearLayout=findViewById(R.id.linear_bottom_sheet);
        textView= linearLayout.findViewById(R.id.text_explain);

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        bottomSheetBehavior.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()));


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() ==R.id.dateAction){
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog =  new DatePickerDialog(
                    MainActivity.this,
                    android.R.style.Theme_Material_Dialog,
                    mDateListener,
                    year,month,day
            );

            dialog.show();

        }
        return super.onOptionsItemSelected(item);
    }
    public void LoadingData(String d) {
        progressBar.setVisibility(View.VISIBLE);

        populartApiCall = networkUtils.getApiInterface().getphoto("4jcgpz8frivFffrj5acGAkMgcbyqaGs2uijh9v4P",d);
        populartApiCall.enqueue(new Callback<photoNasa>() {
            @Override
            public void onResponse( Call<photoNasa> call,  Response<photoNasa> response) {
                System.out.println(response);

                if (response.isSuccessful() && response.body() != null) {
                    Log.e("hh",response.toString());
                   String responsurl = response.body().getUrl();
//                  Glide.with(imageView).load(responsurl).error(R.drawable.images)
//                          .into(imageView);


                    Picasso.get()
                            .load(responsurl)
//                            .resize(50, 50)
//                            .centerCrop()
                            .into(imageView);
                 textView.setText( response.body().getExplanation());
                    progressBar.setVisibility(View.INVISIBLE);

                }

            }

            @Override
            public void onFailure(Call<photoNasa> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);

            }
        });
    }

}