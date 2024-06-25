package com.gildedrose

class GildedRose(var items: List<Item>) {

    fun updateItemQuality(item: Item) {
        var multiplier = 1
        var change = 1

        if (item.name.contains("Backstage passes", true)) {
            // Not sure if this part specifically would be good practice
            if (item.sellIn < 0) {
                item.quality = 0
                return
            }

            if (item.sellIn < 10)
                change += 1
            if (item.sellIn < 5)
                change += 1
        }

        if (item.sellIn < 0)
            multiplier *= 2

        if (item.name.startsWith("Conjured", true))
            multiplier *= 2

        if (item.name.contains("Aged Brie", true)
            || item.name.contains("Backstage passes", true))
            multiplier *= -1

        item.quality -= (change * multiplier)
        item.quality = item.quality.coerceIn(0..50)
    }

    fun updateItemSellIn(item: Item) {
        item.sellIn -= 1
    }

    fun updateQuality() {
        for (item in items) {
            if (!item.name.contains("Sulfuras, Hand of Ragnaros")) {
                updateItemSellIn(item)
                updateItemQuality(item)
            }
        }
    }
}
