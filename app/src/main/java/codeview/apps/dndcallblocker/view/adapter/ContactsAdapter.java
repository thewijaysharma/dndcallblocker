package codeview.apps.dndcallblocker.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import codeview.apps.dndcallblocker.R;
import codeview.apps.dndcallblocker.model.Contact;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private List<Contact> contacts;

    public ContactsAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contact,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Contact contact=contacts.get(position);
        holder.phoneNum.setText(contact.getPhone());
        holder.name.setText(contact.getName());
        String initialChar=contact.getName().substring(0,1);
        holder.initial.setText(initialChar.toUpperCase());

        if(contact.isBlacklisted()){
            holder.checkBox.setChecked(true);
        }else {
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isPressed()){

                    if(isChecked){
                        contact.setBlacklisted(true);
                    }else {
                        contact.setBlacklisted(false);
                    }

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return contacts.size();
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