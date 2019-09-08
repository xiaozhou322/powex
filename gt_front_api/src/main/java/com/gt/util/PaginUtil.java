package com.gt.util;

public class PaginUtil {

	//总页码
	public static int totalPage(int total,int maxResults){
		return total/maxResults+(total%maxResults==0?0:1) ;
	}
	//分页第一个
	public static int firstResult(int currentPage,int maxResults){
		int ret = (currentPage-1)*maxResults ;
		if(ret<0) ret = 0 ;
		return ret ;
	}
	
	//生成分页
	public static String generatePagin(int total,int currentPage,String path){
		if(total<=0){
			return "" ;
		}
		
		StringBuffer sb = new StringBuffer() ;
		sb.append("<ul class='pagination'>");
		if(currentPage==1){
			sb.append("<li class='active'><a  href='javascript:void(0)'>1</a></li>") ;
		}else{
			sb.append("<li><a href='"+path+"currentPage=1'>&lt</a></li>") ;
			sb.append("<li><a href='"+path+"currentPage=1'>1</a></li>") ;
		}
		
		if(currentPage==2){
			sb.append("<li class='active'><a href='javascript:void(0)'>2</a></li>") ;
		}else if(total>=2){
			sb.append("<li><a href='"+path+"currentPage=2'>2</a></li>") ;
		}
		
		if(currentPage>=7){
			sb.append("<li><a href='javascript:void(0)'>...</a></li>") ;
		}
		
		//前三页
		int begin = currentPage-3 ;
		begin = begin<=2?3:begin ;
		for(int i=begin;i<currentPage;i++){
			sb.append("<li><a href='"+path+"currentPage="+i+"'>"+i+"</a></li>") ;
		}
		
		if(currentPage!=1&&currentPage!=2){
			sb.append("<li class='active'><a href='javascript:void(0)'>"+currentPage+"</a></li>") ;
		}
		
		//后三页
		begin = currentPage+1;
		begin = begin<=2?3:begin ;
		int end = currentPage+4 ;
		if(currentPage<6){
			int tInt = 6- currentPage ;
			end = end+((tInt>3?3:tInt)) ;
		}
		for(int i=begin;i<end&&i<=total;i++){
			sb.append("<li><a href='"+path+"currentPage="+i+"'>"+i+"</a></li>") ;
		}
		
		
		/*if(total-currentPage==4){
		sb.append("<li><a href='"+path+"currentPage="+total+"'>"+total+"</a></li>") ;
	}else*/ if(total-currentPage>3){
		sb.append("<li><a href='javascript:void(0)'>...</a></li>") ;
	}
		
		if(total>=11&&total-currentPage>4){
			sb.append("<li><a href='"+path+"currentPage="+total+"'>"+total+"</a></li>") ;
		}
		
		if(currentPage<total){
			sb.append("<li><a href='"+path+"currentPage="+total+"'>&gt</a></li>") ;
		}
		
		sb.append("</ul>");
		
		return sb.toString() ;
	}
	
	
}
