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

import net.fabricmc.fabric.api.tools.DynamicMiningStats;
import net.fabricmc.fabric.api.util.TriState;
import net.fabricmc.fabric.impl.tools.ToolManager;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
	@Shadow
	public abstract Item getItem();

	@Inject(at = @At("HEAD"), method = "isEffectiveOn", cancellable = true)
	public void isEffectiveOn(BlockState state, CallbackInfoReturnable<Boolean> info) {
		TriState triState = ToolManager.handleIsEffectiveOn((ItemStack) (Object) this, state);
		if (triState != TriState.DEFAULT) {
			info.setReturnValue(triState.get());
			info.cancel();
		}
	}

	@Inject(at = @At("HEAD"), method = "getMiningSpeed", cancellable = true)
	public void getBlockBreakingSpeed(BlockState state, CallbackInfoReturnable<Float> info) {
		TriState triState = ToolManager.handleIsEffectiveOn((ItemStack) (Object) this, state);
		if (triState != TriState.DEFAULT) {
			Item item = this.getItem();
			float miningSpeed;
			//first check if it's got dynamic tool stats
			if (item instanceof DynamicMiningStats) {
				miningSpeed = ((DynamicMiningStats) this.getItem()).getMiningSpeed((ItemStack)(Object) this);
				//then check if it's a mining tool
			} else if (item instanceof MiningToolItemAccessor) {
				miningSpeed = ((MiningToolItemAccessor) this.getItem()).getMiningSpeed();
				//if neither, let vanilla do its work
			} else {
				return;
			}
			info.setReturnValue(triState.get() ?  miningSpeed : 1.0F);
		}
	}

}
