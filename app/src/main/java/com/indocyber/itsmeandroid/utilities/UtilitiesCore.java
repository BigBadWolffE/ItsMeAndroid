package com.indocyber.itsmeandroid.utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfRenderer;
import android.media.Image;
import android.os.ParcelFileDescriptor;
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

import androidx.annotation.RawRes;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.indocyber.itsmeandroid.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    /**
     *
     * @author Muhammad Faisal
     * @param context
     * @param message
     * @param alertImage
     * @param dismissListener
     */
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

    /**
     *
     * @author Muhammad Faisal
     * @param context
     * @param message
     * @param alertImage
     * @param dismissListener
     * @param width
     * @param height
     */
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

    /**
     * @author Muhammad Faisal
     * @param amount
     * @return
     */
    public static String formatCurrency(int amount) {
        final Locale indonesia = new Locale("in", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(indonesia);
        String currency = format.format(amount);

        return currency.replace(".",",");
    }

    /**
     *
     * @author Muhammad Faisal
     * @param imageView
     * @param page
     */
    public static void openPdfFromAsset(ImageView imageView, int page, String fileName)
            throws IOException {

        File fileCopy = new File(imageView.getContext().getCacheDir(), fileName);
        copyFileToCache(imageView.getContext(), fileCopy, fileName);

        ParcelFileDescriptor fileDescriptor =
                ParcelFileDescriptor.open(fileCopy, ParcelFileDescriptor.MODE_READ_ONLY);

        PdfRenderer mPdfRenderer = new PdfRenderer(fileDescriptor);
        PdfRenderer.Page mPdfPage = mPdfRenderer.openPage(page);

        Bitmap bitmap = Bitmap
                .createBitmap(mPdfPage.getWidth(), mPdfPage.getHeight(), Bitmap.Config.ARGB_8888);
        mPdfPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        imageView.setImageBitmap(bitmap);
    }

    public static void copyFileToCache(Context context, File file, String filename)
            throws IOException {

        if (!file.exists()) {
            InputStream input = context.getAssets().open(filename);
            FileOutputStream output = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int size;

            while ((size = input.read(buffer)) != -1) {
                output.write(buffer, 0, size);
            }

            input.close();
            output.close();
        }
    }


}
