package me.kreaktech.unility.entity.events.DefaultUniversity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.kreaktech.unility.constants.Enum;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class DefaultActivityData {
	private String date;
	private String organizers;
	private String details;
	private String time;
	private Enum.PhysicalStatus physicalStatus;
	private String languages;
	private String hasRewards;
}