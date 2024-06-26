package com.gildedrose.items

open class ConjuredItem(name: String, sellIn: Int, quality: Int) : BaseItem(name, sellIn, quality) {
    override fun calculateQualityChange(): Int {
        return super.calculateQualityChange() * 2
    }
}