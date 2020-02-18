package com.indocyber.itsmeandroid.utilities.commonclass;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.indocyber.itsmeandroid.R;

import java.util.List;

/**
 * A customized spinner adapter made specifically for Question spinner. It takes question's
 * list of Choices as parameter and then generate the spinner options based on it.
 *
 * @author Muhammad Faisal
 * @version 1.0
 */

public class CustomSpinnerAdapter<T extends SpinnerItem> extends ArrayAdapter<T> {

    private List<T> data;

    /**
     * Create spinner adapter which populates its option from list of choice object.
     *
     * @param context activity which holds spinner
     * @param resource resource value
     * @param dataList list of choice (option) the spinner will provide
     */
    public CustomSpinnerAdapter(
            @NonNull Context context, int resource, @NonNull List<T> dataList) {
        super(context, resource, dataList);
        this.data = dataList;
        setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
    }

    public int getCount() {
        return data.size();
    }

    public T getItem(int position) {
        return data.get(position);
    }

    /**
     * Return the position of an option which has exact value as param.
     * @param id Spinner option id
     * @return position of an option which has exact value as param
     */
    public int getItemPosition(long id) {
        int position = -1;
        for (T object : data) {
            if (object.getId() == (id)) {
                position = data.indexOf(object);
            }
        }
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            label.setTextAppearance(R.style.spinnerText);
        }
        label.setText(data.get(position).getName());
        return label;
    }

    @Override
    public View getDropDownView(
            int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            label.setTextAppearance(R.style.spinnerText);
        }
        label.setText(data.get(position).getName());
        return label;
    }

}
