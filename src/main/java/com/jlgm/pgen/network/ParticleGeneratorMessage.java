package com.jlgm.pgen.network;

import com.google.common.base.Charsets;
import com.jlgm.pgen.tileentity.TileEntityParticleGenerator;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ParticleGeneratorMessage implements IMessage{
	
	private boolean onlyPos;
	private BlockPos pos;
	private int particleID, arg1ID, arg2Metadata;
	private float x, y, z, vX, vY, vZ;
	
	public ParticleGeneratorMessage(){
		
	}
	
	public ParticleGeneratorMessage(boolean onlyPos, BlockPos pos, int particleID, float x, float y, float z, float vX, float vY, float vZ, int arg1ID, int arg2Metadata){
		this.onlyPos = onlyPos;
		this.pos = pos;
		this.particleID = particleID;
		this.x = x;
		this.z = z;
		this.y = y;
		this.z = z;
		this.vX = vX;
		this.vY = vY;
		this.vZ = vZ;
		this.arg1ID = arg1ID;
		this.arg2Metadata = arg2Metadata;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(onlyPos);
		buf.writeLong(pos.toLong());
		buf.writeInt(particleID);
		buf.writeFloat(x);
		buf.writeFloat(y);
		buf.writeFloat(z);
		buf.writeFloat(vX);
		buf.writeFloat(vY);
		buf.writeFloat(vZ);
		buf.writeInt(arg1ID);
		buf.writeInt(arg2Metadata);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		onlyPos = buf.readBoolean();
		pos = BlockPos.fromLong(buf.readLong());
		particleID = buf.readInt();
		x = buf.readFloat();
		y = buf.readFloat();
		z = buf.readFloat();
		vX = buf.readFloat();
		vY = buf.readFloat();
		vZ = buf.readFloat();
		arg1ID = buf.readInt();
		arg2Metadata = buf.readInt();
	}
	
	public static class ParticleGeneratorMessageHandler implements IMessageHandler<ParticleGeneratorMessage, IMessage>{
		
		@Override
		public IMessage onMessage(ParticleGeneratorMessage message, MessageContext ctx) {
			TileEntity tile = ctx.getServerHandler().player.world.getTileEntity(message.pos);
			if(tile instanceof TileEntityParticleGenerator){
				TileEntityParticleGenerator pgen = (TileEntityParticleGenerator) tile;
				if(message.onlyPos){
					pgen.x = message.x;
					pgen.y = message.y;
					pgen.z = message.z;
				} else {
					pgen.particleID = message.particleID;
					pgen.x = message.x;
					pgen.y = message.y;
					pgen.z = message.z;
					pgen.vX = message.vX;
					pgen.vY = message.vY;
					pgen.vZ = message.vZ;
					pgen.arg1ID = message.arg1ID;
					pgen.arg2Metadata = message.arg2Metadata;
				}
				pgen.markDirty();
			}
			return null;
		}
	}
}