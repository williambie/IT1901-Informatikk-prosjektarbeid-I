module eventplanner.rest {

    requires com.google.gson;
    requires spring.boot.starter.web;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.beans;
    requires spring.web;
    requires spring.context;
    requires java.net.http;

    requires transitive eventplanner.core;
    requires transitive eventplanner.data;
    opens eventplanner.rest;
}
