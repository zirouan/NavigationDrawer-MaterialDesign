package br.liveo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rudsonlive on 26/05/15.
 */
public class HelpItem implements Parcelable {

    private String name;
    private int icon = 0;
    private int counter = 0;

    private boolean hide = false;
    private boolean check = true;
    private boolean header = false;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getName());
        dest.writeInt(this.getIcon());
        dest.writeInt(this.getCounter());
        dest.writeByte(isHide() ? (byte) 1 : (byte) 0);
        dest.writeByte(isCheck() ? (byte) 1 : (byte) 0);
        dest.writeByte(isHeader() ? (byte) 1 : (byte) 0);
    }

    public HelpItem() {
    }

    protected HelpItem(Parcel in) {
        this.setName(in.readString());
        this.setIcon(in.readInt());
        this.setCounter(in.readInt());
        this.setHide(in.readByte() != 0);
        this.setCheck(in.readByte() != 0);
        this.setHeader(in.readByte() != 0);
    }

    public static final Parcelable.Creator<HelpItem> CREATOR = new Parcelable.Creator<HelpItem>() {
        public HelpItem createFromParcel(Parcel source) {
            return new HelpItem(source);
        }

        public HelpItem[] newArray(int size) {
            return new HelpItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }
}
