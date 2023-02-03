/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package http.callback;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.readystatesoftware.chuck.internal.support.JsonConvertor;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ================================================
 * ================================================
 */
public class Convert {

    private static Gson create() {
        return GsonHolder.gson;
    }

    private static class GsonHolder {
        private static Gson gson = new GsonBuilder().registerTypeAdapter(new TypeToken<HashMap<String, Object>>() {
        }.getType(), new JsonDeserializer<HashMap<String, Object>>() {
            @Override
            public HashMap<String, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                HashMap<String, Object> hashMap = new HashMap<>();
                JsonObject jsonObject = json.getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                for (Map.Entry<String, JsonElement> entry : entrySet) {
                    JsonElement element = entry.getValue();
                    if (element.isJsonPrimitive()) {
                        JsonPrimitive value = element.getAsJsonPrimitive();
                        if (value.isString()) {
                            hashMap.put(entry.getKey(), value.getAsString());
                        } else if (value.isBoolean()) {
                            hashMap.put(entry.getKey(), value.getAsBoolean());
                        } else if (value.isNumber()) {
                            if (value.getAsString().contains(".") && value.getAsDouble() != value.getAsLong()) {
                                hashMap.put(entry.getKey(), value.getAsDouble());
                            } else if (value.getAsInt() == value.getAsLong()) {
                                hashMap.put(entry.getKey(), value.getAsInt());
                            } else {
                                hashMap.put(entry.getKey(), value.getAsLong());
                            }
                        } else {
                            throw new JsonSyntaxException("Can't parse value: " + value);
                        }
                    } else {
                        if (entry.getKey().equals("pk")) {
                            hashMap.put(entry.getKey(), new Gson().toJson(entry.getValue()));
                        } else {
                            hashMap.put(entry.getKey(), entry.getValue());
                        }
                    }
                }
                return hashMap;
            }
        }).create();
    }

    public static <T> T fromJson(String json, Class<T> type) throws JsonIOException, JsonSyntaxException {
        return create().fromJson(json, type);
    }

    public static <T> T fromJson(String json, Type type) {
        return create().fromJson(json, type);
    }

    public static <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return create().fromJson(reader, typeOfT);
    }

    public static <T> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException {
        return create().fromJson(json, classOfT);
    }

    public static <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return create().fromJson(json, typeOfT);
    }

    public static String toJson(Object src) {
        return create().toJson(src);
    }

    public static String toJson(Object src, Type typeOfSrc) {
        return create().toJson(src, typeOfSrc);
    }

    public static String formatJson(String json) {
        try {
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(json);

            return JsonConvertor.getInstance().toJson(je);
        } catch (Exception e) {
            return json;
        }
    }

    public static String formatJson(Object src) {
        try {
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(toJson(src));
            return JsonConvertor.getInstance().toJson(je);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
