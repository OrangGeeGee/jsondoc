package org.jsondoc.core.pojo;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.UUID;

import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.util.JSONDocUtils;

public class ApiResponseObjectDoc {
	public String jsondocId = UUID.randomUUID().toString();
	private String object;
	private String multiple;

	public static ApiResponseObjectDoc buildFromAnnotation(ApiResponseObject annotation, Method method) {
		return new ApiResponseObjectDoc(getReturnObject(method), String.valueOf(JSONDocUtils.isMultiple(method)));
	}
	
	public static String getReturnObject(Method method) {
		if(Collection.class.isAssignableFrom(method.getReturnType())) {
			if(method.getGenericReturnType() instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) method.getGenericReturnType();
				Type type = parameterizedType.getActualTypeArguments()[0];
				Class<?> clazz = (Class<?>) type;
				return JSONDocUtils.getObjectNameFromAnnotatedClass(clazz);
			} else {
				return JSONDocUtils.UNDEFINED;
			}
		} else if(method.getReturnType().isArray()) {
			Class<?> classArr = method.getReturnType();
			return JSONDocUtils.getObjectNameFromAnnotatedClass(classArr.getComponentType());
			
		}
		return JSONDocUtils.getObjectNameFromAnnotatedClass(method.getReturnType());
	}
	
	public ApiResponseObjectDoc(String object, String multiple) {
		super();
		this.object = object;
		this.multiple = multiple;
	}

	public String getObject() {
		return object;
	}

	public String getMultiple() {
		return multiple;
	}

}