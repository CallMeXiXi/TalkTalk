package com.example.joe.talktalk.im.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.joe.talktalk.R;
import com.example.joe.talktalk.im.activity.ContactsInfoActivity;
import com.example.joe.talktalk.model.ContactsModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/7/4 0004.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private Context context;
    private List<ContactsModel> lists;

    public ContactsAdapter(Context context, List<ContactsModel> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_contacts_item_layout, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        final ContactsModel model = lists.get(position);
        if (model != null) {
            holder.tvName.setText(model.getName());
            holder.civImage.setImageResource(R.mipmap.ic_launcher);

            int sesion = getSesionInPosition(position);
            if (position == getPositionInSesion(sesion)) {
                holder.tvLetter.setText(model.getSortLetters());
            } else {
                holder.tvLetter.setText("");
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ContactsInfoActivity.launch(context, model.getName(), model.getImageUri());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    public class ContactsViewHolder extends RecyclerView.ViewHolder {

        TextView tvLetter;
        TextView tvName;
        CircleImageView civImage;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            tvLetter = itemView.findViewById(R.id.tv_letter);
            tvName = itemView.findViewById(R.id.tv_name);
            civImage = itemView.findViewById(R.id.iv_header);
        }
    }

    /**
     * 获取position位置的 ascii值
     */
    public int getSesionInPosition(int position) {
        return lists.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     *
     * @param sesion
     * @return
     */
    public int getPositionInSesion(int sesion) {
        for (int i = 0; i < lists.size(); i++) {
            String letter = lists.get(i).getSortLetters();
            int letterSesion = letter.toUpperCase().charAt(0);
            if (letterSesion == sesion) {
                return i;
            }
        }
        if (sesion == "↑".charAt(0)) {
            return -2;
        }
        return -1;
    }
}
