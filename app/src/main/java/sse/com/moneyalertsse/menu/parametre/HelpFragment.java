package sse.com.moneyalertsse.menu.parametre;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.util.Locale;

import sse.com.moneyalertsse.Menu_principale;
import sse.com.moneyalertsse.R;
import sse.com.moneyalertsse.baseDeDonnee.ClasseMetier.TypeDepense;
import sse.com.moneyalertsse.baseDeDonnee.ClasseMetier.TypeGain;
import sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao.TypeDepenseDao;
import sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao.TypeGainDao;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends PreferenceFragment
{

    public HelpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference);

        final EditTextPreference nouveauTypeDepense = (EditTextPreference) findPreference("nouveauTypeDepense");
        final EditTextPreference nouveauTypeGain = (EditTextPreference) findPreference("nouveauTypeGain");
        EditTextPreference suggestion = (EditTextPreference) findPreference("suggestion");
        final ListPreference langue = (ListPreference) findPreference("langue");
        final CheckBoxPreference notification = (CheckBoxPreference) findPreference("notification");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        nouveauTypeDepense.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue)
            {
                final TypeDepense typeDepense = new TypeDepense();

                typeDepense.setNomTypeDepense(newValue.toString());

                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        String message = "";

                        try
                        {
                            TypeDepenseDao typeDepenseDao = new TypeDepenseDao(getActivity().getApplicationContext());

                            typeDepenseDao.ouvrirBaseDonnee();

                            typeDepenseDao.insertion(typeDepense);

                            typeDepenseDao.fermerBaseDeDonnee();

                            message = getActivity().getApplicationContext().getResources().getString(R.string.messageReusiste);
                        }
                        catch (Exception e)
                        {
                            message = getActivity().getApplicationContext().getResources().getString(R.string.messageEchec);
                        }
                        finally
                        {
                            final String finalMessage = message;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity().getApplicationContext(), finalMessage, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                }).start();

                return true;
            }
        });

        nouveauTypeGain.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, final Object newValue)
            {

                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        final TypeGain typeGain = new TypeGain();

                        String message = "";

                        try
                        {
                            typeGain.setNomTypeGain(newValue.toString());

                            TypeGainDao typeGainDao = new TypeGainDao(getActivity().getApplicationContext());

                            typeGainDao.ouvrirBaseDonnee();

                            typeGainDao.insertion(typeGain);

                            typeGainDao.fermerBaseDeDonnee();

                            message = getActivity().getApplicationContext().getResources().getString(R.string.messageReusiste);

                        }
                        catch (Exception e)
                        {
                            message = getActivity().getApplicationContext().getResources().getString(R.string.messageEchec);
                        }
                        finally
                        {
                            final String finalMessage = message;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run()
                                {
                                    Toast.makeText(getActivity().getApplicationContext(), finalMessage, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();

                return true;
            }
        });

        suggestion.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, final Object newValue)
            {
                getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.emailSSE)} );
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestion de client pour Money Alert");
                    intent.putExtra(Intent.EXTRA_TEXT, newValue.toString());
                    startActivity(intent);
                }
            });

                return true;
            }
        });

        langue.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
        {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue)
            {
                editor.putString("langue", newValue.toString());

                Locale locale = new Locale(newValue.toString());
                Locale.setDefault(locale);

                Configuration configuration = new Configuration();
                configuration.setLocale(locale);

                getActivity().getBaseContext().getResources().updateConfiguration(configuration, getActivity().getBaseContext().getApplicationContext().getResources().getDisplayMetrics());

                onCreate(savedInstanceState);

                return true;
            }
        });

        notification.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue)
            {
                editor.putBoolean("notification", Boolean.parseBoolean(newValue.toString()));

                return true;
            }
        });

        editor.apply();

    }



}
