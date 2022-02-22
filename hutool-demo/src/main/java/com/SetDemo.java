package com;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;

public class SetDemo {
	public static void main(String[] args) {
		// 新建一个HashSet
		Set<Integer> hashSet = CollUtil.newHashSet(1, 2, 3);
		Set<Integer> linkedHashSet = CollUtil.newLinkedHashSet(4, 2, 3);

		// 两个集合取交集
		Collection<Integer> intersection = CollUtil.intersection(hashSet, linkedHashSet);

		// 两个集合取并集
		Collection<Integer> union = CollUtil.union(hashSet, linkedHashSet);

		// 两个集合取差集
		Collection<Integer> disjunction = CollUtil.disjunction(hashSet, linkedHashSet);

		// 判断一个集合是否为null或空集
		boolean empty = CollUtil.isEmpty(hashSet);

		// 创建一个ArrayList
		List<Integer> arrayList = ListUtil.toList(1, 2, 3);

		// 创建一个LinkedList
		List<Integer> linkedList = ListUtil.toLinkedList(1, 2, 3);

		// 创建一个map
		Map<String, Object> map = MapUtil.<String, Object>builder().put("a", 1).put("b", 2).build();
	}
}
