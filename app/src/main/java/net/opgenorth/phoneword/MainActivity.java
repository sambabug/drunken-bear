package net.opgenorth.phoneword;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    String _translatedNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeToolbar();

        final EditText phoneNumberText = (EditText) findViewById(R.id.phone_number_text);
        final Button callButton = initializeCallButton();
        initializeTranslateButton(callButton, phoneNumberText);
    }

    private void initializeToolbar() {
        Toolbar tb = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(tb);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_call_log) {
            final Intent callIntent = new Intent(MainActivity.this, CallLogActivity.class);
            startActivity(callIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    private void initializeTranslateButton(final Button callButton, final EditText phoneNumberText) {
        Button translateButton = (Button) findViewById(R.id.translate_button);
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
    }

    @NonNull
    private Button initializeCallButton() {
        final Button callButton = (Button) findViewById(R.id.call_button);
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
        return callButton;
    }
}
