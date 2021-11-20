package com.example.fogas;

import android.content.ClipData;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class sequencePracticeFragment extends Fragment {
    private TextView textViewMain;
    private TextView textView31;
    private TextView textView32;
    private TextView textView33;
    private TextView textView34;


    public sequencePracticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sequence_practice, container, false);
        textViewMain = view.findViewById(R.id.textViewMain);
        textView31 = view.findViewById(R.id.textView31);
        textView32 = view.findViewById(R.id.textView32);
        textView33 = view.findViewById(R.id.textView33);
        textView34 = view.findViewById(R.id.textView34);

        textView31.setOnLongClickListener(longClickListener);
        textView32.setOnLongClickListener(longClickListener);
        textView33.setOnLongClickListener(longClickListener);
        textView34.setOnLongClickListener(longClickListener);

        textViewMain.setOnDragListener(dragListener);

        return view;
    }

    View.OnLongClickListener longClickListener = new View.OnLongClickListener(){

        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("","");
            View.DragShadowBuilder myShadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data,myShadowBuilder,v,0);
            return true;
        }
    };

    View.OnDragListener dragListener = new View.OnDragListener() {

        @Override
        public boolean onDrag(View v, DragEvent Event) {
            final View vie = (View) Event.getLocalState();
            int dragEvent = Event.getAction();
            switch (dragEvent) {
                case DragEvent.ACTION_DRAG_ENTERED:

                    if (vie.getId() == R.id.textView31) {
                        textViewMain.setText("TextView31 is entered");
                    }else if(vie.getId() == R.id.textView32){
                        textViewMain.setText("TextView32 is entered");
                    }else if(vie.getId() == R.id.textView33){
                        textViewMain.setText("TextView33 is entered");
                    }
                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    if (vie.getId() == R.id.textView31) {
                        textViewMain.setText("TextView31 is exited");
                    }else if(vie.getId() == R.id.textView32){
                        textViewMain.setText("TextView32 is exited");
                    }else if(vie.getId() == R.id.textView33){
                        textViewMain.setText("TextView33 is exited");
                    }
                    break;
                case DragEvent.ACTION_DROP:

                    vie.animate()
                            .x(textViewMain.getX())
                            .y(textViewMain.getY())
                            .setDuration(700)
                            .start();
                    break;
            }
            return true;
        }
    };
}