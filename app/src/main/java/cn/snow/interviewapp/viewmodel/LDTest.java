package cn.snow.interviewapp.viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

public class LDTest extends LiveData<String> {
    private String mData = "";

    private void LDTest() {
    }

    public static LDTest getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final LDTest INSTANCE = new LDTest();
    }

    @Override
    public void postValue(String value) {
        super.postValue(value);
        this.mData = value;
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        this.mData = value;
    }

    @Nullable
    @Override
    public String getValue() {
        return mData;
    }

    @Override
    protected void onActive() {
        super.onActive();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }


}
