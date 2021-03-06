package com.jlgm.pgen.item;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Nullable;

import com.jlgm.pgen.lib.Vec3f;
import com.jlgm.pgen.network.PGenPacketHandler;
import com.jlgm.pgen.network.ParticleGeneratorMessage;
import com.jlgm.pgen.tileentity.TileEntityParticleGenerator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCoordSaver extends Item {

	private Vec3f posStored;
	
	public ItemCoordSaver(){
		posStored = new Vec3f(0, 0, 0);
	}
	
	/**
	 * Called when a Block is right-clicked with this Item
	 */
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.getTileEntity(pos) instanceof TileEntityParticleGenerator) {
			TileEntityParticleGenerator tileEntity = (TileEntityParticleGenerator) worldIn.getTileEntity(pos);
			PGenPacketHandler.INSTANCE.sendToServer(new ParticleGeneratorMessage(true, tileEntity.getPos(), 0, posStored.x, posStored.y, posStored.z, 0, 0, 0, 0, 0));
			tileEntity.x = posStored.x;
			tileEntity.y = posStored.y;
			tileEntity.z = posStored.z;
			player.sendStatusMessage(new TextComponentTranslation("item.coordsaver.pospasted", new Object[0]), true);
			return EnumActionResult.SUCCESS;
		} else {
			DecimalFormat df = new DecimalFormat("#.###");
			df.setRoundingMode(RoundingMode.HALF_UP);
			float x = 0;
			float y = 0;
			float z = 0;
			if(player.isSneaking()){
				x = Float.valueOf(df.format(pos.getX() + hitX));
				y = Float.valueOf(df.format(pos.getY() + hitY));
				z = Float.valueOf(df.format(pos.getZ() + hitZ));
				posStored = new Vec3f(x, y, z);
			}else{
				x = Float.valueOf(df.format(pos.getX() + 0.5f));
				y = Float.valueOf(df.format(pos.getY() + 0.5f));
				z = Float.valueOf(df.format(pos.getZ() + 0.5f));
				posStored = new Vec3f(x, y, z);
			}
			player.sendStatusMessage(new TextComponentTranslation("item.coordsaver.posstored", new Object[0]).appendSibling(new TextComponentString(" = X:" + posStored.x + " Y:" + posStored.y + " Z:" + posStored.z)), true);
			return EnumActionResult.SUCCESS;
		}
	}
	
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.translateToLocal("item.coordsaver.click1"));
        tooltip.add(I18n.translateToLocal("item.coordsaver.click2"));
        tooltip.add(I18n.translateToLocal("item.coordsaver.click3"));
        tooltip.add(I18n.translateToLocal("item.coordsaver.posstored") + ": ");
        tooltip.add(" X: " + TextFormatting.BLUE + posStored.x);
        tooltip.add(" Y: " + TextFormatting.BLUE + posStored.y);
        tooltip.add(" Z: " + TextFormatting.BLUE + posStored.z);
    }
}
