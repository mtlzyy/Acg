package com.acg.email;



public class SendMail {
	public void sendActivationMail(String email,String activationInfo){
		MailSenderInfo mailInfo = new MailSenderInfo(); 
	    mailInfo.setMailServerHost("smtp.163.com");   
	    mailInfo.setMailServerPort("25");    
	    mailInfo.setValidate(true);  
	    mailInfo.setUserName("yyzRandom@163.com");  
	    mailInfo.setPassword("Zyyprojtest1");
	    mailInfo.setFromAddress("mohang163@163.com");    
	    mailInfo.setToAddress(email); 
	    mailInfo.setSubject("ÑéÖ¤ÓÊ¼þ");  
	    mailInfo.setContent(activationInfo);    
	    SimpleMailSender sms = new SimpleMailSender();   
	    sms.sendTextMail(mailInfo);
	  
	}
}
