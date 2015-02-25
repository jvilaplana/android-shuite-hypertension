package com.android.bpcontrol.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.android.bpcontrol.HomeActivity;
import com.android.bpcontrol.R;
import com.android.bpcontrol.controllers.LateralMenuController;
import com.android.bpcontrol.customviews.RobotoTextView;
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

        RobotoTextView completeName = (RobotoTextView)viewGroup.getChildAt(1);
        completeName.setText(User.getInstance().getName()+" "+User.getInstance().getFirstSurname()
                +" "+User.getInstance().getSecondSurname());

        RobotoTextView identityCard = (RobotoTextView) viewGroup.getChildAt(3);
        identityCard.setText(User.getInstance().getIdentityCard());

        RobotoTextView email = (RobotoTextView) viewGroup.getChildAt(5);
        email.setText(User.getInstance().getEmail());

        RobotoTextView birthDate = (RobotoTextView) viewGroup.getChildAt(7);
        birthDate.setText(User.getInstance().getBirthDate());

        RobotoTextView lastUpdate = (RobotoTextView) viewGroup.getChildAt(9);
        lastUpdate.setText(User.getInstance().getLastUpdate());

        RobotoTextView town = (RobotoTextView) viewGroup.getChildAt(11);
        town.setText(User.getInstance().getTown());

        RobotoTextView mobileNumber = (RobotoTextView) viewGroup.getChildAt(13);
        mobileNumber.setText(User.getInstance().getMobileNumber());

        RobotoTextView dateCreated = (RobotoTextView) viewGroup.getChildAt(15);
        dateCreated.setText(User.getInstance().getCreationDate());

        Button contact = (Button) viewGroup.getChildAt(17);

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).selectMenuItem(LateralMenuController.MenuSections.CONTACT);
            }
        });

    }


}
