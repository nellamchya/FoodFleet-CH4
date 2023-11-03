package com.shine.foodfleet.data.dummy

import com.shine.foodfleet.model.Category

interface DummyCategoryDataSource {
    fun getCategories(): List<Category>
}

class DummyCategoryDataSourceImpl() : DummyCategoryDataSource {
    override fun getCategories(): List<Category> {
        return listOf(
            Category(
                "https://raw.githubusercontent.com/hermasyp/CH3-asset-code-challenge/master/categories/ic_all_category.png",
                "All"
            ),
            Category(
                "https://images.deliveryhero.io/image/fd-kh/Products/1944603.jpg?width=%s",
                "Burger"
            ),
            Category(
                "https://d1vbn70lmn1nqe.cloudfront.net/prod/wp-content/uploads/2023/07/13024827/ini-cara-membuat-mie-goreng-yang-lebih-sehat-dengan-bahan-sederhana-halodoc.jpg.webp",
                "Mie"
            ),
            Category(
                "https://d17qo5cceyilur.cloudfront.net/IEKuwltITNeTnnqQt8ka_IMG_2170.jpg",
                "Snack"
            ),
            Category(
                "https://imgcdn.solopos.com/@space/2022/09/es-teh-res.jpg",
                "Minuman"
            )
        )
    }
}
