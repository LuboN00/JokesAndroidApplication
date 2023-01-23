package nbu.f96226.project.jokes.Utils;

import android.content.ContentValues;

public class ContentValueBuilder {

    private ContentValues values;

    private ContentValueBuilder() {
        values = new ContentValues();
    }

    public static ContentValueBuilder createBuilder() {
        return new ContentValueBuilder();
    }

    public ContentValueBuilder withValue(String key, String value) {
        values.put(key, value);
        return this;
    }

    public ContentValueBuilder withValue(String key, Integer value) {
        values.put(key, value);
        return this;
    }

    public ContentValueBuilder withValue(String key, Long value) {
        values.put(key, value);
        return this;
    }

    public ContentValues build() {
        return values;
    }


}
