package net.opgenorth.phoneword;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

    String _translatedNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button translateButton = (Button) findViewById(R.id.translate_button);
        final Button callButton = (Button) findViewById(R.id.call_button);
        final EditText phoneNumberText = (EditText) findViewById(R.id.phone_number_text);
        Button callLogButton = (Button) findViewById(R.id.call_log_button);

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _translatedNumber = PhonewordUtils.toNumber(phoneNumberText.getText().toString());

                String callButtonText = getResources().getString(R.string.call_button_text);

                if (TextUtils.isEmpty((_translatedNumber))) {
                    callButton.setEnabled(false);
                } else {
                    callButton.setEnabled(true);
                    callButtonText = callButtonText + " " + _translatedNumber;
                }
                callButton.setText(callButtonText);
            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent callIntent = new Intent(Intent.ACTION_CALL);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder
                        .setMessage("Call " + _translatedNumber + "?")
                        .setNeutralButton(R.string.call_button_text, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                callIntent.setData(Uri.parse("tel:" + _translatedNumber));
                                PhonewordUtils.savePhoneword(MainActivity.this, _translatedNumber);
                                startActivity(callIntent);
                            }
                        })
                        .setNegativeButton(R.string.cancel_text, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Nothing to do here.
                            }
                        })
                        .show();
            }
        });

        callLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent callIntent = new Intent(MainActivity.this, CallLogActivity.class);
                startActivity(callIntent);
            }
        });
    }
}
