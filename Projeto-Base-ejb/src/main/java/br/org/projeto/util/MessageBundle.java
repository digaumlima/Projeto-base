package br.org.projeto.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class MessageBundle {
	
	private static ResourceBundle bundle = ResourceBundle.getBundle("messages_labels");
	
	/**
	 * 
	 * @param key
	 * @param params
	 * @return
	 */
	public static String getMensagemFromBundle(String key, String...params){
		if(MessageBundle.bundle.containsKey(key)){
			if(params.length > 0){
				return MessageFormat.format(MessageBundle.bundle.getString(key), (Object[])params);
			}
			
			return MessageBundle.bundle.getString(key);
		}
		
		return key;
	}
}
