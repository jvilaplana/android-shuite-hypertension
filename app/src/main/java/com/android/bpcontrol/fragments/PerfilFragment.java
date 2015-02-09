package com.android.bpcontrol.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.bpcontrol.HomeActivity;
import com.android.bpcontrol.R;
import com.android.bpcontrol.customViews.RobotoTextView;
import com.android.bpcontrol.model.User;

/**
 * Created by Adrian on 09/02/2015.
 */
public class PerfilFragment extends Fragment {

    private ViewGroup viewGroup;

    public static PerfilFragment getNewInstance() {

        PerfilFragment perfilFragment = new PerfilFragment();
        return perfilFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =  inflater.inflate(R.layout.perfilfragment, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.perfilphoto);
        ((HomeActivity) getActivity()).getApplicationContext().loadPerfilImageView(User.getInstance().getUUID(),imageView);
        viewGroup = (ViewGroup) view.findViewById(R.id.profilealltextviews);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RobotoTextView completeName = (RobotoTextView)viewGroup.getChildAt(0);
        completeName.setText(getResources().getString(R.string.completename)+" "+User.getInstance().getName()+
                " "+User.getInstance().getFirstSurname()+" "+User.getInstance().getSecondSurname());

        RobotoTextView identityCard = (RobotoTextView) viewGroup.getChildAt(1);
        identityCard.setText(getResources().getString(R.string.identitycard)+" "+User.getInstance().getIdentityCard());

        RobotoTextView email = (RobotoTextView) viewGroup.getChildAt(2);
        email.setText(getResources().getString(R.string.profileemail)+ " "+ User.getInstance().getEmail());

        RobotoTextView birthDate = (RobotoTextView) viewGroup.getChildAt(3);
        birthDate.setText(getResources().getString(R.string.birthdate)+ " "+ User.getInstance().getBirthDate());

        RobotoTextView lastUpdate = (RobotoTextView) viewGroup.getChildAt(4);
        lastUpdate.setText(getResources().getString(R.string.lastupdate)+ " "+ User.getInstance().getLastUpdate());

        RobotoTextView town = (RobotoTextView) viewGroup.getChildAt(5);
        town.setText(getResources().getString(R.string.town)+ " "+ User.getInstance().getTown());

        RobotoTextView mobileNumber = (RobotoTextView) viewGroup.getChildAt(6);
        mobileNumber.setText(getResources().getString(R.string.mobileNumber)+ " "+ User.getInstance().getMobileNumber());

        RobotoTextView dateCreated = (RobotoTextView) viewGroup.getChildAt(7);
        dateCreated.setText(getResources().getString(R.string.datecreated)+ " "+ User.getInstance().getDateCreated());

    }


}
