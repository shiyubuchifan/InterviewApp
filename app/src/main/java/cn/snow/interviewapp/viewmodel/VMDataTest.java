package cn.snow.interviewapp.viewmodel;

import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VMDataTest extends ViewModel implements Observable {

    PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();


    MutableLiveData<String> mutableLiveData = new MutableLiveData<>();



    public MutableLiveData<String> getMutableLiveData() {
        return mutableLiveData;
    }

    public void setMutableLiveData(String s) {
        this.mutableLiveData.postValue(s);//子线程post
//        this.mutableLiveData.setValue(s);//主线程set
    }

    private String dataString;

    public VMDataTest() {
        super();
    }

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void notifyDataChange() {
        propertyChangeRegistry.notifyCallbacks(this, 0, null);
    }

    public void notifyPropertyChanged(int id) {
        propertyChangeRegistry.notifyCallbacks(this, id, null);
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        propertyChangeRegistry.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

        propertyChangeRegistry.remove(callback);
    }
}
