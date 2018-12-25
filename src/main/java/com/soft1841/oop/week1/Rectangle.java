package com.soft1841.oop.week1;

public class Rectangle extends  Shape {

    public Rectangle(){
        super();
    }

    public Rectangle(double x, double y){
        super.x = x;
        super.y = y;
    }

    @Override
    public double getArea() {
        return  this.x * this.y;
    }
}