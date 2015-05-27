package br.liveo.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rudsonlive on 26/05/15.
 */
public class HelpLiveo {

    private HelpItem helpItem;
    private List<HelpItem> listHelp;

    public final int NO_ICON = 0;
    public final boolean IS_HIDE = true;
    public final boolean IS_HEADER = true;
    public final String ONLY_SEPARATOR = "";

    public HelpLiveo(){
        this.listHelp = new ArrayList<>();
    }

    private void newHelpItem(){
        this.helpItem = new HelpItem();
    }

    public void add(String name){
        this.newHelpItem();
        this.helpItem.setName(name);
        this.listHelp.add(helpItem);
    }

    public void addHide(String name){
        this.newHelpItem();
        this.helpItem.setName(name);
        this.helpItem.setHide(IS_HIDE);
        this.listHelp.add(helpItem);
    }

    public void add(String name, int icon){
        this.newHelpItem();
        this.helpItem.setName(name);
        this.helpItem.setIcon(icon);
        this.listHelp.add(helpItem);
    }

    public void addHide(String name, int icon){
        this.newHelpItem();
        this.helpItem.setName(name);
        this.helpItem.setIcon(icon);
        this.helpItem.setHide(IS_HIDE);
        this.listHelp.add(helpItem);
    }

    public void add(String name, int icon, int counter){
        this.newHelpItem();
        this.helpItem.setName(name);
        this.helpItem.setIcon(icon);
        this.helpItem.setCounter(counter);
        this.listHelp.add(helpItem);
    }

    public void addHide(String name, int icon, int counter){
        this.newHelpItem();
        this.helpItem.setName(name);
        this.helpItem.setIcon(icon);
        this.helpItem.setCounter(counter);
        this.helpItem.setHide(IS_HIDE);
        this.listHelp.add(helpItem);
    }

    public void addSubHeader(String name){
        this.newHelpItem();
        this.helpItem.setName(name);
        this.helpItem.setIcon(NO_ICON);
        this.helpItem.setHeader(IS_HEADER);
        this.listHelp.add(helpItem);
    }

    public void addSeparator(){
        this.newHelpItem();
        this.helpItem.setName(ONLY_SEPARATOR);
        this.helpItem.setIcon(NO_ICON);
        this.helpItem.setHeader(IS_HEADER);
        this.listHelp.add(helpItem);
    }

    public void addSubHeaderHide(String name, int icon){
        this.newHelpItem();
        this.helpItem.setName(name);
        this.helpItem.setIcon(icon);
        this.helpItem.setHeader(IS_HEADER);
        this.helpItem.setHide(IS_HIDE);
        this.listHelp.add(helpItem);
    }

    public HelpItem get(int position){
        return this.listHelp.get(position);
    }

    public List<HelpItem> getHelp(){
        return this.listHelp;
    }

    public int getCount(){
        return this.listHelp.size();
    }
}
