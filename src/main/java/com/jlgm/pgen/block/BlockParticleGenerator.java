package com.jlgm.pgen.block;

import com.jlgm.pgen.client.gui.PGenGuiHandler;
import com.jlgm.pgen.item.ItemCoordSaver;
import com.jlgm.pgen.main.PGenMain;
import com.jlgm.pgen.tileentity.TileEntityParticleGenerator;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockParticleGenerator extends BlockContainer{

	protected BlockParticleGenerator(Material materialIn) {
		super(materialIn);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(worldIn.isRemote){
			if(!(playerIn.getHeldItem(hand).getItem() instanceof ItemCoordSaver)){
				playerIn.openGui(PGenMain.instance, PGenGuiHandler.GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
		}
        return true;
    }
	
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityParticleGenerator();
	}
}
