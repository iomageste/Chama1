package com.example.eduar.gcm;

import android.content.Intent;

import com.example.eduar.registrationService.RegistrationIntentService;
import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by igorm on 28/07/2016.
 */
public class MyInstanceIDListenerService extends InstanceIDListenerService {
    /**
     * Chamado se simbolo InstanceID é atualizado.
     * Isto pode ocorrer se a segurança do token anterior tinha sido comprometido.
     * Esta chamada é iniciada pelo provedor InstanceID.
     **/
    @Override
    public void onTokenRefresh() {
        // Buscar Instância ID token atualizado e notifica o servidor do nosso app de quaisquer alterações (se aplicável).
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
