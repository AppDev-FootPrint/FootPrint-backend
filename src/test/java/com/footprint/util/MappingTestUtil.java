package com.footprint.util;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by ShinD on 2022/06/26.
 */
public class MappingTestUtil {


	/**
	 * @param func 매핑할 함수
	 * @param <O> (Original) - 기존 Class
	 * @param <M> (Mapping) - 매핑될 Class
	 */
	public static  <O, M> List<M> mappingToList(List<O> list, Function<O, M> func){
		return list.stream()
			.map(func)
			.collect(Collectors.toList());
	}


	/**
	 * @param func 매핑할 함수
	 * @param <O> (Original) - 기존 Class
	 * @param <M> (Mapping) - 매핑될 Class
	 */
	public static <O, M> Set<M> mappingToSet(List<O> list, Function<O, M> func){
		return list.stream()
			.map(func)
			.collect(Collectors.toSet());
	}
}
