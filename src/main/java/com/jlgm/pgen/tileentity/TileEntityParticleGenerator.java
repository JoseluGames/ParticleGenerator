package com.jlgm.pgen.tileentity;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.world.IWorldNameable;

public class TileEntityParticleGenerator extends TileEntity implements ITickable, IWorldNameable{
	
	private String customName;
	
	public int particleID = 0;
	public float x = 0;
	public float y = 0;
	public float z = 0;
	public float vX = 0;
	public float vY = 0;
	public float vZ = 0;
	public int arg1ID = 0;
	public int arg2Metadata = 0;
	
	@Override
	public void update() {
		if(this.worldObj.isRemote){
			if(this.worldObj.isBlockPowered(this.getPos())){
				if(particleID == EnumParticleTypes.ITEM_CRACK.getParticleID()){
					this.worldObj.spawnParticle(EnumParticleTypes.getParticleFromId(particleID), x, y, z, vX, vY, vZ, new int[]{arg1ID, arg2Metadata});
				}else if(particleID == EnumParticleTypes.BLOCK_CRACK.getParticleID() || particleID == EnumParticleTypes.BLOCK_DUST.getParticleID() || particleID == EnumParticleTypes.FALLING_DUST.getParticleID()){
					this.worldObj.spawnParticle(EnumParticleTypes.getParticleFromId(particleID), x, y, z, vX, vY, vZ, new int[]{Block.getStateId(Block.getBlockById(arg1ID).getStateFromMeta(arg2Metadata))});
				}else{
					this.worldObj.spawnParticle(EnumParticleTypes.getParticleFromId(particleID), x, y, z, vX, vY, vZ);
				}
			}
		}
	}
	
    /**
     * Get the name of this object. For players this returns their username
     */
    public String getName()
    {
        return this.hasCustomName() ? this.customName : "container.particlegenerator";
    }

    /**
     * Returns true if this thing is named
     */
    public boolean hasCustomName()
    {
        return this.customName != null && !this.customName.isEmpty();
    }

    public void setCustomName(String name)
    {
        this.customName = name;
    }
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound){
		super.writeToNBT(tagCompound);
		tagCompound.setBoolean("hasCustomName", this.hasCustomName());
		if(this.hasCustomName()){
			tagCompound.setString("customName", this.customName);
		}
		tagCompound.setInteger("particleID", this.particleID);
		tagCompound.setFloat("particleX", this.x);
		tagCompound.setFloat("particleY", this.y);
		tagCompound.setFloat("particleZ", this.z);
		tagCompound.setFloat("particlevX", this.vX);
		tagCompound.setFloat("particlevY", this.vY);
		tagCompound.setFloat("particlevZ", this.vZ);
		tagCompound.setInteger("arg1ID", this.arg1ID);
		tagCompound.setInteger("arg2Metadata", this.arg2Metadata);
		return tagCompound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound){
		super.readFromNBT(tagCompound);
		if(tagCompound.getBoolean("hasCustomName")){
			this.customName = tagCompound.getString("customName");
		}
		this.particleID = tagCompound.getInteger("particleID");
		this.x = tagCompound.getFloat("particleX");
		this.y = tagCompound.getFloat("particleY");
		this.z = tagCompound.getFloat("particleZ");
		this.vX = tagCompound.getFloat("particlevX");
		this.vY = tagCompound.getFloat("particlevY");
		this.vZ = tagCompound.getFloat("particlevZ");
		this.arg1ID = tagCompound.getInteger("arg1ID");
		this.arg2Metadata = tagCompound.getInteger("arg2Metadata");
	}
	
	@Override
	public NBTTagCompound getUpdateTag(){
		NBTTagCompound tag = super.getUpdateTag();
		return this.writeToNBT(tag);
	}
	
	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket(){
		NBTTagCompound tagCompound = new NBTTagCompound();
		writeToNBT(tagCompound);
		return new SPacketUpdateTileEntity(this.pos, this.getBlockMetadata(), tagCompound);
	}
	
	@Override
	public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt){
		NBTTagCompound tag = pkt.getNbtCompound();
		readFromNBT(tag);
	}
}
