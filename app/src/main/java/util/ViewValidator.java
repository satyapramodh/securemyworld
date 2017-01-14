package util;

import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akhil on 1/11/2017.
 */
public class ViewValidator {
    private static final String ERROR = "text field can't be empty.";
    private static final List<EditText> array = new ArrayList<>();

    public static boolean isFieldBlank(ViewGroup viewGroup) {
        boolean isBlank = false;
        int count = viewGroup.getChildCount();
        for (int index = 0; index < count; index++) {
            View view = viewGroup.getChildAt(index);
            if (view instanceof EditText) {
                isBlank = validateEditTextField((EditText) view);
            }
        }
        return isBlank;
    }

    private static boolean validateEditTextField(EditText view) {
        boolean isBlank = false;
        if (TextUtils.isEmpty(view.getText().toString())) {
            view.setError(ERROR);
            isBlank = true;
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

    private static void findAllEditTexts(ViewGroup viewGroup) {

        int count = viewGroup.getChildCount();
        for (int index = 0; index < count; index++) {
            View view = viewGroup.getChildAt(index);
            if (view instanceof ViewGroup)
                findAllEditTexts((ViewGroup) view);
            else if (view instanceof EditText) {
                EditText editText = (EditText) view;
                array.add(editText);
            }
        }

    }

    public static boolean nestedLayoutValidation(LinearLayout viewGroup) {
        findAllEditTexts(viewGroup);
        for (int index = 0; index < array.size(); index++)
            if(validateEditTextField(array.get(index))){
                return true;
            }
        return false;
    }
}
