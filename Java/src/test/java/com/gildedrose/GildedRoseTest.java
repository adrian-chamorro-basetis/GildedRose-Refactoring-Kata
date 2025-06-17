package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void itemHasSellInProperty() {
        Item item = new Item("Item", 10, 0);
        assertEquals(10, item.sellIn);
    }

    @Test
    void itemHasQualityProperty() {
        Item item = new Item("Item", 10, 20);
        assertEquals(20, item.quality);
    }

    @Test
    void itemSellInDecreasesBy1AsTimePasses() {
        Item[] items = new Item[]{
            new Item("Item", 10, 20),
            new Item("Item", 5, 30)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertItemSellIn(app.items[0], 9);
        assertItemSellIn(app.items[1], 4);
    }

    @Test
    void itemQualityDecreasesBy1AsTimePasses() {
        Item[] items = new Item[]{
            new Item("Item", 10, 20),
            new Item("Item", 5, 30)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertItemQuality(app.items[0], 19);
        assertItemQuality(app.items[1], 29);
    }

    @Test
    void itemQualityDegradeTwiceFasterWhenSellInPassed() {
        Item[] items = new Item[]{
            new Item("Item", 0, 4),
            new Item("Item", 0, 3)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertItemQuality(app.items[0], 2);
        assertItemQuality(app.items[1], 1);
    }

    @Test
    void itemQualityIsNeverNegativeAsTimePasses() {
        Item[] items = new Item[]{
            new Item("Item", 0, 0),
            new Item("Item", 0, 1),
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertItemQuality(app.items[0], 0);
        assertItemQuality(app.items[1], 0);
    }

    @Test
    void agedBrieIncreaseQualityWhenOlder() {
        Item[] items = new Item[]{
            new Item("Aged Brie", 1, 0)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertItemQuality(app.items[0], 1);
    }

    @Test
    void itemQualityCantBeGreaterThan50AsTimePasses() {
        Item[] items = new Item[]{
            new Item("Aged Brie", 1, 50),
            new Item("Item", 1, 50),
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertItemQuality(app.items[0], 50);
        assertItemQuality(app.items[1], 49);
    }

    @Test
    void sulfurasNeverDecreaseSellInAndQualityAsTimePasses() {
        Item[] items = new Item[]{
            new Item("Sulfuras, Hand of Ragnaros", 1, 50)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertItemSellIn(app.items[0], 1);
        assertItemQuality(app.items[0], 50);
    }

    @Test
    void backstagePassesQualityIncreasesBy1WhenMoreThan10Days() {
        Item[] items = new Item[]{
            new Item("Backstage passes to a TAFKAL80ETC concert", 11, 20)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertItemQuality(app.items[0], 21);
    }

    @Test
    void backstagePassesQualityIncreasesBy2When10DaysOrLess() {
        Item[] items = new Item[]{
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertItemQuality(app.items[0], 22);
    }

    @Test
    void backstagePassesQualityIncreasesBy3When5DaysOrLess() {
        Item[] items = new Item[]{
            new Item("Backstage passes to a TAFKAL80ETC concert", 1, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertItemQuality(app.items[0], 23);
        assertItemQuality(app.items[1], 23);
    }

    @Test
    void backstagePassesQualityDropsTo0OnConcertDay() {
        Item[] items = new Item[]{
            new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertItemQuality(app.items[0], 0);
    }

    @Test
    void backstagePassesQualityDropsTo0AfterConcert() {
        Item[] items = new Item[]{
            new Item("Backstage passes to a TAFKAL80ETC concert", -1, 20)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertItemQuality(app.items[0], 0);
    }

    private void assertItemQuality(Item item, int expectedQuality) {
        assertEquals(expectedQuality, item.quality, "Not expected quality for item: " + item.name);
    }

    private void assertItemSellIn(Item item, int expectedSellIn) {
        assertEquals(expectedSellIn, item.sellIn, "Not expected sell in for item: " + item.name);
    }
}
