package org.febtober.uwavesym;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ComponentAdapter extends BaseAdapter {
    private Context context;
    private Circuit circuit;
    private LayoutInflater inflater;

    public ComponentAdapter(Context mContext, Circuit mCircuit) {
        context = mContext;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        circuit = mCircuit;
    }

    @Override
    public int getCount() {
        return circuit.size();
    }

    @Override
    public Object getItem(int position) {
        return circuit.getComponent(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        Component currComponent = circuit.getComponent(position);
        if (convertView == null) {
            imageView = (ImageView) inflater.inflate(R.layout.view_symbol, parent, false);
//            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//            imageView.setPadding(10, 10, 10, 10);
            imageView.setId(currComponent.getSymViewId());
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(currComponent.getSymId());
        return imageView;
    }
}
