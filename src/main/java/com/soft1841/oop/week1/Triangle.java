package com.soft1841.oop.week1;

public class Triangle extends  Shape {

    public Triangle(){
        super();
    }

    public Triangle(double x, double y){
        super.x = x;
        super.y = y;
    }

    @Override
    public double getArea() {
        return this.x*this.y*0.5;
    }
}
