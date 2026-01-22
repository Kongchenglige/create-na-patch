package org.antarcticgardens.newage.content.motors.extension;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.motor.CreativeMotorBlock;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.VecHelper;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.antarcticgardens.newage.content.motors.extension.variants.IMotorExtensionVariant;

import java.util.List;

public class MotorExtensionBlockEntity extends SmartBlockEntity {
    private MotorExtensionScrollValueBehaviour stressBehavior;
    private float multiplier = 1;
    private final IMotorExtensionVariant variant;

    public MotorExtensionBlockEntity(BlockEntityType<?> arg, BlockPos arg2, BlockState arg3, IMotorExtensionVariant variant) {
        super(arg, arg2, arg3);
        this.variant = variant;
    }

    public static BlockEntityBuilder.BlockEntityFactory<MotorExtensionBlockEntity> create(IMotorExtensionVariant variant) {
        return (type, pos, state) -> new MotorExtensionBlockEntity(type, pos, state, variant);
    }

    @Override
    public void tick() {
        super.tick();
        
        if (multiplier > variant.getMultiplier()) {
            multiplier = variant.getMultiplier();
            stressBehavior.setValue((int) (multiplier * 100));
        }
        
        stressBehavior.step = variant.getScrollStep();
        stressBehavior.betweenValidated(1, (int)(100 * variant.getMultiplier()));
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        stressBehavior = new MotorExtensionScrollValueBehaviour(Lang.translateDirect("new_age.motor.stress_multiplier"), this, new MotorValueBox(), 1);
        stressBehavior.value = 100;
        stressBehavior.withCallback(i -> {
            multiplier = i/100f;
            stressBehavior.value = i;
            this.notifyUpdate();
        });
        behaviours.add(stressBehavior);
    }
    
    public float getMultiplier() {
        return multiplier;
    }
    
    public IMotorExtensionVariant getVariant() {
        return variant;
    }

    static class MotorValueBox extends ValueBoxTransform.Sided {
        @Override
        protected Vec3 getSouthLocation() {
            return VecHelper.voxelSpace(8, 8, 12.5);
        }

        @Override
        public Vec3 getLocalOffset(BlockState state) {
            Direction facing = state.getValue(CreativeMotorBlock.FACING);
            return super.getLocalOffset(state).add(Vec3.atLowerCornerOf(facing.getNormal())
                    .scale(-1 / 16f));
        }

        @Override
        public void rotate(BlockState state, PoseStack ms) {
            super.rotate(state, ms);
            Direction facing = state.getValue(CreativeMotorBlock.FACING);
            if (facing.getAxis() == Direction.Axis.Y)
                return;
            if (getSide() != Direction.UP)
                return;
            TransformStack.cast(ms)
                    .rotateZ(-AngleHelper.horizontalAngle(facing) + 180);
        }

        @Override
        protected boolean isSideActive(BlockState state, Direction direction) {
            Direction facing = state.getValue(CreativeMotorBlock.FACING);
            if (facing.getAxis() != Direction.Axis.Y && direction == Direction.DOWN)
                return false;
            return direction.getAxis() != facing.getAxis();
        }
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        multiplier = compound.getFloat("stressMultiplier");
        stressBehavior.value = (int)multiplier*100;
        super.read(compound, clientPacket);
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        compound.putFloat("stressMultiplier", multiplier);
        super.write(compound, clientPacket);
    }
}
