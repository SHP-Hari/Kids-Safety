package lk.applife.kidssafety.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import lk.applife.kidssafety.R;

public class HomeActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CALL_PHONE = 0;
    private final int REQ_CODE = 100;
    private static final String HOME_SAFETY = "HOME_SAFETY";
    private static final String SCHOOL_SAFETY = "SCHOOL_SAFETY";
    private static final String ROAD_SAFETY = "ROAD_SAFETY";
    CardView homeSafetyCard, schoolSafetyCard, roadSafetyCard;
    AlertDialog alertDialog;
    LinearLayout logoLayout;
    SharedPreferences userSubscribedInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_home);

        homeSafetyCard = (CardView) findViewById(R.id.homeSafetyCard);
        schoolSafetyCard = (CardView) findViewById(R.id.schoolSafetyCard);
        roadSafetyCard = (CardView) findViewById(R.id.roadSafetyCard);
        logoLayout = (LinearLayout) findViewById(R.id.logoLayout);
        alertDialog = new AlertDialog.Builder(this).create();
        userSubscribedInformation = this.getSharedPreferences("userSubscribedInformation", Context.MODE_PRIVATE);

        homeSafetyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ArticlesActivity.class);
                intent.putExtra("safety", HOME_SAFETY);
                startActivity(intent);
            }
        });

        schoolSafetyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ArticlesActivity.class);
                intent.putExtra("safety", SCHOOL_SAFETY);
                startActivity(intent);
            }
        });

        roadSafetyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ArticlesActivity.class);
                intent.putExtra("safety", ROAD_SAFETY);
                startActivity(intent);
            }
        });
    }

    public void connectToApplication(View view) {
        proccedToApplication();
    }

    private void proccedToApplication() {
        boolean status = userSubscribedInformation.getBoolean("subscribedStatus", false);
        if (status){
            dialToApp();
        }else {
            openConnectToAppDialog();
        }
    }

    private void dialToApp() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startDialingToApp();
        } else {
            requestCallPermission();
        }
    }

    @SuppressLint("MissingPermission")
    private void startDialingToApp() {
        String code = getResources().getString(R.string.app_conneting_ussd);
        String encodedHash = Uri.encode("#");
        String ussd = encodedHash + code + encodedHash;
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ussd));
        startActivityForResult(intent, REQ_CODE);
    }

    private void openConnectToAppDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_card, null);

        // Set the custom layout as alert dialog view
        alertDialog.setView(dialogView);

        // Get the custom alert dialog view widgets reference
        Button btn_positive = dialogView.findViewById(R.id.dialog_positive_btn);
        Button btn_negative = dialogView.findViewById(R.id.dialog_neutral_btn);
        TextView title = dialogView.findViewById(R.id.dialog_titile);
        TextView dialog_tv = dialogView.findViewById(R.id.dialog_tv);
        TextView dialog_descriptive = dialogView.findViewById(R.id.dialog_descriptive);
        dialog_descriptive.setVisibility(View.VISIBLE);

        title.setText(R.string.registerAppTitle);
        dialog_tv.setText(R.string.registerAppMessage);
        dialog_descriptive.setText(R.string.registerAppDescriptive);
        btn_positive.setText(R.string.registerAppPositive);
        btn_negative.setText(R.string.registerAppNegative);


        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();
                dialCode();
            }
        });

        btn_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void dialCode() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startDialing();
        } else {
            requestCallPermission();
        }
    }

    @SuppressLint("MissingPermission")
    private void startDialing() {
        String code = getResources().getString(R.string.app_registering_ussd);
        String encodedHash = Uri.encode("#");
        String ussd = encodedHash + code + encodedHash;
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ussd));
        startActivityForResult(intent, REQ_CODE);
        SharedPreferences.Editor editor = userSubscribedInformation.edit();
        editor.putBoolean("subscribedStatus", true);
        editor.commit();
    }

    public void requestCallPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CALL_PHONE)) {
            Snackbar.make(logoLayout, R.string.call_access_required, Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CALL_PHONE);
                }
            }).show();

        } else {
            Snackbar.make(logoLayout, R.string.call_unavailable, Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CALL_PHONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CALL_PHONE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDialing();
            } else {
                Snackbar.make(logoLayout, R.string.call_permission_denied, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (alertDialog != null){
            alertDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (alertDialog != null){
            alertDialog.dismiss();
        }
    }
}
