package sse.com.moneyalertsse.menu.parametre;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import sse.com.moneyalertsse.Menu_principale;
import sse.com.moneyalertsse.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParametreFragment extends PreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new HelpFragment()).commit();

    }


     // cette methode permet de revenir au menu principale

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent(getApplicationContext(), Menu_principale.class);

            startActivity(intent);

            finish();
        }

        return super.onKeyDown(keyCode, event);
    }
}
