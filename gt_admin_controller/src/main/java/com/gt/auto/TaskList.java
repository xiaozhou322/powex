package com.gt.auto;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gt.Enum.ValidateMailStatusEnum;
import com.gt.Enum.ValidateMessageStatusEnum;
import com.gt.entity.Fvalidateemail;
import com.gt.entity.Fvalidatemessage;
import com.gt.service.front.FrontValidateService;

public class TaskList {
	@Autowired
	private FrontValidateService frontValidateService ;
	private LinkedList<Integer> messageList ;
	private LinkedList<Integer> mailList ;

	public TaskList(){
		messageList = new LinkedList<Integer>() ;
		mailList = new LinkedList<Integer>() ;
		
	}
	
	public void init(){
		readData() ;
	}
	private void readData(){
		List<Fvalidatemessage> list1 = this.frontValidateService.findFvalidateMessageByProperty("fstatus", ValidateMessageStatusEnum.Not_send) ;
		
		for (Fvalidatemessage fvalidatemessage : list1) {
			messageList.add(fvalidatemessage.getFid()) ;
		}
		List<Fvalidateemail> list2 = this.frontValidateService.findValidateMailsByProperty("fstatus", ValidateMailStatusEnum.Not_send) ;
		for(Fvalidateemail fvalidateemail:list2){
			this.mailList.add(fvalidateemail.getFid()) ;
		}
	}

	public synchronized Integer getOneMessage() {
		synchronized (messageList) {
			Integer id = null ;
			if(messageList.size()>0){
				id = messageList.pop() ;
			}
			return id ;
		}
	}
	
	public synchronized void returnMessageList(Integer id){
		synchronized (messageList) {
			messageList.add(id) ;
		}
	}
	
	public Integer getOneMail() {
		synchronized(this.mailList){
			Integer id = null ;
			if(this.mailList.size()>0){
				id = mailList.pop() ;
			}
			return id ;
		}
	}
	
	public synchronized void returnMailList(Integer id){
		synchronized(this.mailList){
			mailList.add(id) ;
		}
	}
	
}
