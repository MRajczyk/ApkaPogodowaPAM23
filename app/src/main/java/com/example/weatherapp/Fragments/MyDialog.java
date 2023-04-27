package com.example.weatherapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import com.example.weatherapp.R;

public class MyDialog extends DialogFragment implements View.OnClickListener
{
    private Button acceptButton;
    private Button rejectButton;

    private int position;

    public static interface Callback {
        public void accept(int position);
        public void decline();
    }

    public MyDialog(int position) {
        this.position = position;
    }

    public MyDialog() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment, container);
        acceptButton = (Button) view.findViewById(R.id.dialogfragment_acceptbtn);
        rejectButton = (Button) view.findViewById(R.id.dialogfragment_rejectbtn);
        acceptButton.setOnClickListener(this);
        rejectButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Callback callback = null;
        try {
            callback = (Callback) getTargetFragment();
        }
        catch (ClassCastException e) {
            Log.e(this.getClass().getSimpleName(), "Callback of this class must be implemented by target fragment!", e);
            throw e;
        }

        if (callback != null) {
            if (v == acceptButton) {
                callback.accept(this.position);
                this.dismiss();
            }
            else if (v == rejectButton) {
                callback.decline();
                this.dismiss();
            }
        }
    }
}
