package net.rainroot.ui_1st;

public class ListAlarmitem {
    private int icon;
    private String name;

    public int getIcon(){
        return icon;
    }
    public String getName(){
        return name;
    }
    public ListAlarmitem(int icon,String name){
        this.icon = icon;
        this.name = name;
    }
}
