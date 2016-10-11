package com.keto.optics.database;

import android.provider.BaseColumns;

/**
 * Created by ketanvichare on 10/11/16.
 */
/* Inner class that defines the table contents */
public class FeedEntry implements BaseColumns{

    public static final String TABLE_NAME = "optics_details";
    public static final String COLUMN_NAME_MODEL = "model_no";
    public static final String COLUMN_NAME_BRAND = "brand_name";
    public static final String COLUMN_NAME_PRICE = "price";
    public static final String COLUMN_NAME_IMAGE_DATA = "image_data";
}
