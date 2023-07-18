package cn.snow.interviewapp.viewmodel;

import androidx.databinding.BaseObservable;

public class VMTest extends BaseObservable {

    private String inString;

    public String getInString() {
        return inString;
    }

    public void setInString(String inString) {
        if (inString.equals(this.inString)) return;
        this.inString = inString;
        notifyChange();
//        notifyPropertyChanged(BR.inString);
    }
}
