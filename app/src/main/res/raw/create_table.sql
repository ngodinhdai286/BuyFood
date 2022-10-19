CREATE TABLE [User] (
    email VARCHAR(100) UNIQUE NOT NULL PRIMARY KEY,
    password VARCHAR(100),
    fullname VARCHAR(100),
    avatar LONGTEXT,
    address LONGTEXT
);

    CREATE TABLE [Restaurant] (
        id INTEGER UNIQUE NOT NULL PRIMARY KEY AUTOINCREMENT,
        name VARCHAR(100),
        description LONGTEXT,
        rating INTEGER,
        picture LONGTEXT,
        address LONGTEXT,
        estimated_time INTEGER
    );

CREATE TABLE [Dish](
    id INTEGER UNIQUE NOT NULL PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(100),
    price FLOAT,
    picture LONGTEXT,
    description LONGTEXT,
    rating INTEGER,
    quantity_sold INTEGER,
    restaurant_id INTEGER NOT NULL,

    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(id)
);

CREATE TABLE [Favorite](
    email VARCHAR(100) NOT NULL,
    dish_id INTEGER NOT NULL,

    FOREIGN KEY (email) REFERENCES User(email),
    FOREIGN KEY (dish_id) REFERENCES Dish(id)
);

CREATE TABLE [Order](
    [id] INTEGER UNIQUE NOT NULL PRIMARY KEY AUTOINCREMENT,
    deliver_to LONGTEXT,
    payment_method VARCHAR(100),
    dish_id INTEGER,
    email VARCHAR(100),
    amount FLOAT,

    FOREIGN KEY (email) REFERENCES User(email),
    FOREIGN KEY (dish_id) REFERENCES Dish(id)
);

CREATE TABLE [Notification](
    [id] INTEGER UNIQUE NOT NULL PRIMARY KEY AUTOINCREMENT,
    email VARCHAR(100),
    title LONGTEXT,
    content LONGTEXT,
    is_new BIT,

    FOREIGN KEY (email) REFERENCES User(email)
);
