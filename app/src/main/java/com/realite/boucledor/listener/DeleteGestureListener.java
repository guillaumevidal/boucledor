package com.realite.boucledor.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import com.realite.boucledor.R;

public class DeleteGestureListener extends GestureDetector.SimpleOnGestureListener {
    private ListView list;

    public DeleteGestureListener(ListView list) {
        this.list = list;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (velocityX > 50) {
            if (showDeleteButton(e1)) {
                return true;
            }
        }
        return super.onFling(e1, e2, velocityX, velocityY);
    }

    private boolean showDeleteButton(MotionEvent e1) {
        int pos = list.pointToPosition((int)e1.getX(), (int)e1.getY());
        return showDeleteButton(pos);
    }

    private boolean showDeleteButton(int pos) {
        View child = list.getChildAt(pos);
        if (child != null){
            ImageButton delete = (ImageButton) child.findViewById(R.id.deleteEntryBtn);
            if (delete != null)
                if (delete.getVisibility() == View.INVISIBLE)
                    delete.setVisibility(View.VISIBLE);
                else
                    delete.setVisibility(View.INVISIBLE);
            return true;
        }
        return false;
    }
}