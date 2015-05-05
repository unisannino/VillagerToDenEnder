package com.unisannino.villager2denender.entity.ai;

import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.unisannino.villager2denender.entity.EntityDenender;

public class EntityAIHarvestStemCrops extends EntityAIMoveToBlock
{
	private EntityDenender theDenender;
	private boolean canHervest, hasSeeds;
	//回収時true、待機時false
	private boolean stateDenender;
	protected BlockPos cropPos;

	//EntityAIHarvestFarmlandとほぼ同一処理
	public EntityAIHarvestStemCrops(EntityDenender denender, double p_i45889_2_)
	{
		super(denender, p_i45889_2_, 16);
		this.theDenender = denender;
        this.cropPos = BlockPos.ORIGIN;
	}

	@Override
    public boolean shouldExecute()
    {
        if (this.field_179496_a <= 0)
        {
            if (!this.theDenender.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
            {
                return false;
            }

            this.stateDenender = false;
            this.hasSeeds = this.theDenender.func_175556_cs();
            this.canHervest = this.theDenender.func_175557_cr();
        }

        return super.shouldExecute();
    }

	@Override
    public boolean continueExecuting()
    {
        return this.stateDenender && super.continueExecuting();
    }

    public void updateTask()
    {
        super.updateTask();
        this.theDenender.getLookHelper().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.theDenender.getVerticalFaceSpeed());

        if (this.func_179487_f() && this.stateDenender)
        {
            World world = this.theDenender.worldObj;
            BlockPos blockpos = this.destinationBlock;
            Block block = world.getBlockState(this.cropPos).getBlock();

            if(world.getBlockState(blockpos.down()).getBlock() == Blocks.dirt && this.isTarget(block))
            {
            	this.breakBlocks(world, cropPos);
            }

            this.stateDenender = false;
            this.field_179496_a = 10;
        }
    }


    //基準位置(destinationBlock)のyが-1されていると考える？
	@Override
    protected boolean func_179488_a(World worldIn, BlockPos p_179488_2_)
    {
		//y-1対策
		BlockPos pos = p_179488_2_.up();
	    Block top = worldIn.getBlockState(pos).getBlock();
        Block block = worldIn.getBlockState(p_179488_2_).getBlock();

        if(block == Blocks.dirt && !top.isOpaqueCube() && !worldIn.getBlockState(pos.up()).getBlock().isOpaqueCube())
        {
        	Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
            while (iterator.hasNext())
            {
                EnumFacing enumfacing = (EnumFacing)iterator.next();
                Block target = worldIn.getBlockState(pos.offset(enumfacing)).getBlock();

                if(this.isTarget(target) && this.canHervest)
                {
                	this.cropPos = pos.offset(enumfacing);
                   	this.stateDenender = true;
                	return true;
                }
            }
        }
        return false;
    }


	protected boolean isTarget(Block target)
	{
		return target == Blocks.pumpkin || target == Blocks.melon_block;
	}

	protected void breakBlocks(World world, BlockPos basePos)
	{
    	world.destroyBlock(basePos, true);
	}

}
