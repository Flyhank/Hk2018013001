package com.n9s.flyjet.hk2018013001;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity
{
    LocationManager lm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    public void click1(View v)
    {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(this,
                    new String[] {ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 321);
            //ActivityCompat.requestPermissions( Context context, String[] permissions, int requestCode )

            return;
        }
        else
        {
            startLoc();
        }
        //lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new MyListener());
        //if段落不須type, 完成此行再選"implement",即會出現以下class & interface, 還有MainActivity下的2個classes.....
        //執行程式後,要到手機內的設定->Apps->右上齒輪工具->App Permissions->Location->找到此App->打開權限
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 321)
        {
            if (grantResults.length > 0     //如果權限未開啟
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                //取得權限，進行檔案存取
                startLoc();
            }
            else    //如果權限已開啟
            {

            }
        }
    }

    private void startLoc()
    {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new MyListener());
    }


    class MyListener implements LocationListener //實作Interface
    {
        @Override
        public void onLocationChanged(Location location)    //location:物件名
        {
            Log.d("LOC", "Position Change!!" + location.getLatitude() + "," + location.getLongitude()); //偵測是否位置變化?
            Location loc101 = new Location("LOC");  //101大樓的位置
            loc101.setLatitude(25.0336);
            loc101.setLongitude(121.5646);

            float dist = location.distanceTo(loc101);   //到101大樓的距離(m)
            Log.d("LOC", "Dist:" + dist);

            Geocoder geocoder = new Geocoder(MainActivity.this);
            try {
                List<Address> mylist = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                Address addr = mylist.get(0);
                Log.d("LOC", addr.getAddressLine(0));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }
}
