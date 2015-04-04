package com.unisannino.villager2denender.render;

import java.util.Calendar;
import java.util.Date;

import com.unisannino.villager2denender.entity.EntityDenender;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

public class RenderDenender extends RenderLiving
{
    private static final ResourceLocation villagerTextures = new ResourceLocation("unisannino:mob/denenderman_def.png");
    private static final ResourceLocation farmerVillagerTextures = new ResourceLocation("unisannino:mob/denenderman_farm.png");
    private static final ResourceLocation librarianVillagerTextures = new ResourceLocation("unisannino:mob/denenderman_lib.png");
    private static final ResourceLocation priestVillagerTextures = new ResourceLocation("unisannino:mob/denenderman_pri.png");
    private static final ResourceLocation smithVillagerTextures = new ResourceLocation("unisannino:mob/denenderman_smi.png");
    private static final ResourceLocation butcherVillagerTextures = new ResourceLocation("unisannino:mob/denenderman_but.png");

    private static final ResourceLocation villagerTexturesFool = new ResourceLocation("unisannino:mob/denenderman_def_fool.png");
    private static final ResourceLocation farmerVillagerTexturesFool = new ResourceLocation("unisannino:mob/denenderman_farm_fool.png");
    private static final ResourceLocation librarianVillagerTexturesFool = new ResourceLocation("unisannino:mob/denenderman_lib_fool.png");
    private static final ResourceLocation priestVillagerTexturesFool = new ResourceLocation("unisannino:mob/denenderman_pri_fool.png");
    private static final ResourceLocation smithVillagerTexturesFool = new ResourceLocation("unisannino:mob/denenderman_smi_fool.png");
    private static final ResourceLocation butcherVillagerTexturesFool = new ResourceLocation("unisannino:mob/denenderman_but_fool.png");
	protected ModelDenender denenderModel;

	public RenderDenender(RenderManager p_i46132_1_)
	{
		super(p_i46132_1_, new ModelDenender(), 0.5F);
		this.denenderModel = (ModelDenender) this.mainModel;
	}

    protected void preRenderCallback(EntityDenender p_77041_1_, float p_77041_2_)
    {
        //float f1 = 0.9375F;
    	float f1 = 0.625F;

        if (p_77041_1_.getGrowingAge() < 0)
        {
            f1 = (float)((double)f1 * 0.5D);
            this.shadowSize = 0.25F;
        }
        else
        {
            this.shadowSize = 0.5F;
        }

        GlStateManager.scale(f1, f1, f1);
    }

	//職業に応じてテクスチャを選択します。0:農家、1:司書、2:司祭、3:鍛冶屋、4:肉屋
    protected ResourceLocation getEntityTexture(EntityDenender entity)
    {
    	Calendar day = Calendar.getInstance();
    	if(day.get(Calendar.MONTH) + 1 == 4 && day.get(Calendar.DATE) == 1)
    	{
            switch (entity.getProfession())
            {
                case 0:
                    return farmerVillagerTexturesFool;
                case 1:
                    return librarianVillagerTexturesFool;
                case 2:
                    return priestVillagerTexturesFool;
                case 3:
                    return smithVillagerTexturesFool;
                case 4:
                    return butcherVillagerTexturesFool;
                default:
                    return villagerTexturesFool;
            }
    	}
    	else
    	{
            switch (entity.getProfession())
            {
                case 0:
                    return farmerVillagerTextures;
                case 1:
                    return librarianVillagerTextures;
                case 2:
                    return priestVillagerTextures;
                case 3:
                    return smithVillagerTextures;
                case 4:
                    return butcherVillagerTextures;
                default:
                    return villagerTextures;
            }
		}

    }

    public void doRender(EntityDenender entity, double x, double y, double z, float p_76986_8_, float partialTicks)
    {
    }

    @Override
    protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
    {
        this.preRenderCallback((EntityDenender)p_77041_1_, p_77041_2_);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntityDenender)entity);
    }

    @Override
    public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
    {
        super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
        this.doRender((EntityDenender)entity, x, y, z, p_76986_8_, partialTicks);
    }

}
