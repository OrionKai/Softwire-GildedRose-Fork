package com.gildedrose.items

open class AgedItem(name: String, sellIn: Int, quality: Int) : BaseItem(name, sellIn, quality) {
    override fun calculateQualityChange(): Int {
        return super.calculateQualityChange() * -1
    }
}