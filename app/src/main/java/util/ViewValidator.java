package util;

import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by akhil on 1/7/2017.
 */

public class ViewValidator {
    private static final String ERROR = "text field can't be empty.";

    public static boolean isFieldBlank(ViewGroup viewGroup) {
        boolean isBlank = false;
        int count = viewGroup.getChildCount();
        for (int index = 0; index < count; index++) {
            View view = viewGroup.getChildAt(index);
            if (view instanceof EditText) {
                EditText edittext = (EditText) view;
                if (TextUtils.isEmpty(edittext.getText().toString())) {
                    edittext.setError(ERROR);
                    isBlank = true;
                }
            }
        }
        return isBlank;
    }

    public static boolean isSpinnerValueSetToDefault(ViewGroup viewGroup) {
        boolean isBlank = false;
        int count = viewGroup.getChildCount();
        for (int index = 0; index < count; index++) {
            View view = viewGroup.getChildAt(index);
            if (view instanceof Spinner) {
                Spinner spinner = (Spinner) view;
                if (spinner.getSelectedItemPosition() == 0) {
                    final AppCompatTextView selectedView = (AppCompatTextView) spinner.getSelectedView();
                    selectedView.setError(ERROR);
                    isBlank = true;
                }
            }
        }
        return isBlank;
    }

}
