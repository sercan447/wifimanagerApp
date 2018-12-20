package sercandevops.com.wifimanagerapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    WifiManager mainWifiObj;
    WifiScanReceiver wifiReciever;
    ArrayList<WifiBilgi> wifilist;
    SercanAdapter sercanAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //API 26 VE SONRASI ICIN FINE LOCATION IZNI ALMAK GEREKİYORMUŞ BU BIRAZ UĞRAŞTIRDI.
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION }, 1);

        mainWifiObj = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiReciever = new WifiScanReceiver();

        if(!mainWifiObj.isWifiEnabled()){
            mainWifiObj.setWifiEnabled(true);
        }

        wifilist = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
         sercanAdapter = new SercanAdapter(this,wifilist);

         Listele();

    }
public void Listele()
{

    boolean sonuc = mainWifiObj.startScan();

    if(sonuc){
        Log.i("DINLE :","SCAN RUNING.. ");
    }else{
        Log.i("DINLE :","SCAN PROBLEM");
    }

    if(mainWifiObj.getScanResults().size() > 0)
    {

        for (ScanResult s : mainWifiObj.getScanResults())
        {
            Log.i("GUL", s.SSID + "-"  + s.BSSID+"-");
            String sinyalDurum = "";
            if(WifiManager.calculateSignalLevel(s.level, 5) == 5){
                sinyalDurum = "Mükemmel";
            }else if(WifiManager.calculateSignalLevel(s.level, 5) == 4 ){
                sinyalDurum = "İyi";
            }else if(WifiManager.calculateSignalLevel(s.level, 5) == 3){
                sinyalDurum = "Orta";
            }else if(WifiManager.calculateSignalLevel(s.level, 5) == 2 || WifiManager.calculateSignalLevel(s.level, 5) == 1){
                sinyalDurum = "Zayıf";
            }
            wifilist.add(new WifiBilgi(s.SSID,s.BSSID,sinyalDurum));
            sercanAdapter.notifyDataSetChanged();
        }
    }else{
        Log.i("DINLE", "BOŞ");
    }

    recyclerView.setAdapter(sercanAdapter);

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(linearLayoutManager);


}//Listele


    
    class WifiScanReceiver extends BroadcastReceiver {

        public void onReceive(Context c, Intent intent) {
            final boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED,false);


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if(success)
                    {
                        if(mainWifiObj.getScanResults().size() > 0) {
                            Log.i("DINLE", "WIFI VAR.");
                        }else{
                            Log.i("DINLE", "WIFI YOK.");
                        }
                    }else{
                        Log.i("DINLE", "FAUILERE ..");
                    }

                    wifilist.clear();

                    recyclerView.swapAdapter(sercanAdapter,false);
                    sercanAdapter.notifyDataSetChanged();

                    Listele();

                }
            },1000);
        }//ONRECEİVE
    }//  class WifiScanReceiver


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(wifiReciever);
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(wifiReciever,intentFilter);
    }

}
