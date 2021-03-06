package com.hesoftgroup.bpcontrol.model;

import android.widget.ImageView;

import com.hesoftgroup.bpcontrol.controllers.LateralMenuController;

/**
 * Created by Adrian Carrera on 27/1/15.
 */

public class MenuItem{

    private LateralMenuController.MenuSections id;
    private LateralMenuController.MenuItemCategory category_id;
    private boolean isParentCategory;
    private boolean isImageFromResources;
    private ImageView image ;
    private int imageid;
    private String textView;


    private MenuItem(Builder builder){

        this.id = builder.id;
        this.category_id = builder.category_id;
        this.isParentCategory = builder.isParentCategory;
        this.isImageFromResources = builder.isImageFromResources;
        this.textView = builder.textView;
        this.image = builder.image;
        this.imageid = builder.imageid;

    }

    public LateralMenuController.MenuSections getId() {
        return id;
    }

    public LateralMenuController.MenuItemCategory getCategory_id() {
        return category_id;
    }

    public ImageView getImage() {
        return image;
    }

    public int getImageid() {
        return imageid;
    }

    public String getTextView() {
        return textView;
    }

    public boolean isParentCategory(){
        return isParentCategory;
    }

    public boolean isImageFromResources(){
        return isImageFromResources;
    }

    public static class Builder{

        private LateralMenuController.MenuSections id;
        private LateralMenuController.MenuItemCategory category_id;
        private boolean isParentCategory = false;
        private boolean isImageFromResources = false;
        private int imageid=0;
        private ImageView image;
        private String textView;


        public Builder(String textView){

            this.textView = textView;
        }

        public Builder setId(LateralMenuController.MenuSections id) {
            this.id = id;
            return this;
        }

        public Builder setImage(ImageView image) {
            this.image = image;
            return this;
        }

        public Builder setCategory_id(LateralMenuController.MenuItemCategory category_id) {
            this.category_id = category_id;
            return this;
        }

        public Builder setImageFromResource(int imageid) {
            this.imageid = imageid;
            isImageFromResources = true;
            return this;
        }

        public MenuItem build(){

            if ((imageid == 0) && (id == null)){

                isParentCategory = true;
            }

            return new MenuItem(this);
        }
    }

}
