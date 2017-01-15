package util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akhil on 1/8/2017.
 */

public class SpinnerUtils {

    private static final String DOES_NOT_MATTER = "Doesn\'t Matter";

    public static void setDefaultValueInSpinner(Context context, ViewGroup viewGroup) {
        int count = viewGroup.getChildCount();
        for (int index = 0; index < count; index++) {
            View view = viewGroup.getChildAt(index);
            if (view instanceof Spinner) {
                Spinner spinner = (Spinner) view;
                addDefaultOptionInSpinner(spinner, context);
            }
        }
    }

    private static void addDefaultOptionInSpinner(Spinner spinner, Context context) {

        final List<String> allItems = retrieveAllItems(spinner);
        allItems.add(DOES_NOT_MATTER);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,
                allItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.notifyDataSetChanged();
    }

    private static List<String> retrieveAllItems(Spinner spinner) {
        Adapter adapter = spinner.getAdapter();
        int count = adapter.getCount();
        List<String> items = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            String item = (String) adapter.getItem(i);
            items.add(item);
        }
        return items;
    }
}
