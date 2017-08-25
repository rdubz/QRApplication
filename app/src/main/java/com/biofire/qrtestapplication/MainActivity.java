package com.biofire.qrtestapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import java.util.Dictionary;
import java.util.Hashtable;

import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private final int VIBRATION_LENGTH = 250;
    private static Dictionary dictionary = new Hashtable();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String completedDepartment = getIntent().getStringExtra("Department");

        if(completedDepartment != null)
        {
            //mark department as completed
            //adding a little change here
        }

        SetUpUI();
    }

    public void onClickListener(View view)
    {
        scanBarcode();
    }

    private void scanBarcode()
    {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan a QR Code");
        integrator.setBeepEnabled(false);
        integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 0)
            return;

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        vibrateDevice();
        String url = result.getContents();

        if(URLUtil.isValidUrl(url))
            OpenWebPage(url);
        else
            StartNewActivity();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    private void OpenWebPage(String url)
    {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("URL", url);
        startActivity(intent);
    }

    private void StartNewActivity()
    {
        Intent intent = new Intent(this, SomeOtherActivity.class);
        startActivity(intent);
    }

    @Override
    public void handleResult(Result result)
    {

    }

    private void vibrateDevice()
    {
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VIBRATION_LENGTH);
    }

    private void SetUpUI()
    {
        ImageView coordinates = (ImageView) findViewById(R.id.imageView);
        coordinates.setX(100);
        coordinates.setY(~25);
    }
}
