package net.opgenorth.phoneword;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.view.View;

import static net.opgenorth.phoneword.PhonewordUtils.formatStringAsPhoneNumber;
import static net.opgenorth.phoneword.PhonewordUtils.toNumber;

public class PhonewordViewModel extends BaseObservable {
    private final String TAG = PhonewordViewModel.class.getName();
    private boolean mIsTranslated = false;
    private String mPhoneNumber = "";
    private String mPhoneWord = "";
    private String mCallButtonText = "Call";
    private String phoneNumber;

    @Bindable
    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    @Bindable
    public String getCallButtonText() {
        return mCallButtonText;
    }

    @Bindable
    public boolean getIsTranslated() {
        return mIsTranslated;
    }

    @Bindable
    public String getPhoneWord() {
        return mPhoneWord;
    }


    public void setPhoneWord(String phoneWord) {
        mPhoneWord = phoneWord;
//        notifyPropertyChanged(net.opgenorth.phoneword.BR.phoneWord);

        mPhoneNumber = toNumber(phoneWord);
        mCallButtonText = "Call " + mPhoneNumber + "?";


        if (mPhoneNumber != null) {
            mIsTranslated = true;
//            notifyPropertyChanged(net.opgenorth.phoneword.BR.phoneNumber);
        } else {
            mIsTranslated = false;
        }

    }


}
