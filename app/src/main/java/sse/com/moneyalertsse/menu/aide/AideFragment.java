package sse.com.moneyalertsse.menu.aide;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sse.com.moneyalertsse.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AideFragment extends Fragment
{
    // pour le menu gain

    private RelativeLayout linearLayoutMenuGainAide = null;
    private RelativeLayout linearLayoutEnteteEnregistrementGainAide = null;
    private RelativeLayout linearLayoutEnteteVisualisationGainAide = null;

    private ImageView imageViewIndicateurMenuGainAide = null;
    private ImageView imageViewIndicateurEnregistrementGainAide = null;
    private ImageView imageViewIndicateurVisualisationGainAide = null;

    private TextView textViewEnregistrementMenuGainAide = null;
    private TextView textViewVisualisationMenuGainAide = null;

    private RelativeLayout relativeLayoutCorpsMenuGainAide = null;

    // pour le menu depense

    private RelativeLayout linearLayoutMenuDepenseAide = null;
    private RelativeLayout linearLayoutEnteteEnregistrementDepenseAide = null;
    private RelativeLayout linearLayoutEnteteVisualisationDepenseAide = null;

    private ImageView imageViewIndicateurMenuDepenseAide = null;
    private ImageView imageViewIndicateurEnregistrementDepenseAide = null;
    private ImageView imageViewIndicateurVisualisationDepenseAide = null;

    private TextView textViewEnregistrementMenuDepenseAide = null;
    private TextView textViewVisualisationMenuDepenseAide = null;

    private RelativeLayout relativeLayoutCorpsMenuDepenseAide = null;

    // pour le menu Statistique

    private RelativeLayout linearLayoutMenuStatistiqueAide = null;
    private RelativeLayout linearLayoutEnteteEnregistrementStatistiqueAide = null;
    private RelativeLayout linearLayoutEnteteVisualisationStatistiqueAide = null;

    private ImageView imageViewIndicateurMenuStatistiqueAide = null;
    private ImageView imageViewIndicateurEnregistrementStatistiqueAide = null;
    private ImageView imageViewIndicateurVisualisationStatistiqueAide = null;

    private TextView textViewEnregistrementMenuStatistiqueAide = null;
    private TextView textViewVisualisationMenuStatistiqueAide = null;

    private RelativeLayout relativeLayoutCorpsMenuStatistiqueAide = null;

    // pour le menu Budget

    private RelativeLayout linearLayoutMenuBudgetAide = null;
    private RelativeLayout linearLayoutEnteteEnregistrementBudgetAide = null;
    private RelativeLayout linearLayoutEnteteVisualisationBudgetAide = null;

    private ImageView imageViewIndicateurMenuBudgetAide = null;
    private ImageView imageViewIndicateurEnregistrementBudgetAide = null;
    private ImageView imageViewIndicateurVisualisationBudgetAide = null;

    private TextView textViewEnregistrementMenuBudgetAide = null;
    private TextView textViewVisualisationMenuBudgetAide = null;

    private RelativeLayout relativeLayoutCorpsMenuBudgetAide = null;


    public AideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aide, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

            // deserialisation du menu gain

            linearLayoutMenuGainAide = (RelativeLayout) view.findViewById(R.id.idEnteteMenuGainAide);
            linearLayoutEnteteEnregistrementGainAide = (RelativeLayout) view.findViewById(R.id.idEnteteEnregistrementMenuGainAide);
            linearLayoutEnteteVisualisationGainAide = (RelativeLayout) view.findViewById(R.id.idEnteteVisualisationMenuGainAide);

            relativeLayoutCorpsMenuGainAide = (RelativeLayout) view.findViewById(R.id.idRelativeCorpsMenuGainAide);

            textViewEnregistrementMenuGainAide = (TextView) view.findViewById(R.id.idTexteEnregistrementMenuGainAide);
            textViewVisualisationMenuGainAide = (TextView) view.findViewById(R.id.idTexteVisualisationMenuGainAide);

            imageViewIndicateurMenuGainAide = (ImageView) view.findViewById(R.id.idIndicateurMenuGainAide);
            imageViewIndicateurEnregistrementGainAide = (ImageView) view.findViewById(R.id.idIndicateurEnregistrementGainAide);
            imageViewIndicateurVisualisationGainAide = view.findViewById(R.id.idIndicateurVisualisationGainAide);

        // deserialisation du menu Depense

            linearLayoutMenuDepenseAide = (RelativeLayout) view.findViewById(R.id.idEnteteMenuDepenseAide);
            linearLayoutEnteteEnregistrementDepenseAide = (RelativeLayout) view.findViewById(R.id.idEnteteEnregistrementMenuDepenseAide);
            linearLayoutEnteteVisualisationDepenseAide = (RelativeLayout) view.findViewById(R.id.idEnteteVisualisationMenuDepenseAide);

            relativeLayoutCorpsMenuDepenseAide = (RelativeLayout) view.findViewById(R.id.idRelativeCorpsMenuDepenseAide);

            textViewEnregistrementMenuDepenseAide = (TextView) view.findViewById(R.id.idTexteEnregistrementMenuDepenseAide);
            textViewVisualisationMenuDepenseAide = (TextView) view.findViewById(R.id.idTexteVisualisationMenuDepenseAide);

            imageViewIndicateurMenuDepenseAide = (ImageView) view.findViewById(R.id.idIndicateurMenuDepenseAide);
            imageViewIndicateurEnregistrementDepenseAide = (ImageView) view.findViewById(R.id.idIndicateurEnregistrementDepenseAide);
            imageViewIndicateurVisualisationDepenseAide = view.findViewById(R.id.idIndicateurVisualisationDepenseAide);

            // deserialisation du menu Statistique

            linearLayoutMenuStatistiqueAide = (RelativeLayout) view.findViewById(R.id.idEnteteMenuStatistiqueAide);
            linearLayoutEnteteEnregistrementStatistiqueAide = (RelativeLayout) view.findViewById(R.id.idEnteteEnregistrementMenuStatistiqueAide);
            linearLayoutEnteteVisualisationStatistiqueAide = (RelativeLayout) view.findViewById(R.id.idEnteteVisualisationMenuStatistiqueAide);

            relativeLayoutCorpsMenuStatistiqueAide = (RelativeLayout) view.findViewById(R.id.idRelativeCorpsMenuStatistiqueAide);

            textViewEnregistrementMenuStatistiqueAide = (TextView) view.findViewById(R.id.idTexteEnregistrementMenuStatistiqueAide);
            textViewVisualisationMenuStatistiqueAide = (TextView) view.findViewById(R.id.idTexteVisualisationMenuStatistiqueAide);

            imageViewIndicateurMenuStatistiqueAide = (ImageView) view.findViewById(R.id.idIndicateurMenuStatistiqueAide);
            imageViewIndicateurEnregistrementStatistiqueAide = (ImageView) view.findViewById(R.id.idIndicateurEnregistrementStatistiqueAide);
            imageViewIndicateurVisualisationStatistiqueAide = view.findViewById(R.id.idIndicateurVisualisationStatistiqueAide);

            // deserialisation du menu Budget

            linearLayoutMenuBudgetAide = (RelativeLayout) view.findViewById(R.id.idEnteteMenuBudgetAide);
            linearLayoutEnteteEnregistrementBudgetAide = (RelativeLayout) view.findViewById(R.id.idEnteteEnregistrementMenuBudgetAide);
            linearLayoutEnteteVisualisationBudgetAide = (RelativeLayout) view.findViewById(R.id.idEnteteVisualisationMenuBudgetAide);

            relativeLayoutCorpsMenuBudgetAide = (RelativeLayout) view.findViewById(R.id.idRelativeCorpsMenuBudgetAide);

            textViewEnregistrementMenuBudgetAide = (TextView) view.findViewById(R.id.idTexteEnregistrementMenuBudgetAide);
            textViewVisualisationMenuBudgetAide = (TextView) view.findViewById(R.id.idTexteVisualisationMenuBudgetAide);

            imageViewIndicateurMenuBudgetAide = (ImageView) view.findViewById(R.id.idIndicateurMenuBudgetAide);
            imageViewIndicateurEnregistrementBudgetAide = (ImageView) view.findViewById(R.id.idIndicateurEnregistrementBudgetAide);
            imageViewIndicateurVisualisationBudgetAide = view.findViewById(R.id.idIndicateurVisualisationBudgetAide);


        // gestion des clics sur les differents elements

        /**
         * gestion des clics pour expliquer le fonctionnement du menu Gain
          */

        linearLayoutMenuGainAide.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                   if ( relativeLayoutCorpsMenuGainAide.getVisibility() == View.GONE )
                   {
                       relativeLayoutCorpsMenuGainAide.setVisibility(View.VISIBLE);
                       imageViewIndicateurMenuGainAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_haut) );
                   }
                   else
                   {
                       relativeLayoutCorpsMenuGainAide.setVisibility(View.GONE);
                       imageViewIndicateurMenuGainAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_bas) );
                   }
                }
            });


        linearLayoutEnteteEnregistrementGainAide.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if ( textViewEnregistrementMenuGainAide.getVisibility() == View.GONE )
                {
                    textViewEnregistrementMenuGainAide.setVisibility(View.VISIBLE);
                    imageViewIndicateurEnregistrementGainAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_haut) );
                }
                else
                {
                    textViewEnregistrementMenuGainAide.setVisibility(View.GONE);
                    imageViewIndicateurEnregistrementGainAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_bas) );

                }
            }
        });


        linearLayoutEnteteVisualisationGainAide.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if ( textViewVisualisationMenuGainAide.getVisibility() == View.GONE )
                {
                    textViewVisualisationMenuGainAide.setVisibility(View.VISIBLE);
                    imageViewIndicateurVisualisationGainAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_haut) );
                }
                else
                {
                    textViewVisualisationMenuGainAide.setVisibility(View.GONE);
                    imageViewIndicateurVisualisationGainAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_bas) );

                }
            }
        });


        /**
         * gestion des clics pour expliquer le fonctionnement du menu Depense
         */

        linearLayoutMenuDepenseAide.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if ( relativeLayoutCorpsMenuDepenseAide.getVisibility() == View.GONE )
                {
                    relativeLayoutCorpsMenuDepenseAide.setVisibility(View.VISIBLE);
                    imageViewIndicateurMenuDepenseAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_haut) );
                }
                else
                {
                    relativeLayoutCorpsMenuDepenseAide.setVisibility(View.GONE);
                    imageViewIndicateurMenuDepenseAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_bas) );

                }
            }
        });


        linearLayoutEnteteEnregistrementDepenseAide.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if ( textViewEnregistrementMenuDepenseAide.getVisibility() == View.GONE )
                {
                    textViewEnregistrementMenuDepenseAide.setVisibility(View.VISIBLE);
                    imageViewIndicateurEnregistrementDepenseAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_haut) );
                }
                else
                {
                    textViewEnregistrementMenuDepenseAide.setVisibility(View.GONE);
                    imageViewIndicateurEnregistrementDepenseAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_bas) );

                }
            }
        });


        linearLayoutEnteteVisualisationDepenseAide.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if ( textViewVisualisationMenuDepenseAide.getVisibility() == View.GONE )
                {
                    textViewVisualisationMenuDepenseAide.setVisibility(View.VISIBLE);
                    imageViewIndicateurVisualisationDepenseAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_haut) );
                }
                else
                {
                    textViewVisualisationMenuDepenseAide.setVisibility(View.GONE);
                    imageViewIndicateurVisualisationDepenseAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_bas) );

                }
            }
        });


        /**
         * gestion des clics pour expliquer le fonctionnement du menu Statistique
         */

        linearLayoutMenuStatistiqueAide.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if ( relativeLayoutCorpsMenuStatistiqueAide.getVisibility() == View.GONE )
                {
                    relativeLayoutCorpsMenuStatistiqueAide.setVisibility(View.VISIBLE);
                    imageViewIndicateurMenuStatistiqueAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_haut) );
                }
                else
                {
                    relativeLayoutCorpsMenuStatistiqueAide.setVisibility(View.GONE);
                    imageViewIndicateurMenuStatistiqueAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_bas) );
                }
            }
        });


        linearLayoutEnteteEnregistrementStatistiqueAide.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if ( textViewEnregistrementMenuStatistiqueAide.getVisibility() == View.GONE )
                {
                    textViewEnregistrementMenuStatistiqueAide.setVisibility(View.VISIBLE);
                    imageViewIndicateurEnregistrementStatistiqueAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_haut) );
                }
                else
                {
                    textViewEnregistrementMenuStatistiqueAide.setVisibility(View.GONE);
                    imageViewIndicateurEnregistrementStatistiqueAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_bas) );

                }
            }
        });


        linearLayoutEnteteVisualisationStatistiqueAide.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if ( textViewVisualisationMenuStatistiqueAide.getVisibility() == View.GONE )
                {
                    textViewVisualisationMenuStatistiqueAide.setVisibility(View.VISIBLE);
                    imageViewIndicateurVisualisationStatistiqueAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_haut) );
                }
                else
                {
                    textViewVisualisationMenuStatistiqueAide.setVisibility(View.GONE);
                    imageViewIndicateurVisualisationStatistiqueAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_bas) );

                }
            }
        });

        /**
         * gestion des clics pour expliquer le fonctionnement du menu Budget
         */

        linearLayoutMenuBudgetAide.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if ( relativeLayoutCorpsMenuBudgetAide.getVisibility() == View.GONE )
                {
                    relativeLayoutCorpsMenuBudgetAide.setVisibility(View.VISIBLE);
                    imageViewIndicateurMenuBudgetAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_haut) );
                }
                else
                {
                    relativeLayoutCorpsMenuBudgetAide.setVisibility(View.GONE);
                    imageViewIndicateurMenuBudgetAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_bas) );
                }
            }
        });


        linearLayoutEnteteEnregistrementBudgetAide.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if ( textViewEnregistrementMenuBudgetAide.getVisibility() == View.GONE )
                {
                    textViewEnregistrementMenuBudgetAide.setVisibility(View.VISIBLE);
                    imageViewIndicateurEnregistrementBudgetAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_haut) );
                }
                else
                {
                    textViewEnregistrementMenuBudgetAide.setVisibility(View.GONE);
                    imageViewIndicateurEnregistrementBudgetAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_bas) );

                }
            }
        });


        linearLayoutEnteteVisualisationBudgetAide.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if ( textViewVisualisationMenuBudgetAide.getVisibility() == View.GONE )
                {
                    textViewVisualisationMenuBudgetAide.setVisibility(View.VISIBLE);
                    imageViewIndicateurVisualisationBudgetAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_haut) );
                }
                else
                {
                    textViewVisualisationMenuBudgetAide.setVisibility(View.GONE);
                    imageViewIndicateurVisualisationBudgetAide.setImageDrawable( getResources().getDrawable(R.drawable.indicateur_bas) );

                }
            }
        });


    }
}
