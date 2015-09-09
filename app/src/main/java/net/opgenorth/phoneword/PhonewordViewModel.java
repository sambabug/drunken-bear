package net.opgenorth.phoneword;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import static net.opgenorth.phoneword.PhonewordUtils.toNumber;

public class PhonewordViewModel extends BaseObservable {
    private final String TAG = PhonewordViewModel.class.getName();
    private boolean mIsTranslated = false;
    private String mPhoneNumber = "";
    private String mPhoneWord = "";
    private String mCallButtonText = "Call";

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
        mPhoneNumber = toNumber(phoneWord);

        if (TextUtils.isEmpty(mPhoneNumber)) {
            mCallButtonText = "Call";
            mIsTranslated = false;
        } else {
            mIsTranslated = true;
            mCallButtonText = "Call " + mPhoneNumber + "?";
        }
        notifyPropertyChanged(net.opgenorth.phoneword.BR.phoneNumber);
        notifyPropertyChanged(net.opgenorth.phoneword.BR.isTranslated);
        notifyPropertyChanged(net.opgenorth.phoneword.BR.callButtonText);

    }
}
