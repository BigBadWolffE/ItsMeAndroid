package com.indocyber.itsmeandroid.utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.text.Spanned;
import android.text.SpannedString;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.indocyber.itsmeandroid.R;

import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 *
 *@author Muhammad Faisal
 *@version 1.0
 */
public final class UtilitiesCore {

    /**
     * Resize an instantiated drawable
     * @author Muhammad Faisal
     * @param drawable existing drawable
     * @param context drawable context
     * @param dstwidth target drawable width
     * @param dstheight target drawable height
     * @return resized drawable
     */
    public static Drawable resizeDrawable(final Drawable drawable, final Context context,
                                          final int dstwidth, final int dstheight) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Drawable newdrawable = new BitmapDrawable(
                context.getResources(),
                Bitmap.createScaledBitmap(bitmap, dstwidth, dstheight, true));
        return newdrawable;
    }

    /**
     * Generate drawable with specified size
     * @author Muhammad Faisal
     * @param resource drawable resource id
     * @param context drawable context
     * @param dstwidth target drawable width
     * @param dstheight target drawable height
     * @return resized drawable
     */
    public static Drawable resizeDrawable(final int resource, final Context context,
                                          final int dstwidth, final int dstheight) {
        Drawable drawable = context.getDrawable(resource);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        return new BitmapDrawable(
                context.getResources(),
                Bitmap.createScaledBitmap(bitmap, dstwidth, dstheight, true));
    }

    public static void buildAlertDialog(
            Context context,
            String message,
            int alertImage,
            DialogInterface.OnDismissListener dismissListener
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.alert_dialog, null);
        TextView messageText = view.findViewById(R.id.txtAlertMessage);
        messageText.setText(message);
        ImageView image = view.findViewById(R.id.imgAlertIcon);
        image.setImageResource(alertImage);

        builder.setOnDismissListener(dismissListener);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        Button positiveButton = view.findViewById(R.id.btnAlertPositive);
        positiveButton.setOnClickListener(view1 -> dialog.dismiss());
        dialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = (int)context.getResources().getDimension(R.dimen.alert_dialog_width);
        lp.height = (int)context.getResources().getDimension(R.dimen.alert_dialog_height);
        lp.y = (int)context.getResources().getDimension(R.dimen.alert_y_offset);
        dialog.getWindow().setAttributes(lp);
    }

    public static void buildAlertDialog(
            Context context,
            Spanned message,
            int alertImage,
            DialogInterface.OnDismissListener dismissListener,
            int width,
            int height
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.alert_dialog, null);
        TextView messageText = view.findViewById(R.id.txtAlertMessage);
        messageText.setText(message);
        ImageView image = view.findViewById(R.id.imgAlertIcon);
        image.setImageResource(alertImage);

        builder.setOnDismissListener(dismissListener);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        Button positiveButton = view.findViewById(R.id.btnAlertPositive);
        positiveButton.setOnClickListener(view1 -> dialog.dismiss());
        dialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width,
                context.getResources().getDisplayMetrics());
        lp.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height,
                context.getResources().getDisplayMetrics());
        lp.y = (int)context.getResources().getDimension(R.dimen.alert_y_offset);
        dialog.getWindow().setAttributes(lp);
    }

    public static String formatCurrency(int amount) {
        final Locale indonesia = new Locale("in", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(indonesia);
        String currency = format.format(amount);

        return currency.replace(".",",");
    }
}
