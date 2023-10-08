package com.shine.foodfleet.data.dummy

import com.shine.foodfleet.R
import com.shine.foodfleet.model.Category

interface DummyCategoryDataSource {
    fun getMenuCategory(): List<Category>
}

class DummyCategoryDataSourceImpl() : DummyCategoryDataSource {
    override fun getMenuCategory(): List<Category> {
        return listOf(
            Category(
                R.drawable.ic_rice,
                "Rice"
            ),
            Category(
                R.drawable.burger,
                "Burger"
            ),
            Category(
                R.drawable.ic_snack,
                "Snack"
            ),
            Category(
                R.drawable.drink,
                "Drink"
            ),
        )
    }


}
