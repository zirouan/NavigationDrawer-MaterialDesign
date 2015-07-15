#Change Log

Version 2.3.4 *(15-07-2015)*
----------------------------
It requires 14+ API and android support v7 22.2.0 (AppCompatActivity + Toolbar)

* New functions
```groovy
    Change in the choice of theme
    with(this) default theme is the dark
    with(this, Navigation.THEME_DARK) add theme Dark
    with(this, Navigation.THEME_LIGHT) add theme Light

    Error correction to add a custom header
```

Version 2.3.3 *(03-07-2015)*
----------------------------
It requires 14+ API and android support v7 22.2.0 (AppCompatActivity + Toolbar)

* New functions
```groovy
    Bug Color Dialog
```

Version 2.3.2 *(03-06-2015)*
----------------------------
It requires 14+ API and android support v7 22.2.0 (AppCompatActivity + Toolbar)

* New functions
```groovy
    Bug addHeaderView
```


Version 2.2.2 *(29-05-2015)*
----------------------------
It requires 14+ API and android support v7 22.2.0 (AppCompatActivity + Toolbar)

* New functions
```groovy
    Add NavigationLiveo
    .removeHeader()
```

Version 2.1.1 *(25-05-2015)*
----------------------------
It requires 14+ API and android support v7 22.1.1 (AppCompatActivity + Toolbar)

* New functions
```groovy
    Bug position

    .footerNameColor(int color)
    .footerIconColor(int color)
    .footerBackground(int color)
```


Version 2.0.1 *(25-05-2015)*
----------------------------
It requires 14+ API and android support v7 22.1.1 (AppCompatActivity + Toolbar)

* New functions
```groovy
    Add NavigationActionBarLiveo
    .removeHeader() //only in NavigationActionBarLiveo
```

Version 2.0.0 *(24-05-2015)*
----------------------------
It requires 14+ API and android support v7 22.1.1 (AppCompatActivity + Toolbar)

* New functions
```groovy
    with(this).startingPosition(1) //Starting position in the list
            .nameItem(mListNameItem)
            .iconItem(mListIconItem)
            .headerItem(mListHeaderItem)
            .countItem(mSparseCounterItem)

            //{optional} - List Customization "If you remove these methods and the list will take his white standard color"
            .selectorCheck(R.drawable.selector_check) //Inform the background of the selected item color
            .colorItemDefault(R.color.nliveo_gray) //Inform the standard color name, icon and counter
            .colorItemSelected(R.color.nliveo_purple_colorPrimary) //State the name of the color, icon and meter when it is selected
            .backgroundList(R.color.nliveo_black_light) //Inform the list of background color
            .colorLineSeparator(R.color.nliveo_transparent) //Inform the color of the subheader line

            .footerItem(R.string.settings, R.drawable.ic_settings_black_24dp)

            .setOnClickUser(onClickPhoto)
            .setOnClickFooter(onClickFooter)
            .build();
```

Version 1.0.5 *(22-04-2015)* @deprecated
----------------------------
It requires 14+ API and android support v7 22.1.0 (AppCompatActivity + Toolbar)

* New functions
```groovy
    Add AppCompatActivity
```
<a href="http://android-developers.blogspot.com.br/2015/04/android-support-library-221.html?m=1" target="_blank">AppCompatActivity</a> <br>


Version 1.0.4 *(26-02-2015)*
----------------------------
It requires 14+ API and android support v7 21.0.3 (Toolbar)

* New functions
```groovy
    public void setColorSeparatorItemSubHeaderNavigation(int colorId){}
    public void setNewName(int position, String name){}
    public void setNewName(int position, int name){}
    public void setNewIcon(int position, int icon){}
    public void setNewInformationItem(int position, int name, int icon, int counter){}
    public void setNewInformationItem(int position, String name, int icon, int counter){}
```
<a href="https://gist.github.com/rudsonlive/759a2c554a5d34d8dd05" target="_blank">Other methods</a> <br>


Version 1.0.3 *(25-01-2015)*
----------------------------
It requires 14+ API and android support v7 21.0.3 (Toolbar)

* Corrections
```groovy
Bugs list items
List width in landscape mode
Center toolbar icons in landscape mode
```
* New functions
```groovy
   public void setColorIconItemNavigation(int colorId){}
   public void setColorNameItemNavigation(int colorId){}
   public void setFooterInformationDrawer(String title, int icon, int colorName, int colorIcon){}
   public void setFooterInformationDrawer(int title, int icon, int colorName, int colorIcon){}
```
<a href="https://gist.github.com/rudsonlive/759a2c554a5d34d8dd05" target="_blank">Other methods</a> <br>

Version 1.0.2 *(20-01-2015)*
----------------------------
It requires 14+ API and android support v7 21.0.3 (Toolbar)

* New functions
```groovy
    public void showDefauldHeader(){}
    private void removeDefauldHeader(){}
    public void addCustomHeader(View v){}
    public void removeCustomdHeader(View v){}
    public void setElevationToolBar(float elevation){}
```
<a href="https://gist.github.com/rudsonlive/759a2c554a5d34d8dd05" target="_blank">Other methods</a> <br>

Version 1.1.0 *(17-01-2015)*
----------------------------
It requires 8+ API and android support v7 21.0.3 (Toolbar)

* Add @param
* New functions
```groovy
    public void setFooterIconColorNavigation(int colorId){}
    public void removeAlphaItemNavigation(){}
    public void setColorDefaultItemNavigation(int colorId){}
    public DrawerLayout getDrawerLayout() {}
```
<a href="https://gist.github.com/rudsonlive/759a2c554a5d34d8dd05" target="_blank">Other methods</a> <br>

Version 1.0.0 *(15-01-2015)*
----------------------------
It requires 8+ API and android support v7 21.0.3 (Toolbar)

* Library publication :D
