package codeview.apps.dndcallblocker.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import codeview.apps.dndcallblocker.R;
import codeview.apps.dndcallblocker.model.LogModel;

public class CallLogsAdapter extends RecyclerView.Adapter<CallLogsAdapter.ViewHolder> {

    private List<LogModel> logs = new ArrayList<>();

    public void setLogs(List<LogModel> logs) {
        this.logs = logs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_call_log, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LogModel logModel = logs.get(position);
        String indexValue = String.valueOf(position + 1) + ".\t";
        if (logModel.isSms()) {
            if (logModel.getName() != null) {
                holder.logText.setText(indexValue + "Sms blocked from " + logModel.getName() + " (" + logModel.getPhone() + ")");
            } else {
                holder.logText.setText(indexValue + "Sms blocked from " + logModel.getPhone());
            }
        } else {
            if (logModel.getName() != null) {
                holder.logText.setText(indexValue + "Call blocked from " + logModel.getName() + " (" + logModel.getPhone() + ")");
            } else {
                holder.logText.setText(indexValue + "Call blocked from " + logModel.getPhone());
            }
        }

        holder.time.setText(logModel.getBlockedTime());
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView logText;
        TextView time;

        ViewHolder(View itemView) {
            super(itemView);
            logText = itemView.findViewById(R.id.call_log_text);
            time = itemView.findViewById(R.id.time);
        }
    }
}
