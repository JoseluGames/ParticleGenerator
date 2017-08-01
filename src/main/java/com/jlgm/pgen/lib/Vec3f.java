package com.jlgm.pgen.lib;

import javax.annotation.Nullable;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3i;
public class Vec3f
{
    public static final Vec3f ZERO = new Vec3f(0.0f, 0.0f, 0.0f);
    /** X coordinate of Vec3D */
    public final float x;
    /** Y coordinate of Vec3D */
    public final float y;
    /** Z coordinate of Vec3D */
    public final float z;

    public Vec3f(float xIn, float yIn, float zIn)
    {
        if (xIn == -0.0f)
        {
            xIn = 0.0f;
        }

        if (yIn == -0.0f)
        {
            yIn = 0.0f;
        }

        if (zIn == -0.0f)
        {
            zIn = 0.0f;
        }

        this.x = xIn;
        this.y = yIn;
        this.z = zIn;
    }

    public Vec3f(Vec3i vector)
    {
        this((float)vector.getX(), (float)vector.getY(), (float)vector.getZ());
    }

    /**
     * Returns a new vector with the result of the specified vector minus this.
     */
    public Vec3f subtractReverse(Vec3f vec)
    {
        return new Vec3f(vec.x - this.x, vec.y - this.y, vec.z - this.z);
    }

    /**
     * Normalizes the vector to a length of 1 (except if it is the zero vector)
     */
    public Vec3f normalize()
    {
        float f0 = (float)MathHelper.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        return f0 < 1.0E-4f ? ZERO : new Vec3f(this.x / f0, this.y / f0, this.z / f0);
    }

    public float dotProduct(Vec3f vec)
    {
        return this.x * vec.x + this.y * vec.y + this.z * vec.z;
    }

    /**
     * Returns a new vector with the result of this vector x the specified vector.
     */
    public Vec3f crossProduct(Vec3f vec)
    {
        return new Vec3f(this.y * vec.z - this.z * vec.y, this.z * vec.x - this.x * vec.z, this.x * vec.y - this.y * vec.x);
    }

    public Vec3f subtract(Vec3f vec)
    {
        return this.subtract(vec.x, vec.y, vec.z);
    }

    public Vec3f subtract(float x, float y, float z)
    {
        return this.addVector(-x, -y, -z);
    }

    public Vec3f add(Vec3f vec)
    {
        return this.addVector(vec.x, vec.y, vec.z);
    }

    /**
     * Adds the specified x,y,z vector components to this vector and returns the resulting vector. Does not change this
     * vector.
     */
    public Vec3f addVector(float x, float y, float z)
    {
        return new Vec3f(this.x + x, this.y + y, this.z + z);
    }

    /**
     * Euclidean distance between this and the specified vector, returned as float.
     */
    public float distanceTo(Vec3f vec)
    {
        float f0 = vec.x - this.x;
        float f1 = vec.y - this.y;
        float f2 = vec.z - this.z;
        return (float)MathHelper.sqrt(f0 * f0 + f1 * f1 + f2 * f2);
    }

    /**
     * The square of the Euclidean distance between this and the specified vector.
     */
    public float squareDistanceTo(Vec3f vec)
    {
        float f0 = vec.x - this.x;
        float f1 = vec.y - this.y;
        float f2 = vec.z - this.z;
        return f0 * f0 + f1 * f1 + f2 * f2;
    }

    public float squareDistanceTo(float xIn, float yIn, float zIn)
    {
        float f0 = xIn - this.x;
        float f1 = yIn - this.y;
        float f2 = zIn - this.z;
        return f0 * f0 + f1 * f1 + f2 * f2;
    }

    public Vec3f scale(float p_186678_1_)
    {
        return new Vec3f(this.x * p_186678_1_, this.y * p_186678_1_, this.z * p_186678_1_);
    }

