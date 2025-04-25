package cn.elytra.mod.gtnn.modules.simple.module.waterline_skip

import gregtech.api.enums.GTValues
import gregtech.api.util.GTModHandler
import gregtech.api.util.GTOreDictUnificator
import gregtech.api.util.GTUtility
import gtPlusPlus.xmod.gregtech.api.interfaces.IGregtechItemContainer
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

enum class GTNNCataList: IGregtechItemContainer {
	LimpidWaterCatalyst,
	FlawlessWaterCatalyst,

	;

	private var mStack: ItemStack? = null
	private var mHasNotBeenSet = true

	override fun set(aItem: Item?): GTNNCataList {
		this.mHasNotBeenSet = false
		if (aItem == null) {
			return this
		}
		val aStack = ItemStack(aItem, 1, 0)
		this.mStack = GTUtility.copyAmount(1, aStack)
		return this
	}

	override fun set(aStack: ItemStack?): GTNNCataList {
		this.mHasNotBeenSet = false
		this.mStack = GTUtility.copyAmount(1, aStack)
		return this
	}

	override fun getItem(): Item? {
		if (this.mHasNotBeenSet) {
			throw IllegalAccessError("The Enum '" + this.name + "' has not been set to an Item at this time!")
		}
		if (GTUtility.isStackInvalid(this.mStack)) {
			return null
		}
		return this.mStack!!.getItem()
	}

	override fun getBlock(): Block? {
		if (this.mHasNotBeenSet) {
			throw IllegalAccessError("The Enum '" + this.name + "' has not been set to an Item at this time!")
		}
		return getBlockFromStack(this.getItem())
	}

	override fun hasBeenSet(): Boolean {
		return !this.mHasNotBeenSet
	}

	override fun isStackEqual(aStack: Any?): Boolean {
		return this.isStackEqual(aStack, false, false)
	}

	override fun isStackEqual(aStack: Any?, aWildcard: Boolean, aIgnoreNBT: Boolean): Boolean {
		if (GTUtility.isStackInvalid(aStack)) {
			return false
		}
		return GTUtility
			.areUnificationsEqual(aStack as ItemStack, if (aWildcard) this.getWildcard(1) else this.get(1), aIgnoreNBT)
	}

	fun getBlockFromStack(aStack: Any?): Block? {
		if (GTUtility.isStackInvalid(aStack)) return Blocks.air
		return Block.getBlockFromItem((aStack as ItemStack).getItem())
	}

	override fun get(aAmount: Long, vararg aReplacements: Any?): ItemStack? {
		if (this.mHasNotBeenSet) {
			throw IllegalAccessError("The Enum '" + this.name + "' has not been set to an Item at this time!")
		}
		if (GTUtility.isStackInvalid(this.mStack)) {
			return GTUtility.copyAmount(aAmount, *aReplacements)
		}
		return GTUtility.copyAmount(aAmount, GTOreDictUnificator.get(this.mStack))
	}

	override fun getWildcard(aAmount: Long, vararg aReplacements: Any?): ItemStack? {
		if (this.mHasNotBeenSet) {
			throw IllegalAccessError("The Enum '" + this.name + "' has not been set to an Item at this time!")
		}
		if (GTUtility.isStackInvalid(this.mStack)) {
			return GTUtility.copyAmount(aAmount, *aReplacements)
		}
		return GTUtility.copyAmountAndMetaData(aAmount, GTValues.W.toLong(), GTOreDictUnificator.get(this.mStack))
	}

	override fun getUndamaged(aAmount: Long, vararg aReplacements: Any?): ItemStack? {
		if (this.mHasNotBeenSet) {
			throw IllegalAccessError("The Enum '" + this.name + "' has not been set to an Item at this time!")
		}
		if (GTUtility.isStackInvalid(this.mStack)) {
			return GTUtility.copyAmount(aAmount, *aReplacements)
		}
		return GTUtility.copyAmountAndMetaData(aAmount, 0, GTOreDictUnificator.get(this.mStack))
	}

	override fun getAlmostBroken(aAmount: Long, vararg aReplacements: Any?): ItemStack? {
		if (this.mHasNotBeenSet) {
			throw IllegalAccessError("The Enum '" + this.name + "' has not been set to an Item at this time!")
		}
		if (GTUtility.isStackInvalid(this.mStack)) {
			return GTUtility.copyAmount(aAmount, *aReplacements)
		}
		return GTUtility
			.copyAmountAndMetaData(
				aAmount,
				(this.mStack!!.getMaxDamage() - 1).toLong(),
				GTOreDictUnificator.get(this.mStack)
			)
	}

	override fun getWithName(aAmount: Long, aDisplayName: String?, vararg aReplacements: Any?): ItemStack? {
		val rStack = this.get(1, *aReplacements)
		if (GTUtility.isStackInvalid(rStack)) {
			return null
		}
		rStack!!.setStackDisplayName(aDisplayName)
		return GTUtility.copyAmount(aAmount, rStack)
	}

	override fun getWithCharge(aAmount: Long, aEnergy: Int, vararg aReplacements: Any?): ItemStack? {
		val rStack = this.get(1, *aReplacements)
		if (GTUtility.isStackInvalid(rStack)) {
			return null
		}
		GTModHandler.chargeElectricItem(rStack, aEnergy, Int.Companion.MAX_VALUE, true, false)
		return GTUtility.copyAmount(aAmount, rStack)
	}

	override fun getWithDamage(aAmount: Long, aMetaValue: Long, vararg aReplacements: Any?): ItemStack? {
		if (this.mHasNotBeenSet) {
			throw IllegalAccessError("The Enum '" + this.name + "' has not been set to an Item at this time!")
		}
		if (GTUtility.isStackInvalid(this.mStack)) {
			return GTUtility.copyAmount(aAmount, *aReplacements)
		}
		return GTUtility.copyAmountAndMetaData(aAmount, aMetaValue, GTOreDictUnificator.get(this.mStack))
	}

	override fun registerOre(vararg aOreNames: Any?): GTNNCataList {
		if (this.mHasNotBeenSet) {
			throw IllegalAccessError("The Enum '" + this.name + "' has not been set to an Item at this time!")
		}
		for (tOreName in aOreNames) {
			GTOreDictUnificator.registerOre(tOreName, this.get(1))
		}
		return this
	}

	override fun registerWildcardAsOre(vararg aOreNames: Any?): GTNNCataList {
		if (this.mHasNotBeenSet) {
			throw IllegalAccessError("The Enum '" + this.name + "' has not been set to an Item at this time!")
		}
		for (tOreName in aOreNames) {
			GTOreDictUnificator.registerOre(tOreName, this.getWildcard(1))
		}
		return this
	}
}
