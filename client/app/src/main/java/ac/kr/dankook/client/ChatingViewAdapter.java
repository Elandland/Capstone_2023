package ac.kr.dankook.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatingViewAdapter extends BaseAdapter {



    private Context mContext;
    private int mLayoutId;

    private ArrayList<ChatData> mLogList;
    private LayoutInflater mInflater;

    public ChatingViewAdapter(Context applicationContext, int talklist, ArrayList<ChatData> list) {
        mContext = applicationContext;
        mLayoutId=talklist;
        mLogList=list;
        mInflater=(LayoutInflater)applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert mContext!=null;
        assert mLogList!=null;
        assert mInflater!=null;
    }
    @Override
    public int getCount() {
        return mLogList.size();
    }

    @Override
    public Object getItem(int i) {
        return mLogList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        chatViewHolder chatview=null;
        if(view==null){
            view=mInflater.inflate(mLayoutId,viewGroup,false);
            chatview=new chatViewHolder();
            chatview.myChatView=view.findViewById(R.id.my_chat_text);
            chatview.otherChatView=view.findViewById(R.id.other_chat_text);
            view.setTag(chatview);
        }
        chatview=(chatViewHolder)view.getTag();
        if(mLogList.get(i).isMyChat){
            chatview.myChatView.setText(mLogList.get(i).mChat);
            chatview.otherChatView.setVisibility(View.GONE);
            chatview.myChatView.setVisibility(View.VISIBLE);
        }
        else{
            chatview.otherChatView.setText(mLogList.get(i).mChat);
            chatview.myChatView.setVisibility(View.GONE);
            chatview.otherChatView.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private class chatViewHolder{
        public TextView myChatView;
        public TextView otherChatView;
    }
}
