package com.javathon.queuemonitoring.model.google_response;

import java.util.List;

public class DistanceResponse {

    public List<Row> rows;

    public static class Row {

        public List<Element> elements;

        public static class Element {

            public Field distance;
            public Field duration;
            public String status;

            public static class Field {

                public String text;
                public int value;
            }
        }
    }
}
