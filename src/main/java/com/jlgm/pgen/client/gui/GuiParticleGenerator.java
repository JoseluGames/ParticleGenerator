package com.jlgm.pgen.client.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.google.common.collect.Lists;
import com.jlgm.pgen.lib.PGenConstants;
import com.jlgm.pgen.lib.PGenMath;
import com.jlgm.pgen.network.PGenPacketHandler;
import com.jlgm.pgen.network.ParticleGeneratorMessage;
import com.jlgm.pgen.tileentity.TileEntityParticleGenerator;

import net.minecraft.block.Block;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import scala.util.Random;

public class GuiParticleGenerator extends GuiScreen{
	
	private GuiTextField positionX;
	private GuiTextField positionY;
	private GuiTextField positionZ;
	private GuiTextField argument1;
	private GuiTextField argument2;
	private GuiTextField movementX;
	private GuiTextField movementY;
	private GuiTextField movementZ;
	private GuiButton doneButton;
	private GuiButton cancelButton;
	private static ResourceLocation texture;
	private final TileEntityParticleGenerator tile;
	private final int numberOfParticles = 47;
	private int currentShownParticles = 0; //Max 42;
	private int textureWidth = 176;
	private int textureHeight = 176; //Old 222
	private int chosedParticle = 0;
	private boolean relativeCoords = false;
	
	public GuiParticleGenerator(TileEntityParticleGenerator chatBox){
		texture = new ResourceLocation(PGenConstants.MODID + ":" + "textures/gui/particleGenerator.png");
		tile = chatBox;
	}
	
	@Override
	public void initGui(){
		this.buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		int basePointX = this.width/2 - textureWidth/2;
		int basePointY = this.height/2 - textureHeight/2;
		
		this.buttonList.add(this.doneButton = new GuiButton(0, basePointX + 113, basePointY + 118, 50, 20, new TextComponentString(I18n.format("gui.done")).getUnformattedText()));
		this.buttonList.add(this.cancelButton = new GuiButton(1, basePointX + 113, basePointY + 144, 50, 20, new TextComponentString(I18n.format("gui.cancel")).getUnformattedText()));
		
		this.chosedParticle = tile.particleID;
		this.currentShownParticles = Math.min(this.chosedParticle, numberOfParticles - 5);
		
		this.positionX = new GuiTextField(0, this.fontRendererObj, basePointX + 1 + 22, basePointY + 1 + 70, 41, 12);
		this.positionX.setText(String.valueOf(tile.x));
        this.positionX.setMaxStringLength(9);
        this.positionX.setEnableBackgroundDrawing(true);
        this.positionX.setFocused(false);
        this.positionX.setCanLoseFocus(true);
        
		this.positionY = new GuiTextField(1, this.fontRendererObj, basePointX + 1 + 22, basePointY + 1 + 86, 41, 12);
		this.positionY.setText(String.valueOf(tile.y));
        this.positionY.setMaxStringLength(9);
        this.positionY.setEnableBackgroundDrawing(true);
        this.positionY.setFocused(false);
        this.positionY.setCanLoseFocus(true);
		
		this.positionZ = new GuiTextField(2, this.fontRendererObj, basePointX + 1 + 22, basePointY + 1 + 102, 41, 12);
		this.positionZ.setText(String.valueOf(tile.z));
        this.positionZ.setMaxStringLength(9);
        this.positionZ.setEnableBackgroundDrawing(true);
        this.positionZ.setFocused(false);
        this.positionZ.setCanLoseFocus(true);
		
		this.argument1 = new GuiTextField(3, this.fontRendererObj, basePointX + 1 + 84, basePointY + 1 + 86, 77, 12);
		if(chosedParticle == EnumParticleTypes.ITEM_CRACK.getParticleID()){
			this.argument1.setText(Item.REGISTRY.getNameForObject(Item.getItemById(tile.arg1ID)).toString());
		}else{
			this.argument1.setText(Block.REGISTRY.getNameForObject(Block.getBlockById(tile.arg1ID)).toString());
		}
        this.argument1.setMaxStringLength(255);
        this.argument1.setEnableBackgroundDrawing(true);
        this.argument1.setFocused(false);
        this.argument1.setCanLoseFocus(true);
		
		this.argument2 = new GuiTextField(4, this.fontRendererObj, basePointX + 1 + 84, basePointY + 1 + 102, 77, 12);
		this.argument2.setText(String.valueOf(tile.arg2Metadata));
        this.argument2.setMaxStringLength(255);
        this.argument2.setEnableBackgroundDrawing(true);
        this.argument2.setFocused(false);
        this.argument2.setCanLoseFocus(true);
        
		this.movementX = new GuiTextField(5, this.fontRendererObj, basePointX + 1 + 22, basePointY + 1 + 118, 41, 12);
		this.movementX.setText(String.valueOf(tile.vX));
        this.movementX.setMaxStringLength(9);
        this.movementX.setEnableBackgroundDrawing(true);
        this.movementX.setFocused(false);
        this.movementX.setCanLoseFocus(true);
        
		this.movementY = new GuiTextField(6, this.fontRendererObj, basePointX + 1 + 22, basePointY + 1 + 134, 41, 12);
		this.movementY.setText(String.valueOf(tile.vY));
        this.movementY.setMaxStringLength(9);
        this.movementY.setEnableBackgroundDrawing(true);
        this.movementY.setFocused(false);
        this.movementY.setCanLoseFocus(true);

		this.movementZ = new GuiTextField(7, this.fontRendererObj, basePointX + 1 + 22, basePointY + 1 + 150, 41, 12);
		this.movementZ.setText(String.valueOf(tile.vZ));
        this.movementZ.setMaxStringLength(9);
        this.movementZ.setEnableBackgroundDrawing(true);
        this.movementZ.setFocused(false);
        this.movementZ.setCanLoseFocus(true);
	}
    
