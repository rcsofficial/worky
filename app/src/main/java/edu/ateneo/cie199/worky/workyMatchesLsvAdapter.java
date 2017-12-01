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
 * Created by Richmond Sim on 01/12/2017.
 */

public class workyMatchesLsvAdapter extends ArrayAdapter<workyLinkJob> {
    private static int DEFAULT_LAYOUT_RESOURCE = R.layout.layout_matches_lsv;
    private static int ICON_RESOURCE_ARRAY[] = {
            R.drawable.profpic1,
            R.drawable.profpic2,
            R.drawable.profpic3,
            R.drawable.profpic4
    };

    private Context mContext = null;

    public workyMatchesLsvAdapter(@NonNull Context context, ArrayList<workyLinkJob> list) {
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

        workyLinkJob linkItem = getItem(position);

        /* GET TEXTVIEW AND IMAGEVIEW REFERENCES */
        TextView txvJobTitleLink = (TextView) convertView.findViewById(R.id.txv_jobtitlelink);
        TextView txvMatch = (TextView) convertView.findViewById(R.id.txv_match);
        TextView txvEmail = (TextView) convertView.findViewById(R.id.txv_email);
        TextView txvMobileNum = (TextView) convertView.findViewById(R.id.txv_mobilenum);
        ImageView imvIcon = (ImageView) convertView.findViewById(R.id.imv_avatar);
        int iconRscId;

        /* SET TEXTVIEW CONTENTS */
        txvJobTitleLink.setText(linkItem.getJob().getJobtitle());
        if (linkItem.getJobUsertype().equals("Client")) {
            txvMatch.setText(linkItem.getFreelancer().getUsername());
            txvEmail.setText(linkItem.getFreelancer().getEmail());
            txvMobileNum.setText(String.valueOf(linkItem.getFreelancer().getMobile()));
            iconRscId = ICON_RESOURCE_ARRAY[linkItem.getFreelancer().getIconCode()];

        }
        else {
            txvMatch.setText(linkItem.getClient().getUsername());
            txvEmail.setText(linkItem.getClient().getEmail());
            txvMobileNum.setText(String.valueOf(linkItem.getClient().getMobile()));
            iconRscId = ICON_RESOURCE_ARRAY[linkItem.getClient().getIconCode()];
        }
        imvIcon.setImageResource( iconRscId );

        return convertView;
    }
}
