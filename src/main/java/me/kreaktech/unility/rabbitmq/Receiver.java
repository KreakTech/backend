package me.kreaktech.unility.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import me.kreaktech.unility.entity.events.EventBody;

@Component
@AllArgsConstructor
public class Receiver implements MessageListener {
    private final HandleEvent handleEvent;
	private final Gson gson;

	@Autowired
	public Receiver(HandleEvent handleEvent) {
		this.handleEvent = handleEvent;
		this.gson = new Gson();
	}

	@Override
	public void onMessage(Message message) {
		try {
			String messageString = new String(message.getBody());
			// Deserialize the JSON message into a Java object
			EventBody eventBody = gson.fromJson(messageString, EventBody.class);
			handleEvent.processEvent(eventBody);
		} catch (Exception ignored) {
			System.out.println(ignored.getMessage());
		}

	}
}