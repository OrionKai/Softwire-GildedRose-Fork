package com.gildedrose

import com.gildedrose.items.AgedItem
import com.gildedrose.items.BackstagePassItem
import com.gildedrose.items.BaseItem
import com.gildedrose.items.ConjuredItem
import com.gildedrose.items.LegendaryItem

fun main(args: Array<String>) {

    println("OMGHAI!")

    val items = listOf(
        BaseItem("+5 Dexterity Vest", 10, 20), //
            AgedItem("Aged Brie", 2, 0), //
            BaseItem("Elixir of the Mongoose", 5, 7), //
            LegendaryItem("Sulfuras, Hand of Ragnaros", 0, 80), //
            LegendaryItem("Sulfuras, Hand of Ragnaros", -1, 80),
            BackstagePassItem("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            BackstagePassItem("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            BackstagePassItem("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            // this conjured item does not work properly yet
            ConjuredItem("Conjured Mana Cake", 3, 6)
    )

    val app = GildedRose(items)

    var days = 2
    if (args.size > 0) {
        days = Integer.parseInt(args[0]) + 1
    }

    for (i in 0..days - 1) {
        println("-------- day $i --------")
        println("name, sellIn, quality")
        for (item in items) {
            println(item)
        }
        println()
        app.updateQuality()
    }
}
