package com.acolos.acolostracker.util;

import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class DeathTimer {
	
	public static String[] getDeathTime(LivingDeathEvent event) {
		Long worldtime = (long) event.getEntity().world.getTotalWorldTime();
		Long daylength = (long) 24000;
		Long hour = (long) 6, minute = (long) 0, second = (long) 0;
		Long daysAlive = (long) Math.floor((worldtime + daylength / 4) / daylength);
		Long remainingticks = (worldtime % daylength);
		
		while (remainingticks >= 1000) {
			remainingticks -= 1000;
			hour++;
			if (hour > 24) hour -= 24;
		}
		
		// 60 ticks per second, allows for even division into minutes
		remainingticks *= 3;
		while (remainingticks >= 50) {
			remainingticks -= 50;
			minute++;
		}
		
		// 360 ticks per second, allows for division into seconds
		remainingticks *= 6;
		while (remainingticks >= 5) {
			remainingticks -= 5;
			second++;
		}
		
		String hr = Long.toString(hour);
		String min = Long.toString(minute);
		String sec = Long.toString(second);
		
		if (hour < 10) hr = "0" + hr;
		if (minute < 10) min = "0" + min;
		if (second < 10) sec = "0" + sec;
		
		String day = Long.toString(daysAlive);
		String time = hr + ":" + min + ":" + sec;
		String[] dayTime = {day, time};
		
		return dayTime;
	}

}
