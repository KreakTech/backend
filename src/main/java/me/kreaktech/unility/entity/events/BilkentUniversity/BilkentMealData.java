package me.kreaktech.unility.entity.events.BilkentUniversity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.kreaktech.unility.entity.events.DefaultUniversity.DefaultMealData;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BilkentMealData extends DefaultMealData {
	private Integer energy;
	private Integer carbohydrate;
	private Integer protein;
	private Integer fat;
}
