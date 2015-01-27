package com.android.bpcontrol.model;

import android.view.Menu;
import android.widget.ImageView;

import com.android.bpcontrol.controllers.LateralMenuController;
import com.android.bpcontrol.customViews.RobotoTextView;

/**
 * Created by Adrian Carrera on 27/1/15.
 */

public class MenuItem{

    private LateralMenuController.MenuSections id;
    private LateralMenuController.MenuItemCategory category_id;
    private boolean isCategoryHeader;
    private ImageView imageView;
    private RobotoTextView textView;


    private MenuItem(Builder builder){

        this.id = builder.id;
        this.category_id = builder.category_id;
        this.isCategoryHeader = builder.isCategoryHeader;
        this.textView = builder.textView;
        this.imageView = builder.imageView;

    }

    public static class Builder{

        private LateralMenuController.MenuSections id;
        private LateralMenuController.MenuItemCategory category_id;
        private boolean isCategoryHeader=false;
        private ImageView imageView;
        private RobotoTextView textView;


        public Builder(RobotoTextView textView){

            this.textView = textView;
        }

        public void setId(LateralMenuController.MenuSections id) {
            this.id = id;
        }

        public void setCategory_id(LateralMenuController.MenuItemCategory category_id) {
            this.category_id = category_id;
        }

        public void setTextView(RobotoTextView textView) {
            this.textView = textView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public MenuItem build(){

            if (imageView==null){

                isCategoryHeader = true;
            }

            return new MenuItem(this);
        }
    }

}
