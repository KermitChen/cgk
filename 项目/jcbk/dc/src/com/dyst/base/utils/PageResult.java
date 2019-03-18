package com.dyst.base.utils;

import java.util.ArrayList;
import java.util.List;

public class PageResult {

	//总记录数
	private long totalCount;
	//当前页号
	private int pageNo;
	//总页数
	private int totalPageCount;
	//页大小
	private int pageSize;
	//列表记录
	private List items;
	//设置默认页起始页
	public static int DEFAULT_PAGE_NO=1;
	//设置默认页大小
	public static int DEFAULT_PAGE_SIZE=10;
	//错误信息
	private String errorMessage;
	//多维碰撞 查询标志
	private String dwpzQueryFlag;
	
	
	public PageResult() {
	}

	//计算总页数
	public PageResult(long totalCount, int pageNo, int pageSize, List items) {
		this.items = items==null?new ArrayList():items;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		if(totalCount != 0){
			//计算总页数
			int tem = (int)totalCount/pageSize;
			this.totalPageCount = (totalCount%pageSize==0)?tem:(tem+1);
			this.pageNo = pageNo;
		} else {
			this.pageNo = 0;
		}
	}
	//若产生错误
	public PageResult(String errorMess){
		this.errorMessage = errorMess;
	}
	
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageNo() {
		if(pageNo<1){
			pageNo=DEFAULT_PAGE_NO;
		}
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getTotalPageCount() {
		return totalPageCount;
	}
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	public int getPageSize() {
		if(pageSize<1){
			pageSize=DEFAULT_PAGE_SIZE;
		}
		return pageSize;
	}
	public int getPageSize4es() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List getItems() {
		return items;
	}
	public void setItems(List items) {
		this.items = items;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getDwpzQueryFlag() {
		return dwpzQueryFlag;
	}

	public void setDwpzQueryFlag(String dwpzQueryFlag) {
		this.dwpzQueryFlag = dwpzQueryFlag;
	}

	@Override
	public String toString() {
		return "PageResult [totalCount=" + totalCount + ", pageNo=" + pageNo
				+ ", totalPageCount=" + totalPageCount + ", pageSize="
				+ pageSize + ", errorMessage="
				+ errorMessage + "]";
	}
	
}
