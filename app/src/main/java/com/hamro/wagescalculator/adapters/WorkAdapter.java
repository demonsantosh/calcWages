package com.hamro.wagescalculator.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.hamro.wagescalculator.R;
import com.hamro.wagescalculator.data.WorkedTime;

import java.util.List;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder> {
    List<WorkedTime> workedTimeList;
    private Context context;

    private CallBackListiner callBack;

    public WorkAdapter(List<WorkedTime> workedTimeList, CallBackListiner callBack) {
        this.workedTimeList = workedTimeList;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_layout_work_time, parent, false);
        context = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.inpEdtHours.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String hour = s.toString();
                if (hour != null && !TextUtils.isEmpty(hour)) {
                    int hrs = Integer.valueOf(hour);
                    if (hrs < 25 && hrs > 0) {
                        workedTimeList.get(holder.getAdapterPosition()).setHour(hrs);
                        if (callBack != null) {
                            callBack.updateList(workedTimeList);
                        }
                    } else {
                        holder.inpEdtHours.setText("");
                    }
                } else {
                    workedTimeList.get(holder.getAdapterPosition()).setHour(0);
                    if (callBack != null) {
                        callBack.updateList(workedTimeList);
                    }
                }
            }
        });

        holder.inpEdtMinutes.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String minutes = s.toString();
                if (minutes != null && !TextUtils.isEmpty(minutes)) {
                    int min = Integer.valueOf(minutes);

                    if (min < 61 && min > 0) {

                        workedTimeList.get(holder.getAdapterPosition()).setMinute(min);
                        if (callBack != null) {
                            callBack.updateList(workedTimeList);
                        }
                    } else {
                        holder.inpEdtMinutes.setText("");
                    }
                } else {
                 /*   if (callBack != null) {
                        callBack.setMinute(position,0);
                    }*/
                    workedTimeList.get(position).setMinute(0);
                    if (callBack != null) {
                        callBack.updateList(workedTimeList);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return workedTimeList.size();
    }

    public interface CallBackListiner {
        void updateList(List<WorkedTime> workedList);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextInputEditText inpEdtHours, inpEdtMinutes;

        public ViewHolder(View itemView) {
            super(itemView);
            inpEdtHours = itemView.findViewById(R.id.inpEdtHours);
            inpEdtMinutes = itemView.findViewById(R.id.inpEdtMinutes);

        }
    }
}
