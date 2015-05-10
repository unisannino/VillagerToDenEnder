package com.unisannino.villager2denender.entity.ai;

import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.unisannino.villager2denender.entity.EntityDenender;

public abstract class EntityAIMoveAndWorkBlock extends EntityAIMoveToBlock
{
	protected EntityDenender theDenender;
	protected boolean canWork, hasSeeds;
	//回収時true、待機時false
	protected boolean stateDenender;
	protected BlockPos targetPos, workPos;

	//EntityAIHarvestFarmlandとほぼ同一処理
	public EntityAIMoveAndWorkBlock(EntityDenender denender, double p_i45888_2_, int p_i45888_4_)
	{
		super(denender, p_i45888_2_, p_i45888_4_);
		this.theDenender = denender;
        this.targetPos = BlockPos.ORIGIN;
        this.workPos = BlockPos.ORIGIN;
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
            this.canWork = this.getWorkCondition();
        }

        return super.shouldExecute();
    }

	@Override
    public boolean continueExecuting()
    {
        return this.stateDenender && super.continueExecuting();
    }

	@Override
    public void updateTask()
    {
        super.updateTask();
        this.theDenender.getLookHelper().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.theDenender.getVerticalFaceSpeed());
        //距離判定をガバガバに
        if (this.theDenender.getDistanceSqToCenter(this.workPos) <= 8.0D && this.stateDenender)
        {
            World world = this.theDenender.worldObj;

            if(this.isTarget(this.targetPos, world))
            {
            	this.workByBlock(world, this.targetPos);
            }

            this.stateDenender = false;
            this.field_179496_a = 10;
        }
    }

	@Override
    protected boolean func_179488_a(World worldIn, BlockPos p_179488_2_)
    {
		//y-1対策
		BlockPos pos = p_179488_2_.up();

        if(this.isTarget(pos, worldIn) && this.canWork)
        {
        	Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        	while (iterator.hasNext())
        	{
        		EnumFacing enumfacing = (EnumFacing)iterator.next();
        		if(this.isWorkPos(pos.offset(enumfacing), worldIn))
        		{
        			this.targetPos = pos;
        			//なんかわからないけどズレが生じる
        			//チャンクからの情報取得の際に何かが起こる？
        			this.workPos = pos.offset(enumfacing);
        			this.stateDenender = true;
        			return true;
        		}
        	}
        }
        return false;
    }

	protected void workByBlock(World world, BlockPos basePos)
	{
		//判定をガバガバにしたから元の位置ではなく田園ダーマンのいた場所にドロップさせる
        IBlockState iblockstate = world.getBlockState(basePos);
        Block block = iblockstate.getBlock();

        world.playAuxSFX(2001, basePos, Block.getStateId(iblockstate));
        block.dropBlockAsItem(world, this.theDenender.getPosition(), iblockstate, 0);
        world.setBlockState(basePos, Blocks.air.getDefaultState(), 3);
	}

	protected boolean getWorkCondition()
	{
		return this.theDenender.func_175557_cr();
	}

	protected abstract boolean isTarget(BlockPos targetPos, World world);
	protected abstract boolean isWorkPos(BlockPos targetPos, World world);

}