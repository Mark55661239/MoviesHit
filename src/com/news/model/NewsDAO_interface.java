package com.news.model;

import java.util.List;

public interface NewsDAO_interface {
	public void insert(NewsVO newsVO);
    public void update(NewsVO newsVO);
    public void delete(Integer news_no);
    public NewsVO findByPrimaryKey(Integer news_no);
    public List<NewsVO> getAll();
    //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
//  public List<NewsVO> getAll(Map<String, String[]> map); 
}