package com.unisannino.villager2denender;

import com.unisannino.villager2denender.entity.EntityDenender;
import com.unisannino.villager2denender.event.EventVillagerJoinWorld;
import com.unisannino.villager2denender.render.RenderDenender;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = "v2d", version = "1.0.4.1")
public class CoreVillager2Denender
{
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new EventVillagerJoinWorld());

		EntityRegistry.registerGlobalEntityID(EntityDenender.class, "DenEnderman", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(EntityDenender.class, "DenEnderman", 0, this, 80, 3, true);

		if(FMLCommonHandler.instance().getSide() == Side.CLIENT)
		{
			RenderingRegistry.registerEntityRenderingHandler(EntityDenender.class, new RenderDenender(Minecraft.getMinecraft().getRenderManager()));
		}
	}
}
