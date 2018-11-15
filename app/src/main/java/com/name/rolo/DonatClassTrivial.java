package com.name.rolo;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.name.rolo.util.IabBroadcastReceiver;
import com.name.rolo.util.IabHelper;
import com.name.rolo.util.IabResult;
import com.name.rolo.util.Inventory;
import com.name.rolo.util.Purchase;

import java.util.Calendar;
import java.util.Date;

public class DonatClassTrivial extends Activity implements IabBroadcastReceiver.IabBroadcastListener
         {


    static final String SKU_SMALL = "small_donat";
    static final String SKU_MEDIUM = "medium_donat";
    static final String SKU_BIG = "big_donat";
    static final int RC_REQUEST = 10001;

             //public boolean setWaitScreen=false;

    IabHelper mHelper;

    IabBroadcastReceiver mBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donat_layout);
        TextView Chram = (TextView) findViewById(R.id.TextChram);
        ImageView ChramIm = (ImageView) findViewById(R.id.ImageChram);
        Calendar clndr = Calendar.getInstance();
        Date Now = clndr.getTime();
        int resourseId = getResources().getIdentifier("Chrams", "array", this.getPackageName());
        int ChramIdToday=Now.getMonth()%(getResources().getStringArray(resourseId).length);
        Chram.setText(getResources().getStringArray(resourseId)[ChramIdToday]);
        resourseId =  getResources().getIdentifier("chram"+String.valueOf(ChramIdToday), "drawable", this.getPackageName());
        try {
            ChramIm.setBackground(getResources().getDrawable(resourseId));
        } catch (Exception e){
            ChramIm.setVisibility(View.GONE);
        }
        Chram.setTypeface(Typeface.createFromAsset(getAssets(), MainActivity.MOLS_FONT_ADRESS));
        String Key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlcqZhdQQ7gzpPC1/fkmljf8iCeTNEHpXryCY6UsPvbP5d8LFVR/Vg7asi+p0SFrszrCyB/lItwsiUgQbweO6llO+Nyrq1Wk19/piz2ER9vrRIafVAnKF+yo1zsWaamN507mXM6WnMg1qQAV0g7tCz3/X8RVuRPIyoI31ADN1WB/swXiOBU5mGdE6IK4bxx8HyBQKVJxgj+vjjglObuqXVx1RPCUE4KBW4/OvWRsDecAJQMaMaWJQsa/E/qZnVIuND6kzvB4e5LbjHpjGQUlBMlj64qp8Tnk5djgmR9RkQus/43DKMaUa54OZireuk1E4Eyoj69tqcplHwkY/ncuS5QIDAQAB";

        ////Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(this, Key);
        mHelper.enableDebugLogging(true);
        ////Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                //Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    //Toast.makeText(getApplicationContext(), "Problem setting up in-app billing: " + result, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mHelper == null) return;
                mBroadcastReceiver = new IabBroadcastReceiver(DonatClassTrivial.this);
                IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                registerReceiver(mBroadcastReceiver, broadcastFilter);

                ////Log.d(TAG, "Setup successful. Querying inventory.");
                try {
                    mHelper.queryInventoryAsync(mGotInventoryListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    Toast.makeText(getApplicationContext(), "Error querying inventory. Another async operation in progress.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void receivedBroadcast() {
        // Received a broadcast notification that the inventory of items has changed
        //Log.d(TAG, "Received broadcast notification. Querying inventory.");
        try {
            mHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            //complain("Error querying inventory. Another async operation in progress.");
        }

    }


    public void Donat(View arg0) {
        ////Log.d(TAG, "Buy gas button clicked.");
        String payload = "a";
        String SKU=SKU_SMALL;
        if (arg0.getId()==R.id.ibSmallDonat)
            SKU = SKU_SMALL;

        if (arg0.getId()==R.id.ibMediumDonat)
            SKU = SKU_MEDIUM;

        if (arg0.getId()==R.id.ibBigDonat)
            SKU = SKU_BIG;
        setWaitScreen(true);

        try {
            mHelper.launchPurchaseFlow(this, SKU, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
           // complain("Error launching purchase flow. Another async operation in progress.");
            setWaitScreen(false);
        }
    }
    boolean verifyDeveloperPayload(Purchase p) {
       return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
             //Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
             if (mHelper == null) return;
             // Pass on the activity result to the helper for handling
             if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
                // not handled, so handle it ourselves (here's where you'd
                // perform any handling of activity results not related to in-app
                // billing...
                super.onActivityResult(requestCode, resultCode, data);
             }
             else {
                 //Log.d(TAG, "onActivityResult handled by IABUtil.");
             }
    }


             IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
                 public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                     //Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

                     // if we were disposed of in the meantime, quit.
                     if (mHelper == null) return;

                     if (result.isFailure()) {
                         complain("Error while purchasing: " + result);
                         setWaitScreen(false);
                         return;
                     }
                     if (!verifyDeveloperPayload(purchase)) {
                         complain("Error purchasing. Authenticity verification failed.");
                         setWaitScreen(false);
                         return;
                     }

                     //Log.d(TAG, "Purchase successful.");

                         // bought 1/4 tank of gas. So consume it.
                         ////Log.d(TAG, "Purchase is gas. Starting gas consumption.");
                         try {
                             mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                         } catch (IabHelper.IabAsyncInProgressException e) {
                             //complain("Error consuming gas. Another async operation in progress.");
                             setWaitScreen(false);
                         }

                 }
             };


             IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
                 public void onConsumeFinished(Purchase purchase, IabResult result) {
                     //Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

                     // if we were disposed of in the meantime, quit.
                     if (mHelper == null) return;

                     // We know this is the "gas" sku because it's the only one we consume,
                     // so we don't check which sku was consumed. If you have more than one
                     // sku, you probably should check...
                     if (result.isSuccess()) {
                         // successfully consumed, so we apply the effects of the item in our
                         // game world's logic, which in our case means filling the gas tank a bit
                         complain("Consumption successful. Provisioning.");
                     }
                     else {
                         complain("Error while consuming: " + result);
                     }
                     setWaitScreen(false);
                     ////Log.d(TAG, "End consumption flow.");
                 }
             };




             IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
                 public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
                     //Log.d(TAG, "Query inventory finished.");

                     // Have we been disposed of in the meantime? If so, quit.
                     if (mHelper == null) return;

                     // Is it a failure?
                     if (result.isFailure()) {
                         //complain("Failed to query inventory: " + result);
                         return;
                     }

                     //Log.d(TAG, "Query inventory was successful.");
                 }
             };








    public void complain(String string) {
        //Toast.makeText(getApplicationContext(), string,Toast.LENGTH_SHORT).show();
    }
    void setWaitScreen(boolean set) {
       //findViewById(R.id.screen_main).setVisibility(set ? View.GONE : View.VISIBLE);
       //findViewById(R.id.screen_wait).setVisibility(set ? View.VISIBLE : View.GONE);
    }





}
