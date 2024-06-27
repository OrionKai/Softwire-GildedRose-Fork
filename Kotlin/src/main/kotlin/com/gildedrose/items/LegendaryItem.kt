package com.gildedrose.items

open class LegendaryItem(name: String, sellIn: Int, quality: Int) : BaseItem(name, sellIn, quality) {
    override fun updateSellIn() {

    }

    override fun calculateQualityChange(): Int {
        return 0
    }

    override fun boundQuality() {

    }
}