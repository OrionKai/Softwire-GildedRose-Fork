package com.gildedrose.items

class BackstagePassItem(name: String, sellIn: Int, quality: Int) : BaseItem(name, sellIn, quality) {
    override fun calculateQualityChange(): Int {
        if (sellIn < 0)
            return -quality
        else {
            var qualityChange = 1
            if (sellIn < 10)
                qualityChange += 1
            if (sellIn < 5)
                qualityChange += 1

            return qualityChange
        }
    }
}