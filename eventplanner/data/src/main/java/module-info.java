module eventplanner.data {
    
    requires transitive com.google.gson;

    opens eventplanner.data to com.google.gson;

    requires transitive eventplanner.core;

    exports eventplanner.data;
}
