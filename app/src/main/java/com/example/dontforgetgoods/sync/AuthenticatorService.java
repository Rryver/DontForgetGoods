package com.example.dontforgetgoods.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AuthenticatorService extends Service {

        private Authenticator mAuthenticator;

        @Override
        public void onCreate() {
            super.onCreate();
            mAuthenticator = new Authenticator(getApplicationContext());
        }

        @Override
        public IBinder onBind(Intent intent) {
            return mAuthenticator.getIBinder();
        }

}
