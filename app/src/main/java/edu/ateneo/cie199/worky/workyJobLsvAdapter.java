package edu.ateneo.cie199.worky;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Richmond Sim on 30/11/2017.
 */

public class workyJobLsvAdapter extends ArrayAdapter<workyJobs> {
    private static int DEFAULT_LAYOUT_RESOURCE = R.layout.layout_job_lsv;
    private static int ICON_RESOURCE_ARRAY[] = {
            R.drawable.job1,
            R.drawable.job2,
            R.drawable.job3,
            R.drawable.job4,
            R.drawable.job5,
            R.drawable.job6,
            R.drawable.job7,
            R.drawable.job8,
            R.drawable.job9,
            R.drawable.job10,
            R.drawable.job11,
            R.drawable.job12,
            R.drawable.job13
    };

    private Context mContext = null;

    public workyJobLsvAdapter(@NonNull Context context, ArrayList<workyJobs> list) {
        super(context, DEFAULT_LAYOUT_RESOURCE, list);
        mContext = context;
        return;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(DEFAULT_LAYOUT_RESOURCE, parent, false);
        }

        workyJobs jobItem = getItem(position);

        /* GET TEXTVIEW REFERENCES */
        TextView txvJobTitle = (TextView) convertView.findViewById(R.id.txv_jobtitle);
        TextView txvPostedBy = (TextView) convertView.findViewById(R.id.txv_postedby);
        TextView txvLocation = (TextView) convertView.findViewById(R.id.txv_location);
        TextView txvSalary = (TextView) convertView.findViewById(R.id.txv_salary);
        TextView txvField = (TextView) convertView.findViewById(R.id.txv_field);

        /* SET TEXTVIEW CONTENTS */
        txvJobTitle.setText(jobItem.getJobtitle());
        txvPostedBy.setText(jobItem.getUsername());
        txvLocation.setText(jobItem.getLocation());
        txvSalary.setText(String.valueOf(jobItem.getSalary()));
        txvField.setText(jobItem.getJobfield());

        /* SET ICON */
        ImageView imvIcon = (ImageView) convertView.findViewById(R.id.imv_jobicon);
        int iconRscId = ICON_RESOURCE_ARRAY[jobItem.getJobicon()];
        imvIcon.setImageResource( iconRscId );

        return convertView;
    }
}