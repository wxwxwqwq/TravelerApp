package com.example.travelapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "TravelDB"
        const val DATABASE_VERSION = 20

    }

    override fun onCreate(db: SQLiteDatabase) {




        //Добавление таблиц в базу данных

        db.execSQL("""
        CREATE TABLE Travel (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name STRING,
                days INTEGER
                )
                """)

        db.execSQL("""
        CREATE TABLE Travelers_in_travel (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name VARCHAR(30),
                gender VARCHAR(10),
                age INTEGER,
                restrictions STRING,
                carrying_weight INTEGER,
                max_weight INTEGER,
                travel_id INTEGER REFERENCES Travel (id) ON DELETE CASCADE ON UPDATE CASCADE,
                group_num INTEGER
                )
                """)

        db.execSQL("""
        CREATE TABLE Breakfast (
                day INTEGER,
                travel_id INTEGER REFERENCES Travel (id) ON DELETE CASCADE ON UPDATE CASCADE,
                dish_id INTEGER REFERENCES Dish (id) ON DELETE CASCADE ON UPDATE CASCADE,
                weight_of_dish INTEGER,
                CONSTRAINT pkBreakfast PRIMARY KEY (day, travel_id, dish_id)
                )
                """)

        db.execSQL("""
        CREATE TABLE Dinner (
                day INTEGER,
                travel_id INTEGER REFERENCES Travel (id) ON DELETE CASCADE ON UPDATE CASCADE,
                dish_id INTEGER REFERENCES Dish (id) ON DELETE CASCADE ON UPDATE CASCADE,
                weight_of_dish INTEGER,
                CONSTRAINT pkDinner PRIMARY KEY (day, travel_id, dish_id)
                )
                """)

        db.execSQL("""
        CREATE TABLE Supper (
                day INTEGER,
                travel_id INTEGER REFERENCES Travel (id) ON DELETE CASCADE ON UPDATE CASCADE,
                dish_id INTEGER REFERENCES Dish (id) ON DELETE CASCADE ON UPDATE CASCADE,
                weight_of_dish INTEGER,
                CONSTRAINT pkSupper PRIMARY KEY (day, travel_id, dish_id)
                )
                """)

        db.execSQL("""
        CREATE TABLE Traveler_ingredients (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                day INTEGER,
                traveler_id INTEGER REFERENCES Travelers_in_travel (id) ON DELETE CASCADE ON UPDATE CASCADE,
                ingredient_id INTEGER REFERENCES Ingredients (id) ON DELETE CASCADE ON UPDATE CASCADE,
                type_of_meal STRING,
                weight_of_food INTEGER
                )
                """)

        db.execSQL("""
        CREATE TABLE Dish (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name STRING,
                calories INTEGER,
                portion_weight INTEGER
                )
                """)

        db.execSQL("""
        CREATE TABLE Ingredients_in_dish (
                dish_id INTEGER REFERENCES Dish (id) ON DELETE CASCADE ON UPDATE CASCADE,
                ingredient_id INTEGER REFERENCES Ingredients (id) ON DELETE CASCADE ON UPDATE CASCADE,
                weight_of_ingredient INTEGER,
                CONSTRAINT pkDishIngredients PRIMARY KEY (dish_id, ingredient_id)
                )
                """)

        db.execSQL("""
        CREATE TABLE Ingredients (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name STRING
                )
                """)




        //Добавление ингредиентов в базу данных

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Топлёное масло"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Сушёная морковь"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Сушёный лук"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Рис"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Чернослив"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Изюм"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Сушёный чеснок"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Соль"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Гречневая крупа"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Перец сушеный"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Помидоры сушеные"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Сыр твёрдый"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Яичный порошок"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Тушёнка"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Горох"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Кукуруза"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Макароны"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Репчатый лук"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Грибы"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Картофель"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Морковь"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Перец"
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients (name) VALUES (
                "Лук репчатый"
                )
                """)








        //Добавление блюда в базу данных

        db.execSQL("""
        INSERT INTO Dish (name, calories, portion_weight) VALUES (
                "Каша гречневая",
                600,
                652
                )
                """)


        //Добавление ингредиентоы к блюду

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                1,
                9,
                300
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                1,
                14,
                250
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                1,
                23,
                100
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                1,
                8,
                2
                )
                """)





        //Добавление блюда в базу данных

        db.execSQL("""
        INSERT INTO Dish (name, calories, portion_weight) VALUES (
                "Походный плов",
                1054,
                703
                )
                """)



        //Добавление ингредиентоы к блюду

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                2,
                1,
                20
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                2,
                2,
                90
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                2,
                3,
                40
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                2,
                4,
                300
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                2,
                14,
                200
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                2,
                5,
                10
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                2,
                6,
                30
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                2,
                7,
                10
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                2,
                8,
                2
                )
                """)





        //Добавление блюда в базу данных

        db.execSQL("""
        INSERT INTO Dish (name, calories, portion_weight) VALUES (
                "Мясо с овощами",
                799,
                650
                )
                """)



        //Добавление ингредиентоы к блюду

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                3,
                14,
                250
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                3,
                15,
                200
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                3,
                16,
                200
                )
                """)





        //Добавление блюда в базу данных

        db.execSQL("""
        INSERT INTO Dish (name, calories, portion_weight) VALUES (
                "Грибной суп",
                490,
                696
                )
                """)



        //Добавление ингредиентоы к блюду

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                4,
                14,
                200
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                4,
                19,
                150
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                4,
                20,
                200
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                4,
                23,
                50
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                4,
                21,
                50
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                4,
                17,
                40
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                4,
                8,
                3
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                4,
                22,
                3
                )
                """)





        //Добавление блюда в базу данных

        db.execSQL("""
        INSERT INTO Dish (name, calories, portion_weight) VALUES (
                "Гречневая запеканка",
                802,
                640
                )
                """)



        //Добавление ингредиентоы к блюду

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                5,
                9,
                350
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                5,
                3,
                30
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                5,
                10,
                30
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                5,
                11,
                30
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                5,
                12,
                150
                )
                """)


        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                5,
                13,
                30
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                5,
                1,
                20
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                5,
                8,
                2
                )
                """)





        //Добавление блюда в базу данных

        db.execSQL("""
        INSERT INTO Dish (name, calories, portion_weight) VALUES (
                "Макароны с тушёнкой",
                938,
                672
                )
                """)



        //Добавление ингредиентоы к блюду

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                6,
                17,
                350
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                6,
                14,
                200
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                6,
                23,
                100
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                6,
                1,
                20
                )
                """)

        db.execSQL("""
        INSERT INTO Ingredients_in_dish (dish_id, ingredient_id, weight_of_ingredient) VALUES (
                6,
                8,
                2
                )
                """)





    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        //db.execSQL(""" DROP TABLE Food """)
        //db.execSQL(""" DROP TABLE Travelers_in_travel """)
        //db.execSQL(""" DROP TABLE Traveler_food """)
        //db.execSQL(""" DROP TABLE Breakfast """)
        //db.execSQL(""" DROP TABLE Dinner """)
        //db.execSQL(""" DROP TABLE Supper """)
        //db.execSQL(""" DROP TABLE Travel """)
        //db.execSQL(""" DROP TABLE Dish """)
        //db.execSQL(""" DROP TABLE Traveler_ingredients """)












    }
}