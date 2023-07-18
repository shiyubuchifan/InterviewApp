package cn.snow.interviewapp.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.Tag;
import android.os.Parcelable;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import cn.snow.interviewapp.R;
import cn.snow.interviewapp.base.BaseActivity;
import cn.snow.interviewapp.databinding.ActivityFragmentBinding;
import cn.snow.interviewapp.utils.LogUtil;

public class ActivityFragment extends BaseActivity<ActivityFragmentBinding> implements NfcAdapter.CreateNdefMessageCallback {

    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_fragment;
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        return null;
    }

    @Override
    protected void initView() {

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            finish();
            return;
        }
        // Register callback
        nfcAdapter.setNdefPushMessageCallback(this, this);

        xxx(getIntent());
    }

    @Override
    protected void onStart() {
        super.onStart();
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }


        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            xxx(getIntent());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        nfcAdapter = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);

        LogUtil.d("onNewIntent Start");

        xxx(intent);
    }

    public void xxx(Intent intent) {

        if (intent == null) return;

        Parcelable[] rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawMessages != null) {
            NdefMessage[] messages = new NdefMessage[rawMessages.length];
            for (int i = 0; i < rawMessages.length; i++) {
                messages[i] = (NdefMessage) rawMessages[i];
            }

        }

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        if (tag == null) return;

        String cardID = byteToHexString(tag.getId());

        byte[] idByte = cardID.getBytes();
        System.out.print("Snow Log after byte:");
        for (int i = 0; i < idByte.length; i++) {
            System.out.print(idByte[i]);
        }

        Parcelable[] rawArray = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        StringBuilder stringBuilder = new StringBuilder();
        if (rawArray != null) {
            for (int i = 0; i < rawArray.length; i++) {
                NdefMessage ndefMessage = (NdefMessage) rawArray[i];

                for (int j = 0; j < ndefMessage.getRecords().length; j++) {
                    NdefRecord ndefRecord = ndefMessage.getRecords()[j];

                    if (ndefRecord != null) {
                        String record = null;
                        try {
                            record = new String(ndefRecord.getPayload(), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            record = "byte编码UTF-8转换成String失败";
                        }
                        stringBuilder.append(record).append("\r\n");
                    }
                }
            }
        }

        LogUtil.e("onNewIntent: " + cardID + "|" + stringBuilder.toString() + "|" + Arrays.toString(idByte) + "|" + Arrays.toString(tag.getId()));
    }

    //    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.fcv);
//        LogUtil.d("onSupportNavigateUp");
//        return navController.navigateUp();
//    }

    public String byteToHexString(byte[] data) {

        System.out.print("Snow Log before byte:");
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i]);
        }

        String hexStr = "";
        String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        int j, in;

        for (int i = 0; i < data.length; i++) {
            in = (int) data[i] & 0xff;
            j = (in >> 4) & 0x0f;
            hexStr += hex[j];
            j = in & 0x0f;
            hexStr += hex[j];
        }

        return hexStr;
    }

}