    @Override
    public void updateScreen(){
    	super.updateScreen();
    	if(this.positionX.isFocused()) this.positionX.updateCursorCounter();
    	if(this.positionY.isFocused()) this.positionY.updateCursorCounter();
    	if(this.positionZ.isFocused()) this.positionZ.updateCursorCounter();
    	if(this.argument1.isFocused()) this.argument1.updateCursorCounter();
    	if(this.argument2.isFocused()) this.argument2.updateCursorCounter();
    	if(this.movementX.isFocused()) this.argument1.updateCursorCounter();
    	if(this.movementY.isFocused()) this.argument2.updateCursorCounter();
    	if(this.movementZ.isFocused()) this.argument2.updateCursorCounter();
    }
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		this.drawDefaultBackground();
		mc.getTextureManager().bindTexture(texture);
		int basePointX = this.width/2 - textureWidth/2;
		int basePointY = this.height/2 - textureHeight/2;
		
		this.drawTexturedModalRect(basePointX, basePointY, 0, 0, textureWidth, textureHeight);
		
		float percentaje = (float) currentShownParticles / (numberOfParticles - 5);
		int positionY = Math.round(26 + (30 * percentaje));
		mc.getTextureManager().bindTexture(texture);
		this.drawTexturedModalRect(basePointX + 158, basePointY + positionY, 176, 0, 9, 3);

		for(int i = 0; i < 5; i++){
			if(EnumParticleTypes.getParticleFromId(currentShownParticles + i).getParticleID() == chosedParticle){
				this.drawRect(basePointX + 8, basePointY + 18 + (i * 10), basePointX + 19 + 137, basePointY + 19 + 8 + (i * 10), 0xFF002858);
			}
			if(EnumParticleTypes.getParticleFromId(currentShownParticles + i).getArgumentCount() > 0){
				this.fontRendererObj.drawString("!", basePointX + 154, basePointY + 19 + (i * 10), 0xFF0000);
			}
		}
		
		this.fontRendererObj.drawString(new TextComponentString(I18n.format(tile.getName())).getUnformattedText(), basePointX + 8, basePointY + 8, 0x404040);
		this.fontRendererObj.drawString(EnumParticleTypes.getParticleFromId(currentShownParticles + 0).name(), basePointX + 9, basePointY + 19 + 00, 0xFFFFFF);
		this.fontRendererObj.drawString(EnumParticleTypes.getParticleFromId(currentShownParticles + 1).name(), basePointX + 9, basePointY + 19 + 10, 0xFFFFFF);
		this.fontRendererObj.drawString(EnumParticleTypes.getParticleFromId(currentShownParticles + 2).name(), basePointX + 9, basePointY + 19 + 20, 0xFFFFFF);
		this.fontRendererObj.drawString(EnumParticleTypes.getParticleFromId(currentShownParticles + 3).name(), basePointX + 9, basePointY + 19 + 30, 0xFFFFFF);
		this.fontRendererObj.drawString(EnumParticleTypes.getParticleFromId(currentShownParticles + 4).name(), basePointX + 9, basePointY + 19 + 40, 0xFFFFFF);
		
