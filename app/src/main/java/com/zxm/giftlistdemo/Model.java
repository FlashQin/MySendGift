package com.zxm.giftlistdemo;

/**
 * Created by Administrator on 2016/11/17.
 */

public class Model {
    private int image;//图片地址
    private String money;//钻石
    private boolean isSelected;//

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "Model:"+isSelected;
    }
}
