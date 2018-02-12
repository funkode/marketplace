package com.marketplace.common.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MarketplaceDateSerializer extends StdSerializer<Date> {

    public static final String DATE_PATTERN = "MM-dd-yyyy HH:mm:ss";

    private static SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);

    public MarketplaceDateSerializer() {
        this(null);
    }

    public MarketplaceDateSerializer(Class<Date> dateClass) {
        super(dateClass);
    }

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(sdf.format(value));
    }
}
