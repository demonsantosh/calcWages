package com.hamro.wagescalculator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.hamro.wagescalculator.adapters.WorkAdapter;
import com.hamro.wagescalculator.data.WorkedTime;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment implements WorkAdapter.CallBackListiner {
    private AppCompatSpinner spinnerWorkingDays;
    private TextInputEditText inpEdtWorkHours, inpEdtWorkMinutes, inpEdtSalaryPerMonth;
    private List<WorkedTime> workedTimeList = new ArrayList<>();
    private WorkAdapter workAdapter;
    private RecyclerView recyclerView;
    private AppCompatTextView totalTimeWorked, totalEarning;
    private int expectedEarning, actuallyEarned;
    private int perminuteEarning;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        initializeView(view);
        return view;
    }

    private void initializeView(View view) {
        spinnerWorkingDays = view.findViewById(R.id.spinnerWorkingDays);
        totalTimeWorked = view.findViewById(R.id.totalTimeWorked);
        totalEarning = view.findViewById(R.id.totalEarning);
        inpEdtWorkHours = view.findViewById(R.id.inpEdtWorkHours);
        inpEdtWorkMinutes = view.findViewById(R.id.inpEdtWorkMinutes);
        inpEdtSalaryPerMonth = view.findViewById(R.id.inpEdtSalaryPerMonth);
        recyclerView = view.findViewById(R.id.recyclerView);
        workedTimeList.add(new WorkedTime(0, 0));
        workAdapter = new WorkAdapter(workedTimeList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(workAdapter);


        inpEdtWorkHours.addTextChangedListener(new TextWatcher() {

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
                String workHoursPerDay = s.toString().trim();

                if (!workHoursPerDay.equals("")) {
                    int hrs = Integer.parseInt(workHoursPerDay);
                    if (hrs < 25 && hrs > 0) {
                        updateList(workedTimeList);
                    } else {
                        inpEdtWorkHours.setText("");
                    }
                }
            }
        });

        inpEdtWorkMinutes.addTextChangedListener(new TextWatcher() {

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
                String workHoursPerDay = s.toString().trim();
                if (!workHoursPerDay.equals("")) {
                    int min = Integer.parseInt(workHoursPerDay);
                    if (min < 61 && min > 0) {
                        updateList(workedTimeList);
                    } else {
                        inpEdtWorkMinutes.setText("");
                    }
                }
            }
        });

        inpEdtSalaryPerMonth.addTextChangedListener(new TextWatcher() {

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
                String workHoursPerDay = s.toString().trim();
                if (workHoursPerDay!=null && TextUtils.isEmpty(workHoursPerDay)) {
                    updateList(workedTimeList);
                }
            }
        });
        spinnerWorkingDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position != 0) {
                    String spinnerText = spinnerWorkingDays.getSelectedItem().toString();
                    workedTimeList.clear();
                    int daysValue = Integer.valueOf(spinnerText);
                    for (int i = 1; i <= daysValue; i++) {
                        workedTimeList.add(new WorkedTime(0, 0));
                    }
                    workAdapter = new WorkAdapter(workedTimeList, SecondFragment.this);
                    recyclerView.setAdapter(workAdapter);
                    setBottomVisibility(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });*/
    }


    @Override
    public void updateList(List<WorkedTime> workedList) {
        Log.i("===>", "updateList: " + workedList.toString());
        int hours = 0;
        int minutes = 0;
        if (workedList.size() > 0) {
            for (int i = 0; i < workedList.size(); i++) {
                hours = hours + workedList.get(i).getHour();
                minutes = minutes + workedList.get(i).getMinute();
            }
            if (minutes > 60) {
                hours = hours + (minutes / 60);
                minutes = minutes % 60;
            }
            setBottomVisibility(true);
            if (minutes > 0) {
            }
            int totalMinutes = 0;

            if (inpEdtSalaryPerMonth.getText().toString() != null && !TextUtils.isEmpty(inpEdtSalaryPerMonth.getText().toString())) {
                if (inpEdtWorkHours.getText().toString() != null && !TextUtils.isEmpty(inpEdtWorkHours.getText().toString())) {
                    totalMinutes = Integer.valueOf(inpEdtWorkHours.getText().toString().trim()) * 60;
                }
                if (inpEdtWorkMinutes.getText().toString() != null && !TextUtils.isEmpty(inpEdtWorkMinutes.getText().toString())) {
                    totalMinutes = totalMinutes + Integer.valueOf(inpEdtWorkHours.getText().toString().trim());
                }
                int salaryPerday=Integer.valueOf(inpEdtSalaryPerMonth.getText().toString().trim()) / 30;
                int salaryPerMinute = salaryPerday / totalMinutes;
                totalEarning.setVisibility(View.VISIBLE);
                totalEarning.setText("Total Earning : " + ((hours * 60 * salaryPerMinute) + (minutes * salaryPerMinute)));

            }else{
                totalEarning.setVisibility(View.GONE);
            }
            totalTimeWorked.setText("Total Time Worked :" + hours + " hrs " + minutes + " min");
        }
    }

    private void setBottomVisibility(boolean showOrNot) {
        if (!showOrNot) {
            totalTimeWorked.setVisibility(View.GONE);
            totalEarning.setVisibility(View.GONE);
        } else {
            totalTimeWorked.setVisibility(View.VISIBLE);
            totalEarning.setVisibility(View.VISIBLE);
        }

    }

}
