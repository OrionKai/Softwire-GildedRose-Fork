package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GildedRoseTest {

    /* Helper functions for testing */

    // Only for tests that involve a single item
    fun updateAndCheckSellInAndQuality(app: GildedRose,
                                       expectedSellIn: Int,
                                       expectedQuality: Int) {
        app.updateQuality()
        checkSellInAndQuality(app, 0, expectedSellIn, expectedQuality)
    }
    fun updateAndCheckQuality(app: GildedRose,
                              expectedQuality: Int) {
        app.updateQuality()
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
    fun testItemSellInDegrades() {
        val items = listOf(Item("Thunderfury", 2, 11))
        val app = GildedRose(items)

        for (i in 1..3) {
            app.updateQuality()
            assertEquals(2 - i, app.items[0].sellIn)
        }
    }

    @Test
    fun testItemQualityDegrades() {
        val items = listOf(Item("Thunderfury", 2, 11))
        val app = GildedRose(items)

        for (i in 1..2) {
            app.updateQuality()
            assertEquals(11 - i, app.items[0].quality)
        }
    }

    @Test
    fun testConjuredItemQualityDegradesTwice() {
        val items = listOf(Item("Conjured Thunderfury", 2, 11))
        val app = GildedRose(items)

        for (i in 1..2) {
            app.updateQuality()
            assertEquals(11 - (i * 2), app.items[0].quality)
        }
    }

    @Test
    fun testItemDegradesTwiceAfterSellIn() {
        val items = listOf(Item("Lembas", 0, 11))
        val app = GildedRose(items)

        for (i in 1..2) {
            updateAndCheckQuality(app, 11 - (i * 2))
        }
    }

    @Test
    fun testConjuredItemDegradesQuadrupleAfterSellIn() {
        val items = listOf(Item("Conjured Lembas", 0, 11))
        val app = GildedRose(items)

        for (i in 1..2) {
            updateAndCheckQuality(app, 11 - (i * 4))
        }
    }

    @Test
    fun testQualityNeverNegativeForExpired() {
        val items = listOf(Item("Expired Lembas", 1, 1),
            Item("Conjured Expired Lembas", 1, 1))
        val app = GildedRose(items)

        for (i in 1..3) {
            app.updateQuality()
            assertEquals(0, items[0].quality)
            assertEquals(0, items[1].quality)
        }
    }

    @Test
    fun testQualityNeverNegativeForNonExpired() {
        /* Check possible edge case for item whose quality reaches
        0 before sellIn does */
        val items = listOf(Item("Stale Lembas", 3, 1),
            Item("Conjured Expired Lembas", 3, 1))
        val app = GildedRose(items)

        for (i in 1..3) {
            app.updateQuality()
            assertEquals(0, items[0].quality)
            assertEquals(0, items[1].quality)
        }
    }

    @Test
    fun testAgedBrieIncreasesQuality() {
        val items = listOf(Item("Aged Brie", 1, 3))
        val app = GildedRose(items)

        updateAndCheckQuality(app, 4)
        updateAndCheckQuality(app, 6)
    }

    @Test
    fun testConjuredAgedBrieIncreasesQuality() {
        val items = listOf(Item("Conjured Aged Brie", 1, 3))
        val app = GildedRose(items)

        updateAndCheckQuality(app, 5)
        updateAndCheckQuality(app, 9)
    }

    @Test
    fun testSulfurasDoesNotDegrade() {
        val items = listOf(Item("Sulfuras, Hand of Ragnaros", 80, 80))
        val app = GildedRose(items)

        updateAndCheckSellInAndQuality(app, 80, 80)
    }

    @Test
    fun testConjuredSulfurasDoesNotDegrade() {
        val items = listOf(Item("Conjured Sulfuras, Hand of Ragnaros", 80, 80))
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

        updateAndCheckQuality(app, 11)
        updateAndCheckQuality(app, 12)
    }

    @Test
    fun testConjuredBackstagePassIncreasesQualityBeforeTenDaysLeft() {
        val items = listOf(Item("Conjured backstage passes to a TAFKAL80ETC concert", 12, 10))
        val app = GildedRose(items)

        updateAndCheckQuality(app, 12)
        updateAndCheckQuality(app, 14)
    }

    @Test
    fun testBackstagePassIncreasesQualityAfterTenDaysLeftBeforeFiveDaysLeft() {
        val items = listOf(Item("Backstage passes to a TAFKAL80ETC concert", 10, 10))
        val app = GildedRose(items)

        for (i in 1..5) {
            updateAndCheckQuality(app, 10 + (2 * i))
        }
    }

    @Test
    fun testConjuredBackstagePassIncreasesQualityAfterTenDaysLeftBeforeFiveDaysLeft() {
        val items = listOf(Item("Conjured backstage passes to a TAFKAL80ETC concert", 10, 10))
        val app = GildedRose(items)

        for (i in 1..5) {
            updateAndCheckQuality(app, 10 + (4 * i))
        }
    }

    @Test
    fun testBackstagePassIncreasesQualityAfterFiveDaysLeftBeforeConcert() {
        val items = listOf(Item("Backstage passes to a TAFKAL80ETC concert", 5, 10))
        val app = GildedRose(items)

        for (i in 1..5) {
            updateAndCheckQuality(app, 10 + (3 * i))
        }
    }

    @Test
    fun testConjuredBackstagePassIncreasesQualityAfterFiveDaysLeftBeforeConcert() {
        val items = listOf(Item("Conjured backstage passes to a TAFKAL80ETC concert", 5, 10))
        val app = GildedRose(items)

        for (i in 1..5) {
            updateAndCheckQuality(app, 10 + (6 * i))
        }
    }

    @Test
    fun testBackstagePassQualityZeroAfterConcert() {
        val items = listOf(Item("Backstage passes to a TAFKAL80ETC concert", 0, 10))
        val app = GildedRose(items)

        updateAndCheckQuality(app, 0)
        updateAndCheckQuality(app, 0)
    }

    @Test
    fun testConjuredBackstagePassQualityZeroAfterConcert() {
        val items = listOf(Item("Conjured backstage passes to a TAFKAL80ETC concert", 0, 10))
        val app = GildedRose(items)

        updateAndCheckQuality(app, 0)
        updateAndCheckQuality(app, 0)
    }

    @Test
    fun testItemQualityMaxFifty() {
        // Have one item for each possible way of increasing quality
        val items = listOf(Item("Backstage passes to a TAFKAL80ETC concert", 15, 49),
            Item("Conjured backstage passes to a TAFKAL80ETC concert", 15, 49),
            Item("Backstage passes to a TAFKAL80ETC concert", 9, 49),
            Item("Conjured backstage passes to a TAFKAL80ETC concert", 9, 49),
            Item("Backstage passes to a TAFKAL80ETC concert", 4, 49),
            Item("Conjured backstage passes to a TAFKAL80ETC concert", 4, 49),
            Item("Aged Brie", 5, 49),
            Item("Conjured Aged Brie", 5, 49),
            Item("Aged Brie", 0, 49),
            Item("Conjured Aged Brie", 0, 49)
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


