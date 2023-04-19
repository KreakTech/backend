package me.kreaktech.unility.entity.events.DefaultUniversity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class DefaultAnnouncementData {
	private String title;
	private String link;
}