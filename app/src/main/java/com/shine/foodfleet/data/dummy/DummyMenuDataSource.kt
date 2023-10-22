package com.shine.foodfleet.data.dummy

import com.shine.foodfleet.model.Menu


interface DummyMenuDataSource {
    fun getMenus(): List<Menu>
}

class DummyMenuDataSourceImpl() : DummyMenuDataSource {
    override fun getMenus(): List<Menu> {
        return listOf(
            Menu(
                1,
                "French Fries",
                "These burger patties are made with ground beef and an easy bread crumb mixture. Nothing beats a simple hamburger on a warm summer evening. Pile these burgers with your favorite condiments and pop open a cool drink!",
                25000,
                "Rp. 25.000",
                "https://images.tokopedia.net/img/cache/250-square/hDjmkQ/2022/6/3/96472a39-7a63-4179-9f7d-44cf0959982a.png",
                "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345"
            ),
            Menu(
                2,
                "French Fries",
                "These burger patties are made with ground beef and an easy bread crumb mixture. Nothing beats a simple hamburger on a warm summer evening. Pile these burgers with your favorite condiments and pop open a cool drink!",
                25000,
                "Rp. 25.000",
                "https://images.tokopedia.net/img/cache/250-square/hDjmkQ/2022/6/3/96472a39-7a63-4179-9f7d-44cf0959982a.png",
                "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
            ),

            )
    }
}