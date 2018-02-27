package com.example.cristmasmemorygame;

/**
 * Created by Юрий on 21.09.2017.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CardBackFragment extends Fragment {

    int i = Root.getInteger("CurrentCard", 0);


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(
                i, container, false);
    }
}
