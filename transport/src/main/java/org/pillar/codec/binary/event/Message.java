package org.pillar.codec.binary.event;

import java.io.Serializable;

/**
 * Created by pillar on 2015/8/13.
 * 通用信息
 */
public class Message<T extends MessageBody> implements Serializable {

	private MessageHead head;

	private T body;

	public Message() {
		//
	}

	public Message(MessageHead head, T body) {
		this.head = head;
		this.body = body;
	}

	public MessageHead getHead() {
		return head;
	}

	public T getBody() {
		return body;
	}

	public void setHead(MessageHead head) {
		this.head = head;
	}

	public void setBody(T body) {
		this.body = body;
	}


	@Override
	public String toString() {
		return "Message{" +
				"head=" + head +
				", body=" + body +
				'}';
	}

	/**
	 * NullObject Pattern for Message
	 *
	 * @param <T> type parameter
	 */
	public static class NullMessage<T extends MessageBody> extends Message<T> implements Serializable {


		@Override
		public String toString() {
			return "NullMessage";
		}
	}

	/**
	 * 创建Null Object
	 *
	 * @return 返回Message
	 */
	public Message createNulMessage() {
		return new NullMessage();
	}
}
