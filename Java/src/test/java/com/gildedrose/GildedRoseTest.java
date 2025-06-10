package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void itemHasSellInProperty() {
        Item item = ItemMother.createWithAnyName(10, 20);
        assertItemSellIn(item, 10);
    }

    @Test
    void itemHasQualityProperty() {
        Item item = ItemMother.createWithAnyName(10, 20);
        assertItemQuality(item, 20);
    }

    @Test
    void itemSellInDecreasesBy1AsTimePasses() {
        Item[] items = ItemMother.createMany(
            ItemMother.createWithAnyName(10, 20),
            ItemMother.createWithAnyName(5, 30)
        );

        GildedRose app = createAndUpdateQuality(items);

        assertItemSellIn(app.items[0], 9);
        assertItemSellIn(app.items[1], 4);
    }

    @Test
    void itemQualityDecreasesBy1AsTimePasses() {
        Item[] items = ItemMother.createMany(
            ItemMother.createWithAnyName(10, 20),
            ItemMother.createWithAnyName(5, 30)
        );

        GildedRose app = createAndUpdateQuality(items);

        assertItemQuality(app.items[0], 19);
        assertItemQuality(app.items[1], 29);
    }

    @Test
    void itemQualityDegradeTwiceFasterWhenSellInPassed() {
        Item[] items = ItemMother.createMany(
            ItemMother.createWithAnyName(0, 4),
            ItemMother.createWithAnyName(0, 3)
        );

        GildedRose app = createAndUpdateQuality(items);

        assertItemQuality(app.items[0], 2);
        assertItemQuality(app.items[1], 1);
    }

    @Test
    void itemQualityIsNeverNegativeAsTimePasses() {
        Item[] items = ItemMother.createMany(
            ItemMother.createWithAnyName(0, 0),
            ItemMother.createWithAnyName(0, 1)
        );

        GildedRose app = createAndUpdateQuality(items);

        assertItemQuality(app.items[0], 0);
        assertItemQuality(app.items[1], 0);
    }

    @Test
    void agedBrieIncreaseQualityWhenOlder() {
        Item[] items = ItemMother.createSingle("Aged Brie", 1, 0);
        GildedRose app = createAndUpdateQuality(items);
        assertItemQuality(app.items[0], 1);
    }

    @Test
    void itemQualityCantBeGreaterThan50AsTimePasses() {
        Item[] items = ItemMother.createMany(
            ItemMother.createAgedBrie(1, 50),
            ItemMother.createWithAnyName(1, 50)
        );

        GildedRose app = createAndUpdateQuality(items);

        assertItemQuality(app.items[0], 50);
        assertItemQuality(app.items[1], 49);
    }

    @Test
    void sulfurasNeverDecreaseSellInAndQualityAsTimePasses() {
        Item[] items = ItemMother.createSingleSulfuras(1);

        GildedRose app = createAndUpdateQuality(items);

        assertItemSellIn(app.items[0], 1);
        assertItemQuality(app.items[0], 80);
    }

    @Test
    void backstagePassesQualityIncreasesBy1WhenMoreThan10Days() {
        assertBackstagePassesQuality(11, 20, 21);
    }

    @Test
    void backstagePassesQualityIncreasesBy2When10DaysOrLess() {
        assertBackstagePassesQuality(10, 30, 32);
    }

    @Test
    void backstagePassesQualityIncreasesBy3When5DaysOrLess() {
        assertBackstagePassesQuality(1, 40, 43);
        assertBackstagePassesQuality(5, 40, 43);
    }

    @Test
    void backstagePassesQualityDropsTo0OnConcertDay() {
        assertBackstagePassesQuality(0, 10, 0);
    }

    @Test
    void backstagePassesQualityDropsTo0AfterConcert() {
        assertBackstagePassesQuality(-1, 20, 0);
    }

    @Test
    void conjuredItemsQualityDegradesTwiceAsFast() {
        Item[] items = ItemMother.createMany(
            ItemMother.createConjured(3, 6),
            ItemMother.createConjured(0, 6),
            ItemMother.createConjured(5, 1),
            ItemMother.createConjured(0, 1)
        );
        GildedRose app = createAndUpdateQuality(items);

        assertItemQuality(app.items[0], 4);
        assertItemQuality(app.items[1], 2);
        assertItemQuality(app.items[2], 0);
        assertItemQuality(app.items[3], 0);
    }

    private static void assertItemQuality(Item item, int expectedQuality) {
        assertEquals(expectedQuality, item.quality, "Not expected quality for item");
    }

    private static void assertItemSellIn(Item item, int expectedSellIn) {
        assertEquals(expectedSellIn, item.sellIn, "Not expected sell in for item");
    }

    private static GildedRose createAndUpdateQuality(Item... items) {
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        return app;
    }

    private void assertBackstagePassesQuality(int sellIn, int initialQuality, int expectedQuality) {
        Item[] items = ItemMother.createSingleBackstagePass(sellIn, initialQuality);
        GildedRose app = createAndUpdateQuality(items);
        assertItemQuality(app.items[0], expectedQuality);
    }
}
