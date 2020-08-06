package com.yuyiyun.YYdata.modular.news;

public class TestXUN {

    private int ffront;
    private int rear;
    private int usedSize;
    private  int[] elem;
    public TestXUN(int num){
        this.elem=new int[num];
        ffront=0;
        rear=0;
        usedSize=0;

    }


    public boolean isFull(){
        if ((this.rear+1)%this.elem.length==ffront){
            return true;
        }
        return false;
    }
    public boolean enQueue(int value){

        if (isFull()){
            return false;
        }

        this.elem[rear]=value;
        this.usedSize++;
        this.rear=(rear+1)%elem.length;
        return true;
    }

    public boolean deQueue(){

        return false;
    }



}
