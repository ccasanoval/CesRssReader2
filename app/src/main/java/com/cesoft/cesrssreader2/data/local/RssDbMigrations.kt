package com.cesoft.cesrssreader2.data.local

import androidx.room.migration.Migration


internal object RssDbMigrations {

    const val latestVersion = 1

    val allMigrations: Array<Migration>
        get() = arrayOf(
                //// Add migrations here
                // migration_1_2()
        )

    // fun migration_1_2() = object : Migration(1, 2) {
    //    override fun migrate(database: SupportSQLiteDatabase) {
    //        // Add migration code/SQL here, referencing [V1] and [V2] constants
    //    }
    // }

    object V1 {
        object ItemEmtity {
            const val tableName = "items"
            object Column {
                const val id = "id"
                const val guid = "guid"
                const val title = "title"
                const val author = "author"
                const val link = "link"
                const val pubDate = "pubDate"
                const val description = "description"
                const val content = "content"
                const val image = "image"
                const val categories = "categories"
            }
        }
    }
}