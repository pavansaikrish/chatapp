package com.infy.socket;

import java.io.Serializable;
import java.util.Vector;

public class Message implements Serializable{
    
    private static final long serialVersionUID = 1L;
    public String type, sender, content, recipient;

	public String userForGroupChatlist;
	
    
    public Message(String type, String sender, String content, String recipient,String userForGroupChatList){
        this.type = type; this.sender = sender; this.content = content; this.recipient = recipient;
        this.userForGroupChatlist=userForGroupChatList;
    }
        

	@Override
	public String toString() {
		return " [type=" + type + ", sender=" + sender + ", content="
				+ content + ", recipient=" + recipient
				+ ", userForGroupChatlist=" + userForGroupChatlist + "]";
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getType() {
		return type;
	}

	public String getSender() {
		return sender;
	}

	public String getContent() {
		return content;
	}

	public String getRecipient() {
		return recipient;
	}
    
}