		this.positionX.drawTextBox();
		this.positionY.drawTextBox();
		this.positionZ.drawTextBox();
		this.fontRendererObj.drawString("X:", basePointX + 13, basePointY + 73, 0x404040);
		this.fontRendererObj.drawString("Y:", basePointX + 13, basePointY + 89, 0x404040);
		this.fontRendererObj.drawString("Z:", basePointX + 13, basePointY + 105, 0x404040);
		
		this.argument1.setEnabled(EnumParticleTypes.getParticleFromId(chosedParticle).getArgumentCount() > 0);
		this.argument2.setEnabled(EnumParticleTypes.getParticleFromId(chosedParticle).getArgumentCount() > 0);
		this.argument1.drawTextBox();
		this.argument2.drawTextBox();
		
		this.fontRendererObj.drawString(new TextComponentString(I18n.format("container.particlegenerator.arguments")).getUnformattedText() + ":", basePointX + 69, basePointY + 73, 0x404040);
		this.fontRendererObj.drawString("·1:", basePointX + 69, basePointY + 89, 0x404040);
		this.fontRendererObj.drawString("·2:", basePointX + 69, basePointY + 105, 0x404040);
		
		this.movementX.drawTextBox();
		this.movementY.drawTextBox();
		this.movementZ.drawTextBox();
		this.fontRendererObj.drawString("vX:", basePointX + 7, basePointY + 121, 0x404040);
		this.fontRendererObj.drawString("vY:", basePointX + 7, basePointY + 137, 0x404040);
		this.fontRendererObj.drawString("vZ:", basePointX + 7, basePointY + 153, 0x404040);
		
		float r;
		
		try{
			r = Float.valueOf(this.movementX.getText().isEmpty()? "0" : this.movementX.getText());
		} catch(NumberFormatException e){
			r = tile.vX;
		}
		
		float g;
		
		try{
			g = Float.valueOf(this.movementY.getText().isEmpty()? "0" : this.movementY.getText());
		} catch(NumberFormatException e){
			g = tile.vY;
		}
		
		float b;
		
		try{
			b = Float.valueOf(this.movementZ.getText().isEmpty()? "0" : this.movementZ.getText());
		} catch(NumberFormatException e){
			b = tile.vZ;
		}
		
		Color col = new Color((float) Math.max(0.0f, Math.min(1.0f, r)), (float) Math.max(0.0f, Math.min(1.0f, g)), (float) Math.max(0.0f, Math.min(1.0f, b)));
		this.drawRect(basePointX + 68, basePointY + 119, basePointX + 73, basePointY + 163, col.getRGB()/*((255 & 0x0ff) << 24) | ((r & 0x0ff) << 16) | ((g & 0x0ff) << 8) | (b & 0x0ff)*/);
		
		boolean isIDValid = true;
		if(!this.argument1.getText().isEmpty()){
			if(chosedParticle == EnumParticleTypes.ITEM_CRACK.getParticleID()){
				isIDValid = Item.getByNameOrId(this.argument1.getText()) != null;
			}else{
				isIDValid = Block.getBlockFromName(this.argument1.getText()) != null;
			}
		}
		
		isIDValid = EnumParticleTypes.getParticleFromId(chosedParticle).getArgumentCount() > 0? isIDValid : true;
		
		if(!isIDValid){
			this.fontRendererObj.drawString("!", basePointX + 167, basePointY + 89, 0xFF0000);
		}
				
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		//Tool tip 1
		if(PGenMath.isInRange(mouseX, mouseY, basePointX + 162, basePointX + 173, basePointY + 86, basePointY + 101)){
			List<String> list = Lists.<String>newArrayList();
			if(this.argument1.getText().isEmpty() && EnumParticleTypes.getParticleFromId(chosedParticle).getArgumentCount() > 0){
				list.add(TextFormatting.GOLD + new TextComponentString(I18n.format("container.particlegenerator.tip1_1.line1")).getUnformattedText());
				list.add(TextFormatting.GOLD + new TextComponentString(I18n.format("container.particlegenerator.tip1_1.line2")).getUnformattedText());
			}else if(!isIDValid){
				list.add(TextFormatting.RED + this.argument1.getText());
				list.add(TextFormatting.GOLD + new TextComponentString(I18n.format("container.particlegenerator.tip1_2.line1")).getUnformattedText());
			}
			this.drawHoveringText(list, mouseX, mouseY);
		}
		
