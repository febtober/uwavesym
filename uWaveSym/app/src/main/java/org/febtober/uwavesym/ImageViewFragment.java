package org.febtober.uwavesym;

import android.app.DialogFragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageViewFragment extends DialogFragment {
    private ImageView iv_radiationPattern;

    public ImageViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_view, container, false);
        getDialog().setTitle(R.string.radiation_pattern);
        iv_radiationPattern = (ImageView) view.findViewById(R.id.iv_radiationPattern);
        int radPatternResId = getArguments().getInt("radPatternResId");
        if (radPatternResId != 0) {
            Drawable drw_radPattern = getResources().getDrawable(radPatternResId);
            if (drw_radPattern != null)
                iv_radiationPattern.setImageDrawable(drw_radPattern);
        }
        return view;
    }
}
