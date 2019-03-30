package sse.com.moneyalertsse.menu.equipe;


import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sse.com.moneyalertsse.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipeFragment extends Fragment {

    TextView instagram = null;
    TextView facebook = null;
    TextView gmail = null;
    TextView adresse = null;

    public EquipeFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_equipe, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        instagram  = (TextView) view.findViewById(R.id.idInstagram);
        facebook  = (TextView) view.findViewById(R.id.idFacebook);
        gmail  = (TextView) view.findViewById(R.id.idGmail);
        adresse = (TextView) view.findViewById(R.id.idAdresse);


        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String requete  = "https://www.instagram.com/sse912";

                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, requete );

                startActivity(intent);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String requete = "https://wwww.facebook.com/SierpinSki-Enterprise-SSE-254607595279470/?ref=br_rs";
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);

                intent.putExtra(SearchManager.QUERY, requete);

                startActivity(intent);
            }
        });

        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.emailSSE)} );
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(intent);

            }
        });

    }

}
