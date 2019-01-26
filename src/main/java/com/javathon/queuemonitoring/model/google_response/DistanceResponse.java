package com.javathon.queuemonitoring.model.google_response;

import java.util.List;

public class DistanceResponse {

    public List<Row> rows;

    public class Row {

        public List<Element> elements;

        public class Element {

            public Field distance;
            public Field duration;

            public class Field {

                public String text;
                public int value;
            }
        }
    }
}