    /**
     * Returns the length of the vector.
     */
    public float lengthVector()
    {
        return (float)MathHelper.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public float lengthSquared()
    {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    /**
     * Returns a new vector with x value equal to the second parameter, along the line between this vector and the
     * passed in vector, or null if not possible.
     */
    @Nullable
    public Vec3f getIntermediateWithXValue(Vec3f vec, float x)
    {
        float f0 = vec.x - this.x;
        float f1 = vec.y - this.y;
        float f2 = vec.z - this.z;

        if (f0 * f0 < 1.0000000116860974E-7f)
        {
            return null;
        }
        else
        {
            float f3 = (x - this.x) / f0;
            return f3 >= 0.0f && f3 <= 1.0f ? new Vec3f(this.x + f0 * f3, this.y + f1 * f3, this.z + f2 * f3) : null;
        }
    }

    /**
     * Returns a new vector with y value equal to the second parameter, along the line between this vector and the
     * passed in vector, or null if not possible.
     */
    @Nullable
    public Vec3f getIntermediateWithYValue(Vec3f vec, float y)
    {
        float f0 = vec.x - this.x;
        float f1 = vec.y - this.y;
        float f2 = vec.z - this.z;

        if (f1 * f1 < 1.0000000116860974E-7f)
        {
            return null;
        }
        else
        {
            float f3 = (y - this.y) / f1;
            return f3 >= 0.0f && f3 <= 1.0f ? new Vec3f(this.x + f0 * f3, this.y + f1 * f3, this.z + f2 * f3) : null;
        }
    }

    /**
     * Returns a new vector with z value equal to the second parameter, along the line between this vector and the
     * passed in vector, or null if not possible.
     */
    @Nullable
    public Vec3f getIntermediateWithZValue(Vec3f vec, float z)
    {
        float f0 = vec.x - this.x;
        float f1 = vec.y - this.y;
        float f2 = vec.z - this.z;

        if (f2 * f2 < 1.0000000116860974E-7f)
        {
            return null;
        }
        else
        {
            float f3 = (z - this.z) / f2;
            return f3 >= 0.0f && f3 <= 1.0f ? new Vec3f(this.x + f0 * f3, this.y + f1 * f3, this.z + f2 * f3) : null;
        }
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (!(p_equals_1_ instanceof Vec3f))
        {
            return false;
        }
        else
        {
            Vec3f vec3f = (Vec3f)p_equals_1_;

            if (Float.compare(vec3f.x, this.x) != 0)
            {
                return false;
            }
            else if (Float.compare(vec3f.y, this.y) != 0)
            {
                return false;
            }
            else
            {
                return Float.compare(vec3f.z, this.z) == 0;
            }
        }
    }

    public int hashCode()
    {
        long j = Double.doubleToLongBits(this.x);
        int i = (int)(j ^ j >>> 32);
        j = Double.doubleToLongBits(this.y);
        i = 31 * i + (int)(j ^ j >>> 32);
        j = Double.doubleToLongBits(this.z);
        i = 31 * i + (int)(j ^ j >>> 32);
        return i;
    }

    public String toString()
    {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public Vec3f rotatePitch(float pitch)
    {
        float f = MathHelper.cos(pitch);
        float f3 = MathHelper.sin(pitch);
        float f0 = this.x;
        float f1 = this.y * (float)f + this.z * (float)f3;
        float f2 = this.z * (float)f - this.y * (float)f3;
        return new Vec3f(f0, f1, f2);
    }

    public Vec3f rotateYaw(float yaw)
    {
        float f = MathHelper.cos(yaw);
        float f3 = MathHelper.sin(yaw);
        float f0 = this.x * (float)f + this.z * (float)f3;
        float f1 = this.y;
        float f2 = this.z * (float)f - this.x * (float)f3;
        return new Vec3f(f0, f1, f2);
    }

    /**
     * returns a Vec3d from given pitch and yaw degrees as Vec2f
     */
    public static Vec3f fromPitchYawVector(Vec2f p_189984_0_)
    {
        return fromPitchYaw(p_189984_0_.x, p_189984_0_.y);
    }

    /**
     * returns a Vec3d from given pitch and yaw degrees
     */
    public static Vec3f fromPitchYaw(float p_189986_0_, float p_189986_1_)
    {
        float f = MathHelper.cos(-p_189986_1_ * 0.017453292F - (float)Math.PI);
        float f1 = MathHelper.sin(-p_189986_1_ * 0.017453292F - (float)Math.PI);
        float f2 = -MathHelper.cos(-p_189986_0_ * 0.017453292F);
        float f3 = MathHelper.sin(-p_189986_0_ * 0.017453292F);
        return new Vec3f((float)(f1 * f2), (float)f3, (float)(f * f2));
    }
}