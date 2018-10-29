package com.utils.ui;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public abstract class ListGroupAdapter extends BaseAdapter {
	
	
 	public Context mContext;
	
	
	
	public int getSection(){//获取切片
		return 1;
	}
	
	/**
	 * 根据切片section 获取对应的 row item
	 * @param section
	 * @return
	 */
	public abstract int getRow(int section);

	@Override
	final public int getCount() {
		int section = getSection();
		int count = 0;
		for (int i = 0; i < section; i++) {
			int header = isGroupHeaderForSection(i)?1:0;
			int footer = isGroupFooterForSection(i)?1:0;
			count = count + getRow(i) + header + footer;
		}
		return count;
	}

	
	@Override
	final public int getItemViewType(int position) {
		 IndexPath indexPath = findIndexPathForPosition(position);
		 if (isGroupHeaderForSection(indexPath.section) && indexPath.row == 0 ) {//有头
			 return getItemHeaderViewType(indexPath);
		 }
		 
		 if (isGroupFooterForSection(indexPath.section)) {//有尾
			 int row = getRow(indexPath.section);
			 int header = isGroupHeaderForSection(indexPath.section)?1:0;
			 if (row == indexPath.row - header) {//到尾部了
				return getItemFooterViewType(indexPath);
			}
		 }
		 return getItemViewType(indexPath);
	}

	
	/**
	 * 获取 view type的总数
	 */
	@Override
	public int getViewTypeCount() {
		return 3;
	}
	
	/**
	 * 
	 * @param indexPath 改位置的 view type
	 * @return
	 */
	public int getItemViewType(IndexPath indexPath){
		return 0;
	}
	
	/**
	 * 
	 * @param indexPath section Footer 位置的 view type
	 * @return
	 */
	public int getItemFooterViewType(IndexPath indexPath){
		return 1;
	}
	/**
	 * 
	 * @param indexPath  section Header position view type
	 * @return
	 */
	public int getItemHeaderViewType(IndexPath indexPath){
		return 2;
	}
	
	
	/**
	 * 根据 postion 计算 indexPath
	 * @param position
	 * @return
	 */
	final public IndexPath findIndexPathForPosition(int position){
		position -= getListHeadCount();
		int section = getSection();
		int count = 0;
		int sec = 0;
		for (int i = 0; i < section; i++) {
			int header = isGroupHeaderForSection(i)?1:0;
			int footer = isGroupFooterForSection(i)?1:0;
			
			count = count + getRow(i) + header + footer;
			if (count > position) {
				sec = i;
				count = count - getRow(i) - header - footer;
				break;
			}
		}
		
		IndexPath indexPath = new IndexPath();
		indexPath.section = sec;
		indexPath.row = position-count;
		
		Log.d("demo", "" +indexPath +" po: "+ position +" getRow:"+getRow(indexPath.section));
		
		return indexPath;
	}
	
	/**
	 * listview  header 的数量决定 position 的位置
	 * @return
	 */
	public int getListHeadCount(){
		return 0;
	}
	

	/**
	 * 次getview 不能被重写 
	 */
	@Override
	final public View getView(int position, View convertView, ViewGroup parent) {
		
		 IndexPath indexPath = findIndexPathForPosition(position);
		 
		 int header = isGroupHeaderForSection(indexPath.section)?1:0;
		 
		 if (isGroupHeaderForSection(indexPath.section) && indexPath.row == 0) {//有头
			 return getHeaderView(position, indexPath.section, convertView, parent);
		 }
		 
		 if (isGroupFooterForSection(indexPath.section)) {//有尾
			 int row = getRow(indexPath.section);
			 if (row == indexPath.row - header) {//到尾部了
				return getFooterView(position, indexPath.section, convertView, parent);
			}
		 }
		 
		 if (isGroupHeaderForSection(indexPath.section)) {//包含header row位置 -1 和 list index 保持一致
			 indexPath.row -= 1;
		 }
		 
		 //内容item
		 return getView(position, indexPath, convertView, parent);
		
	}


	
	/**
	 * 该组是否包含HeadView
	 * @param section
	 * @return
	 */
	public boolean isGroupHeaderForSection(int section){
		return false;
	}
	/**
	 * 该组是否包含FooterVew
	 * @param section
	 * @return
	 */
	public boolean isGroupFooterForSection(int section){
		return false;
	}
	
	/**
	 * body item的 view 
	 * @param position
	 * @param indexPath
	 * @param convertView
	 * @param parent
	 * @return
	 */
	public abstract View getView(int position,IndexPath indexPath,View convertView,ViewGroup parent);
	
	public View getHeaderView(int position,int section,View convertView,ViewGroup parent){
		if (isGroupHeaderForSection(section)) {
			TextView header = null;
			if (convertView!=null) {
				header =(TextView) convertView;
			}else {
				header = new TextView(mContext);
			}
			header.setText("第:"+section+" header");
			return header;
		}
		return null;
	}
	public View getFooterView(int position,int section,View convertView,ViewGroup parent){
		if (isGroupFooterForSection(section)) {
			TextView footer =null;
			if (convertView != null) {
				footer = (TextView) convertView;
			}else {
				footer = new TextView(mContext);	
			}
			footer.setText("第:"+section +" footer");
			return footer;
		}
		return null;
	}
	
	
	public static class IndexPath{
		public int row;
		public int section;
		
		@Override
		public String toString() {
			return "IndexPath [row=" + row + ", section=" + section + "]";
		}
		
		
		
	}
	
}
