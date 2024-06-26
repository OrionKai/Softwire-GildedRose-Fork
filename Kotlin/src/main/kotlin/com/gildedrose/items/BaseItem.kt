package com.gildedrose.items

open class BaseItem(name: String, sellIn: Int, quality: Int) : Item(name, sellIn, quality) {
    open fun updateSellIn() {
        sellIn -= 1
    }

    open fun calculateQualityChange(): Int {
        if (sellIn >= 0) {
            return -1
        } else {
            return -2
        }
    }

    open fun boundQuality() {
        quality = quality.coerceIn(0..50)
    }

    // Note: updateSellIn() should always be called before updateQuality()
    open fun updateQuality() {
        quality += calculateQualityChange()
        boundQuality()
    }

    fun update() {
        updateSellIn()
        updateQuality()
    }
}