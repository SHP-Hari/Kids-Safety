package lk.applife.kidssafety.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import lk.applife.kidssafety.R;

public class SingleArticleActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CALL_PHONE = 0;
    private final int REQ_CODE = 100;
    AlertDialog alertDialog;
    Button singleArticleBackBtn;
    TextView tvArticleTitle;
    TextView category;
    TextView article;
    ImageView categoryImg;
    LinearLayout titleLayout;
    SharedPreferences userSubscribedInformation;
    String articleTitle, artId, articleCategory;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_single_article);

        singleArticleBackBtn = (Button) findViewById(R.id.singleArticleBackBtn);
        titleLayout = (LinearLayout) findViewById(R.id.titleLayout);
        tvArticleTitle = (TextView) findViewById(R.id.tvArticleTitle);
        categoryImg = (ImageView) findViewById(R.id.categoryImg);
        article = (TextView) findViewById(R.id.tvArticleInformation);
        alertDialog = new AlertDialog.Builder(this).create();
        userSubscribedInformation = this.getSharedPreferences("userSubscribedInformation", Context.MODE_PRIVATE);

        singleArticleBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getIncomingData();
    }

    private void getIncomingData() {
        if (getIntent().hasExtra("articleTitle") && getIntent().hasExtra("artId")){
            articleTitle = getIntent().getStringExtra("articleTitle");
            artId = getIntent().getStringExtra("artId");
            articleCategory = getIntent().getStringExtra("artCategory");
            id = getIntent().getIntExtra("Id", 5);
            setData();
        }
    }

    private void setData() {
        proccedToApplication(id);
        tvArticleTitle.setText(articleTitle);
        switch (articleCategory){
            case "Home Safety" :
                categoryImg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_home));
                break;
            case "Road Safety" :
                categoryImg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_road));
                break;
            case "School Safety" :
                categoryImg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_school));
                break;
        }
        int resID = getResources().getIdentifier(artId, "string", getPackageName());
        article.setText(resID);
    }

    private void proccedToApplication(int articleId) {
        boolean status = userSubscribedInformation.getBoolean("subscribedStatus", false);
        if (!status) {
            openConnectToAppDialog(articleId);
        }
    }

    private void openConnectToAppDialog(int articleId) {
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
        dialog_descriptive.setVisibility(View.GONE);

        title.setText(R.string.registerAppTitle);
        dialog_tv.setText(R.string.registerAppMessage);
        dialog_descriptive.setText(R.string.registerAppDescriptive);
        btn_positive.setText(R.string.registerAppPositive);


        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();
                dialCode();
            }
        });

        if (articleId < 5){
            btn_negative.setText(R.string.registerAppNegative);
            btn_negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();
                }
            });
        }else {
            btn_negative.setText(R.string.back);
            btn_negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();
                    SingleArticleActivity.this.finish();
                }
            });
        }

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
        editor.apply();
    }

    public void requestCallPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CALL_PHONE)) {
            Snackbar.make(titleLayout, R.string.call_access_required, Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(SingleArticleActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CALL_PHONE);
                }
            }).show();

        } else {
            Snackbar.make(titleLayout, R.string.call_unavailable, Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CALL_PHONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CALL_PHONE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDialing();
            } else {
                Snackbar.make(titleLayout, R.string.call_permission_denied, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

}
