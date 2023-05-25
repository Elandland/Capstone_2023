package ac.kr.dankook.client;

public class ChatData {
    public String mChat;
    public boolean isMyChat;

    public String mDate;
    public ChatData(String chat, boolean isMyChat, String Date){
        this.mChat=chat;
        this.isMyChat=isMyChat;
        this.mDate=Date;
    }

    public ChatData(String chat, boolean isMyChat, int year, int month, int day, int hour, int min, int sec){
        this.mChat=chat;
        this.isMyChat=isMyChat;
        this.mDate=String.format("%tY%tm%td%tH%tM%tS",year,month,day,hour,min,sec);
    }
}
