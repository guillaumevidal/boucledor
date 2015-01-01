package com.realite.boucledor.listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class ListEntryTouchListener implements View.OnTouchListener {

    private final ListView listView;
    private final GestureDetector gestureDetector ;

    public ListEntryTouchListener(Context context, ListView listView) {
        this.listView = listView;
        gestureDetector = new GestureDetector(context, new DeleteGestureListener(listView));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}
