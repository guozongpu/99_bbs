package com.util;

import java.util.ArrayList;
import java.util.List;

import com.entity.Article;
import com.entity.Forum;

public class SortUtil {
	public static List<Article> sortArticles(List<Article> list) {
		Article temp = new Article();
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if (list.get(i).getReplyNumber() < list.get(j).getReplyNumber()) {
					temp = list.get(i);
					list.remove(i);
					list.add(i, list.get(j - 1));
					list.remove(j);
					list.add(j, temp);
				}
			}
		}
		return list;
	}
	
	public static List<Forum> sortForums(List<Forum> list) {
		Forum temp = new Forum();
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if (list.get(i).getfloor() < list.get(j).getfloor()) {
					temp = list.get(i);
					list.remove(i);
					list.add(i, list.get(j - 1));
					list.remove(j);
					list.add(j, temp);
				}
			}
		}
		return list;
	}
	public static void main(String[] args) {
		Article a1 = new Article();
		a1.setContent("I'm a1");
		a1.setReplyNumber(1);
		Article a2 = new Article();
		a2.setContent("I'm a2");
		a2.setReplyNumber(2);
		Article a3 = new Article();
		a3.setContent("I'm a3");
		a3.setReplyNumber(3);
		Article a4 = new Article();
		a4.setContent("I'm a4");
		a4.setReplyNumber(4);
		Article a5 = new Article();
		a5.setContent("I'm a5");
		a5.setReplyNumber(5);
		List<Article> list = new ArrayList<Article>();
		list.add(a1);
		list.add(a2);
		list.add(a3);
		list.add(a4);
		list.add(a5);
		for (int i = 0; i < list.size(); i++)
			System.out.println(list.get(i).getContent());
		sortArticles(list);
		for (int i = 0; i < list.size(); i++)
			System.out.println(list.get(i).getContent());
	}
}
