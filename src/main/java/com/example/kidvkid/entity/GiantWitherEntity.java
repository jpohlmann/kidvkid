package com.example.kidvkid.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.world.World;

public class GiantWitherEntity extends WitherEntity {

    public GiantWitherEntity(EntityType<? extends WitherEntity> p_i50226_1_, World p_i50226_2_) {
        super(p_i50226_1_, p_i50226_2_);
        this.setHealth(this.getMaxHealth());
        this.getNavigator().setCanSwim(true);
        this.experienceValue = 50;
    }

    @Override
    public ITextComponent getDisplayName() {
        ITextComponent text = new StringTextComponent("?????");
        return text;
    }
}
