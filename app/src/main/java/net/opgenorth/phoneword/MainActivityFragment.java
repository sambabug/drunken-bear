package net.opgenorth.phoneword;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import net.opgenorth.phoneword.databinding.FragmentMainBinding;

public class MainActivityFragment extends Fragment {

    private PhonewordViewModel mPhonewordViewModel;
    private Button mTranslateButton;
    private Button mCallButton;
    private EditText mPhoneWordEditText;

    public MainActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPhonewordViewModel = new PhonewordViewModel();
        FragmentMainBinding b = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        b.setPhonewordVM(mPhonewordViewModel);
        View v = b.getRoot();

        mTranslateButton = (Button) v.findViewById(R.id.translate_button);
        mCallButton = (Button) v.findViewById(R.id.call_button);
        mPhoneWordEditText = (EditText) v.findViewById(R.id.phoneword_text);

        mTranslateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhonewordViewModel.setPhoneWord(mPhoneWordEditText.getText().toString());
            }
        });

        mCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent callIntent = new Intent(Intent.ACTION_CALL);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder
                        .setMessage(mCallButton.getText())
                        .setNeutralButton(R.string.call_button_text, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                callIntent.setData(Uri.parse("tel:" + mPhonewordViewModel.getPhoneNumber()));
                                PhonewordUtils.savePhoneword(getActivity(), mPhonewordViewModel.getPhoneWord());
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

        return v;
    }
}
