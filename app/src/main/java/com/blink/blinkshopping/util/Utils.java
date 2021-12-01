package com.blink.blinkshopping.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Build;
import android.provider.Settings;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Utils {

    private static final String TAG = "Utils";

    private static SimpleDateFormat regionDayFromat = new SimpleDateFormat("yyyy-MM-dd");


    public static int getPercentage(double priceDiff, double mainPrice) {
        double cal = (priceDiff / mainPrice) * 100.0f;
        return (int) Math.round(cal);
    }

    public static int getPercentageByProduct(float priceDiff, float mainPrice, float priceQuantity) {
        float cal = ((priceDiff / mainPrice) * 100.0f) / priceQuantity;
        return Math.round(cal);
    }

    public static String print2Decimal(String price) {
        String value = price;
        Float litersOfPetrol = Float.parseFloat(value);
        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(2);
        value = df.format(litersOfPetrol);

        return value;
    }


    public static String regularPrice(String regularPrice) {
        String value = regularPrice + " KD";
        return value;
    }

    public static boolean checkPriceEquality(String regularPrice, String finalPrice) {
        boolean value = regularPrice.equalsIgnoreCase(finalPrice);
        return value;
    }

    public static String finalPrice(String finalPrice) {
        String value = finalPrice + " KD";
        return value;
    }

    public static String DecimalLimitter(String value) {
        double amount = Double.parseDouble(value);
        DecimalFormat f = new DecimalFormat("##.00");
        return f.format(amount);
    }

    public static String html2text(String htmlTxt) {
        String highlighter = "";
        if (htmlTxt != null && !htmlTxt.isEmpty()) {
            highlighter = Html.fromHtml(Html.fromHtml(htmlTxt).toString()).toString();
        } else {
            highlighter = "";
        }
        return highlighter;
    }

//    public static Typeface getMuseoSans100TypeFace(Context context) {
//        return Typeface.createFromAsset(context.getResources().getAssets(), "fonts/MuseoSans_100.otf");
//    }
//
//    public static Typeface getMuseoSans700TypeFace(Context context) {
//        return Typeface.createFromAsset(context.getResources().getAssets(), "fonts/MuseoSans_700.otf");
//    }
//
//    public static Typeface getMuseoSans300TypeFace(Context context) {
//        return Typeface.createFromAsset(context.getResources().getAssets(), "fonts/MuseoSans_300.otf");
//    }
//
//    public static Typeface getMuseoSans500TypeFace(Context context) {
//        return Typeface.createFromAsset(context.getResources().getAssets(), "fonts/MuseoSans_500.otf");
//    }

    public static int dpToPx(Context context, int dp) {
        Resources r = context.getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return px;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isStrNotEmpty(String arg) {
        if (arg != null) {
            arg = arg.trim();
            return arg.length() > 0;
        } else
            return false;
    }

    public static boolean isNull(Object... args) {
        boolean isN = false;
        for (Object arg : args) {
            if (arg == null) {
                isN = true;
                break;
            }
        }

        return isN;
    }

    public static String stringConvertToBase64(String password) {
        byte[] data1 = null;
        String base64Product = "";
        try {
            data1 = password.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return base64Product = Base64.encodeToString(data1, Base64.DEFAULT);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getStringFromBase64(String base64) {
        byte[] data = Base64.decode(base64, Base64.DEFAULT);
        String text = new String(data, StandardCharsets.UTF_8);
        return text;
    }
//
//    public static boolean hasPermission(Context activity, int readAndSendMessage) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            if (readAndSendMessage == Keys.ALL_PERMISSION) {
//                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
//                for (int i = 0; i < permissions.length; i++) {
//                    String permission = permissions[i];
//                    if (ContextCompat.checkSelfPermission(activity,
//                            permission)
//                            != PackageManager.PERMISSION_GRANTED) {
//                        return false;
//                    }
//                }
//
//            }
//
//        }
//        return true;
//    }

    public static void showGPSDialog(final Context mContext) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);


        alertDialog.setTitle("GPS Not Enabled");

        alertDialog.setMessage("Do you wants to turn On GPS");


        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });


        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        alertDialog.show();
    }

