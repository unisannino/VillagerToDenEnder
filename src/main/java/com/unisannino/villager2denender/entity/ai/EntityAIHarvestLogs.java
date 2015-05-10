package com.unisannino.villager2denender.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.unisannino.villager2denender.entity.EntityDenender;

public class EntityAIHarvestLogs extends EntityAIMoveAndWorkBlock
{
	public EntityAIHarvestLogs(EntityDenender denender, double p_i45889_2_)
	{
		super(denender, p_i45889_2_, 16);
	}

	@Override
	protected boolean isTarget(BlockPos targetPos, World world)
	{
		Block target = world.getBlockState(targetPos).getBlock();
		Block utarget = world.getBlockState(targetPos.up()).getBlock();
		Block dtarget = world.getBlockState(targetPos.down()).getBlock();
		return target instanceof BlockLog && utarget instanceof BlockLog && dtarget == Blocks.dirt;
	}

	@Override
	protected boolean isWorkPos(BlockPos targetPos, World world)
	{
		return !world.getBlockState(targetPos).getBlock().isOpaqueCube() && !world.getBlockState(targetPos.up()).getBlock().isOpaqueCube() && !(world.getBlockState(targetPos.up()).getBlock() instanceof BlockLeaves);
	}

	@Override
	protected void workByBlock(World world, BlockPos basePos)
	{
		BlockPos pos;
		IBlockState state;;

		for (int y = -1; y <= 1; y++)
		{
			for (int x = -1; x <= 1; x++)
			{
				for (int z = -1; z <= 1; z++)
				{
					pos = new BlockPos(basePos.getX() + x, basePos.getY()+ y, basePos.getZ() + z);

					if(world.getBlockState(pos).getBlock() instanceof BlockLog)
					{
						state = world.getBlockState(pos);
						this.plantSaplings(pos, state);
						super.workByBlock(world, pos);
						this.workByBlock(world, pos);
					}
				}
			}
		}
	}

	private void plantSaplings(BlockPos targetPos, IBlockState state)
	{
		BlockLog logs = (BlockLog) state.getBlock();
		int type = logs.damageDropped(state);

		InventoryBasic inventory = this.theDenender.func_175551_co();
	}

}
