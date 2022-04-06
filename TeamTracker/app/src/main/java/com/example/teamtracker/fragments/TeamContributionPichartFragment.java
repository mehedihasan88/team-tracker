package com.example.teamtracker.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teamtracker.R;
import com.example.teamtracker.models.Project;
import com.example.teamtracker.models.Task;
import com.example.teamtracker.util.DateTimeUtil;
import com.example.teamtracker.viewmodels.TaskViewModel;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TeamContributionPichartFragment extends Fragment {

    private Project project;
    private TaskViewModel taskViewModel;
    private PieChart teamContributionPichart;

    public TeamContributionPichartFragment(Project project) {
        this.project = project;
    }

    public static TeamContributionPichartFragment newInstance(Project project) {
        TeamContributionPichartFragment fragment = new TeamContributionPichartFragment(project);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_contribution_pichart, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        teamContributionPichart = view.findViewById(R.id.team_contribution_pichart);
        setupPieChart();
        loadPieChartData();
    }
    private void setupPieChart() {
        teamContributionPichart.setDrawHoleEnabled(true);
        teamContributionPichart.setUsePercentValues(true);
        teamContributionPichart.setEntryLabelTextSize(12);
        teamContributionPichart.setEntryLabelColor(Color.BLACK);
        teamContributionPichart.setCenterText("Spending by Category");
        teamContributionPichart.setCenterTextSize(24);
        teamContributionPichart.getDescription().setEnabled(false);

        Legend l = teamContributionPichart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0.2f, "Food & Dining"));
        entries.add(new PieEntry(0.15f, "Medical"));
        entries.add(new PieEntry(0.10f, "Entertainment"));
        entries.add(new PieEntry(0.25f, "Electricity and Gas"));
        entries.add(new PieEntry(0.3f, "Housing"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(teamContributionPichart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        teamContributionPichart.setData(data);
        teamContributionPichart.invalidate();

        teamContributionPichart.animateY(1400, Easing.EaseInOutQuad);
    }
}
