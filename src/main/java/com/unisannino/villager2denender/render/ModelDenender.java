package com.unisannino.villager2denender.render;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelDenender extends ModelBase
{
	//fields
    public ModelRenderer head;
    public ModelRenderer hatBottom;
    public ModelRenderer hatTop;
    public ModelRenderer headwear;
    public ModelRenderer body;
    public ModelRenderer rightArm;
    public ModelRenderer leftArm;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;
    public boolean isSneak;
    public boolean aimedBow;

  public ModelDenender()
  {
    textureWidth = 64;
    textureHeight = 64;

      this.head = new ModelRenderer(this, 0, 0);
      this.head.addBox(-4F, -8F, -4F, 8, 8, 8);
      this.head.setRotationPoint(0F, -14F, 0F);
      this.head.setTextureSize(64, 64);
      this.head.mirror = true;
      setRotation(this.head, 0F, 0F, 0F);
      this.hatBottom = new ModelRenderer(this, 0, 43);
      this.hatBottom.addBox(-6F, -9F, -6F, 12, 1, 12);
      this.hatBottom.setRotationPoint(0F, -23F, 0F);
      this.hatBottom.setTextureSize(64, 64);
      this.hatBottom.mirror = true;
      setRotation(this.hatBottom, 0F, 0F, 0F);
      this.hatTop = new ModelRenderer(this, 0, 32);
      this.hatTop.addBox(-3F, -13F, -3F, 6, 4, 6);
      this.hatTop.setRotationPoint(0F, -23F, 0F);
      this.hatTop.setTextureSize(64, 64);
      this.hatTop.mirror = true;
      setRotation(this.hatTop, 0F, 0F, 0F);
      this.headwear = new ModelRenderer(this, 0, 16);
      this.headwear.addBox(-4F, -8F, -4F, 8, 8, 8);
      this.headwear.setRotationPoint(0F, -14F, 0F);
      this.headwear.setTextureSize(64, 64);
      this.headwear.mirror = true;
      setRotation(this.headwear, 0F, 0F, 0F);
      this.body = new ModelRenderer(this, 32, 16);
      this.body.addBox(-4F, 0F, -2F, 8, 12, 4);
      this.body.setRotationPoint(0F, -14F, 0F);
      this.body.setTextureSize(64, 64);
      this.body.mirror = true;
      setRotation(this.body, 0F, 0F, 0F);
      this.rightArm = new ModelRenderer(this, 56, 0);
      this.rightArm.addBox(-1F, -2F, -1F, 2, 30, 2);
      this.rightArm.setRotationPoint(-5F, -12F, 0F);
      this.rightArm.setTextureSize(64, 64);
      this.rightArm.mirror = true;
      setRotation(this.rightArm, 0F, 0F, 0F);
      this.leftArm = new ModelRenderer(this, 56, 0);
      this.leftArm.addBox(-1F, -2F, -1F, 2, 30, 2);
      this.leftArm.setRotationPoint(5F, -12F, 0F);
      this.leftArm.setTextureSize(64, 64);
      this.leftArm.mirror = true;
      setRotation(this.leftArm, 0F, 0F, 0F);
      this.leftArm.mirror = false;
      this.rightLeg = new ModelRenderer(this, 56, 0);
      this.rightLeg.addBox(-1F, 0F, -1F, 2, 30, 2);
      this.rightLeg.setRotationPoint(-2F, -2F, 0F);
      this.rightLeg.setTextureSize(64, 64);
      this.rightLeg.mirror = true;
      setRotation(rightLeg, 0F, 0F, 0F);
      this.leftLeg = new ModelRenderer(this, 56, 0);
      this.leftLeg.addBox(-1F, 0F, -1F, 2, 30, 2);
      this.leftLeg.setRotationPoint(2F, -2F, 0F);
      this.leftLeg.setTextureSize(64, 64);
      this.leftLeg.mirror = true;
      setRotation(this.leftLeg, 0F, 0F, 0F);
      this.leftLeg.mirror = false;
  }

  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.head.render(f5);
    this.hatBottom.render(f5);
    this.hatTop.render(f5);
    this.headwear.render(f5);
    this.body.render(f5);
    this.rightArm.render(f5);
    this.leftArm.render(f5);
    rightLeg.render(f5);
    this.leftLeg.render(f5);
  }

  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

  public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
  {

      this.head.rotateAngleY = p_78087_4_ / (180F / (float)Math.PI);
      this.head.rotateAngleX = p_78087_5_ / (180F / (float)Math.PI);
      this.headwear.rotateAngleY = this.head.rotateAngleY;
      this.headwear.rotateAngleX = this.head.rotateAngleX;

      //帽子の角度を顔に合わせる
      this.hatTop.rotateAngleY = this.head.rotateAngleY;
      this.hatTop.rotateAngleX = this.head.rotateAngleX;
      this.hatBottom.rotateAngleY = this.head.rotateAngleY;
      this.hatBottom.rotateAngleX = this.head.rotateAngleX;

      this.rightArm.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + (float)Math.PI) * 2.0F * p_78087_2_ * 0.5F;
      this.leftArm.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 2.0F * p_78087_2_ * 0.5F;
      this.rightArm.rotateAngleZ = 0.0F;
      this.leftArm.rotateAngleZ = 0.0F;
      this.rightLeg.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_;
      this.leftLeg.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + (float)Math.PI) * 1.4F * p_78087_2_;
      this.rightLeg.rotateAngleY = 0.0F;
      this.leftLeg.rotateAngleY = 0.0F;

      if (isRiding)
      {
          this.rightArm.rotateAngleX += -((float)Math.PI / 5F);
          this.leftArm.rotateAngleX += -((float)Math.PI / 5F);
          this.rightLeg.rotateAngleX = -((float)Math.PI * 2F / 5F);
          this.leftLeg.rotateAngleX = -((float)Math.PI * 2F / 5F);
          this.rightLeg.rotateAngleY = ((float)Math.PI / 10F);
          this.leftLeg.rotateAngleY = -((float)Math.PI / 10F);
      }

      this.rightArm.rotateAngleY = 0.0F;
      this.leftArm.rotateAngleY = 0.0F;

      if (swingProgress > -9990F)
      {
          float f6 = swingProgress;
          this.body.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * (float)Math.PI * 2.0F) * 0.2F;
          this.rightArm.rotationPointZ = MathHelper.sin(this.body.rotateAngleY) * 5F;
          this.rightArm.rotationPointX = -MathHelper.cos(this.body.rotateAngleY) * 5F;
          this.leftArm.rotationPointZ = -MathHelper.sin(this.body.rotateAngleY) * 5F;
          this.leftArm.rotationPointX = MathHelper.cos(this.body.rotateAngleY) * 5F;
          this.rightArm.rotateAngleY += this.body.rotateAngleY;
          this.leftArm.rotateAngleY += this.body.rotateAngleY;
          this.leftArm.rotateAngleX += this.body.rotateAngleY;

          f6 = 1.0F - swingProgress;
          f6 *= f6;
          f6 *= f6;
          f6 = 1.0F - f6;
          float f8 = MathHelper.sin(f6 * (float)Math.PI);
          float f10 = MathHelper.sin(swingProgress * (float)Math.PI) * -(this.head.rotateAngleX - 0.7F) * 0.75F;

          this.rightArm.rotateAngleX -= f8 * 1.2D + f10;
          this.rightArm.rotateAngleY += this.body.rotateAngleY * 2.0F;
          this.rightArm.rotateAngleZ = MathHelper.sin(swingProgress * (float)Math.PI) * -0.4F;
      }

      if (this.isSneak)
      {
          this.body.rotateAngleX = 0.5F;
          this.rightArm.rotateAngleX += 0.4F;
          this.leftArm.rotateAngleX += 0.4F;
          this.rightLeg.rotationPointZ = 4.0F;
          this.leftLeg.rotationPointZ = 4.0F;
          this.rightLeg.rotationPointY = 9.0F;
          this.leftLeg.rotationPointY = 9.0F;
          this.head.rotationPointY = 1.0F;
          this.headwear.rotationPointY = 1.0F;

          //帽子も頭に合わせる
          this.hatBottom.rotationPointY = 1.0F;
          this.hatTop.rotationPointY = 1.0F;
      }
      else
      {
          this.body.rotateAngleX = 0.0F;
          this.rightLeg.rotationPointZ = 0.1F;
          this.leftLeg.rotationPointZ = 0.1F;
          this.rightLeg.rotationPointY = 12.0F;
          this.leftLeg.rotationPointY = 12.0F;
          this.head.rotationPointY = 0.0F;
          this.headwear.rotationPointY = 0.0F;

          //帽子も頭に合わせる
          this.hatBottom.rotationPointY = 0.0F;
          this.hatTop.rotationPointY = 0.0F;
      }

      this.rightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
      this.leftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
      this.rightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
      this.leftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;

      //denender
      this.head.showModel = true;

      float f6 = -14F;
      this.body.rotateAngleX = 0.0F;
      this.body.rotationPointY = f6;
      this.body.rotationPointZ = -0.0F;
      this.rightLeg.rotateAngleX -= 0.0F;
      this.leftLeg.rotateAngleX -= 0.0F;

      this.rightArm.rotateAngleX = (float)((double)this.rightArm.rotateAngleX * 0.5D);
      this.leftArm.rotateAngleX = (float)((double)this.leftArm.rotateAngleX * 0.5D);
      this.rightLeg.rotateAngleX = (float)((double)this.rightLeg.rotateAngleX * 0.5D);
      this.leftLeg.rotateAngleX = (float)((double)this.leftLeg.rotateAngleX * 0.5D);
      float f7 = 0.4F;

      if (this.rightArm.rotateAngleX > f7)
      {
          this.rightArm.rotateAngleX = f7;
      }

      if (this.leftArm.rotateAngleX > f7)
      {
          this.leftArm.rotateAngleX = f7;
      }

      if (this.rightArm.rotateAngleX < -f7)
      {
          this.rightArm.rotateAngleX = -f7;
      }

      if (this.leftArm.rotateAngleX < -f7)
      {
          this.leftArm.rotateAngleX = -f7;
      }

      if (this.rightLeg.rotateAngleX > f7)
      {
          this.rightLeg.rotateAngleX = f7;
      }

      if (this.leftLeg.rotateAngleX > f7)
      {
          this.leftLeg.rotateAngleX = f7;
      }

      if (this.rightLeg.rotateAngleX < -f7)
      {
          this.rightLeg.rotateAngleX = -f7;
      }

      if (this.leftLeg.rotateAngleX < -f7)
      {
          this.leftLeg.rotateAngleX = -f7;
      }

      this.rightArm.rotationPointZ = 0.0F;
      this.leftArm.rotationPointZ = 0.0F;
      this.rightLeg.rotationPointZ = 0.0F;
      this.leftLeg.rotationPointZ = 0.0F;
      this.rightLeg.rotationPointY = 9F + f6;
      this.leftLeg.rotationPointY = 9F + f6;
      this.head.rotationPointZ = -0F;
      this.head.rotationPointY = f6 + 1.0F;
      this.headwear.rotationPointX = this.head.rotationPointX;
      this.headwear.rotationPointY = this.head.rotationPointY;
      this.headwear.rotationPointZ = this.head.rotationPointZ;
      this.headwear.rotateAngleX = this.head.rotateAngleX;
      this.headwear.rotateAngleY = this.head.rotateAngleY;
      this.headwear.rotateAngleZ = this.head.rotateAngleZ;

      //頭と帽子を合わせる
      this.hatBottom.rotationPointX = this.head.rotationPointX;
      this.hatBottom.rotationPointY = this.head.rotationPointY;
      this.hatBottom.rotationPointZ = this.head.rotationPointZ;
      this.hatBottom.rotateAngleX = this.head.rotateAngleX;
      this.hatBottom.rotateAngleY = this.head.rotateAngleY;
      this.hatBottom.rotateAngleZ = this.head.rotateAngleZ;
      this.hatTop.rotationPointX = this.head.rotationPointX;
      this.hatTop.rotationPointY = this.head.rotationPointY;
      this.hatTop.rotationPointZ = this.head.rotationPointZ;
      this.hatTop.rotateAngleX = this.head.rotateAngleX;
      this.hatTop.rotateAngleY = this.head.rotateAngleY;
      this.hatTop.rotateAngleZ = this.head.rotateAngleZ;
  }

}
