package com.hsae.ims.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.hsae.ims.dto.ProjectDTO;
import com.hsae.ims.entity.Project;

/***
 * Entity 2 Dto convert.
 * @author caowei
 *
 */
public class EntityDtoConvert {

	private Object entity;

	private Object dto;

	public Object entity2Dto(Object entity, Object dto) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException {
		Class<?> clazz = entity.getClass();
		this.entity = entity;
		this.dto = dto;

		Class<?> dtoClazz = dto.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			invoke(clazz.getDeclaredMethods(), dtoClazz.getDeclaredMethods(), f.getName());
		}
		return dto;
	}

	private void invoke(Method[] methods, Method[] methodDtos, String name) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String upperName = Character.toUpperCase(name.charAt(0)) + name.substring(1);
		String setterName = "set" + upperName;
		String getterName = "get" + upperName;
		Method method = null;
		Method methodDto = null;
		method = this.getMethodByName(methods, getterName);
		methodDto = this.getMethodByName(methodDtos, setterName);
		if (method != null && methodDto != null) {
			methodDto.invoke(this.dto, method.invoke(this.entity, null));
		}
	}

	private Method getMethodByName(Method[] methods, String methodName) {
		for (Method m : methods) {
			if (m.getName().equals(methodName)) {
				return m;
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException {
		EntityDtoConvert c = new EntityDtoConvert();
		Project p = new Project();
		p.setId(11L);
		ProjectDTO d = new ProjectDTO();
		ProjectDTO dto = (ProjectDTO) c.entity2Dto(p, d);
		System.out.println(dto.getId());
	}
}
