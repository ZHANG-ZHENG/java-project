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
		// �½�һ��HashSet
		Set<Integer> hashSet = CollUtil.newHashSet(1, 2, 3);
		Set<Integer> linkedHashSet = CollUtil.newLinkedHashSet(4, 2, 3);

		// ��������ȡ����
		Collection<Integer> intersection = CollUtil.intersection(hashSet, linkedHashSet);

		// ��������ȡ����
		Collection<Integer> union = CollUtil.union(hashSet, linkedHashSet);

		// ��������ȡ�
		Collection<Integer> disjunction = CollUtil.disjunction(hashSet, linkedHashSet);

		// �ж�һ�������Ƿ�Ϊnull��ռ�
		boolean empty = CollUtil.isEmpty(hashSet);

		// ����һ��ArrayList
		List<Integer> arrayList = ListUtil.toList(1, 2, 3);

		// ����һ��LinkedList
		List<Integer> linkedList = ListUtil.toLinkedList(1, 2, 3);

		// ����һ��map
		Map<String, Object> map = MapUtil.<String, Object>builder().put("a", 1).put("b", 2).build();
	}
}
