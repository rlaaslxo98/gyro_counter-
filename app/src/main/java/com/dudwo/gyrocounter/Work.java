package com.dudwo.gyrocounter;

/**
 * Created by dudwo on 2017-04-06.
 */

public class Work {

    private String id;
    private String category;
    private String type;
    private int max;
    private int min;

    public Work() {
    }

    public Work(String id, String category, String type, int max, int min) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.max = max;
        this.min = min;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoty() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
