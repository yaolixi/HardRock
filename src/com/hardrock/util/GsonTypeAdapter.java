package com.hardrock.util;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.types.ObjectId;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonTypeAdapter 
{
//	private static final DateTimeFormatter formatter = DateTimeFormat.forPattern(MONGO_DATE_TIME_FORMAT_PATTERN);
	
	private static final String MONGO_DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	
	public static enum GsonAdapterType
	{
		DESERIALIZER,
		SERIALIZER,
		BSON_DESERIALIZER,
		BSON_SERIALIZER
	}
	
	/**
	 * GsonTypeAdapter.getGsonBuilder
	 * @param g - Deserialize from JSON or Serialize to JSON
	 * @return GsonBuilder object with type adapters for MongoDB registered
	 */
	public static GsonBuilder getGsonBuilder(GsonAdapterType g){
		GsonBuilder gb = new GsonBuilder();
		switch (g)
		{
			case DESERIALIZER:
				gb.registerTypeAdapter(ObjectId.class, new GsonTypeAdapter.ObjectIdDeserializer());
				gb.registerTypeAdapter(Date.class, new GsonTypeAdapter.DateDeserializer());
				break;
			case BSON_DESERIALIZER:
				gb.registerTypeAdapter(ObjectId.class, new GsonTypeAdapter.ObjectIdDeserializer());
				gb.registerTypeAdapter(Date.class, new GsonTypeAdapter.BsonDateDeserializer());				
				break;
			case SERIALIZER:
				gb.registerTypeAdapter(ObjectId.class, new GsonTypeAdapter.ObjectIdSerializer());
				gb.registerTypeAdapter(Date.class, new GsonTypeAdapter.DateSerializer());
				break;
			case BSON_SERIALIZER:
				gb.registerTypeAdapter(ObjectId.class, new GsonTypeAdapter.ObjectIdSerializer());
				gb.registerTypeAdapter(Date.class, new GsonTypeAdapter.BsonDateSerializer());
				break;
			default:
				return null;
		}		
		return gb;
	}
	
	public static GsonBuilder getGsonBuilder(GsonAdapterType g, boolean isReadFromMongo){
		GsonBuilder gb = new GsonBuilder();
		
		switch (g)
		{
			case DESERIALIZER:
				gb.registerTypeAdapter(ObjectId.class, new GsonTypeAdapter.ObjectIdDeserializer());
				gb.registerTypeAdapter(Date.class, new GsonTypeAdapter.DateDeserializer(isReadFromMongo));				
				break;
			case SERIALIZER:
				gb.registerTypeAdapter(ObjectId.class, new GsonTypeAdapter.ObjectIdSerializer());
				gb.registerTypeAdapter(Date.class, new GsonTypeAdapter.DateSerializer(isReadFromMongo));
				break;
			default:
				return null;
		}		
		return gb;
	}
	
	/**
	 * ObjectIdDeserializer.deserialize
	 * @return Bson.Types.ObjectId
	 */
	public static class ObjectIdDeserializer implements JsonDeserializer<ObjectId> 
	{
		@Override
		public ObjectId deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			try
			{
				return new ObjectId(json.getAsJsonObject().get("$oid").getAsString());
			}
			catch (Exception e)
			{
				return null;
			}
		}
	}
	
	/**
	 * ObjectIdSerializer.serialize
	 * @return $oid JsonObject from BSON ObjectId
	 */
	public static class ObjectIdSerializer implements JsonSerializer<ObjectId> 
	{
		@Override
		public JsonElement serialize(ObjectId id, Type typeOfT, JsonSerializationContext context)
		{
			JsonObject jo = new JsonObject();
			jo.addProperty("$oid", id.toStringMongod());
			return jo;
		}
	}	
	
	/**
	 * DateDeserializer.deserialize
	 * @return Java.util.Date
	 */
	public static class DateDeserializer implements JsonDeserializer<Date> 
	{
		private boolean isReadFromMongo = true;
		public DateDeserializer(boolean isReadFromMongo){
			this.isReadFromMongo = isReadFromMongo;
		}
		
		public DateDeserializer(){}
		
		@Override
		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException
			{
//				Date d = formatter.parseDateTime(json.getAsJsonObject().get("$date").getAsString()).toDate();
				Date d = DateUtil.parseMongoDateTime(json.getAsJsonObject().get("$date").getAsString());
				//加上8小时先，后续再根据本地时区来转换
				if(isReadFromMongo){
					return new Date(d.getTime() + 28800000L);
				}
				return d;
			}
	}
	
	public static class BsonDateDeserializer implements JsonDeserializer<Date> {

		@Override
		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			Date d = new Date(json.getAsJsonObject().get("$date").getAsLong());
			return d;
		}
	}
	
	/**
	 * DateSerializer.serialize
	 * @return date JsonElement
	 */
	public static class DateSerializer implements JsonSerializer<Date> {
		
		private boolean isReadFromMongo = true;
		public DateSerializer(boolean isReadFromMongo){
			this.isReadFromMongo = isReadFromMongo;
		}
		
		public DateSerializer(){}
		
		@Override
		public JsonElement serialize(Date date, Type typeOfT, JsonSerializationContext context)
		{
			//减上8小时先，后续再根据本地时区来转换
			if(isReadFromMongo){
				date = new Date(date.getTime() - 28800000L);
			}
			
		    JsonObject jo = new JsonObject();
			jo.addProperty("$date", new SimpleDateFormat(MONGO_DATE_TIME_FORMAT_PATTERN).format(date));
			return jo;
		}
	}
	
	public static class BsonDateSerializer implements JsonSerializer<Date> {
		@Override
		public JsonElement serialize(Date date, Type typeOfT, JsonSerializationContext context)
		{
		    JsonObject jo = new JsonObject();
			jo.addProperty("$date", date.getTime());
			return jo;
		}
	}	
}
