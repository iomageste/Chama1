package com.example.eduar.registrationService;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by igorm on 28/07/2016.
 */
public class RegistrationIntentService extends IntentService {

    private static final String TAG = RegistrationIntentService.class.getSimpleName();

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

      /*  try {
            // Inicialmente, esta chamada usa a rede para recuperar o token, chamadas subsequentes são locais.
            // R.string.gcm_defaultSenderId é derivado de google-service.json.
            // Veja https://developers.google.com/cloud-messaging/android/start para detalhes deste arquivo.
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

        } catch (Exception e) {
            sendRegistrationEvent(false, null, null, null, getString(R.string.activity_authenticator_falha_inesperada));
        }*/
    }

    private void sendRegistrationEvent(final boolean register, final String token, final String email, final String password, final String messageError) {
    }
}
