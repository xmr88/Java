package org.example;

@JSONEntity
public class ItemJSON {

    @JSONEntity
    private String name = "Book";

    @JSONEntity(title = "sub_title")
    private String subTitle  = "Science";

    @JSONEntity(expectedType = ExpectedType.PLAIN)
    private double price     = 12.0;
}