		//Tool tip 2
		if(PGenMath.isInRange(mouseX, mouseY, basePointX + 66, basePointX + 74, basePointY + 117, basePointY + 165)){
			List<String> list = Lists.<String>newArrayList();
			list.add(TextFormatting.WHITE + new TextComponentString(I18n.format("container.particlegenerator.tip2.line1")).getUnformattedText());
			list.add(TextFormatting.GRAY + new TextComponentString(I18n.format("container.particlegenerator.tip2.line2")).getUnformattedText());
			list.add(TextFormatting.GRAY + new TextComponentString(I18n.format("container.particlegenerator.tip2.line3")).getUnformattedText());
			list.add(TextFormatting.GRAY + new TextComponentString(I18n.format("container.particlegenerator.tip2.line4")).getUnformattedText());
			list.add(TextFormatting.GRAY + new TextComponentString(I18n.format("container.particlegenerator.tip2.line5")).getUnformattedText());
			this.drawHoveringText(list, mouseX, mouseY);
		}
		
		//Tool tip 3
		if(PGenMath.isInRange(mouseX, mouseY, basePointX + 69, basePointX + 163, basePointY + 86, basePointY + 101)){
			if(EnumParticleTypes.getParticleFromId(chosedParticle).getArgumentCount() > 0){
				List<String> list = Lists.<String>newArrayList();
				if(chosedParticle == EnumParticleTypes.ITEM_CRACK.getParticleID()){
					list.add(TextFormatting.WHITE + new TextComponentString(I18n.format("container.particlegenerator.tip3_1.line1")).getUnformattedText());
					list.add(TextFormatting.GRAY + new TextComponentString(I18n.format("container.particlegenerator.example")).getUnformattedText());
					list.add(TextFormatting.DARK_GRAY + "minecraft:iron_axe");
				}else{
					list.add(TextFormatting.WHITE + new TextComponentString(I18n.format("container.particlegenerator.tip3_2.line1")).getUnformattedText());
					list.add(TextFormatting.GRAY + new TextComponentString(I18n.format("container.particlegenerator.example")).getUnformattedText());
					list.add(TextFormatting.DARK_GRAY + "minecraft:planks");
				}
				this.drawHoveringText(list, mouseX, mouseY);
			}
		}
		
		//Tool tip 4
		if(PGenMath.isInRange(mouseX, mouseY, basePointX + 69, basePointX + 163, basePointY + 102, basePointY + 117)){
			if(EnumParticleTypes.getParticleFromId(chosedParticle).getArgumentCount() > 0){
				List<String> list = Lists.<String>newArrayList();
				if(chosedParticle == EnumParticleTypes.ITEM_CRACK.getParticleID()){
					list.add(TextFormatting.WHITE + new TextComponentString(I18n.format("container.particlegenerator.tip4_1.line1")).getUnformattedText());
					list.add(TextFormatting.GRAY + new TextComponentString(I18n.format("container.particlegenerator.example")).getUnformattedText());
					list.add(TextFormatting.DARK_GRAY + "4");
				}else{
					list.add(TextFormatting.WHITE + new TextComponentString(I18n.format("container.particlegenerator.tip4_2.line1")).getUnformattedText());
					list.add(TextFormatting.GRAY + new TextComponentString(I18n.format("container.particlegenerator.example")).getUnformattedText());
					list.add(TextFormatting.DARK_GRAY + "4");
				}
				this.drawHoveringText(list, mouseX, mouseY);
			}
		}
		