//    public static int countRealAdded(String d, ArrayList<AddedDatum> list) {
//        int count = 0;
//        for (int i = 0; i < list.size(); i++) {
//            if (d.equalsIgnoreCase(list.get(i).getD().getId())) {
//                count = count + list.get(i).getD().getNoOfItemsText();
//            }
//        }
//        return count;
//    }


//    public static int getCartItemQuantity(String productSku, ArrayList<Item> list) {
//        int count = 0;
//        if (list != null && list.size() > 0) {
//            for (int i = 0; i < list.size(); i++) {
//                if (productSku.equalsIgnoreCase(list.get(i).getSku())) {
//                    count = count + (int) (list.get(i).getQty().doubleValue());
//                }
//            }
//        }
//        return count;
//    }

//    public static int getCartItemQuantity(double cartItemId, ArrayList<Item> list) {
//        int count = 0;
//        if (list != null && list.size() > 0) {
//            for (int i = 0; i < list.size(); i++) {
//                if (cartItemId == list.get(i).getItemId().doubleValue()) {
//                    return (int) list.get(i).getQty().doubleValue();
//                }
//            }
//        }
//        return count;
//    }


//    public static int countRealAdded(double d, ArrayList<Item> list) {
//        int count = 0;
//        for (int i = 0; i < list.size(); i++) {
//            if (d == (list.get(i).getItemId())) {
//                count = (int) (count + list.get(i).getQty().doubleValue());
//            }
//        }
//        return count;
//    }

