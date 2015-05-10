package com.unisannino.villager2denender.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStem;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.unisannino.villager2denender.entity.EntityDenender;

public class EntityAIHarvestStemCrops extends EntityAIMoveAndWorkBlock
{
	public EntityAIHarvestStemCrops(EntityDenender denender, double p_i45889_2_)
	{
		super(denender, p_i45889_2_, 16);
	}

	@Override
	protected boolean isTarget(BlockPos targetPos, World world)
	{
		Block target = world.getBlockState(targetPos).getBlock();
		Block base = world.getBlockState(targetPos.down()).getBlock();
		return target == Blocks.pumpkin || target == Blocks.melon_block && base == Blocks.dirt;
	}

	@Override
	protected boolean isWorkPos(BlockPos targetPos, World world)
	{
		return world.getBlockState(targetPos).getBlock() instanceof BlockStem && !world.getBlockState(targetPos.up()).getBlock().isOpaqueCube();
	}


}
