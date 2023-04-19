package me.kreaktech.unility.entity.events;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.kreaktech.unility.constants.Enum.EventType;
import me.kreaktech.unility.constants.Enum.Language;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventBody {

	private Timestamp date;
	private EventType type;
	private Language language;
	private String universityName;
	// * Static universityId to be added after validity of universityName is checked
	private Integer universityId = -999;
	private Object data;

}
