package org.example;

import java.lang.reflect.Field;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static String parseObject(Object parsableObject) throws IllegalAccessException {
        // 1. Taking the object we need,
        // and we ask + receive data for the class

       Class parsableObjectClass =  parsableObject.getClass();

        Field[] fieldCollection = parsableObjectClass.getDeclaredFields();

        // We will make a String builder that would keep all variables
        // for the XML document

        StringBuilder xmlBuilder = new StringBuilder();

        for( Field singleField : fieldCollection ){

            // This changes the private variables to be accessible
            singleField.setAccessible(true);

            if(singleField.isAnnotationPresent(org.example.Documentable.class)){

                Documentable documentableAnnotation = singleField.getAnnotation(org.example.Documentable.class);
                String fieldName = documentableAnnotation.title().equals("_")
                                ? singleField.getName()
                                : documentableAnnotation.title();


                xmlBuilder.append("<")
                        .append(fieldName)
                        .append(">")
                        .append(singleField.get(parsableObject))
                        .append("</")
                        .append(fieldName)
                        .append(">");
            }
        }

        return xmlBuilder.toString();
    }

    // Realisation of JSON parser
    // which receives instance and returns arbitrary JSON object

    // We can return JSON value only if the CLASS
    // is annotated with JSONentity annotation

    // Each field from the class has to have annotation
    // JSONField with two properties - title
    // - name of the field
    // - expected_type with two properties STRING and PLAIN
    // -> if the value is STRING the result should be enclosed with quotes - ( " " )
    // -> if the value is PLAIN the result should not have quotes


    public static String parseObjectToJSON(Object parsableObject) throws IllegalAccessException {

        Class parsableObjectClass = parsableObject.getClass();

        //Checking if the class has the JSONEntity annotation
        if (!parsableObjectClass.isAnnotationPresent(org.example.JSONEntity.class)) {

            // Return an empty JSON object if the class is not annotated
            return "{}";
        }


        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        Field[] fieldCollectionJSON = parsableObjectClass.getDeclaredFields();
        boolean firstField = true;

        for (Field singleField : fieldCollectionJSON) {
            singleField.setAccessible(true);
            // Check if the field has the JSONField annotation
            if(singleField.isAnnotationPresent(
                    org.example.JSONEntity.class)){
                continue;
            }

            JSONEntity jsonFieldAnnotation = singleField.getAnnotation(org.example.JSONEntity.class);
            String annotationTitle = jsonFieldAnnotation.title().equals("_")
                                    ? singleField.getName()
                                    :jsonFieldAnnotation.title();
            ExpectedType type = jsonFieldAnnotation.expectedType();

            jsonBuilder.append("\"")
                    .append(annotationTitle)
                    .append("\"")
                    .append(":");
            if( type == ExpectedType.STRING){
                jsonBuilder.append("\"")
                        .append(singleField.get(parsableObject))
                        .append("\"");
            }
            if (type == ExpectedType.PLAIN){
                jsonBuilder.append("")
                        .append(singleField.get(parsableObject))
                        .append("");
            }
            jsonBuilder.append(",");
        }

        String result = jsonBuilder.toString();
        String jsonNoComma = result.substring(0, result.length() - 1);
        return jsonNoComma + "}";
    }

    public static void main(String[] args) throws IllegalAccessException {

        Item myItem = new Item();
        System.out.println(parseObject(myItem));



    }
}