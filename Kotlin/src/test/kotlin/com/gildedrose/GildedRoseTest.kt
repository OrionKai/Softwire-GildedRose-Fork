package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GildedRoseTest {

    /* Helper functions for testing */
    fun updateAndCheckSellInAndQuality(app: GildedRose,
                                       expectedSellIn: Int,
                                       expectedQuality: Int) {
        app.updateQuality()
        checkSellInAndQuality(app, 0, expectedSellIn, expectedQuality)
        assertEquals(expectedSellIn, app.items[0].sellIn)
        assertEquals(expectedQuality, app.items[0].quality)
    }

    fun checkSellInAndQuality(app: GildedRose,
                              index: Int,
                              expectedSellIn: Int,
                              expectedQuality: Int) {
        assertEquals(expectedSellIn, app.items[index].sellIn)
        assertEquals(expectedQuality, app.items[index].quality)
    }

    @Test
    fun testItemSellInAndQualityDegrades() {
        val items = listOf(Item("Thunderfury", 2, 11))
        val app = GildedRose(items)

        updateAndCheckSellInAndQuality(app, 1, 10)
        updateAndCheckSellInAndQuality(app, 0, 9)

//        app.updateQuality()
//        assertEquals(1, app.items[0].sellIn)
//        assertEquals(10, app.items[0].quality)
//
//        /* Possible edge case; check that item quality doesn't degrade
//        twice as fast already when sellIn has just degraded to 0 */
//        app.updateQuality()
//        assertEquals(0, app.items[0].sellIn)
//        assertEquals(9, app.items[0].quality)
    }

    @Test
    fun testItemDegradesTwiceAfterSellIn() {
        val items = listOf(Item("Lembas", 0, 11))
        val app = GildedRose(items)

        updateAndCheckSellInAndQuality(app, -1, 9)
        updateAndCheckSellInAndQuality(app, -2, 7)
    }

    @Test
    fun testQualityNeverNegativeForExpired() {
        val items = listOf(Item("Expired Lembas", 1, 1))
        val app = GildedRose(items)

        updateAndCheckSellInAndQuality(app, 0, 0)
        updateAndCheckSellInAndQuality(app, -1, 0)
        updateAndCheckSellInAndQuality(app, -2, 0)
    }

    @Test
    fun testQualityNeverNegativeForNonExpired() {
        /* Check possible edge case for item whose quality reaches
        0 before sellIn does */
        val items = listOf(Item("Expired Lembas", 3, 1))
        val app = GildedRose(items)

        updateAndCheckSellInAndQuality(app, 2, 0)
        updateAndCheckSellInAndQuality(app, 1, 0)
        updateAndCheckSellInAndQuality(app, 0, 0)
    }

    @Test
    fun testAgedBrieIncreasesQuality() {
        val items = listOf(Item("Aged Brie", 1, 3))
        val app = GildedRose(items)

        updateAndCheckSellInAndQuality(app, 0, 4)
        updateAndCheckSellInAndQuality(app, -1, 6)
    }

    @Test
    fun testSulfurasDoesNotDegrade() {
        val items = listOf(Item("Sulfuras, Hand of Ragnaros", 80, 80))
        val app = GildedRose(items)

        updateAndCheckSellInAndQuality(app, 80, 80)
    }

    @Test
    fun testExpiredSulfurasDoesNotDegrade() {
        /* Check possible edge case for when Sulfuras's sellIn value
        is somehow negative */
        val items = listOf(Item("Sulfuras, Hand of Ragnaros", -1, 80))
        val app = GildedRose(items)

        updateAndCheckSellInAndQuality(app, -1, 80)
    }

    @Test
    fun testBackstagePassIncreasesQualityBeforeTenDaysLeft() {
        val items = listOf(Item("Backstage passes to a TAFKAL80ETC concert", 12, 10))
        val app = GildedRose(items)

        updateAndCheckSellInAndQuality(app, 11, 11)
        updateAndCheckSellInAndQuality(app, 10, 12)
    }

    @Test
    fun testBackstagePassIncreasesQualityAfterTenDaysLeftBeforeFiveDaysLeft() {
        val items = listOf(Item("Backstage passes to a TAFKAL80ETC concert", 10, 10))
        val app = GildedRose(items)

        for (i in 1..5) {
            updateAndCheckSellInAndQuality(app, 10 - i, 10 + (2 * i))
        }
    }

    @Test
    fun testBackstagePassIncreasesQualityAfterFiveDaysLeftBeforeConcert() {
        val items = listOf(Item("Backstage passes to a TAFKAL80ETC concert", 5, 10))
        val app = GildedRose(items)

        for (i in 1..5) {
            updateAndCheckSellInAndQuality(app, 5 - i, 10 + (3 * i))
        }
    }

    @Test
    fun testBackstagePassQualityZeroAfterConcert() {
        val items = listOf(Item("Backstage passes to a TAFKAL80ETC concert", 0, 10))
        val app = GildedRose(items)

        updateAndCheckSellInAndQuality(app, -1, 0)
        updateAndCheckSellInAndQuality(app, -2, 0)
    }

    @Test
    fun testItemQualityMaxFifty() {
        // Have one item for each possible way of increasing quality
        val items = listOf(Item("Backstage passes to a TAFKAL80ETC concert", 15, 49),
            Item("Backstage passes to a TAFKAL80ETC concert", 9, 49),
            Item("Backstage passes to a TAFKAL80ETC concert", 4, 49),
            Item("Aged Brie", 5, 49),
            Item("Aged Brie", 0, 49)
        )

        val app = GildedRose(items)

        for (i in 1..2) {
            app.updateQuality()
            for (item in items) {
                assertEquals(50, item.quality)
            }
        }
    }
}


