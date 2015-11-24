package br.liveo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rudsonlive on 26/05/15.
 */
public class HelpItem implements Parcelable {

    public String name;
    public int icon = 0;
    public int counter = 0;

    public boolean hide = false;
    public boolean header = false;

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

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.icon);
        dest.writeByte(header ? (byte) 1 : (byte) 0);
        dest.writeInt(this.counter);
        dest.writeByte(hide ? (byte) 1 : (byte) 0);
    }

    public HelpItem() {
    }

    private HelpItem(Parcel in) {
        this.name = in.readString();
        this.icon = in.readInt();
        this.header = in.readByte() != 0;
        this.counter = in.readInt();
        this.hide = in.readByte() != 0;
    }

    public static final Parcelable.Creator<HelpItem> CREATOR = new Parcelable.Creator<HelpItem>() {
        public HelpItem createFromParcel(Parcel source) {
            return new HelpItem(source);
        }

        public HelpItem[] newArray(int size) {
            return new HelpItem[size];
        }
    };
}
