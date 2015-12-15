/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.researchersexchange.mail;

public class MailClass {
    public static void sendMail(String body, String from,String sub,String to) {
		// TODO Auto-generated method stub
		/*String body ="Hello Class we are trying to test java mail";//Body of the mail
		String from ="nbadvathsan@gmail.com"; // email of sender
		String sub ="Testing";// subject of the mail*/
		String id = "nbadvathsan@gmail.com"; // email of the sender
		String pass = "magic1nbad"; // password of the sender's email
		//String to = "vathsan.vk@gmail.com";// email of the reciever*/
		GMailSender sender = new GMailSender(id, pass);
		
		try {
			sender.sendMail(sub + " " + from, body, "nbadvathsan@gmail.com", to);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
