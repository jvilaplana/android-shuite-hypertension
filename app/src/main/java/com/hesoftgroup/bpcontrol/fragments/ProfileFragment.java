package com.hesoftgroup.bpcontrol.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.hesoftgroup.bpcontrol.HomeActivity;
import com.hesoftgroup.bpcontrol.R;
import com.hesoftgroup.bpcontrol.controllers.LateralMenuController;
import com.hesoftgroup.bpcontrol.customviews.RobotoTextView;
import com.hesoftgroup.bpcontrol.model.User;

/**
 * Created by Adrian on 09/02/2015.
 */
public class ProfileFragment extends Fragment {

    private ViewGroup viewGroup;

    public static ProfileFragment getNewInstance() {

        ProfileFragment perfilFragment = new ProfileFragment();
        return perfilFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =  inflater.inflate(R.layout.perfilfragment, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.perfilphoto);
        ((HomeActivity) getActivity()).getApplicationContext().loadPerfilImageView(User.getInstance().getUUID(),imageView,0);
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
        birthDate.setText(turnDate(User.getInstance().getBirthDate()));

        RobotoTextView lastUpdate = (RobotoTextView) viewGroup.getChildAt(9);
        String[] lastupdate = User.getInstance().getLastUpdate().split(" ");
        lastUpdate.setText(turnDate(lastupdate[0])+" "+lastupdate[1]);

        RobotoTextView town = (RobotoTextView) viewGroup.getChildAt(11);
        town.setText(User.getInstance().getTown());

        RobotoTextView mobileNumber = (RobotoTextView) viewGroup.getChildAt(13);
        mobileNumber.setText(User.getInstance().getMobileNumber());

        RobotoTextView dateCreated = (RobotoTextView) viewGroup.getChildAt(15);
        dateCreated.setText(turnDate(User.getInstance().getCreationDate()));

        Button contact = (Button) viewGroup.getChildAt(17);

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).selectMenuItem(LateralMenuController.MenuSections.CONTACT);
            }
        });

    }

    private String turnDate(String date){

        if (date != null && !date.equals("")){
            String[] array = date.split("-");
            return array[2]+"-"+array[1]+"-"+array[0];
        }
        return "";
    }


}
