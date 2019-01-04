package com.acolos.acolostracker.proxy;

import java.sql.SQLException;

import com.acolos.acolostracker.util.DeathTimer;
import com.acolos.acolostracker.util.LoadDriver;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CommonProxy {
	
	public static String[] details;
	public static PlayerRespawnEvent respawn;
	public static GuiScreen guiscreen;
	public static LivingDeathEvent livingdeath;
	private static Minecraft mc = Minecraft.getMinecraft();

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event) {
		//EntityPlayer player = (EntityPlayer) event.getEntity();
		DamageSource source = event.getSource();

		/* Check to see if player died to a creature
		 * 
		 * event.getEntity().getName() = player name
		 * slayer.getName() = name of mob killed by
		 */
		if (event.getEntity() == null) return;
		
		Entity slayer = event.getSource().getTrueSource();
		
		if ((event.getEntity() instanceof EntityPlayer) && (slayer instanceof EntityCreature)) {
			String[] dayTimeOfDeath = DeathTimer.getDeathTime(event);
			this.details = new String[] {event.getEntity().getName(), slayer.getName(), dayTimeOfDeath[0], dayTimeOfDeath[1]};
			this.livingdeath = (LivingDeathEvent) event;
		} else if ((event.getEntity() instanceof EntityPlayer) && !(slayer instanceof EntityCreature)) {
			String[] dayTimeOfDeath = DeathTimer.getDeathTime(event);
			this.details = new String[] {event.getEntity().getName(), source.damageType, dayTimeOfDeath[0], dayTimeOfDeath[1]};
			this.livingdeath = (LivingDeathEvent) event;
		}
	}
	
	@SubscribeEvent
	public void onOpenGui(GuiOpenEvent event) {
		if(this.livingdeath != null && (event.getGui() instanceof GuiGameOver)) {
			this.guiscreen = (GuiGameOver) event.getGui();
		}
	}
	
	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event) throws SQLException, InterruptedException {
		if (this.guiscreen != null && this.livingdeath != null && (this.guiscreen instanceof GuiGameOver)) {
			if (this.livingdeath != null && (this.livingdeath.getEntity() instanceof EntityPlayer) && (this.livingdeath.getSource().getTrueSource() instanceof EntityCreature)) {
				LoadDriver.init(this.details);
			} else if (this.livingdeath != null && (this.livingdeath.getEntity() instanceof EntityPlayer) && !(this.livingdeath.getSource().getTrueSource() instanceof EntityCreature)) {
				LoadDriver.init(this.details);
			}
		}
	}
	
	public void init() {
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}

}