		//Tool tip 5
		for(int i = 0; i < 5; i++){
			if(PGenMath.isInRange(mouseX, mouseY, basePointX + 7, basePointX + 156, basePointY + 16 + (i * 10), basePointY + 27 + (i * 10))){
				if(EnumParticleTypes.getParticleFromId(currentShownParticles + i).getArgumentCount() > 0){
					List<String> list = Lists.<String>newArrayList();
					list.add(TextFormatting.WHITE + EnumParticleTypes.getParticleFromId(currentShownParticles + i).name());
					list.add(TextFormatting.GOLD + new TextComponentString(I18n.format("container.particlegenerator.tip5.line1")).getUnformattedText());
					list.add(TextFormatting.GRAY + new TextComponentString(I18n.format("container.particlegenerator.tip5.line2")).getUnformattedText());
					this.drawHoveringText(list, mouseX, mouseY);
				}
			}
		}
	}
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
    	super.mouseClicked(mouseX, mouseY, mouseButton);
		int basePointX = this.width/2 - textureWidth/2;
		int basePointY = this.height/2 - textureHeight/2;
		
    	this.positionX.mouseClicked(mouseX, mouseY, mouseButton);
    	this.positionY.mouseClicked(mouseX, mouseY, mouseButton);
    	this.positionZ.mouseClicked(mouseX, mouseY, mouseButton);
    	this.argument1.mouseClicked(mouseX, mouseY, mouseButton);
    	this.argument2.mouseClicked(mouseX, mouseY, mouseButton);
    	this.movementX.mouseClicked(mouseX, mouseY, mouseButton);
    	this.movementY.mouseClicked(mouseX, mouseY, mouseButton);
    	this.movementZ.mouseClicked(mouseX, mouseY, mouseButton);
		
		if(mouseButton == 0){
			if(mouseX > basePointX + 156 && mouseX < basePointX + 168 && mouseY > basePointY + 18 && mouseY < basePointY + 25){
				if(currentShownParticles > 0){
					currentShownParticles--;
					this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
				}
			}
			if(mouseX > basePointX + 156 && mouseX < basePointX + 168 && mouseY > basePointY + 58 && mouseY < basePointY + 66){
				if(currentShownParticles < numberOfParticles - 5){
					currentShownParticles++;
					this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
				}
			}
			
			if(mouseX > basePointX + 156 && mouseX < basePointX + 168 && mouseY > basePointY + 26 && mouseY < basePointY + 57){
				int position = mouseY - (basePointY + 27); //0 - 29
				float percentaje = (float) position / 29;
				currentShownParticles = (int) Math.floor((numberOfParticles - 5) * percentaje);
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			}
			
			for(int i = 0; i < 5; i++){
				if(PGenMath.isInRange(mouseX, mouseY, basePointX + 7, basePointX + 156, basePointY + 17 + (i * 10), basePointY + 27 + (i * 10))){
					chosedParticle = currentShownParticles + i;
					this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
				}
			}
		}
    }
    
    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick){
    	super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
		int basePointX = this.width/2 - textureWidth/2;
		int basePointY = this.height/2 - textureHeight/2;
		
		if(clickedMouseButton == 0){
			if(mouseX > basePointX + 156 && mouseX < basePointX + 168 && mouseY > basePointY + 26 && mouseY < basePointY + 57){
				int position = mouseY - (basePointY + 27); //0 - 29
				float percentaje = (float) position / 29;
				currentShownParticles = (int) Math.floor((numberOfParticles - 5) * percentaje);
			}
		}
    }
    
    @Override
    public void handleMouseInput() throws IOException{
    	super.handleMouseInput();
        int rotation = Mouse.getEventDWheel();
        int mouseX = this.width - Mouse.getX();
        int mouseY = this.height - Mouse.getY();
		int basePointX = this.width/2 - textureWidth/2;
		int basePointY = this.height/2 - textureHeight/2;
		
        int movement = rotation / 120;
        if(movement > 0){
        	currentShownParticles = Math.max(0, currentShownParticles - movement);
        }else{
        	currentShownParticles = Math.min(numberOfParticles - 5, currentShownParticles - movement);
        }
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException{
    	super.keyTyped(typedChar, keyCode);
    	this.argument1.textboxKeyTyped(typedChar, keyCode);
    	this.argument2.textboxKeyTyped(typedChar, keyCode);
    	if(Character.isDigit(typedChar) || Character.isISOControl(typedChar) || typedChar == '.' || typedChar == '-' || typedChar == '+' || typedChar == '~'){
        	this.positionX.textboxKeyTyped(typedChar, keyCode);
        	this.positionY.textboxKeyTyped(typedChar, keyCode);
        	this.positionZ.textboxKeyTyped(typedChar, keyCode);
        	this.movementX.textboxKeyTyped(typedChar, keyCode);
        	this.movementY.textboxKeyTyped(typedChar, keyCode);
        	this.movementZ.textboxKeyTyped(typedChar, keyCode);
    	}
    }
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException{
		if(button == this.doneButton){
			int particleID = chosedParticle;
			
			float x;
			
			try{
				x = Float.valueOf((this.positionX.getText().isEmpty()?"0":this.positionX.getText()));
			} catch(NumberFormatException e){
				x = tile.x;
			}
			
			float y;
			
			try{
				y = Float.valueOf((this.positionY.getText().isEmpty()?"0":this.positionY.getText()));
			} catch(NumberFormatException e){
				y = tile.y;
			}
			
			float z;
			
			try{
				z = Float.valueOf((this.positionZ.getText().isEmpty()?"0":this.positionZ.getText()));
			} catch(NumberFormatException e){
				z = tile.z;
			}
			
			
			float vX;
			
			try{
				vX = Float.valueOf((this.movementX.getText().isEmpty()?"0":this.movementX.getText()));
			} catch(NumberFormatException e){
				vX = tile.vX;
			}
			
			float vY;
			
			try{
				vY = Float.valueOf((this.movementY.getText().isEmpty()?"0":this.movementY.getText()));
			} catch(NumberFormatException e){
				vY = tile.vY;
			}
			
			float vZ;
			
			try{
				vZ = Float.valueOf((this.movementZ.getText().isEmpty()?"0":this.movementZ.getText()));
			} catch(NumberFormatException e){
				vZ = tile.vZ;
			}
			
			/*float x = Float.valueOf(positionX.getText().replace("~", String.valueOf(this.tile.getPos().getX())));
			float y = Float.valueOf(positionY.getText().replace("~", String.valueOf(this.tile.getPos().getY())));
			float z = Float.valueOf(positionZ.getText().replace("~", String.valueOf(this.tile.getPos().getZ())));
			float vX = Float.valueOf(movementX.getText().replace("~", String.valueOf(this.tile.getPos().getX())));
			float vY = Float.valueOf(movementY.getText().replace("~", String.valueOf(this.tile.getPos().getY())));
			float vZ = Float.valueOf(movementZ.getText().replace("~", String.valueOf(this.tile.getPos().getZ())));*/
			int arg1ID = 0;
			boolean isIDValid = true;
			if(!this.argument1.getText().isEmpty()){
				if(chosedParticle == EnumParticleTypes.ITEM_CRACK.getParticleID()){
					isIDValid = Item.getByNameOrId(this.argument1.getText()) != null;
				}else{
					isIDValid = Block.getBlockFromName(this.argument1.getText()) != null;
				}
			}
			
			if(!this.argument1.getText().isEmpty() && isIDValid){
				if(chosedParticle == EnumParticleTypes.ITEM_CRACK.getParticleID()){
					arg1ID = Item.getIdFromItem(Item.getByNameOrId(this.argument1.getText()));
				}else{
					arg1ID = Block.getIdFromBlock(Block.getBlockFromName(this.argument1.getText()));
				}
			}
			
			int arg2Metadata = Integer.valueOf((this.argument2.getText().isEmpty()?"0":this.argument2.getText()));
			
			PGenPacketHandler.INSTANCE.sendToServer(new ParticleGeneratorMessage(tile.getPos(), particleID, x, y, z, vX, vY, vZ, arg1ID, arg2Metadata));
			
			tile.particleID = particleID;
			tile.x = x;
			tile.y = y;
			tile.z = z;
			tile.vX = vX;
			tile.vY = vY;
			tile.vZ = vZ;
			tile.arg1ID = arg1ID;
			tile.arg2Metadata = arg2Metadata;
			
			this.mc.displayGuiScreen(null);
			if(this.mc.currentScreen == null){
				this.mc.setIngameFocus();
			}
		} else if(button == this.cancelButton){
			this.mc.displayGuiScreen(null);
			if(this.mc.currentScreen == null){
				this.mc.setIngameFocus();
			}
		}
	}
}
