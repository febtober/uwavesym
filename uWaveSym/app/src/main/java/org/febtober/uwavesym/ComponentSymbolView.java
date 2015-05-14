package org.febtober.uwavesym;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ComponentSymbolView extends ImageView {

    public ComponentSymbolView(Context context) {
        super(context);
    }

    public ComponentSymbolView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ComponentSymbolView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
