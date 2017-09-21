package com.jlgm.pgen.item;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Nullable;

import com.jlgm.pgen.lib.Vec3f;
import com.jlgm.pgen.main.PGenMain;
import com.jlgm.pgen.network.PGenPacketHandler;
import com.jlgm.pgen.network.ParticleGeneratorMessage;
import com.jlgm.pgen.tileentity.TileEntityParticleGenerator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
		boolean relativeCoords = PGenMain.instance.configStorage.relativeCoords;
		if (worldIn.getTileEntity(pos) instanceof TileEntityParticleGenerator) {
			TileEntityParticleGenerator tileEntity = (TileEntityParticleGenerator) worldIn.getTileEntity(pos);

			NBTTagCompound nbt;
			ItemStack stack = player.getHeldItem(hand);
			float storedX = 0;
			float storedY = 0;
			float storedZ = 0;
			
			if(stack.hasTagCompound()) {
				nbt = stack.getTagCompound();
				if(nbt.hasKey("PositionX"))
					storedX = stack.getTagCompound().getFloat("PositionX");

				if(nbt.hasKey("PositionY"))
					storedY = stack.getTagCompound().getFloat("PositionY");

				if(nbt.hasKey("PositionZ"))
					storedZ = stack.getTagCompound().getFloat("PositionZ");
			}
			
			posStored = new Vec3f(storedX, storedY, storedZ);
			
			Vec3f relPosStored = posStored.subtract(new Vec3f(pos.getX(), pos.getY(), pos.getZ()));
			
			Vec3f finalPos = relativeCoords ? relPosStored : posStored;
			
			PGenPacketHandler.INSTANCE.sendToServer(new ParticleGeneratorMessage(true, tileEntity.getPos(), 0, finalPos.x, finalPos.y, finalPos.z, 0, 0, 0, 0, 0));
			tileEntity.x = finalPos.x;
			tileEntity.y = finalPos.y;
			tileEntity.z = finalPos.z;
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
			if(!relativeCoords) {
				player.sendStatusMessage(new TextComponentTranslation("item.coordsaver.posstored", new Object[0]).appendSibling(new TextComponentString(" = X:" + posStored.x + " Y:" + posStored.y + " Z:" + posStored.z)), true);	
			} else {
				player.sendStatusMessage(new TextComponentTranslation("item.coordsaver.posstored", new Object[0]).appendSibling(new TextComponentString(".")), true);
			}
			
			NBTTagCompound nbt;
			ItemStack stack = player.getHeldItem(hand);
			if(stack.hasTagCompound()) {
				nbt = stack.getTagCompound();
			} else {
				nbt = new NBTTagCompound();
			}
			
			nbt.setFloat("PositionX", posStored.x);
			nbt.setFloat("PositionY", posStored.y);
			nbt.setFloat("PositionZ", posStored.z);
			
			stack.setTagCompound(nbt);
			
			return EnumActionResult.SUCCESS;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
		super.addInformation(stack, worldIn, tooltip, flagIn);
		boolean relativeCoords = PGenMain.instance.configStorage.relativeCoords;
		tooltip.add(I18n.translateToLocal("item.coordsaver.click1"));
		tooltip.add(I18n.translateToLocal("item.coordsaver.click2"));
		tooltip.add(I18n.translateToLocal("item.coordsaver.click3"));
		tooltip.add(TextFormatting.GOLD + I18n.translateToLocal("item.coordsaver.posstored") + (relativeCoords ? "." : ": "));
		if(!PGenMain.instance.configStorage.relativeCoords) {

			NBTTagCompound nbt;
			float storedX = 0;
			float storedY = 0;
			float storedZ = 0;
			
			if(stack.hasTagCompound()) {
				nbt = stack.getTagCompound();
				if(nbt.hasKey("PositionX"))
					storedX = stack.getTagCompound().getFloat("PositionX");

				if(nbt.hasKey("PositionY"))
					storedY = stack.getTagCompound().getFloat("PositionY");

				if(nbt.hasKey("PositionZ"))
					storedZ = stack.getTagCompound().getFloat("PositionZ");
			}
			
			posStored = new Vec3f(storedX, storedY, storedZ);
			
			tooltip.add(" X: " + TextFormatting.BLUE + posStored.x);
			tooltip.add(" Y: " + TextFormatting.BLUE + posStored.y);
			tooltip.add(" Z: " + TextFormatting.BLUE + posStored.z);
		}
	}
}
