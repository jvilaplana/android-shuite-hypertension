package com.hesoftgroup.bpcontrol.model;

/**
 * Created by Adrian on 24/01/2015.
 */
public class GridCellResources {
    private int background_id;
    private int icon_id;
    private int text_id;

    public GridCellResources(int background_id,int icon_id,int text_id){
        this.background_id = background_id;
        this.icon_id = icon_id;
        this.text_id = text_id;
    }

    public int getBackground_id() {
        return background_id;
    }

    public int getIcon_id() {
        return icon_id;
    }

    public int getText_id() {
        return text_id;
    }
}
