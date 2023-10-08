package com.shine.foodfleet.data.dummy

import com.shine.foodfleet.R
import com.shine.foodfleet.model.Menu


interface DummyMenuDataSource {
    fun getMenuList(): List<Menu>
}

class DummyMenuDataSourceImpl() : DummyMenuDataSource {
    override fun getMenuList(): List<Menu> {
        return listOf(
            Menu(
                1,
                "Ayam Goreng",
                20000.0,
                R.drawable.ic_ayamm,
                4.5,
                "Potongan ayam yang digoreng garing dengan rempah-rempah, cocok untuk pecinta makanan gurih.",
                "Lokasi Restoran XYZ"
            ),
            Menu(
                2,
                "Burger",
                25000.0,
                R.drawable.ic_burger,
                4.2,
                "Burger lezat dengan patty daging, sayuran segar dan saus spesial.",
                "Lokasi Restoran XYZ"
            ),
            Menu(
                3,
                "Sate Ayam",
                30000.0,
                R.drawable.ic_sate_ayam,
                4.8,
                "Potongan-potongan ayam yang dipanggang dengan bumbu kacang khas, memberikan cita rasa manis dan pedas.",
                "Lokasi Restoran XYZ"
            ),
            Menu(
                4,
                "Spaghetti",
                22000.0,
                R.drawable.ic_spaghetti,
                4.4,
                "Pasta lezat dengan saus tomat dan daging cincang, disajikan dengan keju parmesan yang meleleh.",
                "Lokasi Restoran XYZ"
            ),
            Menu(
                5,
                "Sushi",
                18.000,
                R.drawable.ic_sushi,
                4.6,
                "Gumpalan nasi yang dilapisi dengan potongan ikan segar, cocok untuk pencinta hidangan Jepang.",
                "Lokasi Restoran XYZ"
            ),
            Menu(
                6,
                "Ramen",
                15.000,
                R.drawable.ic_ramen,
                4.0,
                "Mie gandum dengan kuah kaldu lezat, dilengkapi dengan irisan daging dan sayuran.",
                "Lokasi Restoran XYZ"
            ),
            Menu(
                7,
                "Kentang Goreng",
                12.000,
                R.drawable.ic_kentang_goreng,
                4.3,
                "Kentang potong yang digoreng hingga renyah, cocok sebagai camilan atau pendamping hidangan.",
                "Lokasi Restoran XYZ"
            )
        )
    }
}