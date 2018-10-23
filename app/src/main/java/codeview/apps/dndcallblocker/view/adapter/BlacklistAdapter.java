package codeview.apps.dndcallblocker.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import codeview.apps.dndcallblocker.R;
import codeview.apps.dndcallblocker.model.BlacklistModel;

public class BlacklistAdapter  extends RecyclerView.Adapter<BlacklistAdapter.ViewHolder> {

    private List<BlacklistModel> blacklists;

    public BlacklistAdapter(List<BlacklistModel> blacklists) {
        this.blacklists = blacklists;
    }

    @NonNull
    @Override
    public BlacklistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_blacklist,parent,false);
        return new BlacklistAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BlacklistAdapter.ViewHolder holder, int position) {
        final BlacklistModel contact= blacklists.get(position);
        holder.phoneNum.setText(contact.getPhone());
        holder.name.setText(contact.getName());
        String initialChar=contact.getName().substring(0,1);
        holder.initial.setText(initialChar.toUpperCase());

    }

    @Override
    public int getItemCount() {
        return blacklists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView phoneNum,name, initial;
        CheckBox checkBox;

        ViewHolder(View itemView) {
            super(itemView);
            phoneNum=itemView.findViewById(R.id.contact_number);
            name=itemView.findViewById(R.id.contact_name);
            initial=itemView.findViewById(R.id.contact_initial);
            checkBox=itemView.findViewById(R.id.checkbox);

        }
    }
}
