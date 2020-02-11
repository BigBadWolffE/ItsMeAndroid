package com.indocyber.itsmeandroid.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.text.Spanned;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;
import com.indocyber.itsmeandroid.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        return new BitmapDrawable(
                context.getResources(),
                Bitmap.createScaledBitmap(bitmap, dstwidth, dstheight, true));
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
     * @param context Invoker activity
     * @param message Alert message to show
     * @param alertImage Alert icon
     * @param dismissListener Action on alert dismissal
     */
    public static void buildAlertDialog(
            Activity context,
            String message,
            int alertImage,
            DialogInterface.OnDismissListener dismissListener
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ViewGroup rootView = (ViewGroup)context.findViewById(android.R.id.content).getRootView();
        View view = LayoutInflater.from(context).inflate(R.layout.alert_dialog, rootView, false);
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
    }

    /**
     *
     * @author Muhammad Faisal
     * @param context Invoker activity
     * @param message Alert message to show
     * @param alertImage Alert icon
     * @param dismissListener Action on alert dismissal
     */
    public static void buildAlertDialog(
            Context context,
            Spanned message,
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
    }

    /**
     * @author Muhammad Faisal
     * @param amount A value of integer
     * @return A string of formatted currency
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
     * @param imageView target imageview to apply pdf
     * @param page the pdf page to show
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
    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
    public static String getFormattedTimeEvent(Long time) {
        SimpleDateFormat newFormat = new SimpleDateFormat("h:mm a");
        return newFormat.format(new Date(time));
    }

    public static void snackBarIconError(Activity activity, String message) {
        View parent_view = activity.findViewById(android.R.id.content);
        final Snackbar snackbar = Snackbar.make(parent_view, "", Snackbar.LENGTH_SHORT);
        //inflate view
        View custom_view = activity.getLayoutInflater().inflate(R.layout.snackbar_icon_text, null);

        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarView.setPadding(0, 0, 0, 0);

        ((TextView) custom_view.findViewById(R.id.message)).setText(message);
        ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_close);
        (custom_view.findViewById(R.id.parent_view)).setBackgroundColor(activity.getResources().getColor(R.color.red_600));
        snackBarView.addView(custom_view, 0);
        snackbar.show();
    }

    public static String encodeImage(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return "data:image/png;base64," +encImage;
    }

    public static Bitmap decodeImage(String base64){
        base64 = base64.replace("data:image/png;base64,", "");
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    /**
     * @author Muhammad Faisal
     * @return current year in integer
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * @author Muhammad Faisal
     * @return true if email format is valid
     */
    public static boolean checkEmailFormat(String email) {
        Pattern regexPattern = Pattern.compile("^(.+)@(.+)$");
        Matcher matcher = regexPattern.matcher(email);
        return matcher.matches();
    }

    /**
     *
     */
    public static String cardNumberSpacing(String cardNumber, int spacing) {
        StringBuilder paddedText = new StringBuilder(cardNumber);
        StringBuilder spacings = new StringBuilder();
        for (int i = 0; i < spacing; i ++) {
            spacings.append(" ");
        }

        return paddedText.substring(0, 4) + spacings + paddedText.substring(4, 8) + spacings
                + paddedText.substring(8, 12) + spacings + paddedText.substring(12, 16);
    }
}
