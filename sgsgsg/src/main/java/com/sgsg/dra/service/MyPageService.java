package com.sgsg.dra.service;

import java.util.List;
import java.util.Map;

import com.sgsg.dra.domain.MyPoint;


public interface MyPageService {
	public int dataCount(Map<String, Object> map);
	public List<MyPoint> listPoint(Map<String, Object> map);
	public MyPoint readPoint(Map<String, Object> map);
}
