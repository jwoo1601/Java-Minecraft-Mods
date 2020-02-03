package caramel.nbt;

import static caramel.parser.FormatParser.tryParseValueOf;
import static caramel.reflection.ReflectionUtil.getAllAnnotatedFields;
import static caramel.reflection.ReflectionUtil.getAllFieldsUpTo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import caramel.attribute.nbt.NBTAttribute;
import caramel.attribute.nbt.NBTByteArrayAttribute;
import caramel.attribute.nbt.NBTByteAttribute;
import caramel.attribute.nbt.NBTCompoundAttribute;
import caramel.attribute.nbt.NBTDoubleAttribute;
import caramel.attribute.nbt.NBTFloatAttribute;
import caramel.attribute.nbt.NBTIntArrayAttribute;
import caramel.attribute.nbt.NBTIntAttribute;
import caramel.attribute.nbt.NBTListAttribute;
import caramel.attribute.nbt.NBTLongAttribute;
import caramel.attribute.nbt.NBTShortAttribute;
import caramel.attribute.nbt.NBTStringAttribute;
import caramel.nbt.NBTSerializable.NBTTag;
import caramel.nbt.exception.*;
import caramel.parser.exception.IllegalFormatException;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class SerializationHelper {

	public static final <T extends Object> NBTTagCompound serialize(@Nonnull T target) {
		if (target == null)
			throw new NullPointerException();
		
		if (target instanceof INBTSerializable) {
			INBTSerializable serial = (INBTSerializable) target;
			NBTTagCompound tagCompound = new NBTTagCompound();
			
			serial.write(tagCompound);
			return tagCompound;
		}
		
		NBTSerializable serial = target.getClass().getAnnotation(NBTSerializable.class);
		
		if (serial == null)
			return null;
		
		if (target.getClass().getDeclaredAnnotation(NonSerialized.class) != null)
			return null;
		
		NBTTagCompound rootTag = new NBTTagCompound();
		
		Set<String> keySet = new HashSet<String>();		
		AdditionalTags tags = target.getClass().getAnnotation(AdditionalTags.class);
		
		// Process AdditionalTags
		if (tags != null) {
			List<Field> targetFields = getAllFieldsUpTo(target.getClass());
			
			for (NBTTag nbttag : tags.tags()) {			
				String keyRaw = nbttag.key();
				String keyResult = tryParseValueOf(target, targetFields, keyRaw).toString();
			
				String valueRaw = nbttag.value();
				Object valueResult = tryParseValueOf(target, targetFields, valueRaw);
				
				if (valueResult instanceof String && valueResult.equals(valueRaw))
					valueResult = parseToPrimitive(nbttag.type(), valueRaw);
				
				addTag(rootTag, keyResult, valueResult);
				keySet.add(keyResult);
			}
		}
		
		List<Field> fields = getAllAnnotatedFields(target.getClass(), NBTTag.class);
		
		if (!fields.isEmpty()) {
			List<NBTAttribute> attrList = getSerializedResult(target, fields, keySet);
			
			for (NBTAttribute attr : attrList)
				rootTag.setTag(attr.getKey(), (NBTBase) attr.getValue());
		}
			
		return rootTag;
	}
	
	public static final <T> T deserialize(Class<?> clazz, NBTTagCompound tag) {
		return null;
	}
	
	private static final void addTag(NBTTagCompound tag, String key, Object value) {
		
		if (value instanceof Byte)
			tag.setByte(key, (Byte) value);
		
		else if (value instanceof byte[])
			tag.setByteArray(key, (byte[]) value);
		
		else if (value instanceof Short)
			tag.setShort(key, (Short) value);
		
		else if (value instanceof Integer)
			tag.setInteger(key, (Integer) value);
		
		else if (value instanceof int[])
			tag.setIntArray(key, (int[]) value);
		
		else if (value instanceof Long)
			tag.setLong(key, (Long) value);
		
		else if (value instanceof Float)
			tag.setFloat(key, (Float) value);
		
		else if (value instanceof Double)
			tag.setDouble(key, (Double) value);
		
		else if (value instanceof NBTStringAttribute)
			tag.setString(key, (String) value);
		
		else if (value instanceof List) {
			List<NBTBase> list = (List<NBTBase>) value;
			NBTTagList tagList = new NBTTagList();
			
			for (NBTBase comp : list)
				tagList.appendTag(comp);
			
			tag.setTag(key, tagList);				
		}
		
		else {
			NBTTagCompound tagCompound = serialize(value);
			
			if (tagCompound != null)
				tag.setTag(key, tagCompound);
		}
	}
	
	private static final Object parseToPrimitive(NBTTagType type, String target) {
		String decodeTarget = target.trim();
		
		switch (type) {
		
		case TAG_BYTE:
			return Byte.decode(decodeTarget);
			
		case TAG_BYTE_ARRAY:
			int idx_ob = decodeTarget.indexOf("{");
			
			if (idx_ob == -1)
				return Byte.decode(decodeTarget);
			
			
			int idx_cb = decodeTarget.indexOf("}");
			
			if (idx_cb == -1)
				throw new IllegalFormatException();
			
			String[] strValues = decodeTarget.substring(idx_ob, idx_cb).split(",");
			byte[] values = new byte[strValues.length];
			
			for (int i=0; i < values.length; i++)
				values[i] = Byte.decode(strValues[i]);
			
			return values;
			
		case TAG_SHORT:
			return Short.decode(decodeTarget);
			
		case TAG_INT:
			return Integer.decode(decodeTarget);
			
		case TAG_INT_ARRAY:
			int idx_i_ob = decodeTarget.indexOf("{");
			
			if (idx_i_ob == -1)
				return Integer.decode(decodeTarget);
			
			
			int idx_i_cb = decodeTarget.indexOf("}");
			
			if (idx_i_cb == -1)
				throw new IllegalFormatException();
			
			String[] strValues_i = decodeTarget.substring(idx_i_ob, idx_i_cb).split(",");
			int[] values_i = new int[strValues_i.length];
			
			for (int j=0; j < values_i.length; j++)
				values_i[j] = Integer.decode(strValues_i[j]);
			
			return values_i;
			
		case TAG_LONG:
			return Long.decode(decodeTarget);
			
		case TAG_FLOAT:
			return Float.parseFloat(decodeTarget);
			
		case TAG_DOUBLE:
			return Double.parseDouble(decodeTarget);
			
			// Parsing this kind of tag is not supported on current Version but will be added on next Version.
		case TAG_LIST:
			throw new NotSupportedTypeException(type);
			
		case TAG_STRING:
			return target;
			
			// Parsing this kind of tag is not supported on current Version but will be added on next Version.
		case TAG_COMPOUND:
			throw new NotSupportedTypeException(type);
			
		default:
			throw new IllegalArgumentException("tag.type() is not a valid NBTTagType!");
		}
	}
	
	private static final List<NBTAttribute> getSerializedResult(Object obj, List<Field> fields, Set<String> keySet) {
		List<NBTAttribute> list = Lists.newArrayList();
		
		for (Field field : fields) {
			NBTTag tag = field.getAnnotation(NBTTag.class);
			
			if (tag == null)
				throw new NullPointerException();

			NBTAttribute attr = getAttribute(obj, field, fields, tag);
			
			if (keySet.isEmpty())
				list.add(attr);
			
			else {
				for (String key : keySet) {
					if (attr.getKey().equals(key))
						continue;
					
					list.add(attr);
				}
			}
		}
		
		return list;
	}
		
	private static final NBTAttribute getAttribute(Object obj, Field target, List<Field> fields, NBTTag tag) {
		String keyRaw = tag.key();
		String keyResult = null;
		
		if (keyRaw.equals(""))
			keyResult = target.getName();
		
		else {
			Object _key = tryParseValueOf(obj, fields, tag.key());
			Class _keyClass = _key.getClass();
		
			if (!String.class.isAssignableFrom(_keyClass))
				throw new IllegalTypeException(_keyClass);
		
			keyResult = (String) _key;
		}
		
		String valueRaw = tag.value();
		Object _value = null;
		
		if (valueRaw.equals("")) {
			target.setAccessible(true);
			
			try { _value = target.get(obj); }
			catch (Exception e) { throw new SerializationException(e); }
		}
		
		else
			_value = tryParseValueOf(obj, fields, tag.value());
		
		Class _valueClass = _value.getClass();
		NBTTagType tagType = tag.type();
		
		if (!isLegalType(_valueClass, tagType))
			throw new IllegalTypeException(_valueClass);
		
			
		switch (tagType) {
		
		case TAG_BYTE:
			byte value_b = (Byte) _value;
				
			return new NBTByteAttribute(keyResult, value_b);
			
		case TAG_BYTE_ARRAY:
			byte[] value_ba = (byte[]) _value;
			
			return new NBTByteArrayAttribute(keyResult, value_ba);
			
		case TAG_SHORT:
			short value_s = (Short) _value;
			
			return new NBTShortAttribute(keyResult, value_s);
			
		case TAG_INT:
			int value_i = (Integer) _value;
			
			return new NBTIntAttribute(keyResult, value_i);
			
		case TAG_INT_ARRAY:
			int[] value_ia = (int[]) _value;
			
			return new NBTIntArrayAttribute(keyResult, value_ia);
			
		case TAG_LONG:
			long value_l = (Long) _value;
			
			return new NBTLongAttribute(keyResult, value_l);
			
		case TAG_FLOAT:
			float value_f = (Float) _value;
			
			return new NBTFloatAttribute(keyResult, value_f);
			
		case TAG_DOUBLE:
			double value_d = (Double) _value;
			
			return new NBTDoubleAttribute(keyResult, value_d);
			
		case TAG_LIST:
			List value_ls = (List) _value;
			
			return new NBTListAttribute(keyResult, value_ls);
			
		case TAG_STRING:
			String value_str = (String) _value;
			
			return new NBTStringAttribute(keyResult, value_str);
			
		case TAG_COMPOUND:
			NBTTagCompound value_comp = (NBTTagCompound) serialize(_value);
			
			return new NBTCompoundAttribute(keyResult, value_comp);
			
		default:
			throw new IllegalArgumentException("tag.type() is not a valid NBTTagType!");
		}
	}
	
	public static final boolean isLegalType(Class<?> primitive, NBTTagType nbt) {
		
		switch (nbt) {
		
		case TAG_BYTE:
			
			if (Byte.class.isAssignableFrom(primitive))
				return true;
			
			return false;
			
		case TAG_BYTE_ARRAY:
			
			if (byte[].class.isAssignableFrom(primitive))
				return true;
			
			return false;
			
		case TAG_SHORT:
			
			if (Short.class.isAssignableFrom(primitive))
				return true;
			
			return false;
			
		case TAG_INT:
			
			if (Integer.class.isAssignableFrom(primitive))
				return true;
			
			return false;
			
		case TAG_INT_ARRAY:
			
			if (int[].class.isAssignableFrom(primitive))
				return true;
			
			return false;
			
		case TAG_LONG:
			
			if (Long.class.isAssignableFrom(primitive))
				return true;
			
			return false;
			
		case TAG_FLOAT:
			
			if (Float.class.isAssignableFrom(primitive))
				return true;
			
			return false;
			
		case TAG_DOUBLE:
			
			if (Double.class.isAssignableFrom(primitive))
				return true;
			
			return false;
			
		case TAG_LIST:
			
			if (List.class.isAssignableFrom(primitive))
				return true;
			
			return false;
			
		case TAG_STRING:
			
			if (String.class.isAssignableFrom(primitive))
				return true;
			
			return false;
			
		case TAG_COMPOUND:
			
			if (INBTSerializable.class.isAssignableFrom(primitive) || primitive.isAnnotationPresent(NBTSerializable.class))
				return true;
			
			return false;
			
		default:
			throw new IllegalArgumentException();
		}
	}
	
}
