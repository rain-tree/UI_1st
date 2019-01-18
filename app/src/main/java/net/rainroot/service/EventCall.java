package net.rainroot.service;

public class EventCall {
    public int[] Type;
    public EventCall(int id){
        Type = new int[]{id};
    }
    public EventCall(int[] ints) {
        Type = ints;
    }
    public void onCall(int eventId){

    };
}
