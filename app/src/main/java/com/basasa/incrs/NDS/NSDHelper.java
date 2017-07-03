package com.basasa.incrs.NDS;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.net.InetAddress;

/**
 * Created by basasa on 6/10/17.
 */

public class NSDHelper {
    public static final String TAG = "NSD_DISCOVER";
    public static final String SERVICE_TYPE = "_http._tcp.";
    NsdManager.DiscoveryListener mDiscoveryListener;
    NsdManager.ResolveListener mResolveListener;
    NsdManager mNsdManager;
    int port;
    Context Mcontext;
    InetAddress host;
    public NSDHelper(Context context) {
        Mcontext = context;
        mNsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
    }
   public void intialised() {
       initializeResolveListener();
       initializeDiscoveryListener();
       mNsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener);
   }


    public void initializeDiscoveryListener() {
        mDiscoveryListener = new NsdManager.DiscoveryListener() {

            @Override
            public void onDiscoveryStarted(String regType) {
                Log.v(TAG, "onDiscoveryStarted Service discovery started");
            }

            @Override
            public void onServiceFound(NsdServiceInfo service) {
                if (!service.getServiceType().equals(SERVICE_TYPE)) {
                    Log.v(TAG, "onServiceFound Unknown Service Type: " + service.getServiceType());
                } else {
                    Log.v(TAG, "onServiceFound Known Service Type: " + service.getServiceType());
                    mNsdManager.resolveService(service, mResolveListener);
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo service) {
                Log.e(TAG, "service lost" + service);
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Log.i(TAG, "Discovery stopped: " + serviceType);
            }

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                mNsdManager.stopServiceDiscovery(this);
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                mNsdManager.stopServiceDiscovery(this);
            }
        };
    }


    public void initializeResolveListener() {
        mResolveListener = new NsdManager.ResolveListener() {

            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                Log.e(TAG, "onResolveFailed Resolve failed" + errorCode);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.v(TAG, "onServiceResolved Resolve Succeeded. " + serviceInfo);
                port = serviceInfo.getPort();
                host = serviceInfo.getHost();
                Toast.makeText(Mcontext,"DOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO",Toast.LENGTH_LONG).show();
            }
        };
    }
}