//    public static void checkPreviousAddedProduct(AddedDatum d, ArrayList<AddedDatum> list, boolean isSimpleProduct) {
//        boolean found = false;
//        int count = 0;
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).getD().getId().equalsIgnoreCase(d.getD().getId()) && Double.parseDouble(list.get(i).getSelectedPrice().replace(",", ".")) == (Double.parseDouble(d.getSelectedPrice().replace(",", "."))) && list.get(i).getSelectedDropType().equalsIgnoreCase(d.getSelectedDropType())) {
//                found = true;
//                count = d.getD().getAddedNo() + list.get(i).getD().getNoOfItemsText();
//                list.get(i).getD().setNoOfItemsText(count);
//                d.getD().setNoOfItemsText(count);
//            } else if (list.get(i).getD().getId().equalsIgnoreCase(d.getD().getId()) && !isSimpleProduct) {
//                found = true;
//                count = d.getD().getAddedNo() + list.get(i).getD().getNoOfItemsText();
//                list.get(i).getD().setNoOfItemsText(count);
//                list.get(i).setSelectedPrice(d.getSelectedPrice());
//                d.getD().setNoOfItemsText(count);
//            }
//        }
//        if (!found) {
//            if (d.getD().getNoOfItemsText() == 0)
//                d.getD().setNoOfItemsText(d.getD().getAddedNo());
//            else {
//                d.getD().setNoOfItemsText(d.getD().getAddedNo());
//            }
//
//            list.add(d);
//        } else {
//            d.getD().setNoOfItemsText(count);
//        }
//
//    }


//    public static boolean checkPreviousAddedProductInCart(AddedDatum d, ArrayList<AddedDatum> list, boolean isSimpleProduct) {
//        boolean found = false;
//        int count = 0;
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).getD().getId().equalsIgnoreCase(d.getD().getId()) && Double.parseDouble(list.get(i).getSelectedPrice().replace(",", ".")) == (Double.parseDouble(d.getSelectedPrice().replace(",", "."))) && list.get(i).getSelectedDropType().equalsIgnoreCase(d.getSelectedDropType())) {
//                found = true;
//                count = d.getD().getAddedNo() + list.get(i).getD().getNoOfItemsText();
//                list.get(i).getD().setNoOfItemsText(count);
//                d.getD().setNoOfItemsText(count);
//            } else if (list.get(i).getD().getId().equalsIgnoreCase(d.getD().getId()) && !isSimpleProduct) {
//                found = true;
//                count = d.getD().getAddedNo() + list.get(i).getD().getNoOfItemsText();
//                list.get(i).getD().setNoOfItemsText(count);
//                list.get(i).setSelectedPrice(d.getSelectedPrice());
//                d.getD().setNoOfItemsText(count);
//            }
//        }
//        if (!found) {
//            if (d.getD().getNoOfItemsText() == 0)
//                d.getD().setNoOfItemsText(d.getD().getAddedNo());
//            else {
//                d.getD().setNoOfItemsText(d.getD().getAddedNo());
//            }
//
//            list.add(d);
//        } else {
//            d.getD().setNoOfItemsText(count);
//        }
//
//        return found;
//    }
//
//    public static void checkPreviousAddedProductNew(AddedDatum d, ArrayList<AddedDatum> list) {
//        boolean found = false;
//        int count = 0;
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).getD().getId().equalsIgnoreCase(d.getD().getId()) && list.get(i).getSelectedPrice().equalsIgnoreCase(d.getSelectedPrice()) && list.get(i).getSelectedDropType().equalsIgnoreCase(d.getSelectedDropType())) {
//                found = true;
//                count = 1 + list.get(i).getD().getNoOfItemsText();
//                list.get(i).getD().setNoOfItemsText(count);
//                d.getD().setNoOfItemsText(count);
//            }
//        }
//        if (!found) {
//            if (d.getD().getNoOfItemsText() == 0)
//                d.getD().setNoOfItemsText(d.getD().getAddedNo());
//            else {
//                d.getD().setNoOfItemsText(1);
//            }
//
//            list.add(d);
//        } else {
//            d.getD().setNoOfItemsText(count);
//        }
//
//    }
//
//    public static boolean checkProductIsInCart(AddedDatum d, ArrayList<AddedDatum> list) {
//        boolean found = false;
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).getD().getId().equalsIgnoreCase(d.getD().getId()) && list.get(i).getSelectedPrice().equalsIgnoreCase(d.getSelectedPrice()) && list.get(i).getSelectedDropType().equalsIgnoreCase(d.getSelectedDropType())) {
//                found = true;
//                return found;
//            }
//        }
//        return found;
//    }
//
//    public static void checkPreviousAddedProduct(HashMap<String, List<Datum>> parentList, Datum list) {
//        if (parentList != null)
//            for (int i = 0; i < parentList.size(); i++) {
//                Object firstKey = parentList.keySet().toArray()[i];
//                Object valueForFirstKey = parentList.get(firstKey);
//
//                ArrayList<Datum> products = (ArrayList<Datum>) valueForFirstKey;
//                for (int j = 0; j < products.size(); j++) {
//                    Datum d = products.get(j);
//                    boolean found = false;
//                    int count = 0;
//                    // for (int i = 0; i < list.size(); i++) {
//                    if (list.getId().equalsIgnoreCase(d.getId())) {
//                        d.setActualNoOfAdded(list.getActualNoOfAdded());
//                        d.setISaDDED(list.getISaDDED());
//                    }
//                    // }
//
//                }
//            }
//    }
//
//    public static void checkPreviousMinusProduct(AddedDatum d, ArrayList<AddedDatum> list) {
//        boolean found = false;
//        int count = 0;
//        for (int i = (list.size() - 1); i >= 0; i--) {
//            if (list.get(i).getD().getId().equalsIgnoreCase(d.getD().getId())) {
//                found = true;
//                //if (list.get(i).getSelectedPrice().equalsIgnoreCase(d.getSelectedPrice())) {
//
//                count = list.get(i).getD().getNoOfItemsText() - 1;
//                list.get(i).getD().setNoOfItemsText(count);
//                d.getD().setNoOfItemsText(count);
//                if (count < 1) {
//                    list.remove(i);
//                    d.getD().setNoOfItemsText(0);
//                    d.getD().setISaDDED("no");
//                }
//                // }
//                break;
//            }
//        }
//
//        if (!found) {
//            d.getD().setNoOfItemsText(0);
//            d.getD().setISaDDED("no");
//        }
//
//    }
//
//    public static void checkPreviousMinusProductCart(AddedDatum d, ArrayList<AddedDatum> list) {
//        boolean found = false;
//        int count = 0;
//        for (int i = (list.size() - 1); i >= 0; i--) {
//            if (list.get(i).getD().getId().equalsIgnoreCase(d.getD().getId()) && list.get(i).getSelectedPrice().equalsIgnoreCase(d.getSelectedPrice()) && list.get(i).getSelectedDropType().equalsIgnoreCase(d.getSelectedDropType())) {
//                found = true;
//                //if (list.get(i).getSelectedPrice().equalsIgnoreCase(d.getSelectedPrice())) {
//
//                count = list.get(i).getD().getNoOfItemsText() - 1;
//                list.get(i).getD().setNoOfItemsText(count);
//                d.getD().setNoOfItemsText(count);
//                if (count < 1) {
//                    list.remove(i);
//                    d.getD().setNoOfItemsText(0);
//                    d.getD().setISaDDED("no");
//                }
//                // }
//                break;
//            }
//        }
//
//        if (!found) {
//            d.getD().setNoOfItemsText(0);
//            d.getD().setISaDDED("no");
//        }
//
//    }
//
//    public static void checkPreviousAddedProducts(ArrayList<Datum> d, ArrayList<AddedDatum> list) {
//        for (int j = 0; j < d.size(); j++) {
//            boolean found = false;
//            int count = 0;
//            for (int i = 0; i < list.size(); i++) {
//                if (list.get(i).getD().getId().equalsIgnoreCase(d.get(j).getId())) {
//                    found = true;
//                    count = d.get(j).getNoOfItemsText() + list.get(i).getD().getNoOfItemsText();
//                    list.get(i).getD().setNoOfItemsText(count);
//                }
//            }
//            if (!found) {
//                if (d.get(j).getNoOfItemsText() == 0)
//                    d.get(j).setNoOfItemsText(1);
//                else {
//
//                }
//                AddedDatum ad = new AddedDatum();
//                ad.setD(d.get(j));
//                ad.setSelectedWeight("");
//                ad.setSelectedPrice("");
//                list.add(ad);
//            } else {
//                d.get(j).setNoOfItemsText(count);
//            }
//        }
//
//    }
//
//    public static Object parseData(Response res, Object object) {
//        String data = (String) res.body().toString();
//        Object ob = null;
//        try {
//            JSONObject obj = new JSONObject(data);
//            JSONObject meta = obj.getJSONObject("metadata");
//            boolean result = meta.getBoolean("success");
//            String error;
//            if (!result) {
//                error = meta.getString("errorCode");
//            } else {
//                JSONObject payload = obj.getJSONObject("payload");
//                JSONObject dat = payload.getJSONObject("data");
//                JsonParser parser = new JsonParser();
//                JsonElement mJson = parser.parse(dat.toString());
//                Gson gson = new Gson();
//                ob = gson.fromJson(mJson, Object.class);
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return ob;
//
//    }

    public static Bitmap getScaledBitmap(File file) {
        FileInputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 1000000; // 1.2MP
            in = new FileInputStream(file);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();


            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }
            Log.d(TAG, "scale = " + scale + ", orig-width: " + o.outWidth + ",orig-height: " + o.outHeight);

            Bitmap b = null;
            in = new FileInputStream(file);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();
                Log.d(TAG, "1th scale operation dimenions - width: " + width + ", height: " + height);

                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                b.recycle();
                b = scaledBitmap;

                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();

            Log.d(TAG, "bitmap size - width: " + b.getWidth() + ", height: " +
                    b.getHeight());
            return b;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    public static Bitmap toGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    public static String loadJSONFromAsset(Context ctx, String file) {
        String json = null;
        try {

            InputStream is = ctx.getResources().getAssets().open("files/" + file);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public static String properCase(String inputVal) {
        // Empty strings should be returned as-is.

        if (inputVal.trim().length() == 0) return "";

        // Strings with only one character uppercased.

        if (inputVal.trim().length() == 1) return inputVal.toUpperCase();

        // Otherwise uppercase first letter, lowercase the rest.

        return inputVal.trim().substring(0, 1).toUpperCase()
                + inputVal.trim().substring(1).toLowerCase();
    }

    public static String upperCase(String inputVal) {


        return inputVal.toUpperCase();
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
//
//    public static void setupImage(Context context, ImageView view, String uriString) {
//
//        String urlStr = uriString;
//        if (uriString.contains("ec2-34-250-168-34.eu-west-1")) {
//            String qureyStr = null;
//            final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
//            urlStr = "http://ec2%2D34%2D250%2D168%2D34.eu%2Dwest%2D1" + uriString.substring(35, uriString.length());
//        }
//
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(R.drawable.placeholder);
//
//        requestOptions.error(R.drawable.placeholder);
//        requestOptions.fitCenter();
//        requestOptions.skipMemoryCache(false);
//        //requestOptions.format(DecodeFormat.PREFER_RGB_565);
//        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
//        requestOptions.dontAnimate();
//        requestOptions.dontTransform();
//        Glide.with(context).load(Uri.parse(uriString))
//                .thumbnail(0.9f).apply(requestOptions)
//                .into(view);
//
//        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                view.getViewTreeObserver().removeOnPreDrawListener(this);
//                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
//                        .setResizeOptions(new ResizeOptions(80, 80))
//                        .setAutoRotateEnabled(true)
//                        .build();
//                PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
//                        .setOldController(view.getController())
//                        .setImageRequest(request)
//                        .build();
//                view.setController(controller);
//                return true;
//            }
//        });
//        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri).setCacheChoice(ImageRequest.CacheChoice.SMALL)
//                .build();
//        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
//                .setImageRequest(imageRequest)
//                .setOldController(view.getController())
//                .setAutoPlayAnimations(true)
//                .build();
//        view.setController(draweeController);
//
//
//        simpleDraweeView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                simpleDraweeView.getViewTreeObserver().removeOnPreDrawListener(this);
//                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
//                        .setResizeOptions(new ResizeOptions(80, 80))
//                        .build();
//                PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
//                        .setOldController(simpleDraweeView.getController())
//                        .setImageRequest(request)
//                        .build();
//                simpleDraweeView.setController(controller);
//                simpleDraweeView.setImageURI(uri);
//                return true;
//            }
//        });
//    }
//
//    public static StoreRegionMain getStoreRegionMainFromString(String region) {
//        StoreRegionMain storeMain = null;
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        Gson gson = gsonBuilder.create();
//        storeMain = gson.fromJson(region, StoreRegionMain.class);
//        return storeMain;
//    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity != null) {
            try {
                InputMethodManager inputMethodManager =
                        (InputMethodManager) activity.getSystemService(
                                Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(
                        activity.getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {

            }
        }
    }

    public static String parseDate(String time, String inputPattern, String outputPattern) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = time;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String toTitleCase(String inputVal) {
        // Empty strings should be returned as-is.

        if (inputVal == null) {
            return "";
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(inputVal);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }

    public static void clearCookies(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

    public static String getFormatedDoubleToString(double amount) {
        return String.format(Locale.ENGLISH, "%.2f", amount);
    }



    /*
     * Check Item is added cart based on product id
     * if item added to cart return cartitemid
     * else
     * return -1
     *
     * */


//    public static double getProductCartItemId(String id, ArrayList<Item> cartItems) {
//        if (cartItems != null && cartItems.size() > 0) {
//            for (Item item : cartItems) {
//                if (item != null && item.getSku().equalsIgnoreCase(id)) {
//                    return item.getItemId();
//                }
//            }
//        }
//        return -1;
//    }

//    public static double getProductCartItemIdWithOptions(String sku, String optionName, String optionValue,
//                                                         String optionName2, String optionValue2,
//                                                         ArrayList<Item> cartItems, Integer hasCustomOption) {
//        if (cartItems != null && cartItems.size() > 0) {
//            for (Item item : cartItems) {
//                if (item != null && item.getSku().equalsIgnoreCase(sku)) {
//                    if (hasCustomOption == 1) {
//                        boolean hasSameWeightOption = false;
//
//                        if (isNullOrEmpty(optionName) && isNullOrEmpty(item.getWeightOptionLabel()))
//                            hasSameWeightOption = true;
//                        else if (optionValue.equalsIgnoreCase(item.getWeightOptionValue()))
//                            hasSameWeightOption = true;
//                        else
//                            hasSameWeightOption = false;
//
//
//                        //Second
//                        boolean hasSameOtherOption = false;
//
//                        if (isNullOrEmpty(optionName2) && isNullOrEmpty(item.getOtherOptionLabel()))
//                            hasSameOtherOption = true;
//                        else if (optionValue2.equalsIgnoreCase(item.getOtherOptionValue()))
//                            hasSameOtherOption = true;
//                        else
//                            hasSameOtherOption = false;
//
//
//                        if (hasSameOtherOption && hasSameWeightOption)
//                            return item.getItemId();
//
//                    } else {
//                        return item.getItemId();
//                    }
//                }
//            }
//        }
//        return -1;
//    }

    public void showLanguageDialog(final Activity activity) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

        alertDialog.setTitle("Language");

        alertDialog.setMessage("Choose Language ");


//        alertDialog.setPositiveButton("English", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                setNewLocale(activity, Keys.LANGUAGE_ENGLISH, false);
//            }
//        });
//
//
//        alertDialog.setNegativeButton("Arabic", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                setNewLocale(activity, Keys.LANGUAGE_ARABIC, false);
//            }
//        });


        alertDialog.show();
    }

//    private void setNewLocale(Activity activity, String language, boolean restartProcess) {
//        LocaleManager.setNewLocale(activity, language);
//
//        Intent i = new Intent(activity, HomeActivity.class);
//        activity.startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
//
//        if (restartProcess) {
//            System.exit(0);
//        } else {
//            // Toast.makeText(activity, "Activity restarted", Toast.LENGTH_SHORT).show();
//        }
//
//    }

    public static String getDecodedBase64String(String encodedData) {
        byte[] shippingAddressByte = Base64.decode(encodedData, Base64.DEFAULT);
        String decodedData = null;
        try {
            decodedData = new String(shippingAddressByte, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return decodedData;
    }


    public static String getCurrenttFormatDayforRegion() {
        Calendar calendar = Calendar.getInstance();
        return regionDayFromat.format(calendar.getTime());
    }

    public static String getFormatDayforRegion(Date date) {

        return regionDayFromat.format(date);
    }

    public static boolean isNullOrEmpty(String str) {
        if (str != null && !str.isEmpty())
            return false;
        return true;
    }

//    public static void fetchDeferredAppLinkData(Context context) {
//        AppLinkData.fetchDeferredAppLinkData(context,
//                new AppLinkData.CompletionHandler() {
//                    @Override
//                    public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
//                        // Process app link data
//                    }
//                }
//        );
//    }
//
//    public static void ContactEvent(Context context) {
//        AppEventsLogger logger = AppEventsLogger.newLogger(context);
//        logger.logEvent("Contact");
//    }
//
//    public static void SearchEvent(Context context, String Search_String) {
//        AppEventsLogger logger = AppEventsLogger.newLogger(context);
//        Bundle params = new Bundle();
//        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, "product");
//        params.putString(AppEventsConstants.EVENT_PARAM_SEARCH_STRING, Search_String);
//        params.putInt(AppEventsConstants.EVENT_PARAM_SUCCESS, true ? 1 : 0);
//        logger.logEvent(AppEventsConstants.EVENT_NAME_SEARCHED, params);
//
//        Map<String, Object> eventValue = new HashMap<String, Object>();
//        eventValue.put(AFInAppEventParameterName.SEARCH_STRING, Search_String);
//        //eventValue.put(AFInAppEventParameterName.CONTENT_LIST, new String[] {"001", "092"});
//        AppsFlyerLib.getInstance().trackEvent(getApplicationContext(), AFInAppEventType.SEARCH, eventValue);
//    }
//
//    public static void ViewContentEvent(Context context, String ContentID, String ContentType, String Currency, String ValueToSum) {
//        AppEventsLogger logger = AppEventsLogger.newLogger(context);
//        Bundle params = new Bundle();
//        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, ContentType);
//        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, ContentID);
//        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, Currency);
//        logger.logEvent(AppEventsConstants.EVENT_NAME_VIEWED_CONTENT, Double.parseDouble(ValueToSum), params);
//
//        Map<String, Object> eventValue = new HashMap<String, Object>();
//        eventValue.put(AFInAppEventParameterName.PRICE, ValueToSum);
//        eventValue.put(AFInAppEventParameterName.CONTENT_ID, ContentID);
//        eventValue.put(AFInAppEventParameterName.CONTENT_TYPE, ContentType);
//        eventValue.put(AFInAppEventParameterName.CURRENCY, Currency);
//        AppsFlyerLib.getInstance().trackEvent(getApplicationContext(), AFInAppEventType.CONTENT_VIEW, eventValue);
//
//    }
//
//    public static void Addtocart(Context context, String ContentID, String ContentType, String Currency, String ValueToSum) {
//        AppEventsLogger logger = AppEventsLogger.newLogger(context);
//        Bundle params = new Bundle();
//        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, ContentID);
//        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, ContentType);
//        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, Currency);
//        logger.logEvent(AppEventsConstants.EVENT_NAME_ADDED_TO_CART, Double.parseDouble(ValueToSum), params);
//
//        Map<String, Object> eventValue = new HashMap<String, Object>();
//        eventValue.put(AFInAppEventParameterName.PRICE, ValueToSum);
//        eventValue.put(AFInAppEventParameterName.CONTENT_ID, ContentID);
//        eventValue.put(AFInAppEventParameterName.CONTENT_TYPE, ContentType);
//        eventValue.put(AFInAppEventParameterName.CURRENCY, Currency);
//        eventValue.put(AFInAppEventParameterName.QUANTITY, 1);
//    }
//
//    public static void AddToWishlistEvent(Context context, String ContentID, String ContentType, String Currency, String ValueToSum) {
//        AppEventsLogger logger = AppEventsLogger.newLogger(context);
//        Bundle params = new Bundle();
//        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, ContentID);
//        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, ContentType);
//        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, Currency);
//        logger.logEvent(AppEventsConstants.EVENT_NAME_ADDED_TO_WISHLIST, Double.parseDouble(ValueToSum), params);
//    }
//
//    public static void InitiateCheckoutEvent(Context context, String ContentID, String ContentType,
//                                             String Currency, String ValueToSum, String numItems) {
//
//        AppEventsLogger logger = AppEventsLogger.newLogger(context);
//        Bundle params = new Bundle();
//        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, ContentID);
//        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, ContentType);
//        params.putInt(AppEventsConstants.EVENT_PARAM_NUM_ITEMS, Integer.parseInt(numItems));
//        params.putInt(AppEventsConstants.EVENT_PARAM_PAYMENT_INFO_AVAILABLE, true ? 1 : 0);
//        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, Currency);
//        logger.logEvent(AppEventsConstants.EVENT_NAME_INITIATED_CHECKOUT, Double.parseDouble(ValueToSum), params);
//
//        Map<String, Object> eventValue = new HashMap<String, Object>();
//        eventValue.put(AFInAppEventParameterName.PRICE, ValueToSum);
//        eventValue.put(AFInAppEventParameterName.CONTENT_ID, ContentID);
//// for multiple product categories, set the param value as: new String {"221", "124"}
//        eventValue.put(AFInAppEventParameterName.CONTENT_TYPE, ContentType);
//// for multiple product categories,, set the param value as: new String {"shirt", "pants"}
//        eventValue.put(AFInAppEventParameterName.CURRENCY, Currency);
//        eventValue.put(AFInAppEventParameterName.QUANTITY, numItems);
//// for multiple product categories,, set the param value as: new int {2, 5}
//        AppsFlyerLib.getInstance().trackEvent(getApplicationContext(), AFInAppEventType.INITIATED_CHECKOUT, eventValue);
//    }
//
//    public static void PurchaseEvent(Context context, int numItems, String ContentID, String ContentType, String currency, double ValueToSum) {
//        AppEventsLogger logger = AppEventsLogger.newLogger(context);
//
//        Bundle params = new Bundle();
//        params.putInt(AppEventsConstants.EVENT_PARAM_NUM_ITEMS, numItems);
//        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, ContentType);
//        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, ContentID);
//        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, currency);
//        logger.logPurchase(BigDecimal.valueOf(ValueToSum), java.util.Currency.getInstance(currency), params);
//
//
///*Utils.CodeLessPurchase(OrderPreviewActivity.this, model.getPayload().getData().getOrderDetails().getOrder().getId(),
//                product_id_list.size(), grandTotal);*/
//
//
//        Map<String, Object> eventValue = new HashMap<String, Object>();
//        eventValue.put(AFInAppEventParameterName.REVENUE, ValueToSum);
//        eventValue.put(AFInAppEventParameterName.PRICE, ValueToSum);
//        eventValue.put(AFInAppEventParameterName.CONTENT_ID, ContentID);
//// for multiple product categories, set the param value as: // new String {"221", "124"}
//        eventValue.put(AFInAppEventParameterName.CONTENT_TYPE, ContentType);
//// for multiple product categories,, set the param value as: new String {"shoes", "pants"}
//        eventValue.put(AFInAppEventParameterName.CURRENCY, currency);
//        eventValue.put(AFInAppEventParameterName.QUANTITY, numItems);
//// for multiple product categories, set the param value as: new int {2, 5}
//        eventValue.put(AFInAppEventParameterName.RECEIPT_ID, ContentID);
//        eventValue.put("af_order_id", ContentID);
//        AppsFlyerLib.getInstance().trackEvent(getApplicationContext(), AFInAppEventType.PURCHASE, eventValue);
//
//    }
//
//    public static void CodeLessPurchase(Context context, String contect_id, int num_items, String grandTotal) {
//        AppEventsLogger logger = AppEventsLogger.newLogger(context);
//        Bundle parameters = new Bundle();
//        parameters.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, "EGP");
//        parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, "product");
//        parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, contect_id);
//        parameters.putString(AppEventsConstants.EVENT_PARAM_NUM_ITEMS, String.valueOf(num_items));
//
//        logger.logEvent(AppEventsConstants.EVENT_NAME_PURCHASED, Double.parseDouble(grandTotal), parameters);
//    }
//
//    public static void AddPaymentInfo(Context context, boolean Success) {
//        AppEventsLogger logger = AppEventsLogger.newLogger(context);
//        Bundle params = new Bundle();
//        params.putInt(AppEventsConstants.EVENT_PARAM_SUCCESS, Success ? 1 : 0);
//        logger.logEvent(AppEventsConstants.EVENT_NAME_ADDED_PAYMENT_INFO, params);
//    }
//
//    public static void AppEventsLogger(Context context, String email, String first_name, String last_name, String phone_number,
//                                       String birth_date, String gender, String city,
//                                       String state, String zip_code, String country) {
//        AppEventsLogger logger = AppEventsLogger.newLogger(context);
//        logger.setUserData(email, first_name, last_name, phone_number, birth_date, gender, city, state, zip_code, country);
//    }
//
//    public static void loginAppFlyer() {
//        AppsFlyerLib.getInstance().trackEvent(getApplicationContext(), AFInAppEventType.LOGIN, null);
//    }
//
//    public static void removeFromCart(String product_id, String ContentType) {
//
//        Map<String, Object> eventValue = new HashMap<String, Object>();
//        eventValue.put(AFInAppEventParameterName.CONTENT_ID, product_id);
//        eventValue.put(AFInAppEventParameterName.CONTENT_TYPE, ContentType);
//        AppsFlyerLib.getInstance().trackEvent(getApplicationContext(), "remove_from_cart", eventValue);*//*
//
//    }

}

