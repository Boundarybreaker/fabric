/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.mixin.tools;

import net.fabricmc.fabric.api.tools.v1.ToolAttributeHolder;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ToolItem.class)
public abstract class MixinToolItem extends Item implements ToolAttributeHolder {

	@Shadow
	public abstract ToolMaterial getMaterial();

	public MixinToolItem(Settings settings) {
		super(settings);
	}

	@Override
	public int getMiningLevel(ItemStack stack) {
		return this.getMaterial().getMiningLevel();
	}

	@Override
	public float getMiningSpeed(ItemStack stack) {
		return this.getMaterial().getMiningSpeed();
	}
}
